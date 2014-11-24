package io.leopard.commons.utility;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UdbConstant {// NOPMD
	private static final Log logger = LogFactory.getLog(UdbConstant.class);

	/**
	 * 获取字节数(一个中文相当于2个字节)</br>
	 * 
	 * @param str
	 * @return
	 */
	public static int getBytes(String str) {
		if (StringUtils.isEmpty(str)) {
			return 0;
		}
		try {
			return str.getBytes("GBK").length;
		}
		catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage(), e);
			throw new NullPointerException("转换编码出错.");
		}
	}

	/**
	 * 判断是否合法的用户名（兼容旧版用户名）</br>
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isValidUsername(String username) {// NOPMD
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (true) {
			int bytes = getBytes(username);
			if (bytes > 30) {
				// String message = "invalid username:" + username + " bytes:" + bytes;
				// Exception e = new Exception(message);
				// logger.error(e.getMessage(), e);
				return false;
			}
		}
		if (true) {
			// 用户名不允许有空格
			int usernameLen = username.length();
			for (int i = 0; i < usernameLen; i++) {
				char c = username.charAt(i);
				if (c <= 32) {
					return false;
				}
				if ('　' == c) {
					// 全角空格不视为空格
					continue;
				}
				if ((Character.isWhitespace(c))) {
					return false;
				}
				// 64 : @
				// 60 : <
				// 62 : >
				if (c == '@' || c == '<' || c == '>') {
					return false;
				}
				if ((c >= 'A' && c <= 'Z')) {
					// 如果有有字符是大写字母，则返回false
					return false;
				}
			}
		}

		return true;
	}
}
