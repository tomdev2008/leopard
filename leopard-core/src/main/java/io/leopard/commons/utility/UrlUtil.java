package io.leopard.commons.utility;

import java.util.Map.Entry;

public final class UrlUtil {

	public static String hostOfUrl(String url) {
		return url.replaceAll(".*://", "").replaceAll("/.*", "").replaceAll(":.*", "");
	}

	public static String appendUriMethod2Url(String url, String uriMethod) {
		String url2 = url + uriMethod;
		// url = StringUtils.replace(url, "//", "/");
		url2 = url2.replaceAll("//", "/").replaceAll(":/", "://");
		return url2;
	}

	public static String appendParams2Url(String url, Entry<String, Object>... entries) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		for (int i = 0; i < entries.length; i++) {
			if (i == 0) {
				sb.append("?");
			}
			else {
				sb.append("&");
			}
			sb.append(entries[i].getKey()).append("=").append(entries[i].getValue().toString());
		}
		return sb.toString();
	}

	// public static void main(String[] args) {
	// String url = "http://www.baidu.com:8080/abc/index.do";
	// System.err.println(hostOfUrl(url));
	// String url1 = "http://www.baidu.com:8080/a/";
	// String url2 = "/b/c/a.do";
	// System.err.println(appendUriMethod2Url(url1, url2));
	//
	// }
}
