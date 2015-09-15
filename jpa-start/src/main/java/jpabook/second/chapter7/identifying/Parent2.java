package jpabook.second.chapter7.identifying;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Parent2 {

	@EmbeddedId
	private ParentId2 parentId;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ParentId2 getParentId() {
		return parentId;
	}
	public void setParentId(ParentId2 parentId) {
		this.parentId = parentId;
	}
	
}
