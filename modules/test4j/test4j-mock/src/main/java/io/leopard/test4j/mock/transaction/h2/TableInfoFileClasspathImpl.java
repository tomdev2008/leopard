package io.leopard.test4j.mock.transaction.h2;

import io.leopard.core.exception.IORuntimeException;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 表结构.
 * 
 * @author ahai
 * 
 */
public class TableInfoFileClasspathImpl implements TableInfoFile {

	public static String currentDatabaseName = null;

	private String alterSqlContent;
	private String sqlContent;
	private String databaseName;

	public TableInfoFileClasspathImpl() {
		// System.err.println("new TableInfoFileClasspathImpl");
		try {
			this.init();
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
	}

	protected void init() throws IOException {
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:/*.sql");

		// System.err.println("resources:" + resources.length);
		for (Resource resource : resources) {
			String filename = resource.getFilename();

			if ("alter.sql".equals(filename)) {
				this.alterSqlContent = getContent(resource);
			}
			else {
				if (StringUtils.isNotEmpty(this.databaseName)) {
					throw new RuntimeException("数据库名称已经初始化为[" + this.databaseName + "]，怎么还有一个" + filename + "?");
				}
				this.databaseName = filename.replace(".sql", "");
				currentDatabaseName = databaseName;
				// System.err.println("filename:" + filename + " projectName:" + projectName);
				this.sqlContent = getContent(resource);
			}
		}
		// System.err.println("TableInfoFileClasspathImpl projectName:" + projectName);
	}

	protected String getContent(Resource resource) throws IOException {
		InputStream is = resource.getInputStream();
		String content = IOUtils.toString(is);
		is.close();
		return content;
	}

	@Override
	public String getDatabaseName() {
		return this.databaseName;
	}

	@Override
	public String getSqlContent() {
		return this.sqlContent;

	}

	@Override
	public String getAlterSqlContent() {
		return this.alterSqlContent;
	}

	@Override
	public boolean hasData() {
		return StringUtils.isNotEmpty(databaseName);
	}

}
