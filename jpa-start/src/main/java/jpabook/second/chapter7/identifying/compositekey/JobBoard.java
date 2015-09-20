package jpabook.second.chapter7.identifying.compositekey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class JobBoard {

	@Id
	@GeneratedValue
	@Column(name="BOARD_ID")
	private Long id;
	private String title;
	@OneToOne(mappedBy="board")
	private JobBoardDetail boardDetail;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
