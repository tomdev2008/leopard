package io.leopard.test4j.mock.transaction;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.util.ListUtil;
import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.data4j.jdbc.StatementParameter;
import io.leopard.test4j.mock.transaction.redis.RedisBean;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.CannotAcquireLockException;

import redis.clients.jedis.Tuple;

import com.google.inject.Inject;

public class RedisBaseImpl extends ContextImpl {

	@Inject
	protected Jdbc jdbc;

	protected static final String TABLE = "memcache";
	protected static final int SECONDS = 60 * 60 * 24 * 30;
	protected static final long LONG_MAX_VALUE = Integer.MAX_VALUE - 10L;

	// protected String toNum(String fieldName) {
	//
	// return "cast(" + fieldName + " as double)";
	// }

	protected String get(String key) {
		String sql = "select value from " + TABLE + " where `key`=?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		String value = jdbc.queryForString(sql, param);
		return value;
	}

	protected int count(String key) {
		String sql = "select count(*) from " + TABLE + " where `key`=?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		int count = jdbc.queryForInt(sql, param);
		return count;
	}

	protected String getScoreString() {
		return "CAST(score as Double)";
	}

	protected double getScore(String key, boolean asc) {
		String order = asc ? "asc" : "desc";
		String sql = "select " + getScoreString() + " from " + TABLE + " where `key`=? order by " + this.getScoreString() + " " + order + " limit 1;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		Integer count = jdbc.queryForInt(sql, param);
		// super.printSQL(sql, param);
		if (count == null) {
			return 0;
		}
		return count;
	}

	protected String get(String key, String member) {
		RedisBean bean = this.getBean(key, member);
		if (bean == null) {
			return null;
		}
		return bean.getValue();
	}

	public RedisBean getBean(String key, String member) {
		AssertUtil.assertNotEmpty(key, "参数key不能为空.");
		AssertUtil.assertNotEmpty(member, "参数member不能为空.");
		String sql = "select * from " + TABLE + " where `key`=? and value=?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setString(member);

		jdbc.printSQL(logger, sql, param);
		RedisBean bean = jdbc.query(sql, RedisBean.class, param);
		return bean;
	}

	protected String getByField(String key, String field) {
		String sql = "select value from " + TABLE + " where `key`=? and score=?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setString(field);
		String value = jdbc.queryForString(sql, param);
		// jdbc.printSQL(sql, param);
		return value;
	}

	public boolean exist(String key) {
		String value = this.get(key);
		return (value != null);
	}

	protected boolean exist(String key, String member) {
		String value = this.get(key, member);
		return (value != null);
	}

	protected synchronized boolean existByField(String key, String field) {
		String value = this.getByField(key, field);
		return (value != null);
	}

	protected synchronized boolean delete(String key) {
		String sql = "delete from " + TABLE + " where `key`=?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		try {
			return jdbc.updateForBoolean(sql, param);
		}
		catch (CannotAcquireLockException e) {
			jdbc.printSQL(logger, sql, param);
			throw e;
		}
	}

	protected synchronized boolean deletes(String key, String... members) {
		boolean success = true;
		for (String member : members) {
			success = success & this.delete(key, member);
		}
		return success;
	}

	protected synchronized boolean delete(String key, String member) {
		AssertUtil.assertNotEmpty(key, "参数key不能为空.");
		AssertUtil.assertNotEmpty(member, "参数member不能为空.");
		String sql = "delete from " + TABLE + " where `key`=? and value=?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setString(member);
		boolean success = jdbc.updateForBoolean(sql, param);
		// System.out.println("delete key:" + key + " member:" + member + " success:" + success);
		return success;
	}

	protected synchronized boolean deleteByFields(String key, String... fields) {
		if (fields.length == 0) {
			return false;
		}
		boolean success = true;
		for (String field : fields) {
			success = success & this.deleteByField(key, field);
		}
		return success;
	}

	protected synchronized boolean deleteByField(String key, String field) {
		String sql = "delete from " + TABLE + " where `key`=? and score=?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setString(field);
		boolean flag = jdbc.updateForBoolean(sql, param);
		// String sql2 = jdbc.getSQL(sql, param);
		// System.out.println("flag:" + flag + " sql2:" + sql2);
		return flag;
	}

	protected synchronized boolean insert(String key, String value) {
		return this.insert(key, value, SECONDS);
	}

	protected synchronized boolean insert(String key, String value, int seconds) {
		long millis = System.currentTimeMillis() + (seconds * 1000L);
		Date expiry = new Date(millis);
		String field = "";
		return this.insert(key, field, value, expiry);
	}

	protected synchronized boolean inserts(String key, double score, String... values) {
		boolean success = false;
		for (String value : values) {
			success = success & this.insert(key, score, value);
		}
		return success;
	}

	protected synchronized boolean insert(String key, double score, String value) {
		int seconds = 60 * 60 * 24 * 30;
		long millis = System.currentTimeMillis() + (seconds * 1000L);
		Date expiry = new Date(millis);
		String field = Double.toString(score);
		return this.insert(key, field, value, expiry);
	}

	protected synchronized boolean insert(String key, String field, String value) {
		int seconds = 60 * 60 * 24 * 30;
		long millis = System.currentTimeMillis() + (seconds * 1000L);
		Date expiry = new Date(millis);
		return this.insert(key, field, value, expiry);
	}

	protected synchronized boolean insert(String key, String field, String value, Date expiry) {
		AssertUtil.assertNotEmpty(key, "参数key不能为空.");
		AssertUtil.assertNotNull(value, "参数value不能为null.");
		String sql = "insert into " + TABLE + "(`key`, score, value, expiry) values(?,?,?,?);";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setString(field);
		param.setString(value);
		param.setDate(expiry);
		jdbc.printSQL(logger, sql, param);
		return jdbc.updateForBoolean(sql, param);
	}

	protected Long boolToLong(boolean bool) {
		if (bool) {
			return 1L;
		}
		else {
			return 0L;
		}
	}

	protected Long index(Set<String> set, String member) {
		if (set == null) {
			return null;
		}
		long index = 0;
		for (String str : set) {
			if (str.equals(member)) {
				return index;
			}
			index++;
		}
		return null;
	}

	public Set<String> tupleToString(Set<Tuple> set) {
		Set<String> result = new LinkedHashSet<String>();
		for (Tuple tuple : set) {
			String element = tuple.getElement();
			result.add(element);
		}
		return result;
	}

	public Set<Double> tupleToScores(Set<Tuple> set) {
		Set<Double> result = new LinkedHashSet<Double>();
		for (Tuple tuple : set) {
			Double score = tuple.getScore();
			result.add(score);
		}
		return result;
	}

	protected Set<String> toSet(List<String> list) {
		Set<String> set = ListUtil.toSet(list);
		if (set == null) {
			return null;
		}
		if (set.size() != list.size()) {
			throw new RuntimeException("list转换成set之后，size不一样了[" + set.size() + "." + list.size() + "].");
		}
		return set;
	}

	protected Set<Tuple> toTupleSet(List<RedisBean> list) {
		if (list == null) {
			return null;
		}
		Set<Tuple> set = new LinkedHashSet<Tuple>();
		for (RedisBean bean : list) {
			String element = bean.getValue();
			Double score = Double.parseDouble(bean.getScore());
			Tuple tuple = new Tuple(element, score);
			set.add(tuple);
		}
		return set;
	}
	
	

}
