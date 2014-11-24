package io.leopard.commons.utility;

import org.apache.commons.lang.StringUtils;

/**
 * 数据验证(和参数验证的区别就是抛不同的异常)
 * 
 * @author Administrator
 * 
 */
public class AssertData {

	/**
	 * 判断一个字符串是否为空，如果为空抛异常</br>
	 * 
	 * @param str
	 *            字符串
	 * @param message
	 *            抛出的异常信息
	 */
	public static void notEmpty(String str, String message) {
		if (StringUtils.isEmpty(str)) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 判断一个布尔值是否为true,如果不是抛异常</br>
	 * 
	 * @param success
	 *            布尔值
	 * @param message
	 *            抛出的异常信息
	 */
	public static void assertTrue(boolean success, String message) {
		if (!success) {
			throw new RuntimeException(message);
		}
	}

}
