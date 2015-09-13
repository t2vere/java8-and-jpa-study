package jpabook.second.runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.second.Locker;
import jpabook.second.Member2;

/**
 * one-to-one
 * 
 * @author greygoose
 *
 */
public class TestEntityMappingOneToOne {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logic(em);
			logic2(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logic2(EntityManager em) {
		Member2 m2 = em.find(Member2.class, 2L);
		if (m2 != null) {
			System.out.println("m2=" + m2.getLocker().getName());
			System.out.println("m2's locker->username =" + m2.getLocker().getMember().getUsername());
		}
	}

	private static void logic(EntityManager em) {
		Locker locker = new Locker();
		locker.setName("LOCKER-A");
		em.persist(locker);
		
		Member2 member = new Member2();
		member.setUsername("hyeon");
		member.setLocker(locker);
		em.persist(member);
		
		System.out.println("member2's locker =" + member.getLocker().getName());
		System.out.println("member2's locker->username =" + member.getLocker().getMember().getUsername());
	}
}
