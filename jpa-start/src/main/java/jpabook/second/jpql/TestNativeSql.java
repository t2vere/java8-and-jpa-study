package jpabook.second.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class TestNativeSql {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logicSelect(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logicSelect(EntityManager em) {
		String sql = "SELECT ID, AGE, NAME, TEAM_ID FROM MEMBER WHERE AGE > ?";
		
		// jpa는 공식적으로 native sql에서 위치 기반 파라미터만 지원
		// hibernate는 이름 기반 파라미터도 사용 가능 (hibernate 구현체 사용시) 
		// TypeQuery가 아닌 Query 리턴되는 이유는 jpa 1.0에서 api 규약이 정해져버렸기 때문.
		Query nativeQuery = em.createNativeQuery(sql, Member.class)
				.setParameter(1, 20);
		// entity 조회
		List<Member> result = nativeQuery.getResultList();
		
		String sql2 = "SELECT ID, AGE, NAME, TEAM_ID FROM MEMBER WHERE AGE > ?";
		Query nativeQuery2 = em.createNativeQuery(sql2).setParameter(1, 10);
		// 값 조회
		// 스칼라 값들을 조회했을 뿐이므로 영속성 컨텍스트에서 결과가 관리되지 않음
		List<Object[]> result2 = nativeQuery2.getResultList();
		for (Object[] row : result2) {
			System.out.println(row);
		}
		
		String sql3 = "SELECT M.ID, AGE, NAME, TEAM_ID, I.ORDER_COUNT"
				+ " FROM MEMBER M"
				+ " LEFT JOIN"
				+ " 	(SELECT IM.ID, COUNT(*) AS ORDER_COUNT"
				+ " 	FROM ORDERS O, MEMBER IM"
				+ " 	WHERE O.MEMBER_ID = IM.ID) I"
				+ " ON M.ID = I.ID";
		
		// 결과 매핑 사용
		// 엔티티와 스칼라 값을 함께 조회
		// Member 엔티티에 결과 매핑 정의 필요
		
		// 결과 매핑 어노테이션
		// @SqlResultSetMapping : 결과 매핑
		// @EntityResult : 결과로 사용할 엔티티 클래스 지정
		// @FieldResult : 결과를 받을 필드 지정
		// @ColumnResult : 결과 컬럼 지정
		Query nativeQuery3 = em.createNativeQuery(sql3, "memberWithOrderCount");
		
		List<Object[]> result3 = nativeQuery3.getResultList();
		for (Object[] row : result3) {
			Member member = (Member) row[0];
			Integer orderCount = (Integer) row[1];
		}
		
		//named native sql
		//jpql named 쿼리와 같은 createNamedQuery 메소드 사용 --> TypeQuery 사용 가능
		TypedQuery<Member> nativeQuery4 = em.createNamedQuery("Member.memberSQL", Member.class);
		
		// stored procedure (jpa 2.1 부터 지원)
		// 생략
		
		//벌크 연산
		// 영속성 컨텍스트를 무시하고 db 직접 쿼리하기 때문에 주의 필요
		/* 해결 방안
			1) em.refresh() 사용 : db에서 다시 조회
			2) 벌크 연산 먼저 실행(권장) : jpa+jdbc 함께 사용시에도 유용함
			3) 벌크 연산 수행 후 영속성 컨텍스트 초기화
		*/
		String qlString = "update Product p set p.price = p.price * 1.1 where p.stockAmount < :stockAmount";
		int resultCount= em.createQuery(qlString)
				.setParameter("stockAmount", 10)
				.executeUpdate();
		
		qlString = "delete from Product p where p.price < :price";
		resultCount = em.createQuery(qlString)
					.setParameter("price", 100)
					.executeUpdate();
		
		// hibernate에 한해서 insert 벌크 연산 지원 (jpa 표준 아님)
		qlString = "insert into ProductTemp(id, name, price, stockAmount) select p.id, p.name, p.price, p.stockAmount from Product p where p.price < :price";
		resultCount = em.createQuery(qlString)
				.setParameter("price", 100)
				.executeUpdate();
		
		// flush mode : 영속성 컨텍스트의 변경 내역을 db에 동기화
		// em.flush() 직접호출하거나 commit 직전, query 실행 직전에 자동으로 호출
		// em.setFlushMode(FlushModeType) 으로 조정 가능
		// flushmode commit은 너무 자주 flush가 일어나는 상황에 사용하여 성능 최적화 (flush 횟수 줄임)
		// jdbc 호출 직전에도 em.flush() 호출해서 영속성 컨텍스트 내용을 db에 동기화 할 것!
		
	}

}
