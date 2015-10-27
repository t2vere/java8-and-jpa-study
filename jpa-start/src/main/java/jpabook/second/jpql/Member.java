package jpabook.second.jpql;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;

@Entity(name="Member10")
@NamedQueries({
	@NamedQuery(name="Member10.findByUsername", query="select m from Member10 m where m.username = :username")
	, @NamedQuery(name="Member10.count", query="select count(m) from Member m")
})
// 결과 매핑
@SqlResultSetMapping(name="memberWithOrderCount"
	, entities = {@EntityResult(entityClass=Member.class)}
	, columns = {@ColumnResult(name="ORDER_COUNT")}
)
// Named native SQL
@NamedNativeQuery(
		name="Member.memberSQL"
		, query = "SELECT ID, AGE, NAME, TEAM_ID FROM MEMBER WHERE AGE > ?"
		, resultClass = Member.class
		
		// ormMember.xml에 선언
		/*name="Member.memberWithOrderCount"
		, query = "SELECT M.ID, AGE, NAME, TEAM_ID, I.ORDER_COUNT"
				+ " FROM MEMBER M"
				+ " LEFT JOIN"
				+ " 	(SELECT IM.ID, COUNT(*) AS ORDER_COUNT"
				+ " 	FROM ORDERS O, MEMBER IM"
				+ " 	WHERE O.MEMBER_ID = IM.ID) I"
				+ " ON M.ID = I.ID"
		, resultSetMapping = "memberWithOrderCount"*/
		)
public class Member {

	@Id @GeneratedValue
	private Long id;
	
	@Column(name="name")
	private String username;
	
	@ManyToOne
	private Team team;
	
	private int age;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
