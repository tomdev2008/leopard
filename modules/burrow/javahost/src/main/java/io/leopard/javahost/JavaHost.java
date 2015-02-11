package io.leopard.javahost;

import io.leopard.javahost.impl.DnsImpl;
import io.leopard.javahost.impl.HostsCacheImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 虚拟DNS util类.
 * 
 * @author 阿海
 *
 */
public class JavaHost {

	private static Dns dns = new DnsImpl();
	private static Hosts hosts = new HostsCacheImpl();

	/**
	 * 获取虚拟DNS接口
	 * 
	 * @return
	 */
	public static Dns getDns() {
		return dns;
	}

	/**
	 * 获取hosts文件解析接口
	 * 
	 * @return
	 */
	public static Hosts getHosts() {
		return hosts;
	}

	/**
	 * 更新虚拟DNS域名指向.
	 * 
	 * @param host
	 *            域名
	 * @param ip
	 *            IP
	 * @return
	 */
	public static boolean updateVirtualDns(String host, String ip) {
		return dns.update(host, ip);
	}

	/**
	 * 根据域名查询IP.
	 * 
	 * @param host
	 *            域名
	 * @return IP
	 */
	public static String queryForIp(String host) {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(host);
		}
		catch (UnknownHostException e) {
			return null;
		}
		return inetAddress.getHostAddress();
	}

	/**
	 * 是否在hosts文件配置的域名?
	 * 
	 * @param host
	 *            域名
	 * @return
	 */
	public static boolean isLocalHost(String host) {
		return hosts.exist(host);
	}
}
