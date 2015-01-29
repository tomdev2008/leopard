package io.leopard.test4j.mock.transaction.h2;

import io.leopard.burrow.io.IOUtil;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NotImplementedException;

/**
 * 表结构.
 * 
 * @author ahai
 * 
 */
public class TableInfoFileDocdirImpl implements TableInfoFile {

	protected String getFullPath() {
		// String root = AppProperties.getRootDir();
		if (true) {
			// FIXME ahai 未重构完成
			throw new NotImplementedException();
		}
		String root = null;// AppProperties.getRootDir();
		String fullPath = root + "/doc/initdata/";
		return fullPath;
	}

	@Override
	public String getDatabaseName() {
		// return AppProperties.getProjectName();
		// FIXME ahai 未重构完成
		throw new NotImplementedException();
	}

	@Override
	public String getSqlContent() {
		if (true) {
			// FIXME ahai 未重构完成
			throw new NotImplementedException();
		}
		// String projectName = AppProperties.getProjectName();
		String projectName = null;// AppProperties.getProjectName();

		System.err.println("projectName:" + projectName);
		String fullPath = this.getFullPath() + projectName + ".sql";
		return IOUtil.readFileToString(fullPath);

	}

	@Override
	public String getAlterSqlContent() {
		String fullPath = this.getFullPath() + "alter.sql";
		try {
			String content = FileUtils.readFileToString(new File(fullPath));
			return content;
		}
		catch (IOException e) {
			return null;
		}
	}

	@Override
	public boolean hasData() {
		return true;
	}

}
