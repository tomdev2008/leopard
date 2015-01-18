package io.leopard.test.hosts;

import org.springframework.core.io.Resource;

/**
 * Hosts设置.
 * 
 * @author 阿海
 *
 */
public interface HostLei {

	/**
	 * 获取hosts配置文件.
	 * 
	 * @return
	 */
	Resource getResource();

}
