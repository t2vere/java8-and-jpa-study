package jpabook.second.chapter7.identifying.compositekey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class MapsGrandChild {

	@EmbeddedId
	private EmbGrandChildId id;
	
	@MapsId("childId")
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="PARENT_ID", referencedColumnName="PARENT_ID")
		, @JoinColumn(name="CHILD_ID", referencedColumnName="CHILD_ID")
	})
	private MapsChild child;
	private String name;
	
	public EmbGrandChildId getId() {
		return id;
	}
	public void setId(EmbGrandChildId id) {
		this.id = id;
	}
	public MapsChild getChild() {
		return child;
	}
	public void setChild(MapsChild child) {
		this.child = child;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
