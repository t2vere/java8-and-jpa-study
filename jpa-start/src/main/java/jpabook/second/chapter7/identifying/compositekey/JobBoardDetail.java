package jpabook.second.chapter7.identifying.compositekey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class JobBoardDetail {

	@Id
	private Long boardId;
	
	@MapsId // boarddetail.boardId 매핑
	@OneToOne
	@JoinColumn(name="BOARD_ID")
	private JobBoard board;
	
	private String content;
	
	public Long getBoardId() {
		return boardId;
	}
	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}
	public JobBoard getBoard() {
		return board;
	}
	public void setBoard(JobBoard board) {
		this.board = board;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
