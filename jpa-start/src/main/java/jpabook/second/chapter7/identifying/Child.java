package jpabook.second.chapter7.identifying;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class Child {

	@Id
	private String id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="PARENT_ID1", referencedColumnName="PARENT_ID1")
		, @JoinColumn(name="PARENT_ID2", referencedColumnName="PARENT_ID2") // 속성값 같으면 refColName 생략 가능
	})
	private Parent parent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
}
