package jpabook.second;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestDrive {

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
		for (int i = 0; i < 100; i++) {
			DashUser user = new DashUser();
			// user.setId("id1");
			user.setUsername("hyeon_" + i);
			user.setAge(i + 23);
			if (i % 10 == 0) {
				user.setRoleType(RoleType.ADMIN);
			} else {
				 user.setRoleType(RoleType.USER);
			}
			em.persist(user);
			
			System.out.println("user-id=" + user.getId());
		}
	}
}
