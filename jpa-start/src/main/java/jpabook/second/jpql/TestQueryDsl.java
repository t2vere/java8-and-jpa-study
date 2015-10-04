package jpabook.second.jpql;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.QueryModifiers;
import com.mysema.query.SearchResults;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.Projections;
import com.mysema.query.types.QTuple;

public class TestQueryDsl {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logicQueryDsl(em);
			logicUsingDefaultInstanceAndWhereCondition(em);
			logicJoin(em);
			logicSubQuery(em);
			logicProjection(em);
			logicBatch(em);
			logicDynamicQuery(em);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logicDynamicQuery(EntityManager em) {
		SearchParam param = new SearchParam();
		param.setName("JPA Book");
		param.setPrice(10000L);
		
		QOrderItem item = QOrderItem.orderItem;
		BooleanBuilder builder = new BooleanBuilder();
		if (param.getName() != null) {
			builder.and(item.name.contains(param.getName()));
		}
		if (param.getPrice() != null) {
			builder.and(item.price.gt(param.getPrice()));
		}
		
		JPAQuery query = new JPAQuery();
		List<OrderItem> result = query.from(item)
				.where(builder)
				.list(item);
		
		// delegate methods : 쿼리 타입에 검색 조건 직접 정의
		result = query.from(item)
				.where(item.isExpensive(30_000L))
				.list(item);
	}

	private static void logicBatch(EntityManager em) {
		//jpql과 같이 영속성 컨텍스트 무시하고 db 직접 쿼리
		QOrderItem item = QOrderItem.orderItem;
		JPAUpdateClause updateClause = new JPAUpdateClause(em, item);
		long count = updateClause.where(item.name.eq("JPA Book"))
				.set(item.price, item.price.add(100))
				.execute();
		
		JPADeleteClause deleteClause = new JPADeleteClause(em, item);
		long count2 = deleteClause.where(item.name.eq("JPA Book"))
				.execute();
	}

	private static void logicProjection(EntityManager em) {
		// select 절에 조회 대상을 지정하는 것을 프로젝션이라고 함.
		// 프로젝션 대상이 하나
		JPAQuery query = new JPAQuery(em);
		QOrderItem item = QOrderItem.orderItem;
		List<String> result = query.from(item).list(item.name);
		
		for (String name : result) {
			System.out.println("name=" + name);
		}
		
		// 여러 컬럼 반환과 튜플
		List<Tuple> result2 = query.from(item).list(item.name, item.price);
		List<Tuple> result3 = query.from(item).list(new QTuple(item.name, item.price)); //같은 방식
		
		for (Tuple tuple : result2) {
			System.out.println("name=" + tuple.get(item.name));
			System.out.println("price=" + tuple.get(item.price));
		}
		
		// 빈 생성 : 쿼리 결과를 엔티티가 아닌 특정 객체로 받고 싶은 경우
		// 3가지 방법 : 프로퍼티 접근, 필드 직접 접근, 생성자 사용
		
		// 프로퍼티 접근
		List<ItemDTO> result4 = query.from(item).list(
				Projections.bean(ItemDTO.class, item.name.as("username"), item.price) // 쿼리 결과와 매핑할 프로퍼티 이름이 다를 경우 as 사용
		);
		
		// 필드 직접 접근
		result4 = query.from(item).list(
				Projections.fields(ItemDTO.class, item.name.as("username"), item.price)
		);
		
		// 생성자 사용
		result4 = query.from(item).list(
				Projections.constructor(ItemDTO.class, item.name, item.price)
		);
		
		// distinct
		query.distinct().from(item).list(item);
		
		
	}

	private static void logicSubQuery(EntityManager em) {
		QOrderItem item = QOrderItem.orderItem;
		QOrderItem itemSub = new QOrderItem("itemSub");
		
		JPAQuery query = new JPAQuery(em);
		// 결과가 하나면 unique(), 여러건이면 list()
		query.from(item)
			.where(item.price.eq(new JPASubQuery().from(itemSub).unique(itemSub.price.max())))
			.list(item);
		
		// 여러건의 서브쿼리 사용하는 방법
		query.from(item)
			.where(item.in(new JPAQuery().from(itemSub)
					.where(item.name.eq(itemSub.name))
					.list(itemSub)))
			.list(item);
	}

	private static void logicJoin(EntityManager em) {
		// join(조인대상, 별칭으로 사용할 쿼리 타입)
		QMember member = QMember.member;
		QOrder order = QOrder.order;
		QOrderItem orderItem = QOrderItem.orderItem;
		
		JPAQuery query = new JPAQuery(em);
		query.from(order)
			.join(order.member, member)
			.leftJoin(order.orderItems, orderItem)
			.list(order);
		
		query.from(order)
			.leftJoin(order.orderItems, orderItem)
			.on(orderItem.count.gt(2))
			.list(order);
		
		query.from(order)
			.innerJoin(order.member, member).fetch()
			.leftJoin(order.orderItems, orderItem).fetch()
			.list(order);
		
		query.from(order, member)
			.where(order.member.eq(member))
			.list(order);
		
	}

	private static void logicUsingDefaultInstanceAndWhereCondition(EntityManager em) {
		JPAQuery query = new JPAQuery(em);
		List<Member> members = query.from(QMember.member) // import static 문으로 더 간결하게 작성 가능
				.where(
					QMember.member.username.eq("회원1")
					.and(QMember.member.id.gt(0))
					.and(QMember.member.id.between(1, 100))
					.and(QMember.member.username.contains("회원")) // name like %회원%
					.and(QMember.member.username.startsWith("회")) // name like '회%'
				).orderBy(QMember.member.username.desc())
				.list(QMember.member); // list() 혹은 uniqueResult(), singleResult() 사용
		
		System.out.println(members);
		
		QMember qmember = QMember.member;
		List<Member> members2 = query.from(qmember)
				.orderBy(qmember.username.desc(), qmember.id.asc())
				.offset(10).limit(20) // paging
				.list(qmember);
		
		QueryModifiers modifiers = new QueryModifiers(20L, 10L); //limit, offset
		members2 = query.from(qmember)
				.orderBy(qmember.username.desc(), qmember.id.asc())
				.restrict(modifiers) // paging 2
				.list(qmember);
		
		SearchResults<Member> memberResults = query.from(qmember)
				.orderBy(qmember.username.desc(), qmember.id.asc())
				.restrict(modifiers) // paging 2
				.listResults(qmember); //전체 데이터 조회를 위한 count 쿼리를 한번 더 실행
		
		long total = memberResults.getTotal(); //전체 데이터 수
		long limit = memberResults.getLimit();
		long offset = memberResults.getOffset();
		List<Member> member3 = memberResults.getResults(); //조회된 데이터
		
		List<Member> members4 = query.from(qmember)
				.groupBy(qmember.username)
				.having(qmember.id.gt(1))
				.list(qmember);
	}

	private static void logicQueryDsl(EntityManager em) {
		Member newMember = new Member();
		newMember.setUsername("회원1");
		em.persist(newMember);
		
		JPAQuery query = new JPAQuery(em);
		QMember qMember = new QMember("mem9"); //jqpl alias
		//qMember = QMember.member; //기본 인스턴스 사용
		
		List<Member> members = query.from(qMember)
				.where(qMember.username.eq("회원1"))
				.orderBy(qMember.username.desc())
				.list(qMember);
		System.out.println(members);
	}
}
