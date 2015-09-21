package jpabook.second.chapter8;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestCascade {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logicNoCascading(em);
			logicCascading(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	/**
	 * 영속성 전이 활성화된 경우
	 * @param em
	 */
	private static void logicCascading(EntityManager em) {
		
		Child child1 = new Child();
		Child child2 = new Child();
		
		Parent parent = new Parent();
		child1.setParent(parent);
		child2.setParent(parent);
		parent.getChildren().add(child1);
		parent.getChildren().add(child2);
		
		// 부모 저장시 연관된 자식들 저장
		em.persist(parent);
	}

	/**
	 * 영속성 전이 사용하지 않는 경우
	 * @param em
	 */
	private static void logicNoCascading(EntityManager em) {
		//jpa에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속상태여야 한다.
		Parent parent = new Parent();
		em.persist(parent);
		
		Child child1 = new Child();
		child1.setParent(parent);
		parent.getChildren().add(child1);
		em.persist(child1);
		
		Child child2 = new Child();
		child2.setParent(parent);
		parent.getChildren().add(child2);
		em.persist(child2);
	}
}
