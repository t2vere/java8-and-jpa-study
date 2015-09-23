package jpabook.second.chapter9;

import javax.persistence.Embeddable;

@Embeddable
public class Zipcode {

	String zip;
	String plusFour;

	public Zipcode() {
		// TODO Auto-generated constructor stub
	}

	public Zipcode(String zip, String plusFour) {
		this.zip = zip;
		this.plusFour = plusFour;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPlusFour() {
		return plusFour;
	}

	public void setPlusFour(String plusFour) {
		this.plusFour = plusFour;
	}
}
