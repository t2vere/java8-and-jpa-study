package cc.mytest.fp.function;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="parent")
public class HelloSub {

	private Long id;
	private HelloDmoain parent;
}
