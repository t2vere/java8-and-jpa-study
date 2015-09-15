package jpabook.second.chapter7.runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.second.chapter7.identifying.Parent;
import jpabook.second.chapter7.identifying.ParentId;

public class TestIdClass {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logic(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logic(EntityManager em) {
		
		Parent parent = new Parent();
		parent.setId1("myid1");
		parent.setId2("myid2");
		parent.setName("parentname");
		em.persist(parent); // 호출시 엔티티 등록하기 직전 내부에서 식별자 클래스 생성하고 영속성 컨텍스트의 키로 사용
		
		
		ParentId parentId = new ParentId("myid1", "myid3");
		Parent p2 = em.find(Parent.class, parentId);
		System.out.println("parent2=" + p2);
		
		ParentId parentId2 = new ParentId("myid1", "myid2");
		Parent p3 = em.find(Parent.class, parentId2);
		System.out.println("parent3=" + p3.getName());
		
	}
}
