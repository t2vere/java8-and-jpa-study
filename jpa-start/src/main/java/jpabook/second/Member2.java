package jpabook.second;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="MEMBER2")
public class Member2 {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MEMBER_ID")
	private Long id;
	
	private String username;
	
	@OneToOne
	@JoinColumn(name="LOCKER_ID")
	private Locker locker;
	
	@ManyToMany
	@JoinTable(name="MEMBER_PRODUCT"
		, joinColumns = @JoinColumn(name="MEMBER_ID") // 현재 방향
		, inverseJoinColumns = @JoinColumn(name="PRODUCT_ID")) // 반대 방향
	private List<Product> products = new ArrayList<Product>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		if (locker != null) {
			locker.setMember(this);
		}
		this.locker = locker;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	// 연관관계 편의 제공 메소드
	public void addProduct(Product product) {
		products.add(product);
		product.getMembers().add(this);
	}
}
