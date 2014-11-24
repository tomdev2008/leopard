package io.leopard.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 分页对象，生成对象后丢到ModelMap，同时再在JSP中引用pager.jsp即可 注：目前JSP结构以及样式只适合后台使用
 * 
 * @author CJ
 * 
 */
public class Pager {
	public static final int PAGE_SIZE = 10;// 页面默认记录条数
	public static final String PAGE_PARAM_NAME = "page";

	private int totalPage;
	private int currentPage;
	private int totalCount;

	private int size;

	private String url;
	private String pageParamName;
	private String sizeParamName;

	private Map<String, Object> params;

	/**
	 * 初始化分页对象
	 * 
	 * @param totalCount
	 *            记录总数
	 * @param currentPage
	 *            当前页码
	 * @param size
	 *            分页大小
	 * @param url
	 *            URL链接
	 * @param params
	 *            参数
	 */
	public Pager(int totalCount, int currentPage, int size, String url, Map<String, Object> params) {
		this.totalPage = (totalCount - 1) / size + 1;
		this.totalCount = totalCount;
		this.size = size;
		this.currentPage = currentPage;
		this.url = url;
		this.pageParamName = PAGE_PARAM_NAME;
		this.sizeParamName = "size";
		this.params = params;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public int getNextPage() {
		return this.currentPage + 1 < this.totalPage ? this.currentPage + 1 : this.totalPage;
	}

	public int getPrevPage() {
		return this.currentPage - 1 > 0 ? this.currentPage - 1 : 1;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 返回分页的链接地址
	 * 
	 * @param page
	 *            页码
	 * @param size
	 *            分页大小
	 * @return URL链接
	 */
	public String getUrl(int page, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getUrlExceptPage());
		sb.append(this.pageParamName).append("=").append(page).append("&").append(this.sizeParamName).append("=").append(size);
		return sb.toString();
	}

	/**
	 * 返回首页的URL链接
	 * 
	 * @return URL链接
	 */
	public String getFirstUrl() {
		return this.getUrl(1, this.getSize());
	}

	/**
	 * 返回尾页的URL链接
	 * 
	 * @return URL链接
	 */
	public String getLastUrl() {
		return this.getUrl(this.getTotalPage(), this.getSize());
	}

	/**
	 * 返回下一页的URL链接
	 * 
	 * @return URL链接
	 */
	public String getNextUrl() {
		return this.getUrl(this.getNextPage(), this.getSize());
	}

	/**
	 * 返回上一页的URL链接
	 * 
	 * @return URL链接
	 */
	public String getPreUrl() {
		return this.getUrl(this.getPrevPage(), this.getSize());
	}

	/**
	 * 返回不包含页面、分页大小的URL链接
	 * 
	 * @return URL链接
	 */
	public String getUrlExceptPage() {
		StringBuilder sb = new StringBuilder();
		sb.append(url).append("?");
		if (params != null) {
			Iterator<String> it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object value = params.get(key);
				if (StringUtils.isEmpty(key) || value == null) {
					continue;
				}
				if (value instanceof java.util.Date) {
					value = this.formatDate(value); // 日期对象不能以默认toString()格式输出
				}
				sb.append(JstlFunctions.urlEncode(key.toString())).append("=").append(JstlFunctions.urlEncode(value.toString())).append("&");
			}
		}
		return sb.toString();

	}

	/**
	 * 格式化日期对象，转成yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateObj
	 *            日期对象
	 * @return 格式化后的字符串日期
	 */
	protected String formatDate(Object dateObj) {
		Date date = (Date) dateObj;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	public String getPageParamName() {
		return pageParamName;
	}

	public void setPageParamName(String pageParamName) {
		this.pageParamName = pageParamName;
	}

	public String getSizeParamName() {
		return sizeParamName;
	}

	public void setSizeParamName(String sizeParamName) {
		this.sizeParamName = sizeParamName;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 获取分页结果的起始行号
	 * 
	 * @return int 起始行号
	 */
	public int getFirst() {
		return (this.getCurrentPage() - 1) * this.getSize();
	}

}
