package jpabook.second.jpql;

import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class TestJpql {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logicSaveDummy(em);
			logicJpql(em);
			logicJpqlPathExpression(em);
			logicJpqlSubQuery(em);
			logicJpqlConditionalExpression(em);
			logicJpqlPolymorphism(em);
			logicJpqlUserDefinedFunction(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logicJpqlUserDefinedFunction(EntityManager em) {
		//jpa 2.1부터 지원
	}

	private static void logicJpqlPolymorphism(EntityManager em) {
		// 다형성 쿼리
		// 부모를 조회하면 자식 entity도 함께 조회
		
		// TYPE
		// select i from Item i where type(i) in (Book, Movie)
		
		// TREAT (JPA 2.1) -> type casting과 비슷
		// jpa표준은 from ,where 에서만 사용가능하지만 hibernate는 select에서도 가능
		// select i from Item i where treat(i as Book).author = 'kim'
	}

	private static void logicJpqlConditionalExpression(EntityManager em) {
		// 조건식
		// 날짜
		// DATE {d 'yyyy-mm-dd'}
		// TIME {t 'hh:mm:ss'}
		// DATETIME {ts 'yyyy-mm-dd hh:mm:ss.f'}
		// TRUE, FALSE
		// Enum: full package path 사용
		
		//between (a, b 값 포함)
		// select m from Member m where m.age between 10 and 20 
		
		// like, escape문자
		// %(any char or null), _(any char)
		
		// 컬렉션 식 (컬렉션은 컬렉션 식만 사용 가능)
		// is [not] empty
		// 컬렉션 멤버 식 : a member of collection
		// select t from Team t where :memberParam member of t.members
		
		// 날짜함수
		// CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP
		// 하이버네이트에선 날짜 타입에서 연월일시분초 추출 가능
		
		// CASE
		// 기본CASE, 심플CASE(조건식 사용불가), COALESCE(nvl역할), NULLIF(두 값 같으면 NULL, 다르면 첫번째 PARAM리턴)
	}

	private static void logicJpqlSubQuery(EntityManager em) {
		//where, having에서만 사용 가능 (select, from에서는 불가)
		// select m from Member m where m.age > (select avg(m2.age) from Member m2)
		// select m from Member m where (select count(o) from order o where m = o.member) > 0
		// select m from Member m where m.orders.size > 0
		
		// exists
		// select m from Member m where exists (select t from m.team t where t.name = 'T')

		// all/any/some
		// select o from Order o where o.orderAmount > ALL (select p.stockAmount from  Product p)
		// select m from Member m where m.team = ANY(select t from Team t)
		
		// IN (subquery가 아닌곳에서도 사용 가능)
		// select t from Team t where t IN (select t2 from Team t2 join t2.members m2 where m2.age >= 20)
	}

	private static void logicJpqlPathExpression(EntityManager em) {
		//////////path expression
		// state field: 단순히 값 저장
		// assocication field: 연관관계를 위한 필드, 임베디드 타입 포함
		// 단일 값 연관 필드 : OTO, MTO -> 묵시적 join
		// 컬렉션 값 연관 필드 : OTM, MTM
		//ex
		//select o.member.team from Order o where o.product.name = 'PA' and o.address.city = 'Seoul'
		
		// 컬렉션 값에서는 경로 탐색 불가 -> join을 사용해서 별칭 획득 후 사용 가능
		
		//컬렉션에서는 크기를 구할 수 있는 size 사용 가능 -> sql의 count function 변환
		// 묵시적 조인은 파악이 어렵기 때문에 명시적 조인 사용 권장 (성능이슈 있는 경우)
	}

	private static void logicSaveDummy(EntityManager em) {
		Team t = new Team();
		t.setName("ttt");
		em.persist(t);

		IntStream.range(0, 100).mapToObj((idx) -> {

			Member m = new Member();
			m.setUsername("kim");
			m.setTeam(t);
			return m;
		}).forEach(m -> em.persist(m));

		IntStream.range(0, 200).mapToObj((idx) -> {
			Member m = new Member();
			m.setUsername("kim");
			return m;
		}).forEach(m -> em.persist(m));
	}

	private static void logicJpql(EntityManager em) {
		String jpql = "select m from Member10 as m where m.username = 'kim'";
		// TypedQuery<Member> typedQuery = em.createQuery(jpql, Member.class);
		List<Member> members = em.createQuery(jpql, Member.class).getResultList(); // 결과가
																					// 없으면
																					// empty
																					// collection
																					// 반환

		System.out.println("member count: " + members.size());

		Query query = em.createQuery("select m.id, m.username from Member10 as m where m.username = 'kim'");
		for (Object o : query.getResultList()) {
			Object[] result = (Object[]) o;
			System.out.print("id: " + result[0]);
			System.out.println("\tusername: " + result[1]);
		}

		TypedQuery<Long> oneQuery = em.createQuery("select m.id from Member10 as m where m.id = 2", Long.class);
		System.out.println("first id: " + oneQuery.getSingleResult());

		try {
			oneQuery = em.createQuery("select m.id from Member10 as m where m.id = 1999", Long.class); // 예외
																										// 발생
			System.out.println("id: " + oneQuery.getSingleResult());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			oneQuery = em.createQuery("select m.id from Member10 as m where m.id > 0", Long.class); // 예외
																									// 발생
			System.out.println("id: " + oneQuery.getSingleResult());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String usernameParam = "kim";
		TypedQuery<Member> query2 = em.createQuery("select m from Member10 m where m.username = :username",
				Member.class);
		query2.setParameter("username", usernameParam); // sql-injection 방지,
														// db에서 쿼리 파싱 결과 재사용
		List<Member> list2 = query2.getResultList();
		System.out.println("list2: " + list2.size());

		List<Member> list3 = em.createQuery("select m from Member10 m where m.username = ?1", Member.class)
				.setParameter(1, usernameParam).getResultList();
		System.out.println("list3: " + list3.size());

		// 엔티티 프로젝션 -> 영속성 컨텍스트에서 관리
		// 임베디드 타입 프로젝션 -> 엔티티를 통해서 조회
		// 스칼라 타입 프로젝션
		// distinct, avg 등 통계 함수 가능
		// 여러 엔티티에서 여러 값 조회 -> Query 클래스만 사용 가능
		List<Object[]> list4 = em.createQuery("select m.id, m.username from Member10 m").getResultList();
		for (Object[] row : list4) {
			System.out.print("object[]\tid=" + (Long) row[0]);
			System.out.println("\t\tname=" + (String) row[1]);
		}

		// 여러 값 조회시 DTO 사용
		// full package path
		// 순서와 타입 일치하는 생성자
		List<MemberDTO> list5 = em
				.createQuery("select new jpabook.second.jpql.MemberDTO(m.id, m.username) from Member10 m",
						MemberDTO.class)
				.getResultList();
		for (MemberDTO dto : list5) {
			System.out.print("dto\tid=" + dto.getId());
			System.out.println("\t\tname=" + dto.getUsername());
		}

		// paging api
		TypedQuery<Member> query6 = em.createQuery("select m from Member10 m where m.username = ?1", Member.class)
				.setParameter(1, usernameParam);

		query6.setFirstResult(10); // 시작 위치
		query6.setMaxResults(20); // 건수
		List<Member> list6 = query6.getResultList();
		System.out.println("list6: " + list6.size());

		// 집합과 정렬
		// count, max, min, avg, sum

		// group by, having

		// order by

		// join
		String teamName = "ttt";
		List<Member> list7 = em
				.createQuery("select m from Member10 m inner join m.team t where t.name = :teamName", Member.class)
				.setParameter("teamName", teamName).getResultList();
		System.out.println("list7: " + list7.size());

		List<Object[]> list8 = em.createQuery("select m, t from Member10 m inner join m.team t").getResultList();
		for (Object o : list8) {
			Object[] result = (Object[]) o;
			System.out.print("member: " + result[0]);
			System.out.println("\tteam: " + result[1]);
		}
		System.out.println("list8: " + list8.size());

		// outer join
		// 컬렉션 조인
		List<Member> list9 = em.createQuery("select m from Member10 m left join m.team t", Member.class)
				.getResultList();
		System.out.println("list9: " + list9.size());

		// theta join
		Long val10 = em.createQuery("select count(m) from Member10 m, Team10 t", Long.class).getSingleResult();
		System.out.println("val10: " + val10);

		// join on (v2.1 이상)
		// join 대상 필터링하고 조인
		// inner join의 on은 where과 같으므로 보통 outer join에서만 사용
		List<Object[]> list11 = em.createQuery("select m, t from Member10 m left join m.team t on t.name='ttt'")
				.getResultList();
		for (Object o : list11) {
			Object[] result = (Object[]) o;
			System.out.print("member: " + result[0]);
			System.out.println("\tteam: " + result[1]);
		}
		System.out.println("list11: " + list11.size());

		// *******중요 : fetch join
		
		// jpql에서 성능 최적화를 위해 제공하는 기능
		// (심지어 글로벌 로딩이 lazy설정이어도) 연관된 엔티티나 컬렉션을 한번에 같이 조회하는 기능 -> lazy 로딩 발생 안함, 실제 엔티티이기 때문에 준영속 상태에서도 연관 객체 조회 가능
		// jpql에서는 결과 반환시 연관관계까지 고려하지 않음, 단지 select절에 지정한 엔티티만 조회
		
		// 별칭 사용 불가 (하이버네이트에서는 가능, 구현체마다 다름)
		// paging api 사용 불가
		// 둘 이상의 컬렉션 페치 불가
		// 여러 테이블 조인해서 새로운 타입의 결과를 반환하기 위해선 dto 생성이 더 효과적
		
		//entity fetch join
		List<Member> list12 = em.createQuery("select m from Member10 m join fetch m.team", Member.class)
				.getResultList();
		System.out.println("list12: " + list12.size());
		
		//collection fetch join
		List<Team> list13 = em.createQuery("select t from Team10 t join fetch t.members", Team.class)
				.getResultList();
		System.out.println("list13: " + list13.size());
		
		//collection fetch join and distinct
		List<Team> list14 = em.createQuery("select distinct t from Team10 t join fetch t.members", Team.class)
				.getResultList();
		System.out.println("list14: " + list14.size());
		
	}
}
