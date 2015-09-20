package jpabook.second.chapter7.identifying.compositekey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class MapsChild {

	@EmbeddedId
	private EmbChildId id;
	
	@MapsId("parentId")
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private JobParent parent;
	private String name;

	public EmbChildId getId() {
		return id;
	}

	public void setId(EmbChildId id) {
		this.id = id;
	}

	public JobParent getParent() {
		return parent;
	}

	public void setParent(JobParent parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
