package io.leopard.schema.model;

public class ConfigSchema {

	private String adminType;// 管理员登录过滤器验证类型
	private String adminFolder;// admin目录
	private boolean checkAdminAllowIp;//
	private String adminRole;// 是否启用角色
	private String performance;// 是否启用方法级别的性能监控
	private String distributedSession;// 是否启用分布式session
	// private String nocsrf;// 是否启用noCsrf
	private String convert;//
	// private boolean csrfLog;// 遇到csrf漏洞时,仅输出日志.
	private int sessionExpiry;// session过期时间(秒)
	private String udbJsFolder;// UDB JS文件目录.
	// private String permission;// 是否启用权限控制组件
	private String csrf;// 是否启用CSRF漏洞防范
	private String xss;// 是否启用XSS漏洞防范

	private String domainWhiteListJdbcRef;// 域名白名单JDBC数据源

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

	public String getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public String getDistributedSession() {
		return distributedSession;
	}

	public void setDistributedSession(String distributedSession) {
		this.distributedSession = distributedSession;
	}

	public int getSessionExpiry() {
		return sessionExpiry;
	}

	public void setSessionExpiry(int sessionExpiry) {
		this.sessionExpiry = sessionExpiry;
	}

	public String getAdminFolder() {
		return adminFolder;
	}

	public void setAdminFolder(String adminFolder) {
		this.adminFolder = adminFolder;
	}

	public String getUdbJsFolder() {
		return udbJsFolder;
	}

	public void setUdbJsFolder(String udbJsFolder) {
		this.udbJsFolder = udbJsFolder;
	}

	public boolean isCheckAdminAllowIp() {
		return checkAdminAllowIp;
	}

	public void setCheckAdminAllowIp(boolean checkAdminAllowIp) {
		this.checkAdminAllowIp = checkAdminAllowIp;
	}

	// public String getNocsrf() {
	// return nocsrf;
	// }
	//
	// public void setNocsrf(String nocsrf) {
	// this.nocsrf = nocsrf;
	// }
	//
	// public boolean isCsrfLog() {
	// return csrfLog;
	// }
	//
	// public void setCsrfLog(boolean csrfLog) {
	// this.csrfLog = csrfLog;
	// }

	public String getConvert() {
		return convert;
	}

	public void setConvert(String convert) {
		this.convert = convert;
	}

	// public String getPermission() {
	// return permission;
	// }
	//
	// public void setPermission(String permission) {
	// this.permission = permission;
	// }

	public String getDomainWhiteListJdbcRef() {
		return domainWhiteListJdbcRef;
	}

	public void setDomainWhiteListJdbcRef(String domainWhiteListJdbcRef) {
		this.domainWhiteListJdbcRef = domainWhiteListJdbcRef;
	}

	public String getCsrf() {
		return csrf;
	}

	public void setCsrf(String csrf) {
		this.csrf = csrf;
	}

	public String getXss() {
		return xss;
	}

	public void setXss(String xss) {
		this.xss = xss;
	}

}
