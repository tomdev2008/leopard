package io.leopard.web;

/**
 * 系统启动时做的初始化操作.
 * 
 * 可以把MySQL连接、redis连接在这里初始化，减少resin重启时的访问堵塞.
 * 
 * @author 阿海
 * 
 */
public interface StartService {

	void load();
}
