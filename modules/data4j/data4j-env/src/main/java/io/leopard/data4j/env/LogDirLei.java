package io.leopard.data4j.env;

import io.leopard.burrow.LeopardLei;

/**
 * 日志目录.
 * 
 * @author 阿海
 *
 */
public interface LogDirLei extends LeopardLei {

	/**
	 * 获取日志目录.
	 * 
	 * @return
	 */
	String getDir();
}
