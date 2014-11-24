package io.leopard.data;

import java.util.List;

/**
 * 分页数据.
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 */
public class Page<BEAN> {

	/**
	 * 记录总量
	 */
	private int count;
	/**
	 * 数据
	 */
	private List<BEAN> data;

	/**
	 * 记录总量
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 数据.
	 * 
	 * @return
	 */
	public List<BEAN> getData() {
		return data;
	}

	public void setData(List<BEAN> data) {
		this.data = data;
	}

	public static int getCount(Page<?> page) {
		if (page == null) {
			return 0;
		}
		else {
			return page.getCount();
		}
	}

	public static <BEAN> List<BEAN> getData(Page<BEAN> page) {
		if (page == null) {
			return null;
		}
		else {
			return page.getData();
		}
	}
}
