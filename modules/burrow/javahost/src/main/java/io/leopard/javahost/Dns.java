package io.leopard.javahost;

/**
 * 虚拟DNS.
 * 
 * @author 阿海
 *
 */
public interface Dns {

	/**
	 * 设置域名解析.
	 * 
	 * @param host
	 * @param ip
	 * @return
	 */
	boolean update(String host, String ip);

	/**
	 * 删除域名解析.
	 * 
	 * @param host
	 * @param ip
	 * @return
	 */
	boolean remove(String host);

	/**
	 * 解析域名.
	 * 
	 * @param host
	 *            域名
	 * @return IP
	 */
	String query(String host);
}
