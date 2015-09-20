package cc.mytest.fp.function;

public class HelloFunction {

	public static void main(String[] args) {
		final HelloDmoain d = new HelloDmoain();
		
		final HelloSub sub = new HelloSub();
		sub.setParent(d);
		
		d.setId(1L);
		d.setAge(23);
		d.setName("nam");
		d.setSub(sub);
		System.out.println(d);

		d.setId(2L);

	}

}
