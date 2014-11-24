package io.leopard.data.env;

public interface EnvDao {
	

	/**
	 * 是否启用当前.
	 * 
	 * @return
	 */
	boolean isEnabled();

	/**
	 * 获取当前环境(dev、test、prod).
	 * 
	 * @return
	 */
	String getEnv();

	/**
	 * 获取项目根目录.
	 * 
	 * @return
	 */
	String getRootDir();

	/**
	 * 是否开发环境.
	 * 
	 * @return
	 */
	boolean isDevEnv();

}
