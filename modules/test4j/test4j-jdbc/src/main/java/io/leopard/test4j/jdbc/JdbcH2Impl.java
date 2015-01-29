package io.leopard.test4j.jdbc;

import io.leopard.data4j.jdbc.JdbcMysqlImpl;
import io.leopard.data4j.jdbc.StatementParameter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.inject.Inject;

public class JdbcH2Impl extends JdbcMysqlImpl {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Inject
	public void setH2DataSource(DataSource dataSource) {
		// System.out.println("setH2DataSource:" + dataSource);
		super.setDataSource(dataSource);
		jdbcTemplate.setDataSource(dataSource);
		super.setJdbcTemplate(jdbcTemplate);
	}

	private String replaceH2Date(String sql) {
		String regex = "DATE\\((.*?)\\)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String param = m.group(1);
			String replacement = "left(" + param + ",10)";
			m.appendReplacement(sb, replacement);
		}
		// sql = sql.replace("DATE(?)", "left(?,10)");
		m.appendTail(sb);
		return sb.toString();
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType) {
		{
			sql = sql.replace("CONVERT(name USING GBK)", "name");
		}
		return super.queryForList(sql, elementType);
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param) {
		sql = this.replaceH2Date(sql);

		return super.queryForList(sql, elementType, param);
	}

	public Integer queryForInt(String sql) {
		// System.out.println("queryForInt:" + sql);
		sql = this.replaceH2Date(sql);
		return super.queryForInt(sql);
	}

	public Integer queryForInt(String sql, StatementParameter param) {
		sql = this.replaceH2Date(sql);
		return super.queryForInt(sql, param);
	}

	public int update(String sql, StatementParameter param) {
		sql = this.replaceH2Date(sql);
		return super.update(sql, param);
	}

	protected String replaceSql(String sql) {
		sql = sql.replace("INSERT IGNORE INTO ", "INSERT INTO ");
		sql = sql.replace("insert ignore into ", "insert into ");
		sql = sql.replace("REPLACE INTO ", "INSERT INTO ");
		sql = sql.replace("replace into ", "insert into ");
		return sql;
	}

	public boolean insertForBoolean(String sql, StatementParameter param) {

		sql = this.replaceSql(sql);

		return super.insertForBoolean(sql, param);
	}

	public <T> T query(String sql, Class<T> elementType, StatementParameter param) {
		sql = sql.replace("(status | ?)", "?");
		return super.query(sql, elementType, param);
	}

	@Override
	public long insertForLastId(String sql, StatementParameter param) {
		sql = this.replaceSql(sql);
		System.out.println("sql:" + sql);
		// return super.insertForLastId(sql, param);
		this.insertForBoolean(sql, param);
		return 1;
	}
}
