package jpabook.second.jpql;

import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.expr.BooleanExpression;

public class ItemExpression {

	// 적용할 엔티티 지정, 생성된 쿼리 타입을 보면 기능이 추가되이어 있음
	// 필요시 Stirng, Date와 같은 자바 기본 내장 타입에도 메소드 위임 기능 사용 가능
	@QueryDelegate(OrderItem.class)
	public static BooleanExpression isExpensive(QOrderItem item, Long price) {
		return item.price.gt(price);
	}
}
