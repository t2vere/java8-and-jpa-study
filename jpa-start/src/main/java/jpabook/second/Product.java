package jpabook.second;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Product {

	@Id
	@Column(name="PRODUCT_ID")
	private String id;
	
	private String name;
	
	@ManyToMany(mappedBy="products") // 역방향 추가
	private List<Member2> members = new ArrayList<Member2>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Member2> getMembers() {
		return members;
	}

	public void setMembers(List<Member2> members) {
		this.members = members;
	}
	
}
