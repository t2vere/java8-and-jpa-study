package jpabook.second.chapter8;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Parent08")
public class Parent {

	@Id
	@GeneratedValue
	private Long id;

	//jpa에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속상태여야 한다.
	//연관관계 매핑과는 관련없고 영속화할 때 단지 연관된 엔티티도 함께 영속화하는 편의 제공 : persist, remove, ...
	//cascade=영속성 전이 활성화, remove는 fulsh호출될때 전이 발생
	//orphanRemoval=부모 엔티티 컬렉션에서 자식 엔티티 참조 제거시 자식 엔티티 자동으로 삭제, OTO, OTM에서만 사용 가능
	@OneToMany(mappedBy = "parent", cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true) 
	private List<Child> children = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}
}
