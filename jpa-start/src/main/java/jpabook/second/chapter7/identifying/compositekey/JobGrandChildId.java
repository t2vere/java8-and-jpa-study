package jpabook.second.chapter7.identifying.compositekey;

import java.io.Serializable;

public class JobGrandChildId implements Serializable {

	private JobChild child;
	private String id;
	
	public JobGrandChildId() {
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}

