package io.leopard.burrow.ubb;

import java.util.ArrayList;
import java.util.List;

/**
 * UBB标签解析.
 * 
 * @author 阿海
 * 
 */
public class UbbUtil {
	private static List<Ubb> ubbList = new ArrayList<Ubb>();

	static {
		ubbList.add(new FeijipiaoUbb());
		ubbList.add(new BrUbb());
		ubbList.add(new StrongUbb());
	}

	public static String parse(String content) {
		for (Ubb ubb : ubbList) {
			content = ubb.parse(content);
		}
		return content;
	}
}
