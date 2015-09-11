package jpabook.second;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DASH_TEAM")
public class Team {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TEAM_ID")
	private Integer id;
	private String name;
	
	@OneToMany(mappedBy="team") // 양방향 연관관계시 주인이 아닌쪽에만 설정
	// 연관관계의 주인이란 외래키 관리자의 의미 (예제에서는 DashUser 엔티티)
	private List<DashUser> dashUsers = new ArrayList<DashUser>();
	
	public Team() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DashUser> getDashUsers() {
		return dashUsers;
	}
	public void setDashUsers(List<DashUser> dashUsers) {
		this.dashUsers = dashUsers;
	}
}
