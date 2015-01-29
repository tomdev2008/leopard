package io.leopard.data4j.jdbc;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;
import io.leopard.burrow.util.DateTime;

import java.util.Date;

public class SqlUtil {

	/**
	 * 获取拼接参数后的sql.
	 * 
	 * @param sql
	 *            sql
	 * @param param
	 *            参数列表
	 * @return 拼接后的sql
	 */
	public static String getSQL(String sql, StatementParameter param) {
		int i = 0;
		while (sql.indexOf('?') > -1) {
			if (param == null) {
				throw new InvalidParamDataAccessException("没有设置参数.");
			}
			if (i >= param.size()) {
				return sql;
			}
			Class<?> type = param.getType(i);
			Object obj = param.getObject(i);
			String value = getValue(type, obj);
			sql = sql.substring(0, sql.indexOf('?')) + value + sql.substring(sql.indexOf('?') + 1, sql.length());
			i++;
		}
		return sql;//
	}

	protected static String getValue(Class<?> type, Object obj) {
		String value;
		if (type.equals(String.class)) {
			value = "'" + ((String) obj) + "'";
		}

		else if (type.equals(Date.class)) {
			value = "'" + DateTime.getTime(((Date) obj)) + "'";
		}
		else if (type.equals(OnlyDate.class)) {
			value = "'" + ((OnlyDate) obj).toString() + "'";
		}
		else if (type.equals(Month.class)) {
			value = "'" + ((Month) obj).toString() + "'";
		}
		else if (type.equals(Integer.class)) {
			value = Integer.toString(((Integer) obj));
		}
		else if (type.equals(Boolean.class)) {
			value = (((Boolean) obj) ? 1 : 0) + "";
		}
		else if (type.equals(Float.class)) {
			value = Float.toString(((Float) obj));
		}
		else if (type.equals(Double.class)) {
			value = Double.toString(((Double) obj));
		}
		else if (type.equals(Long.class)) {
			value = Long.toString(((Long) obj));
		}
		else if (type.equals(byte[].class)) {
			value = "byte[]";
		}
		else {
			throw new InvalidParamDataAccessException("未知参数类型[" + type.getName() + "].");
			// throw new InvalidParamDataAccessException("未知数据类型[" + type +
			// "]");
		}
		return value;
	}

	/**
	 * 转换成select count(*)语句.
	 * 
	 * @param sql
	 * @return
	 */
	public static String toCountSql(String sql) {
		// TODO ahai 未完整实现
		sql = sql.replace("select * from", "select count(*) from");
		sql = sql.replace("SELECT * FROM", "SELECT count(*) FROM");
		sql = sql.replaceAll(" LIMIT.*", "");
		sql = sql.replaceAll(" limit.*", "");
		return sql;
	}

	// public static String getTimeRangeSql(String fieldName, Date startTime,
	// Date endTime) {
	// StringBuilder sb = new StringBuilder();
	// return sb.toString();
	// }
}
