package jpabook.second;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity(name="DashUser")
@Table(name = "DASH_USER", uniqueConstraints={
		@UniqueConstraint(name="NAME_AGE_UNIQUE", columnNames={"NAME", "AGE"})
})
@SequenceGenerator(
		name="DASH_SEQ_GENERATOR" // name in JPA
		, sequenceName = "DASH_SEQ" // name in DB
		, initialValue = 1
		, allocationSize = 1) // 성능 최적화시 사용
@TableGenerator(
name="DASH_SEQ_GENERATOR2"
, table="MY_SEQ"
, pkColumnValue="DASH_SEQ"
, allocationSize= 1)
@Access(AccessType.FIELD) // 직접 필드 접근, property는 getter로 접근, 설정하지 않으면 @Id의 위치를 기준으로 결정됨, 병행 가능
public class DashUser {

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY) // auto_increment
	/*
	 * SEQUENCE object
	 * persist 호출시 먼저 시퀀스 사용
	 * 조회한 식별자를 엔티티에 할당 후 영속성 컨텍스트에 저장
	 * 트랜잭션 커밋해서 플러시되면 엔티티를 db에 저장
	 * */
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DASH_SEQ_GENERATOR")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="DASH_SEQ_GENERATOR2")
	//@GeneratedValue(strategy=GenerationType.AUTO) //DB 방언에 따라 달라짐, 기본값
	@Column(name = "ID")
	private Integer id;

	@Column(name = "NAME", nullable = false, length = 10)
	private String username;

	private Integer age;
	
	@Transient
	private String temp;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@Lob
	private String description;
	
	//연관관계 매핑
	@ManyToOne
	@JoinColumn(name="TEAM_ID")
	private Team team;

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public DashUser() {
		// 기본 생성자는 필수
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
