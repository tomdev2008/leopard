package io.leopard.test.internal;

import io.leopard.data4j.env.ClassLoaderUtil;
import io.leopard.data4j.env.EnvUtil;

public class ModuleParser {

	
	private String path;
	private String folder;

	public ModuleParser() {
		this.path = this.parsePath();
		this.folder = parseFolder(this.path);
	}

	public void setPath(String path) {
		this.path = path;
		this.folder = parseFolder(this.path);
	}

	protected String parsePath() {
		// URL url = ClassLoader.getSystemResource("");
		// String path = url.toString();
		String path = ClassLoaderUtil.getCurrentPath();
		// System.out.println("path:" + path);
		path = path.replaceFirst("file:/[A-Z]:/", "/");
		path = path.replaceFirst("file:/", "/");
		int index = path.indexOf("/target/");
		if (index == -1) {
			throw new RuntimeException("非法classes目录[" + path + "].");
		}
		path = path.substring(0, index);
		return path;
	}

	/**
	 * 是否模块?
	 * 
	 * @return 单模块项目返回false
	 */
	public boolean isModule() {
		int index = folder.lastIndexOf("-");
		return (index > -1);
	}

	// /work/news/news/news-dao
	public String getModuleName() {
		String moduleName = EnvUtil.getModuleName(folder);
		int index = moduleName.lastIndexOf("-");
		if (index == -1) {
			throw new IllegalArgumentException("非法模块名称[" + folder + "].");
		}
		return moduleName.substring(index + 1);
	}

	protected String parseFolder(String path) {
		if (path.endsWith("/")) {
			throw new IllegalArgumentException("非法路径格式[" + path + "].");
		}
		int index = path.lastIndexOf("/");
		if (index == -1) {
			throw new IllegalArgumentException("非法路径格式[" + path + "].");
		}
		String folder = path.substring(index + 1);
		return folder;
	}
}
