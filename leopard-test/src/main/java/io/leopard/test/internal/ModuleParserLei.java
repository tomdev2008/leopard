package io.leopard.test.internal;

import io.leopard.burrow.LeopardLei;

/**
 * 模块名称解析.
 * 
 * @author 阿海
 *
 */
public interface ModuleParserLei extends LeopardLei {
	/**
	 * 判断是否单模块项目?
	 * 
	 * @return
	 */
	boolean isSingleModule();

	// /work/news/news/news-dao

	String getModuleName();
}
