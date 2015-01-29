package io.leopard.web4j.permission.config;

import java.util.List;

/**
 * 权限bean
 */
public class Bean {

	private String id;
	private String url;
	private String parent;
	private boolean enable;
	private List<Allow> allowList;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Allow> getAllowList() {
		return allowList;
	}

	public void setAllowList(List<Allow> allowList) {
		this.allowList = allowList;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
