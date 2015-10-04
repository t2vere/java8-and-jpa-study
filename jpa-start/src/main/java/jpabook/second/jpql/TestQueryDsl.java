package jpabook.second.jpql;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.mysema.query.QueryModifiers;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;

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
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logicSubQuery(EntityManager em) {
		QOrderItem item = QOrderItem.orderItem;
		QOrderItem itemSub = new QOrderItem("itemSub");
		
		JPAQuery query = new JPAQuery(em);
		query.from(item)
			.where(item.price.eq(new JPASubQuery().from(itemSub).unique(itemSub.price.max())))
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
