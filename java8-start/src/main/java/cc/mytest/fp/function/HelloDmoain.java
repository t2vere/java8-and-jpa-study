package cc.mytest.fp.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloDmoain {

	private Long id;
	private String name;
	private Integer age;
	private String description;
	private HelloSub sub;
}
