package jpabook.second.chapter9;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
// �ĺ��ڰ� ����
// ���� �ֱ⸦ ��ƼƼ�� ����
// �������� �ʴ°��� ���� -> immutable�� ����
public class Address {

	@Column(name="city") // ������ �÷� ���� ����
	private String city;
	private String street;
	private String state;
	private String zipcode2;
	
	@Embedded Zipcode zipcode; // �Ӻ���� Ÿ�� ���� 
	
	protected Address() { //�⺻ �����ڴ� �ʼ�
		
	}
	
	// �� Ÿ�����μ� ���, �����ڷθ� ����� ������ �� ������
	public Address(String city, String street, String state, Zipcode zipcode, String zipcode2) {
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
