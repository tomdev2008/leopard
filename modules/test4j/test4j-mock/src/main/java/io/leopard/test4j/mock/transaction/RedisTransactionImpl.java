package io.leopard.test4j.mock.transaction;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.Json;
import io.leopard.burrow.util.ListUtil;
import io.leopard.burrow.util.ObjectUtil;
import io.leopard.burrow.util.SetUtil;
import io.leopard.jdbc.StatementParameter;
import io.leopard.redis.Redis;
import io.leopard.redis.RedisInfo;
import io.leopard.redis.util.IJedisPool;
import io.leopard.redis.util.RedisUtil;
import io.leopard.test4j.mock.transaction.redis.JedisTestnbImpl;
import io.leopard.test4j.mock.transaction.redis.RedisBean;
import io.leopard.test4j.mock.transaction.redis.RedisTransactionUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

/**
 * redis基于jdbc的实现(为了测试时可以事务回滚).
 * 
 * @author 阿海
 * 
 */
public class RedisTransactionImpl extends RedisBaseImpl implements Redis {

	// @Autowired

	// @Inject
	// protected JedisTestnbImpl jedisTestnbImpl;

	// @Inject
	// protected JedisBridgeImpl jedisBridgeImpl;

	@Override
	public String set(String key, String value) {
		int seconds = 60 * 60 * 24 * 30;
		return this.setex(key, seconds, value);
	}

	@Override
	public String get(String key) {
		return super.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return super.exist(key);
	}

	@Override
	public String type(String key) {
		throw new NotImplementedException();

	}

	@Override
	public Long expire(String key, int seconds) {
		long millis = System.currentTimeMillis() + (seconds * 1000L);
		Date expiry = new Date(millis);

		String sql = "update " + TABLE + " set expiry=? where `key`=?;";
		// String sql = "insert into " + TABLE +
		// "(`key`, score, value, expiry) values(?,?,?,?);";
		StatementParameter param = new StatementParameter();
		param.setDate(expiry);
		param.setString(key);

		// jdbc.printSQL(sql, param);
		jdbc.updateForBoolean(sql, param);
		return 1L;
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		throw new NotImplementedException();
	}

	@Override
	public Long ttl(String key) {
		throw new NotImplementedException();

	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		throw new NotImplementedException();

	}

	@Override
	public Boolean getbit(String key, long offset) {
		throw new NotImplementedException();

	}

	@Override
	public Long setrange(String key, long offset, String value) {
		// System.out.println("setrange key:" + key + " offset:" + offset +
		// " value:" + value);
		String oldValue = this.get(key);
		oldValue = StringUtils.defaultString(oldValue);
		StringBuilder sb = new StringBuilder(oldValue);

		if (true) {
			int diff = (int) (offset - sb.length());
			for (int i = 0; i < diff; i++) {
				sb.append((char) 2);
			}
		}
		if (true) {
			int start = (int) offset;
			int end = (int) (offset + value.length());
			sb.replace(start, end, value);
		}

		this.set(key, sb.toString());
		return (long) sb.length();
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		throw new NotImplementedException();

	}

	@Override
	public String getSet(String key, String value) {
		String oldValue = this.get(key);
		this.set(key, value);
		return oldValue;
	}

	@Override
	public Long setnx(String key, String value) {
		if (!this.exist(key)) {
			this.insert(key, value, SECONDS);
			return 1L;
		}
		return 0L;
	}

	@Override
	public synchronized String setex(String key, int seconds, String value) {
		// if (!this.exist(key)) {
		this.delete(key);
		this.insert(key, value, seconds);
		// }
		return value;
	}

	@Override
	public Long decrBy(String key, long integer) {
		String value = this.get(key);
		long num = NumberUtils.toLong(value);
		num = num - integer;
		super.delete(key);
		super.insert(key, Long.toString(num));
		return num;
	}

	@Override
	public Long decr(String key) {
		return this.decrBy(key, 1);
	}

