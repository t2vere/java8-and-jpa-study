package jpabook.second.chapter8;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Member08")
public class Member {

	@Id
	private Long id;
	private String name;

	/*
	 * fetch 속성의 기본 설정값
	 * @ManyToOne, @OneToOne -> EAGER
	 * 		- 컬렉션을 하나 이상 즉시 로딩하는 것은 권장하지 않음
	 * 		- N*M 결과, 성능 저하 가능
	 *		- 항상 외부 조인 사용(MTM, OTO 관계의 optional에서만 내부 조인) 
	 * 
	 * @OneToMany, @ManyToMany -> LAZY
	 * 
	 * 추천: 모든 연관관계에 지연 로딩 사용 -> 운영 상황을 보아가며 필요한 곳에만 즉시 로딩 적용
	 * SQL 직접 사용시 이러한 최적화가 어려움
	 */
	
	@ManyToOne(fetch=FetchType.EAGER) // 즉시 로딩, 가능하면 JPA내부에서 조인 쿼리 사용
	@JoinColumn(name="TEAM_ID", nullable=false)
	// @JoinColumn.nullable 설정에 따른 조인 전략
	// true: NULL 허용, 기본값, 외부조인 사용, 선택적 관계
	// false: NULL 허용하지 않음, 내부조인 사용, 필수 관계
	// 혹은 @ManyToOne.optional=false 속성으로 대체 가능
	private Team team;
	
	@ManyToOne(fetch=FetchType.LAZY)
	// 지연 로딩, 실제 객체가 사용될때까지 로딩을 미룸, 초기에는 proxy 객체로 생성됨
	// 조회 대상이 영속성 컨텍스트에 이미 있으면 실제 객체 사용
	// 컬렉션은 컬렉션 래퍼가 지연 로딩 처리, 컬렉션에 대한 프록시 역할 수행
	// 컬렉션의 실제 데이터를 조회할 때 DB 조회 (getList()-> X, getList().get(0) -> O)
	@JoinColumn(name="TASK_ID")
	private Task task;

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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}


	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", team=" + team + ", task=" + task + "]";
	}

}
