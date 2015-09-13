package jpabook.second.runner;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.second.Member2;
import jpabook.second.Product;

/**
 * many-to-many
 * 다대다 관계를 일대다, 다대일 관계로 풀어내기 위해 연결 테이블을 만들때 식별자를 구성하는 방법 선택
 * 1) 식별 관계
 * 2) 비식별 관계 : 새로운 식별자 추가 (단순하고 편리하기 때문에 추천)
 * @author greygoose
 *
 */
public class TestEntityMappingManyToMany {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logic(em);
			logicFind(em);
			lobicFindInverse(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void lobicFindInverse(EntityManager em) {
		System.out.println("-----lobicFindInverse----");
		
		Product product = em.find(Product.class, "PA");
		List<Member2> members = product.getMembers();
		
		for (Member2 m : members) {
			System.out.println("member=" + m.getUsername());
		}
	}

	private static void logicFind(EntityManager em) {
		Member2 member = em.find(Member2.class, 1L);
		List<Product> products = member.getProducts();
		
		for (Product p : products) {
			System.out.println("product.name = " + p.getName());
		}
	}

	private static void logic(EntityManager em) {
		Product productA = new Product();
		productA.setId("PA");
		productA.setName("상품A");
		em.persist(productA);
		
		Member2 member1 = new Member2();
		member1.setUsername("회원-1");
		//member1.getProducts().add(productA);
		member1.addProduct(productA);
		em.persist(member1);
	}
	
}
