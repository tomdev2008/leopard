package io.leopard.test4j.mock.transaction.h2;

import java.util.ArrayList;
import java.util.List;

/**
 * 表结构.
 * 
 * @author ahai
 * 
 */
public class TableInfoFileImpl implements TableInfoFile {

	private TableInfoFile tableInfoFile;

	public TableInfoFileImpl() {
		List<TableInfoFile> list = new ArrayList<TableInfoFile>();
		list.add(new TableInfoFileClasspathImpl());
		list.add(new TableInfoFileDocdirImpl());
		for (TableInfoFile tableInfoFile : list) {
			if (tableInfoFile.hasData()) {
				this.tableInfoFile = tableInfoFile;
				break;
			}
		}
		if (this.tableInfoFile == null) {
			throw new NullPointerException("找不到表结构.");
		}
	}

	@Override
	public String getDatabaseName() {
		return tableInfoFile.getDatabaseName();
	}

	@Override
	public String getSqlContent() {
		return tableInfoFile.getSqlContent();
	}

	@Override
	public String getAlterSqlContent() {
		return tableInfoFile.getAlterSqlContent();
	}

	@Override
	public boolean hasData() {
		return this.tableInfoFile.hasData();
	}

}
