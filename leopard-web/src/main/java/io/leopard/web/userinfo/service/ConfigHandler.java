package io.leopard.web.userinfo.service;

import io.leopard.web4j.admin.AdminAllowIp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录接口.
 * 
 * @author 阿海
 * 
 */
public class ConfigHandler {

	/**
	 * 用户验证通过后触发该事件(即将当前用户名写入session时)
	 * 
	 * @param sessAccount
	 */
	public void logined(Object sessAccount, HttpServletRequest request) {

	}

	/**
	 * 是否开启耗时日志.
	 * 
	 * @return
	 */
	public boolean isEnableTimeLog() {
		return false;
	}

	/**
	 * 不需要登录的页面地址.
	 * 
	 * @return
	 */
	public List<String> getExcludeUris() {
		return null;
	}

	/**
	 * 需要做并发限制的页面地址.
	 * 
	 * @return null:表示所有页面不做并发限制.
	 */
	public List<String> getConnectionLimitIncludeUris() {
		return null;
	}

	/**
	 * CSRF、JSON劫持漏洞域名白名单,使用<leopard:config domainWhiteListJdbcRef="jdbc"/>MySQL存储后该配置无效
	 * 
	 * @return
	 */
	public Set<String> getRefererDomainWhiteSet() {
		return null;
	}

	protected AdminAllowIp adminAllowIp = new AdminAllowIp();

	protected AdminAllowIp monitorAllowIp = new AdminAllowIp();

	/**
	 * 判断IP是否允许访问管理后台.
	 * 
	 * @param ip
	 * @return
	 */
	public boolean isAllowAdminIp(String ip) {
		return adminAllowIp.isAllowAdminIp(ip);
	}

	/**
	 * 判断IP是否允许访问监控系统.
	 * 
	 * @param proxyIp
	 * @return
	 */
	public boolean isMonitorServer(String ip) {
		return this.monitorAllowIp.isAllowAdminIp(ip);
	}

	public ConfigHandler() {
		{
			List<String> list = new ArrayList<String>();

			list.add("127.0.0.1");
			list.add("113.106.251.82");
			list.add("113.108.232.34");
			list.add("183.60.177.226");
			list.add("58.248.138.4");
			list.add("58.248.138.5");
			list.add("58.248.138.6");

			// 50
			list.add("113.108.232.33");
			list.add("113.108.232.34");
			list.add("113.108.232.35");
			list.add("113.108.232.36");
			list.add("113.108.232.37");
			list.add("113.108.232.38");
			list.add("113.108.232.39");
			list.add("113.108.232.40");
			list.add("113.108.232.41");
			list.add("113.108.232.42");
			list.add("113.108.232.43");
			list.add("113.108.232.44");
			list.add("113.108.232.45");
			list.add("113.108.232.46");

			// VPN
			list.add("113.108.232.34");
			list.add("183.60.177.226");
			list.add("183.60.177.227");
			list.add("183.60.177.228");
			list.add("183.60.177.229");
			list.add("183.60.177.234");
			list.add("58.248.138.4");

			// IP段
			list.add("172.17");
			list.add("172.19");
			list.add("172.16");

			for (String ip : list) {
				this.adminAllowIp.addIp(ip);
				this.monitorAllowIp.addIp(ip);
			}

			this.monitorAllowIp.addIp("113.108.228.153");
			this.monitorAllowIp.addIp("58.248.181.25");
			this.monitorAllowIp.addIp("125.90.88.108");
			this.monitorAllowIp.addIp("221.5.47.108");

			this.monitorAllowIp.addIp("113.106.251.82");
			this.monitorAllowIp.addIp("113.108.232.34");
			this.monitorAllowIp.addIp("127.0.0.1");

			this.monitorAllowIp.addIp("121.14.39.243");
			this.monitorAllowIp.addIp("58.249.113.115");
			this.monitorAllowIp.addIp("58.215.138.28");
			this.monitorAllowIp.addIp("122.97.250.28");

			// 121.14.39.243
		}

	}
}
