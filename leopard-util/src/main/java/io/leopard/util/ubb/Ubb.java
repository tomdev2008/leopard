package io.leopard.util.ubb;

/**
 * UBB解析接口.
 * 
 * @author 阿海
 * 
 */
public interface Ubb {
	/**
	 * 
	 * @param jsClassName
	 *            JS类名.
	 * @param content
	 * @return
	 */
	String parse(String content);
}
