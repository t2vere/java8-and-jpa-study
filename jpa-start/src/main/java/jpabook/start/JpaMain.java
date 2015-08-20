package jpabook.start;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

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

		String id = "id1";
		Member member = new Member();
		member.setId(id);
		member.setUsername("Ã¶¼ö");
		member.setAge(34);

		em.persist(member);
		//member.setId(null);
		member.setAge(40);
		Member findMember = em.find(Member.class, id);
		System.out.println("findMember===");
		System.out.println(findMember);
		Member anotherMember = em.find(Member.class, id);
		System.out.println(anotherMember == findMember);

		List<Member> members = em.createQuery("select m from Member m",
				Member.class).getResultList();
		System.out.println("members===");
		System.out.println(members.size());

		em.remove(member);
	}
}
