package jpabook.second.chapter9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name="Member09")
public class Member {

	@Id @GeneratedValue
	@Column(name="MEMBER_ID")
	private Long id;
	private String name;
	
	@Embedded
	Period workPeriod; // 임베디드 타입 포함
	
	@Embedded
	Address homeAddress; // 임베디드 타입 포함
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="city", column=@Column(name="COMPANY_CITY"))
		, @AttributeOverride(name="street", column=@Column(name="COMPANY_STREET"))
		, @AttributeOverride(name="state", column=@Column(name="COMPANY_STATE"))
		, @AttributeOverride(name="zipcode.plusFour", column=@Column(name="COMPANY_ZIP_PLUSFOUR"))
		, @AttributeOverride(name="zipcode.zip", column=@Column(name="COMPANY_ZIP_ZIP"))
		, @AttributeOverride(name="zipcode2", column=@Column(name="COMPANY_ZIPCODE2"))
	})
	Address companyAddress; // 임베디드 타입 포함, 
	
	@Embedded
	PhoneNumber phoneNumber; // 임베디드 타입 포함
	
	// 값 타입 컬렉션
	// 구현체에 따라 다르지만... 사용하는 컬렉션이나 여러 조건에 따라 기본키 식별 못할 가능성 있음
	//->모두 삭제하고 다시 저장하는 최악의 시나리오 고려하면서 사용
	@ElementCollection(fetch=FetchType.LAZY) // lazy가 기본값 
	@CollectionTable(name="FAVORITE_FOODS", joinColumns=@JoinColumn(name="MEMBER_ID"))
	@Column(name="FOOD_NAME")
	private Set<String> favoriteFoods = new HashSet<String>();
	
	@ElementCollection
	@CollectionTable(name="ADDRESS", joinColumns=@JoinColumn(name="MEMBER_ID"))
	private List<Address> addressHistory = new ArrayList<Address>();
	
	// 값 타입 컬렉션 대신에 일대다 관계 사용
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="MEMBER_ID")
	private List<Address2> addressHistory2 = new ArrayList<Address2>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Period getWorkPeriod() {
		return workPeriod;
	}
	public void setWorkPeriod(Period workPeriod) {
		this.workPeriod = workPeriod;
	}
	public Address getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
	public Address getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(Address companyAddress) {
		this.companyAddress = companyAddress;
	}
	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Set<String> getFavoriteFoods() {
		return favoriteFoods;
	}
	public void setFavoriteFoods(Set<String> favoriteFoods) {
		this.favoriteFoods = favoriteFoods;
	}
	public List<Address> getAddressHistory() {
		return addressHistory;
	}
	public void setAddressHistory(List<Address> addressHistory) {
		this.addressHistory = addressHistory;
	}
	public List<Address2> getAddressHistory2() {
		return addressHistory2;
	}
	public void setAddressHistory2(List<Address2> addressHistory2) {
		this.addressHistory2 = addressHistory2;
	}
}
