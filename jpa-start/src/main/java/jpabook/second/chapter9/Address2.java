package jpabook.second.chapter9;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address2 {

	@Id @GeneratedValue
	private Long id;
	@Column(name="city") // ������ �÷� ���� ����
	private String city;
	private String street;
	private String state;
	private String zipcode2;
	
	@Embedded Zipcode zipcode; // �Ӻ���� Ÿ�� ���� 
	
	protected Address2() { //�⺻ �����ڴ� �ʼ�
		
	}
	
	// �� Ÿ�����μ� ���, �����ڷθ� ����� ������ �� ������
	public Address2(String city, String street, String state, Zipcode zipcode, String zipcode2) {
		this.city = city;
		this.street = street;
		this.state = state;
		this.zipcode2 = zipcode2;
		this.zipcode = zipcode;
	}
	
	public String getCity() {
		return city;
	}
	public String getStreet() {
		return street;
	}
	public String getState() {
		return state;
	}
	public Zipcode getZipcode() {
		return zipcode;
	}
	public String getZipcode2() {
		return zipcode2;
	}

	public Long getId() {
		return id;
	}
	
	/* �����ڴ� �����ϰ� �����ڴ� ������ ���� -> �Һ���ü
	 * 
	 * public void setCity(String city) {
		this.city = city;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZipcode(Zipcode zipcode) {
		this.zipcode = zipcode;
	}
	public void setZipcode2(String zipcode2) {
		this.zipcode2 = zipcode2;
	}*/
}
