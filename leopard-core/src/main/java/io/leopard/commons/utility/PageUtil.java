package io.leopard.commons.utility;

/**
 * 分页相关方法
 * 
 * @author 阿海
 * 
 */
public class PageUtil {

	/**
	 * 返回当前分页起始记录编号
	 * @param pageId 分页编号
	 * @param size 分页大小
	 * @return 当前分页起始记录编号
	 */
	public static int getStart(int pageId, int size) {
		return (pageId - 1) * size;
	}
}
