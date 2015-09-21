package jpabook.second.chapter8;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestFetchType {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logicPersist(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {
			tx.begin();
			logicLoad(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logicLoad(EntityManager em) {
		Member m = em.find(Member.class, 1L);
		System.out.println("TestFetchType.load member = " + m);
	}

	private static void logicPersist(EntityManager em) {
		Team team = new Team();
		team.setId(1L);
		team.setName("Teamname TA");
		em.persist(team);
		
		Task task = new Task();
		task.setId(1L);
		task.setName("TASK NAME A");
		em.persist(task);
		
		Member member = new Member();
		member.setId(1L);
		member.setName("Membername A");
		member.setTeam(team);
		member.setTask(task);
		em.persist(member);
	}
}
