package io.leopard.test.internal;

import io.leopard.data4j.env.ClassLoaderUtil;
import io.leopard.data4j.env.EnvUtil;

public class ModuleParserLeiImpl implements ModuleParserLei {

	private String folder;

	public ModuleParserLeiImpl() {
		String path = this.parsePath();
		this.folder = parseFolder(path);

	}

	protected String parsePath() {
		String path = ClassLoaderUtil.getCurrentPath();
		path = path.replaceFirst("file:/[A-Z]:/", "/");
		path = path.replaceFirst("file:/", "/");
		int index = path.indexOf("/target/");
		if (index == -1) {
			throw new RuntimeException("非法classes目录[" + path + "].");
		}
		path = path.substring(0, index);
		return path;
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

	@Override
	// /work/news/news/news-dao
	public String getModuleName() {
		String moduleName = EnvUtil.getModuleName(folder);
		int index = moduleName.lastIndexOf("-");
		if (index == -1) {
			throw new IllegalArgumentException("非法模块名称[" + moduleName + "].");
		}
		return moduleName.substring(index + 1);
	}

	@Override
	public boolean isSingleModule() {
		String moduleName = EnvUtil.getModuleName(folder);
		int index = moduleName.lastIndexOf("-");
		return (index == -1);
	}

}
