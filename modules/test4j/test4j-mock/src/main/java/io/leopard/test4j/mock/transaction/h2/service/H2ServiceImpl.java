package io.leopard.test4j.mock.transaction.h2.service;

import io.leopard.core.exception.IORuntimeException;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.google.inject.Inject;

public class H2ServiceImpl implements H2Service {

	@Inject
	private H2Dao h2Dao;

	protected void isH2DataSource() {
		if (!h2Dao.isH2DataSource()) {
			throw new RuntimeException("当前使用的datasource，不像是h2的配置啊?");
		}
	}

	@Override
	public void importDatabase() {
		if (!this.h2Dao.isH2DataSource()) {
			return;
		}

		String content = this.h2Dao.getSqlContent();
		String alterContent = this.h2Dao.getAlterSqlContent();
		content = StringUtils.defaultString(content);
		alterContent = StringUtils.defaultString(alterContent);

		String allContent = content + alterContent;

		if (StringUtils.isNotEmpty(content)) {
			int hashCode = this.h2Dao.getH2TableInfoHashCode();
			int hashCode2 = allContent.hashCode();
			if (hashCode == hashCode2) {
				// 内容没有变化.
				return;
			}
			this.h2Dao.importDatabaseByContent(content);
			this.h2Dao.importDatabaseByContent(alterContent);
			this.h2Dao.createH2TableInfoTable(hashCode2);
		}
		this.h2Dao.importMemcacheTable();
	}

	@Override
	public void createH2TableInfoTable(int hashCode) {
		this.h2Dao.createH2TableInfoTable(hashCode);
	}

	// @Override
	// public boolean existDatabase() {
	// return this.h2Dao.existDatabase();
	// }

	@Override
	public void importMemcacheTable() {
		this.h2Dao.importMemcacheTable();
	}

	@Override
	public void createTable(String filename) {
		this.isH2DataSource();// 检查当前datasource是否h2
		String content;
		try {
			InputStream input = new ClassPathResource(filename).getInputStream();
			content = IOUtils.toString(input);
			IOUtils.closeQuietly(input);
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
		this.h2Dao.importDatabaseByContent(content);
	}
}
