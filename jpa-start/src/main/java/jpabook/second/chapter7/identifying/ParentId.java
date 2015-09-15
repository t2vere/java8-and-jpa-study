package jpabook.second.chapter7.identifying;

import java.io.Serializable;

/**
 * DB 친화적 방법
 * 
 * 복합키 클래스의 조건
 * 
 * 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자 속성명 동일
 * {@link Serializable} 인터페이스 구현
 * equals, hashCode 구현
 * 기본 생성자
 * 식별자 클래스는 public
 * 
 * c.f. 복합키에는 @GeneratedValue를 사용할 수 없다.
 * 
 * @author greygoose
 *
 */
public class ParentId implements Serializable {

	private String id1;
	private String id2;

	public ParentId() {
		// TODO Auto-generated constructor stub
	}

	public ParentId(String id1, String id2) {
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
