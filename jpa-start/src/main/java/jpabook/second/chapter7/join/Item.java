package jpabook.second.chapter7.join;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 조인 전략
 * - 장점 : 테이블 정규화, FK 참조 무결성 제약조건 활용, 저장공간 효율
 * - 단점 : 조회시 조인 많이 사용, 성능 저하, 쿼리 복잡, insert sql 여러번 호출
 * 
 * JPA 표준 명세는 구분 컬럼 사용하도록 함
 * 하이버네이트를 포함한 몇몇 구현체는 구분 컬럼 없이도 동작
 * 
 * @author greygoose
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED) // 상속 매핑시 부모 클래스에 사용, 조인 전략
@DiscriminatorColumn(name="DTYPE") // 부모 클래스에 구분 컬럼 지정, 기본값은 'DTYPE'
public abstract class Item {
	
	@Id
	@Column(name="ITEM_ID")
	private Long id;
	private String name;
	private int price;
	
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
