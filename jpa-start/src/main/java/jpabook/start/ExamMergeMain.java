package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
	
	public static void main(String[] args) {
		Member member = createMember("memberA","회원1", 30);
		member.setUsername("변경1");
		mergeMember(member);
	}

	private static void mergeMember(Member member) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Member mergeMember = em.merge(member);
		tx.commit();
		
		System.out.println(member);
		System.out.println(mergeMember);
		System.out.println(em.contains(member));
		System.out.println(em.contains(mergeMember));
		
		em.close();
	}

	private static Member createMember(String id, String name, Integer age) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		Member member = new Member();
		member.setId(id);
		member.setUsername(name);
		member.setAge(age);
		em.persist(member);
		
		tx.commit();
		em.close();
		
		return member;
	}
}
