package jpabook.second.chapter8;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Team08")
public class Team {

	@Id
	@Column(name="TEAM_ID")
	private Long id;
	private String name;
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
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + "]";
	}
}
