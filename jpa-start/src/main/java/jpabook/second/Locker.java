package jpabook.second;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LOCKER_ID")
	private Long id;
	
	private String name;
	
	@OneToOne(mappedBy="locker")
	private Member2 member;
	
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
	public Member2 getMember() {
		return member;
	}
	public void setMember(Member2 member) {
		this.member = member;
	}
}
