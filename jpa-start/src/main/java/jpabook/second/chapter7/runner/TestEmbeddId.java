package jpabook.second.chapter7.runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.second.chapter7.identifying.Parent2;
import jpabook.second.chapter7.identifying.ParentId2;

public class TestEmbeddId {

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
		
		Parent2 parent = new Parent2();
		ParentId2 pid2 = new ParentId2("myid100", "myid200");
		parent.setParentId(pid2);
		parent.setName("parent name, embeddid");
		em.persist(parent);
		
		
		ParentId2 pid3 = new ParentId2("myid100", "myid200");
		Parent2 parent3 = em.find(Parent2.class, pid3);
		System.out.println("parent 3 =" + parent3.getName());
		
	}
}
