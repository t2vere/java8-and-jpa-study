package jpabook.second.chapter7.mapsuperclz;

import javax.persistence.Entity;

@Entity(name="Member3")
public class Member extends BaseEntity {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