	@Override
	public Long incrBy(String key, long integer) {
		String value = this.get(key);
		long num = NumberUtils.toLong(value);
		num = num + integer;
		super.delete(key);
		super.insert(key, Long.toString(num));
		return num;
	}

	@Override
	public Long incr(String key) {
		return this.incrBy(key, 1);
	}

	@Override
	public Long append(String key, String value) {
		String str = this.get(key);
		str = StringUtils.defaultString(str);
		str += value;
		this.set(key, str);
		return (long) value.length();// 字符
	}

	@Override
	public boolean append(String key, String value, int seconds) {
		String str = this.get(key);
		str = StringUtils.defaultString(str);
		str += value;
		this.set(key, str, seconds);
		return true;
	}

	@Override
	public String substr(String key, int start, int end) {
		throw new NotImplementedException();
	}

	@Override
	public synchronized Long hset(String key, String field, String value) {
		boolean exist = this.existByField(key, field);
		if (exist) {
			super.deleteByField(key, field);
		}
		super.insert(key, field, value);
		return super.boolToLong(!exist);
	}

	@Override
	public String hget(String key, String field) {
		// System.out.println("hget key:" + key + " field:" + field);
		String value = super.getByField(key, field);
		return value;
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		if (this.existByField(key, field)) {
			return 0L;
		}
		super.insert(key, field, value);
		return 1L;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		Iterator<Entry<String, String>> iterator = hash.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String field = entry.getKey();
			String value = entry.getValue();
			this.hset(key, field, value);
		}
		return "OK";
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		List<String> list = new ArrayList<String>();
		for (String field : fields) {
			String value = this.hget(key, field);
			list.add(value);
		}
		return list;
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		String oldValue = this.hget(key, field);
		long num = NumberUtils.toLong(oldValue);
		num = num + value;
		this.hset(key, field, Long.toString(num));
		// System.out.println(" oldValue:" + oldValue + " num:" + num);
		return num;
	}

	@Override
	public Boolean hexists(String key, String field) {
		return this.existByField(key, field);
	}

	@Override
	public Long hdel(String key, String... fields) {
		boolean success = this.deleteByFields(key, fields);

		return super.boolToLong(success);
	}

	@Override
	public Long hlen(String key) {
		Set<String> set = this.hkeys(key);
		return (long) SetUtil.size(set);
	}

	@Override
	public Set<String> hkeys(String key) {
		String sql = "select score from " + TABLE + " where `key`=?";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		List<String> list = jdbc.queryForStrings(sql, param);
		return this.toSet(list);
	}

	@Override
	public List<String> hvals(String key) {
		String sql = "select value from " + TABLE + " where `key`=?";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		List<String> list = jdbc.queryForStrings(sql, param);
		return list;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		String sql = "select score, value from " + TABLE + " where `key`=?";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		List<RedisBean> list = jdbc.queryForList(sql, RedisBean.class, param);
		if (list == null) {
			return null;
		}
		Map<String, String> result = new LinkedHashMap<String, String>();
		for (RedisBean bean : list) {
			String field = bean.getField();
			String value = bean.getValue();
			result.put(field, value);
		}
		return result;
	}

	@Override
	public Long rpush(String key, String... strings) {
		long score = (long) super.getScore(key, false);
		if (score <= 0) {
			score = 100000 / 2L;
		}
		score++;
		this.inserts(key, score, strings);
		return (long) super.count(key);
	}

	@Override
	public Long lpush(String key, String... strings) {
		long score = (long) super.getScore(key, true);
		if (score <= 0) {
			score = 100000 / 2L;
		}
		score--;
		this.inserts(key, score, strings);
		return (long) super.count(key);
	}

	@Override
	public Long llen(String key) {
		int count = this.count(key);
		return (long) count;
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		if (end == -1) {
			end = LONG_MAX_VALUE;
		}
		long size = end - start + 1;

		AssertUtil.assertTrue(size > 0, "size不能小于0[" + size + "." + start + "." + end + "].");
		String sql = "select value from " + TABLE + " where `key`=? order by " + getScoreString() + " asc limit ?,?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setLong(start);
		param.setLong(size);

		// jdbc.printSQL(sql, param);
		List<String> list = jdbc.queryForStrings(sql, param);
		return list;
	}

	@Override
	public String ltrim(String key, long start, long end) {
		throw new NotImplementedException();

	}

	@Override
	public String lindex(String key, long index) {
		throw new NotImplementedException();

	}

	@Override
	public String lset(String key, long index, String value) {
		throw new NotImplementedException();

	}

	@Override
	public Long lrem(String key, long count, String value) {
		throw new NotImplementedException();

	}

	@Override
	public String lpop(String key) {
		Set<Tuple> set = this.zrangeWithScores(key, 0, 0);
		if (SetUtil.isEmpty(set)) {
			return null;
		}
		Tuple tuple = set.iterator().next();
		String field = Double.toString(tuple.getScore());
		super.deleteByField(key, field);

		String element = tuple.getElement();
		return element;
	}

	@Override
	public String rpop(String key) {
		Set<Tuple> set = this.zrevrangeWithScores(key, 0, 0);
		if (SetUtil.isEmpty(set)) {
			return null;
		}
		Tuple tuple = set.iterator().next();
		String field = Double.toString(tuple.getScore());
		super.deleteByField(key, field);

		String element = tuple.getElement();
		return element;
	}

	@Override
	public Long sadd(String key, String... members) {
		double score = this.getScore(key, false);// 随便用的
		// System.out.println("key:" + key + " score:" + score);
		if (score < 0) {
			score = 0;
		}
		score++;
		long count = 0;
		for (String member : members) {
			long num = this.zadd(key, score, member);
			count += num;
		}
		return count;
	}

	@Override
	public Set<String> smembers(String key) {
		return this.zrevrange(key, 0, Integer.MAX_VALUE);
	}

	@Override
	public Long srem(String key, String... members) {
		return this.zrem(key, members);
	}

	@Override
	public String spop(String key) {
		Set<Tuple> set = this.zrevrangeWithScores(key, 0, -1);
		if (SetUtil.isEmpty(set)) {
			return null;
		}
		// System.out.println("set:" + set);
		Tuple tuple = set.iterator().next();
		String field = Double.toString(tuple.getScore());
		super.deleteByField(key, field);

		String element = tuple.getElement();
		return element;
	}

	@Override
	public Long scard(String key) {
		return this.zcard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {
		RedisBean bean = this.getBean(key, member);
		// return (bean != null);
		return ObjectUtil.isNotNull(bean);
	}

	@Override
	public String srandmember(String key) {
		AssertUtil.assertNotEmpty(key, "参数key不能为空.");
		String sql = "select * from " + TABLE + " where `key`=? order by rand() limit 1;";
		StatementParameter param = new StatementParameter();
		param.setString(key);

		jdbc.printSQL(logger, sql, param);
		RedisBean bean = jdbc.query(sql, RedisBean.class, param);
		if (bean == null) {
			return null;
		}
		return bean.getValue();
	}

	// @Override
	public List<String> srandmember(String key, int count) {
		throw new NotImplementedException();
	}

	@Override
	public synchronized Long zadd(String key, double score, String member) {
		// System.err.println("zadd " + key + " " + score + " " + member);
		this.delete(key, member);
		this.insert(key, score, member);
		return 1L;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		Set<Tuple> set = this.zrangeWithScores(key, start, end);
		return super.tupleToString(set);

		// if (end == -1) {
		// end = Integer.MAX_VALUE;
		// }
		// int size = end - start;
		//
		// AssertUtil.assertTrue(size > 0, "size不能小于0[" + size + "." + start +
		// "." + end + "].");
		// String sql = "select value from " + TABLE +
		// " where `key`=? order by score+0 asc limit ?,?;";
		// StatementParameter param = new StatementParameter();
		// param.setString(key);
		// param.setInt(start);
		// param.setInt(size);
		//
		// super.printSQL(sql, param);
		// List<String> list = super.queryForStrings(sql, param);
		// return this.toSet(list);
	}

	@Override
	public Long zrem(String key, String... members) {
		boolean success = super.deletes(key, members);
		// System.out.println("success:" + success);
		return super.boolToLong(success);
	}

	@Override
	public synchronized Double zincrby(String key, double score, String member) {
		RedisBean bean = super.getBean(key, member);
		double totalScore = score;
		if (bean != null) {
			totalScore += Double.parseDouble(bean.getScore());
		}
		this.zadd(key, totalScore, member);
		return totalScore;
	}

	@Override
	public Long zrank(String key, String member) {
		Set<String> set = this.zrange(key, 0, LONG_MAX_VALUE);
		return super.index(set, member);
	}

	@Override
	public Long zrevrank(String key, String member) {
		Set<String> set = this.zrevrange(key, 0, LONG_MAX_VALUE);
		return super.index(set, member);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		Set<Tuple> set = this.zrevrangeWithScores(key, start, end);
		return super.tupleToString(set);

		// int size = end - start;
		// String sql = "select value from " + TABLE +
		// " where `key`=? order by score+0 desc limit ?,?;";
		// StatementParameter param = new StatementParameter();
		// param.setString(key);
		// param.setInt(start);
		// param.setInt(size);
		// List<String> list = super.queryForStrings(sql, param);
		// return this.toSet(list);
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		AssertUtil.assertNotEmpty(key, "参数key不能为空.");
		if (end == -1) {
			end = LONG_MAX_VALUE;
		}
		long size = end - start;
		if (size < Long.MAX_VALUE) {
			size++;
		}

		AssertUtil.assertTrue(size > 0, "size不能小于0[" + size + "." + start + "." + end + "].");
		String sql = "select * from " + TABLE + " where `key`=? order by " + getScoreString() + " asc limit ?,?;";
		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setLong(start);
		param.setLong(size);

		// jdbc.printSQL(sql, param);
		List<RedisBean> list = jdbc.queryForList(sql, RedisBean.class, param);
		return this.toTupleSet(list);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		// System.out.println("zrevrangeWithScores key:" + key + " start:" +
		// start + " end:" + end);
		if (end == -1 || end > LONG_MAX_VALUE) {
			end = LONG_MAX_VALUE;
		}
		long size = end - start;
		if (size < Long.MAX_VALUE) {
			size++;
		}

		String sql = "select * from " + TABLE + " where `key`=? order by " + getScoreString() + " desc limit ?,?;";
		// String sql = "select * from " + TABLE +
		// " where `key`=? order by CAST(score as Double) desc limit ?,?;";
		// String sql =
		// "select `key`,value,expiry, CAST(score as Double) score from " +
		// TABLE +
		// " where `key`=? order by CAST(score+0 as Double) desc limit ?,?;";

		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setLong(start);
		param.setLong(size);

		// jdbc.printSQL(sql, param);
		List<RedisBean> list = jdbc.queryForList(sql, RedisBean.class, param);
		// logger.info("list:" + list);
		return this.toTupleSet(list);
	}

	@Override
	public Long zcard(String key) {
		Set<String> set = this.zrange(key, 0, LONG_MAX_VALUE);
		return (long) set.size();
	}

	@Override
	public Double zscore(String key, String member) {
		RedisBean bean = this.getBean(key, member);
		if (bean == null) {
			return null;
		}
		else {
			return Double.parseDouble(bean.getScore());
		}
	}

	@Override
	public Double zscore(final String key, final long member) {
		return this.zscore(key, Long.toString(member));
	}

	@Override
	public List<String> sort(String key) {
		throw new NotImplementedException();

	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		throw new NotImplementedException();

	}

	@Override
	public Long zcount(String key, double min, double max) {
		Set<String> set = this.zrangeByScore(key, min, max);
		return (long) SetUtil.size(set);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return this.zrangeByScore(key, min, max, 0, Integer.MAX_VALUE);

	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return this.zrevrangeByScore(key, max, min, 0, Integer.MAX_VALUE);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		Set<Tuple> set = this.zrangeByScoreWithScores(key, min, max, offset, count);
		return super.tupleToString(set);
		// if (max == -1) {
		// max = Double.MAX_VALUE;
		// }
		// String sql = "select value from " + TABLE +
		// " where `key`=? and score+0>=? and score+0<=? order by score+0 asc limit ?,?";
		//
		// StatementParameter param = new StatementParameter();
		// param.setString(key);
		// param.setDouble(min);
		// param.setDouble(max);
		// param.setInt(offset);
		// param.setInt(count);
		// super.printSQL(sql, param);
		// List<String> list = super.queryForStrings(sql, param);
		// super.printSQL(sql, param);
		// return this.toSet(list);

	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		String sql = "select value from " + TABLE + " where `key`=? and " + getScoreString() + ">=? and " + getScoreString() + "<=? order by " + getScoreString() + " desc limit ?,?";

		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setDouble(min);
		param.setDouble(max);
		param.setInt(offset);
		param.setInt(count);
		// jdbc.printSQL(sql, param);
		List<String> list = jdbc.queryForStrings(sql, param);
		return this.toSet(list);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		int offset = 0;
		int count = Integer.MAX_VALUE;
		return this.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		throw new NotImplementedException();

	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		if (max == -1) {
			max = Double.MAX_VALUE;
		}
		String sql = "select * from " + TABLE + " where `key`=? and " + getScoreString() + ">=? and " + getScoreString() + "<=? order by " + getScoreString() + " asc limit ?,?";

		StatementParameter param = new StatementParameter();
		param.setString(key);
		param.setDouble(min);
		param.setDouble(max);
		param.setInt(offset);
		param.setInt(count);
		// jdbc.printSQL(sql, param);

		List<RedisBean> list = jdbc.queryForList(sql, RedisBean.class, param);
		return this.toTupleSet(list);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		throw new NotImplementedException();
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		Set<String> set = this.zrangeByScore(key, start, end);
		// System.out.println("set:" + set + " start:" + start + " end:" + end);
		if (SetUtil.isEmpty(set)) {
			return new Long(0);
		}
		for (String member : set) {
			super.delete(key, member);
		}
		return new Long(set.size());
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		throw new NotImplementedException();
	}

	@Override
	public Jedis getResource() {
		Jedis jedis = MockTransactionModule.getInstance(JedisTestnbImpl.class);
		// System.out.println("jedis:" + jedis);
		return jedis;
	}

	@Override
	public boolean rename(String oldkey, String newkey) {
		this.del(newkey);

		String sql = "update " + TABLE + " set key=? where `key`=?;";
		StatementParameter param = new StatementParameter();
		param.setString(newkey);
		param.setString(oldkey);
		return jdbc.updateForBoolean(sql, param);
	}

	@Override
	public Transaction multi() {
		throw new NotImplementedException();
	}

	@Override
	public boolean flushDB() {
		String sql = "delete from " + TABLE;
		StatementParameter param = new StatementParameter();
		return this.jdbc.updateForBoolean(sql, param);
		// throw new NotImplementedException();

	}

	@Override
	public RedisInfo info() {
		throw new NotImplementedException();

	}

	@Override
	public boolean rename(String oldkey, String newkey, int seconds) {
		throw new NotImplementedException();

	}

	@Override
	public long getUsedMemory() {
		throw new NotImplementedException();

	}

	@Override
	public long dbSize() {
		throw new NotImplementedException();

	}

	@Override
	public String set(String key, String value, int seconds) {

		this.set(key, value);
		this.expire(key, seconds);
		return value;
	}

	@Override
	public boolean flushAll() {
		throw new NotImplementedException();

	}

	@Override
	public boolean set(List<String> keyList, List<String> valueList) {
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);
			this.set(key, value);
		}
		return true;
	}

	@Override
	public boolean append(List<String> keyList, List<String> valueList, int seconds) {
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);
			// this.set(key, value, seconds);
			this.append(key, value, seconds);
		}
		return true;
	}

	@Override
	public Long del(String key) {
		boolean success = super.delete(key);
		if (success) {
			return 1L;
		}
		return 0L;
	}

	@Override
	public Long del(String... keys) {
		long count = 0;
		for (String key : keys) {
			boolean success = super.delete(key);
			if (success) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void returnResource(Jedis jedis) {

	}

	@Override
	public List<String> mget(String... keys) {
		List<String> list = new ArrayList<String>();
		for (String key : keys) {
			String value = this.get(key);
			list.add(value);
		}
		return list;
	}

	@Override
	public Long zinterstore(String dstkey, String... sets) {
		int[] weights = RedisUtil.getDefaultWeights(sets);
		ZParams params = new ZParams().aggregate(ZParams.Aggregate.SUM);
		params.weights(weights);
		return this.zinterstore(dstkey, params, sets);
		// throw new NotImplementedException();
	}

	@Override
	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		super.delete(dstkey);
		// ZParams zParams = new ZParams().aggregate(ZParams.Aggregate.SUM);
		List<Double> weights = RedisUtil.getWeights(params);
		weights = ListUtil.defaultList(weights);
		for (int i = 0; i < sets.length; i++) {
			weights.add(1D);
		}

		ZParams.Aggregate aggregate = RedisUtil.getAggregate(params);
		StringBuilder where = new StringBuilder("where (");
		// where (t0.value=t1.value);
		for (int i = 0; i < sets.length; i++) {
			where.append("t" + i + ".value=");
		}
		where.deleteCharAt(where.length() - 1);
		where.append(')');
		logger.info("where:" + where.toString());

		String unionSql = RedisTransactionUtil.getUnionSql(weights, sets);
		String sql = unionSql + " " + where.toString();
		System.out.println(sql);
		List<Map<String, Object>> list = jdbc.queryForMaps(sql);
		if (ListUtil.isEmpty(list)) {
			return 0L;
		}

		for (Map<String, Object> map : list) {

			String member = (String) map.get("value");
			Map<String, Object> scoreMap = toScoreMap(map, sets.length);
			Json.print(map, "scoreMap");
			double score = RedisTransactionUtil.aggregateScore(aggregate, scoreMap);
			this.zadd(dstkey, score, member);
		}
		return (long) list.size();
	}

	protected Map<String, Object> toScoreMap(Map<String, Object> map, int count) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < count; i++) {
			String key = "score" + i;
			if (!map.containsKey(key)) {
				continue;
			}
			Object score = map.get(key);
			result.put(key, score);
		}
		return result;
	}

	@Override
	public Set<String> keys(String pattern) {
		throw new NotImplementedException();

	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		// TODO ahai 未实现
		return 1L;
	}

	@Override
	public String getServerInfo() {
		return "redis.transaction.impl";
	}

	// @Override
	// public String hget(String key, int field) {
	// return this.hget(key, Integer.toString(field));
	// }

	@Override
	public String hget(String key, long field) {
		return this.hget(key, Long.toString(field));
	}

	// @Override
	// public Long hset(String key, int field, String value) {
	// Long result = this.hset(key, Integer.toString(field), value);
	// return result;
	// }

	@Override
	public Long hset(String key, long field, String value) {
		Long result = this.hset(key, Long.toString(field), value);
		return result;
	}

	// @Override
	// public Long hdel(String key, int field) {
	// return this.hdel(key, Integer.toString(field));
	// }

	@Override
	public Long hdel(String key, long field) {
		return this.hdel(key, Long.toString(field));
	}

	@Override
	public Long zadd(String key, double score, long member) {
		return this.zadd(key, score, Long.toString(member));
	}

	@Override
	public Long zrem(String key, long member) {
		return this.zrem(key, Long.toString(member));
	}

	@Override
	public Set<String> zunionStoreInJava(String... sets) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets) {
		throw new NotImplementedException();
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		throw new NotImplementedException();
	}

	@Override
	public Long zcount(String key, String min, String max) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		throw new NotImplementedException();
	}

	@Override
	public Long lpushx(String key, String string) {
		throw new NotImplementedException();
	}

	@Override
	public Long rpushx(String key, String string) {
		throw new NotImplementedException();
	}

	@Override
	public Long setrange(String key, int offset, String value) {

		return this.setrange(key, (long) offset, value);
	}

	// @Override
	// public Long zadd2(String key, Map<String, Double> scoreMembers) {
	// Iterator<Entry<String, Double>> iterator =
	// scoreMembers.entrySet().iterator();
	// long count = 0;
	// while (iterator.hasNext()) {
	// Entry<String, Double> entry = iterator.next();
	// double score = entry.getValue();
	// String member = entry.getKey();
	// Long result = this.zadd(key, score, member);
	// count += result;
	// }
	// return count;
	// }

	@Override
	public Object eval(String arg0) {
		throw new NotImplementedException();
	}

	@Override
	public Object eval(String arg0, int arg1, String... arg2) {
		throw new NotImplementedException();
	}

	@Override
	public Object evalAssertSha(String arg0, String arg1) {
		throw new NotImplementedException();
	}

	@Override
	public String evalReturnSha(String arg0) {
		throw new NotImplementedException();
	}

	@Override
	public Object evalsha(String arg0) {
		throw new NotImplementedException();
	}

	@Override
	public Object evalsha(String arg0, List<String> arg1, List<String> arg2) {
		throw new NotImplementedException();
	}

	@Override
	public Object evalsha(String arg0, int arg1, String... arg2) {
		throw new NotImplementedException();
	}

	// @Override
	// public Ludis getLudis() {
	// throw new NotImplementedException();
	// }

	@Override
	public String bgrewriteaof() {
		throw new NotImplementedException();
	}

	@Override
	public String bgsave() {
		throw new NotImplementedException();
	}

	@Override
	public String save() {
		throw new NotImplementedException();
	}

	@Override
	public IJedisPool getJedisPool() {
		throw new NotImplementedException();
	}

	@Override
	public Long publish(String channel, String message) {
		JedisPubSub jedisPubSub = this.channelMap.get(channel);
		if (jedisPubSub != null) {
			jedisPubSub.onMessage(channel, message);
		}
		return 1L;
	}

	private Map<String, JedisPubSub> channelMap = new ConcurrentHashMap<String, JedisPubSub>();

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		for (String channel : channels) {
			channelMap.put(channel, jedisPubSub);
		}
	}

	@Override
	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> sdiff(String... keys) {
		Set<String> diffSet = this.smembers(keys[0]);
		System.out.println("diffSet:" + diffSet);
		for (int i = 1; i < keys.length; i++) {
			Set<String> set = this.smembers(keys[i]);
			if (SetUtil.isEmpty(set)) {
				continue;
			}
			diffSet.removeAll(set);
		}
		if (diffSet.isEmpty()) {
			return null;
		}
		return diffSet;
	}

	@Override
	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		// FIXME ahai 未实现
		return 1L;
	}

	@Override
	public String randomKey() {
		throw new NotImplementedException();
	}

	@Override
	public Long persist(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		throw new NotImplementedException();
	}

	@Override
	public Long strlen(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long lpushx(String key, String... string) {
		throw new NotImplementedException();
	}

	@Override
	public Long rpushx(String key, String... string) {
		throw new NotImplementedException();
	}

	@Override
	public List<String> blpop(String arg) {
		throw new NotImplementedException();
	}

	@Override
	public List<String> brpop(String arg) {
		throw new NotImplementedException();
	}

	@Override
	public String echo(String string) {
		throw new NotImplementedException();
	}

	@Override
	public Long move(String key, int dbIndex) {
		throw new NotImplementedException();
	}

	@Override
	public Long bitcount(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		throw new NotImplementedException();
	}

	@Override
	public Long pfadd(String key, String... elements) {
		throw new NotImplementedException();
	}

	@Override
	public long pfcount(String key) {
		throw new NotImplementedException();
	}

	@Override
	public String set(String key, String arg1, String arg2, String arg3, long arg4) {
		throw new NotImplementedException();
	}

}
