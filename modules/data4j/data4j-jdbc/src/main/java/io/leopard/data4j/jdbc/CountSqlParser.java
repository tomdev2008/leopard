package io.leopard.data4j.jdbc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CountSqlParser {

	private final String sql;
	private final StatementParameter param;

	private String countSql;
	private int limitParamCount;// 0表示没有，1表示1个，2表示2个

	public CountSqlParser(String sql, StatementParameter param) {
		this.sql = sql;
		this.param = param;
		this.parse();
	}

	private static final String LIMIT_REGEX = " limit (.*)";
	private static final Pattern LIMIT_PATTERN = Pattern.compile(LIMIT_REGEX, Pattern.CASE_INSENSITIVE);

	protected void parse() {
		String sql = this.sql;
		sql = sql.replace("select * from", "select count(*) from");
		sql = sql.replace("SELECT * FROM", "SELECT count(*) FROM");

		Matcher m = LIMIT_PATTERN.matcher(sql);
		if (m.find()) {
			String limitParam = m.group(1);
			this.limitParamCount = this.parseLimitParamCount(limitParam);
			this.countSql = sql.substring(0, m.start());
		}
		else {
			limitParamCount = 0;
			this.countSql = sql;
		}

		// System.out.println("limitParamCount:" + limitParamCount);
	}

	protected int parseLimitParamCount(String limitParam) {
		return StringUtils.countMatches(limitParam, "?");
		// return 2;
	}

	public String getCountSql() {
		return this.countSql;
	}

	public StatementParameter getCountParam() {
		if (this.limitParamCount <= 0) {
			return this.param;
		}
		Object[] values = this.param.getArgs();
		int max = values.length - this.limitParamCount;
		StatementParameter param = new StatementParameter();
		for (int i = 0; i < max; i++) {
			Class<?> type = this.param.getType(i);
			param.setObject(type, values[i]);
		}
		return param;
	}
}
