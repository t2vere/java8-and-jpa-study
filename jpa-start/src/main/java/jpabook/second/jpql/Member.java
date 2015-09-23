package jpabook.second.jpql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name="Member10")
@NamedQueries({
	@NamedQuery(name="Member10.findByUsername", query="select m from Member10 m where m.username = :username")
	, @NamedQuery(name="Member10.count", query="select count(m) from Member m")
})
public class Member {

	@Id @GeneratedValue
	private Long id;
	
	@Column(name="name")
	private String username;
	
	@ManyToOne
	private Team team;

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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", team=" + team + "]";
	}
}
