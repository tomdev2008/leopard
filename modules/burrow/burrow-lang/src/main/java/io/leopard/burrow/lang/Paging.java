package io.leopard.burrow.lang;

import java.util.List;

/**
 * 分页接口.
 * 
 * @author 阿海
 *
 * @param <E>
 */
public interface Paging<E> extends List<E> {

	/**
	 * 记录总量
	 * 
	 * @return
	 */
	int getCount();

	/**
	 * 是否有下一页.
	 * 
	 * @return
	 */
	boolean hasNextPage();
}
