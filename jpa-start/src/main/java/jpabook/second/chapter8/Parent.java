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

	//jpa���� ��ƼƼ�� ������ �� ������ ��� ��ƼƼ�� ���ӻ��¿��� �Ѵ�.
	//�������� ���ΰ��� ���þ��� ����ȭ�� �� ���� ������ ��ƼƼ�� �Բ� ����ȭ�ϴ� ���� ���� : persist, remove, ...
	//cascade=���Ӽ� ���� Ȱ��ȭ, remove�� fulshȣ��ɶ� ���� �߻�
	//orphanRemoval=�θ� ��ƼƼ �÷��ǿ��� �ڽ� ��ƼƼ ���� ���Ž� �ڽ� ��ƼƼ �ڵ����� ����, OTO, OTM������ ��� ����
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
