package jpabook.second;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestEntityMapping {

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
		Team team = new Team();
		team.setName("team-x2");
		em.persist(team);
		
		DashUser user = new DashUser();
		user.setUsername("username");
		user.setTeam(team);
		em.persist(user);
	}
}
