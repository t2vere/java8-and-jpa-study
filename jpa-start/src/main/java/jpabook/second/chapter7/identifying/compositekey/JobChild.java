package jpabook.second.chapter7.identifying.compositekey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(value=JobChildId.class)
public class JobChild {

	@Id
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private JobParent parent;
	
	@Id
	@Column(name="CHILD_ID")
	private String childId;
	private String name;
	
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JobParent getParent() {
		return parent;
	}
	public void setParent(JobParent parent) {
		this.parent = parent;
	}
}
