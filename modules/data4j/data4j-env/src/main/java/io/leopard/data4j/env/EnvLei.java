package io.leopard.data4j.env;

import io.leopard.burrow.LeopardLei;

/**
 * 环境配置.
 * 
 * @author 阿海
 *
 */
// Leopard Extended Interface
public interface EnvLei extends LeopardLei {

	/**
	 * 是否启用当前.
	 * 
	 * @return
	 */
	boolean isEnabled();

	/**
	 * 获取项目根目录.
	 * 
	 * @return
	 */
	String getRootDir();

}
