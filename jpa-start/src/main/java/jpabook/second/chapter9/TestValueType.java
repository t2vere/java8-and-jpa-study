package jpabook.second.chapter9;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestValueType {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logicPersist(em);
			logicUpdate(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logicUpdate(EntityManager em) {
		Member member = em.find(Member.class, 1L);
		
		//1. 임베디드 값 타입 수정
		member.setHomeAddress(new Address("새로운 도시", "신도시", "Korea", new Zipcode("111", "5667"), "123-123"));
		
		//2. 기본값 타입 컬렉션 수정
		Set<String> favFoods = member.getFavoriteFoods();
		favFoods.remove("pizza");
		favFoods.add("cookie");
		
		//3. 임베디드 값 타입 컬렉션 수정
		List<Address> addressHistory = member.getAddressHistory();
		addressHistory.remove(new Address("Seoul2", "Teheranro2", "Korea", new Zipcode("112", "7777"), "234-513"));
		addressHistory.add(new Address("new Seoul2", "new Teheranro2", "new Korea", new Zipcode("112", "7777"), "234-513"));
	}

	private static void logicPersist(EntityManager em) {
		Member member = new Member();
		
		//임베디드 값 타입
		member.setHomeAddress(new Address("Seoul", "Teheranro", "Korea", new Zipcode("111", "5667"), "123-123"));
		
		//기본값 타입 컬렉션
		member.getFavoriteFoods().add("apple");
		member.getFavoriteFoods().add("pizza");
		member.getFavoriteFoods().add("pasta");
		
		//임베디드 값 타입 컬렉션
		member.getAddressHistory().add(new Address("Seoul2", "Teheranro2", "Korea", new Zipcode("112", "7777"), "234-513"));
		member.getAddressHistory().add(new Address("Seoul3", "Teheranro3", "Korea", new Zipcode("234", "5555"), "234-555"));
		
		em.persist(member);
	}
}
