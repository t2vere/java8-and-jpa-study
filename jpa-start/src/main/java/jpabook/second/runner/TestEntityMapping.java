package jpabook.second.runner;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.second.DashUser;
import jpabook.second.Team;

public class TestEntityMapping {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logic(em);
			//logicJpql(em);
			//logicUpdateRelation(em);
			logicBiDirection(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();

	}

	private static void logicBiDirection(EntityManager em) {
		Team team = em.find(Team.class, 1);
		
		List<DashUser> users = team.getDashUsers();
		System.out.println(users.size());
		for (DashUser user : users) {
			System.out.println("team.user.name = " + user.getUsername());
		}
	}

	private static void logicUpdateRelation(EntityManager em) {
		Team team2 = new Team();
		team2.setName("팀2");
		em.persist(team2);
		
		DashUser user = em.find(DashUser.class, 1);
		user.setTeam(team2);
		System.out.println(user.getTeam().getName());
		//em.remove(team2); // 외래키 제약조건으로 인한 오류 발생, 객체가 먼저 remove되어야 함
		
		//user.setTeam(null); // team 설정 제거
		//System.out.println(user.getTeam() == null);
		//em.remove(team2);
	}

	private static void logicJpql(EntityManager em) {
		String jpql = "select u from DashUser u join u.team t where t.name=:teamName";
		List<DashUser> list = em.createQuery(jpql, DashUser.class)
				.setParameter("teamName", "team-x2").getResultList();
		int i = 0;
		for (DashUser user : list) {
			System.out.println("[query_" + (++i) + "] member.username=" + user.getUsername());
		}
	}

	private static void logic(EntityManager em) {
		Team team = new Team();
		team.setName("team-x2");
		em.persist(team);
		
		DashUser user = new DashUser();
		user.setUsername("username-1");
		user.setTeam(team);
		em.persist(user);

		DashUser storedUser = em.find(DashUser.class, 1);
		System.out.println("find.user=" + storedUser.getTeam().getName());
		//storedUser.getTeam().getDashUsers().add(storedUser); //user entitiy에서 편의메소드 제공시 호출할 필요 없음
		System.out.println("find.user=" + storedUser.getTeam().getDashUsers());
	}
}
