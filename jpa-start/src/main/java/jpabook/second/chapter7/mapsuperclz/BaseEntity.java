package jpabook.second.chapter7.mapsuperclz;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * 공통 매핑 정보 정의, 테이블과 매핑할 필요는 없고 매핑 정보만 제공
 * 엔티티가 아니기 때문에 em.find()나 jpql 사용 불가
 * 추상클래스 권장
 * c.f. 엔티티(@Entity)는 @Entity이거나 @MappedSuperclass로 지정한 클래스만 상속 가능!
 * 
 * 물려받은 매핑 정보를 재정의 : @AttributeOverrides
 * 연관관계 재정의 : @AssociationOverride or @AssociationOverrides
 * 
 * @author greygoose
 *
 */
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue
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
}
