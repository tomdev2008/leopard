package io.leopard.commons.utility;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 飞机票
 * 
 * @author 阿海
 * 
 */
public class FeiJiPiao {
	/**
	 * 将字符串转换成base64字符
	 * @param content 需要转换的字符
	 * @return 转换成base64的字符串
	 */
	public static String encode(String content) {
		return Base64.encode(content);
	}
	
	/**
	 * 将base64字符串转换成字符
	 * @param content 需要转换的base64字符
	 * @return 转换后的字符串
	 */
	public static String decode(String encode) {
		return Base64.decode(encode);
	}
	
	
	/**
	 * 将base64字符串转换成字符</br>
	 * 
	 * @param encode 需要转换的base64字符
	 * @return 转换后的字符串并存入map
	 */
	public static Map<String, String> parse(String encode) {
		String decode = decode(encode);
		String[] strs = decode.split("&");
		Map<String, String> param = new LinkedHashMap<String, String>();
		for (String str : strs) {
			String[] blocks = str.split("=");
			String name = blocks[0];
			String value = blocks[1];
			param.put(name, value);
		}
		return param;
	}

}
