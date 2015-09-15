package jpabook.second.chapter7.singletable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 단일테이블 전략
 * - 장점 : 조인 불필요, 조회 성능 빠름, 쿼리 단순
 * - 단점 : 자식 엔티티가 매핑한 컬럼은 null 허용 필요, 테이블 규모 커지며 상황에 따라 성능 저하 가능
 * 
 * 구분 컬럼 반드시 필요 => @DiscriminatiorColumn 꼭 설정
 * @DiscriminatorName 을 지정하지 않으면 기본으로 엔티티 이름 사용 
 * 
 * @author greygoose
 *
 */
@Entity(name="Item2")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) // 상속 매핑시 단일 테이블 전략 사용
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
