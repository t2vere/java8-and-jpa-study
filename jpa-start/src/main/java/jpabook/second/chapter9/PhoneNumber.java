package jpabook.second.chapter9;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PhoneNumber {

	String areaCode;
	String localNumber;
	@ManyToOne
	PhoneServiceProvider provider; // 엔티티 참조
}
