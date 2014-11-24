package io.leopard.commons.utility;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * <b>数组转换工具类</b>
 * 
 * @author Administrator
 * 
 */
// @Deprecated
public class ArgsUtil {

	/**
	 * 数组转换成字符</br>
	 * 
	 * @param arr
	 *            需要转换的数组
	 * @return 转换后的字符串，用“，”号分隔
	 */
	public static String arr2Str(String[] arr) {
		String result = "";
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i].trim();
			if (i != 0) {
				result = "," + result;
			}
			result = str + result;
		}
		return result;
	}

	/**
	 * 字符串转换成List</br>
	 * 
	 * @param str
	 *            需要转换的字符串，用","号分隔
	 * @return 转换后的List<String>
	 */
	public static List<String> str2List(String str) {
		// str.replace(" ", "");
		List<String> arr = new ArrayList<String>();
		if (!StringUtils.isEmpty(str)) {
			String[] strarr = str.split(",");
			for (int i = 0; i < strarr.length; i++) {
				if (!StringUtils.isEmpty(strarr[i])) {
					arr.add(strarr[i]);
				}
			}
		}
		return arr;
	}

	/**
	 * 字符串，转换成数组</br> 注意：</br> ahai 这个方法绕好大一圈，为了过滤空元素？</br> ahai
	 * 程序里不应该做这样的兼容性，如果要兼容方法命名应该特别标明
	 * 
	 * @param str
	 *            需要转换的字符串,用“，”号分隔
	 * @return 数组
	 */
	public static String[] str2Arr(String str) {
		List<String> list = ArgsUtil.str2List(str);
		String[] arr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

}
