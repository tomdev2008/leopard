package io.leopard.test.mock;

import java.util.List;

/**
 * 
 * @author 阿海
 * 
 */
public class Any {

	public static int i() {
		return Mock.anyInt();
	}

	public static boolean b() {
		return Mock.anyBoolean();
	}

	public static List<String> slist() {
		return Mock.anyListOf(String.class);
	}

	public static List<Integer> ilist() {
		return Mock.anyListOf(Integer.class);
	}
}
