package jpabook.second.chapter7.identifying;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 객체 친화적 방법
 * 
 * 임베드 클래스의 조건
 * 
 * @Embeddable 어노테이션 추가
 * Serializable 인터페이스 구현
 * equals, hashCode 구현
 * 기본 생성자
 * 식별자 클래스는 public
 * 
 * c.f. 복합키에는 @GeneratedValue를 사용할 수 없다.
 * 
 * @author greygoose
 *
 */
@Embeddable
public class ParentId2 implements Serializable {

	@Column(name="PARENT_ID1")
	private String id1;
	@Column(name="PARENT_ID2")
	private String id2;

	public ParentId2() {
		// TODO Auto-generated constructor stub
	}

	public ParentId2(String id1, String id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
