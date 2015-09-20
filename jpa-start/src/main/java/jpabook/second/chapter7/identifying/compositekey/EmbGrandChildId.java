package jpabook.second.chapter7.identifying.compositekey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmbGrandChildId implements Serializable {

	private EmbChildId childId; //@MapsId("childId")로 매핑
	@Column(name="GRANDCHILD_ID")
	private String id;
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	public EmbChildId getChildId() {
		return childId;
	}
	public void setChildId(EmbChildId childId) {
		this.childId = childId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
