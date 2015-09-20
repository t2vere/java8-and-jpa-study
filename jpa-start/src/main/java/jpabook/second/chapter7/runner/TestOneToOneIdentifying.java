package jpabook.second.chapter7.runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.second.chapter7.identifying.compositekey.JobBoard;
import jpabook.second.chapter7.identifying.compositekey.JobBoardDetail;

public class TestOneToOneIdentifying {

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
		
		JobBoard board = new JobBoard();
		board.setTitle("제목1");
		em.persist(board);
		
		JobBoardDetail boardDetail = new JobBoardDetail();
		boardDetail.setContent("내용-1");
		boardDetail.setBoard(board);
		em.persist(boardDetail);
		
	}
}
