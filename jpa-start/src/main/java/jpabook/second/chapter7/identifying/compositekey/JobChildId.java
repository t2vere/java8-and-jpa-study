package jpabook.second.chapter7.identifying.compositekey;

import java.io.Serializable;

public class JobChildId implements Serializable {

	private String parent;
	private String childId;
	
	public JobChildId() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
