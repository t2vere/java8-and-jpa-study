package jpabook.second.chapter7.identifying.compositekey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
@IdClass(value=JobGrandChildId.class)
public class JobGrandChild {

	@Id
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="PARENT_ID", referencedColumnName="PARENT_ID")
		, @JoinColumn(name="CHILD_ID", referencedColumnName="CHILD_ID")
	})
	private JobChild child;
	
	@Id
	@Column(name="GRANDCHILD_ID")
	private String id;
	private String name;
	public JobChild getChild() {
		return child;
	}
	public void setChild(JobChild child) {
		this.child = child;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
