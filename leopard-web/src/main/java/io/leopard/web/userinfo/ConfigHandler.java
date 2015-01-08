package io.leopard.web.userinfo;

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
			// FIXME ahai IP写死了
			List<String> list = new ArrayList<String>();

			list.add("127.0.0.1");

			// IP段
			list.add("172.17");
			list.add("172.19");
			list.add("172.16");

			for (String ip : list) {
				this.adminAllowIp.addIp(ip);
				this.monitorAllowIp.addIp(ip);
			}

			this.monitorAllowIp.addIp("127.0.0.1");

		}

	}
}
