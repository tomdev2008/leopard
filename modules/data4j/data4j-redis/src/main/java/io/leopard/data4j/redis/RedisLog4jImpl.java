package io.leopard.data4j.redis;

import io.leopard.data4j.env.LogDirLeiImpl;
import io.leopard.data4j.log.Level;
import io.leopard.data4j.log.Log4jUtil;
import io.leopard.data4j.redis.util.IJedisPool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

/**
 * Redis单机实现(操作Redis服务器之前会写入log4j日志).
 * 
 * @author 阿海
 * 
 */
public class RedisLog4jImpl extends AbstractRedis implements Redis {
	// private final Log logDb = LogFactory.getLog("REDISLOG." +
	// RedisLog4jImpl.class.getName());
	private final Log logDb = getRedisLogger(this.getClass());

	public static Log getRedisLogger(Class<?> clazz) {
		String filename = LogDirLeiImpl.getLogDir() + "/redis.log";
		return Log4jUtil.getLogger("REDISLOG." + clazz.getName(), Level.DEBUG, filename);
	}

	private Redis redis;
	// private Redis redisImpl;

	private String server;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public void setServer(String server) {
		// System.err.println("server:" + server);
		this.server = server;
	}

	@Override
	public void init() {
		// System.err.println("init server:" + server);

		super.init();
		if (StringUtils.isNotEmpty(server)) {
			RedisImpl redisImpl = new RedisImpl(server, maxActive, initialPoolSize, enableBackup, backupTime, timeout);
			redisImpl.init();
			this.redis = redisImpl;
		}
	}

	@Override
	public void destroy() {
		if (redis != null) {
			this.redis.destroy();
		}
		super.destroy();
	}

	private String encode(String content) {
		try {
			return URLEncoder.encode(content, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected String format(double score) {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setGroupingUsed(false);
		String s = formatter.format(score);
		return s;
	}

	@Override
	public String set(String key, String value) {
		String result = redis.set(key, value);
		this.logDb.debug("set " + key + " " + encode(value));
		return result;
	}

	@Override
	public String get(String key) {
		return redis.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return this.redis.exists(key);
	}

	@Override
	public String type(String key) {
		return this.redis.type(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		Long result = this.redis.expire(key, seconds);
		this.logDb.debug("expire " + key + " " + seconds);
		return result;
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		Long result = this.redis.expireAt(key, unixTime);
		this.logDb.debug("expireAt " + key + " " + unixTime);
		return result;
	}

	@Override
	public Long ttl(String key) {
		return this.redis.ttl(key);
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		boolean result = this.redis.setbit(key, offset, value);
		this.logDb.debug("setbit " + key + " " + offset + " " + value);
		return result;
	}

	@Override
	public Boolean getbit(String key, long offset) {
		return this.redis.getbit(key, offset);
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		long result = this.redis.setrange(key, offset, value);
		this.logDb.debug("setrange " + key + " " + offset + " " + encode(value));
		return result;
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		return this.redis.getrange(key, startOffset, endOffset);
	}

	@Override
	public String getSet(String key, String value) {
		return this.redis.getSet(key, value);
	}

	@Override
	public Long setnx(String key, String value) {
		Long result = this.redis.setnx(key, value);
		this.logDb.debug("setnx " + key + " " + encode(value));
		return result;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		String result = this.redis.setex(key, seconds, value);
		this.logDb.debug("setex " + key + " " + seconds + " " + encode(value));
		return result;
	}

	@Override
	public Long decrBy(String key, long integer) {
		Long result = this.redis.decrBy(key, integer);
		this.logDb.debug("decrBy " + key + " " + integer);
		return result;
	}

	@Override
	public Long decr(String key) {
		Long result = this.redis.decr(key);
		this.logDb.debug("decr " + key);
		return result;
	}

	@Override
	public Long incrBy(String key, long integer) {
		Long result = this.redis.incrBy(key, integer);
		this.logDb.debug("incrBy " + key + " " + integer);
		return result;
	}

	@Override
	public Long incr(String key) {
		Long result = this.redis.incr(key);
		this.logDb.debug("incr " + key);
		return result;
	}

	@Override
	public Long append(String key, String value) {
		Long result = this.redis.append(key, value);
		this.logDb.debug("append " + key + " " + encode(value));
		return result;
	}

	@Override
	public String substr(String key, int start, int end) {
		return this.redis.substr(key, start, end);
	}

	// @Override
	// public Long hset(String key, int field, String value) {
	// return this.hset(key, Integer.toString(field), value);
	// }

	@Override
	public Long hset(String key, long field, String value) {
		return this.hset(key, Long.toString(field), value);
	}

	@Override
	public Long hset(String key, String field, String value) {
		Long result = this.redis.hset(key, field, value);
		this.logDb.debug("hset " + key + " " + field + " " + encode(value));
		return result;
	}

	// @Override
	// public String hget(String key, int field) {
	// return this.hget(key, Integer.toString(field));
	// }

	@Override
	public String hget(String key, long field) {
		return this.hget(key, Long.toString(field));
	}

	@Override
	public String hget(String key, String field) {
		return this.redis.hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		Long result = this.redis.hsetnx(key, field, value);
		this.logDb.debug("hsetnx " + key + " " + field + " " + encode(value));
		return result;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		String result = this.redis.hmset(key, hash);
		if (hash != null) {
			Iterator<Entry<String, String>> iterator = hash.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				String field = entry.getKey();
				String value = entry.getValue();
				this.logDb.debug("hmset " + key + " " + this.encode(field) + " " + this.encode(value));
			}
		}
		return result;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return this.redis.hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		Long result = this.redis.hincrBy(key, field, value);
		this.logDb.debug("hincrBy " + key + " " + field + " " + value);
		return result;
	}

	@Override
	public Boolean hexists(String key, String field) {
		return this.redis.hexists(key, field);
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
	public Long hdel(String key, String... field) {
		Long result = this.redis.hdel(key, field);
		this.logDb.debug("hdel " + key + " " + field);
		return result;
	}

	@Override
	public Long hlen(String key) {
		return this.redis.hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) {
		return this.redis.hkeys(key);
	}

	@Override
	public List<String> hvals(String key) {
		return this.redis.hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return this.redis.hgetAll(key);
	}

	@Override
	public Long rpush(String key, String... string) {
		Long result = this.redis.rpush(key, string);
		this.logDb.debug("rpush " + key + " " + encode(StringUtils.join(string, " ")));
		return result;
	}

	@Override
	public Long lpush(String key, String... string) {
		Long result = this.redis.lpush(key, string);
		this.logDb.debug("lpush " + key + " " + encode(StringUtils.join(string, " ")));
		return result;
	}

	@Override
	public Long llen(String key) {
		return this.redis.llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return this.redis.lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return this.redis.ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) {
		return this.redis.lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) {
		String result = this.redis.lset(key, index, value);
		this.logDb.debug("lset " + key + " " + index + " " + encode(value));
		return result;
	}

	@Override
	public Long lrem(String key, long count, String value) {
		return this.redis.lrem(key, count, value);
	}

	@Override
	public String lpop(String key) {
		String result = this.redis.lpop(key);
		this.logDb.debug("lpop " + key);
		return result;
	}

	@Override
	public String rpop(String key) {
		String result = this.redis.rpop(key);
		this.logDb.debug("rpop " + key);
		return result;
	}

	@Override
	public Long sadd(String key, String... members) {
		Long result = this.redis.sadd(key, members);
		this.logDb.debug("sadd " + key + " " + encode(StringUtils.join(members, " ")));
		return result;
	}

	@Override
	public Set<String> smembers(String key) {
		return this.redis.smembers(key);
	}

	@Override
	public Long srem(String key, String... members) {
		Long result = this.redis.srem(key, members);
		this.logDb.debug("srem " + key + " " + encode(StringUtils.join(members, " ")));
		return result;
	}

	@Override
	public String spop(String key) {
		String result = this.redis.spop(key);
		this.logDb.debug("spop " + key);
		return result;
	}

	@Override
	public Long scard(String key) {
		return this.redis.scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {
		return this.redis.sismember(key, member);
	}

	@Override
	public String srandmember(String key) {
		return this.redis.srandmember(key);
	}

	@Override
	public Long zadd(String key, double score, long member) {
		return this.zadd(key, score, Long.toString(member));
	}

	@Override
	public Long zadd(String key, double score, String member) {
		Long result = this.redis.zadd(key, score, member);
		this.logDb.debug("zadd " + key + " " + format(score) + " " + member);
		return result;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		return this.redis.zrange(key, start, end);
	}

	@Override
	public Long zrem(String key, String... member) {
		Long result = this.redis.zrem(key, member);
		this.logDb.debug("zrem " + key + " " + encode(StringUtils.join(member, " ")));
		return result;
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		Double result = this.redis.zincrby(key, score, member);
		this.logDb.debug("zincrby " + key + " " + format(score) + " " + encode(member));
		return result;
	}

	@Override
	public Long zrank(String key, String member) {
		return this.redis.zrank(key, member);
	}

	@Override
	public Long zrevrank(String key, String member) {
		return this.redis.zrevrank(key, member);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		return this.redis.zrevrange(key, start, end);
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return this.redis.zrangeWithScores(key, start, end);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return this.redis.zrevrangeWithScores(key, start, end);
	}

	@Override
	public Long zcard(String key) {
		return this.redis.zcard(key);
	}

	@Override
	public Double zscore(String key, String member) {
		return this.redis.zscore(key, member);
	}

	@Override
	public Double zscore(final String key, final long member) {
		return this.zscore(key, Long.toString(member));
	}

	@Override
	public List<String> sort(String key) {
		return this.redis.sort(key);
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		return this.redis.sort(key, sortingParameters);
	}

	@Override
	public Long zcount(String key, double min, double max) {
		return this.redis.zcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return this.redis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return this.redis.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		return this.redis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		return this.redis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return this.redis.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		return this.redis.zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		return this.redis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		return this.redis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		Long result = this.redis.zremrangeByRank(key, start, end);
		this.logDb.debug("zremrangeByRank " + key + " " + start + " " + end);
		return result;
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		Long result = this.redis.zremrangeByScore(key, start, end);
		this.logDb.debug("zremrangeByScore " + key + " " + start + " " + end);
		return result;
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		// if (true) {
		throw new NotImplementedException("linsert方法未启用");
		// }
		// Long result = this.redis.linsert(key, where, pivot, value);
		// //log未启用.
		// return result;
	}

	@Override
	public Jedis getResource() {
		return this.redis.getResource();
	}

	@Override
	public boolean append(String key, String value, int seconds) {
		boolean result = this.redis.append(key, value, seconds);
		this.logDb.debug("append " + key + " " + this.encode(value) + " " + seconds);
		return result;
	}

	@Override
	public boolean rename(String oldkey, String newkey) {
		boolean result = this.redis.rename(oldkey, newkey);
		this.logDb.debug("rename " + oldkey + " " + newkey);
		return result;
	}

	@Override
	public Transaction multi() {
		return this.redis.multi();
	}

	@Override
	public boolean flushDB() {
		boolean result = this.redis.flushDB();
		this.logDb.debug("flushDB");
		return result;
	}

	@Override
	public RedisInfo info() {
		return this.redis.info();
	}

	@Override
	public boolean rename(String oldkey, String newkey, int seconds) {
		boolean result = this.redis.rename(oldkey, newkey, seconds);
		this.logDb.debug("rename " + oldkey + " " + newkey + " " + seconds);
		return result;
	}

	@Override
	public long getUsedMemory() {
		return this.redis.getUsedMemory();
	}

	@Override
	public long dbSize() {
		return this.redis.dbSize();
	}

	@Override
	public String set(String key, String value, int seconds) {
		String result = this.redis.set(key, value, seconds);
		this.logDb.debug("set " + key + " " + this.encode(value) + " " + seconds);
		return result;
	}

	@Override
	public boolean flushAll() {
		boolean result = this.redis.flushAll();
		this.logDb.debug("flushAll");
		return result;
	}

	@Override
	public boolean set(List<String> keyList, List<String> valueList) {
		boolean result = this.redis.set(keyList, valueList);
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);
			this.logDb.debug("set " + key + " " + encode(value));
		}
		return result;
	}

	@Override
	public boolean append(List<String> keyList, List<String> valueList, int seconds) {
		boolean result = this.redis.append(keyList, valueList, seconds);
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);
			this.logDb.debug("append " + key + " " + this.encode(value) + " " + seconds);
		}
		return result;
	}

	@Override
	public Long del(String... keys) {
		Long result = this.redis.del(keys);
		this.logDb.debug("del " + StringUtils.join(keys, " "));
		return result;
	}

	@Override
	public Long del(String key) {
		return this.redis.del(key);
	}

	@Override
	public void returnResource(Jedis jedis) {
		this.redis.returnResource(jedis);
	}

	@Override
	public List<String> mget(String... keys) {
		return this.redis.mget(keys);
	}

	@Override
	public Long zinterstore(String dstkey, String... sets) {
		Long result = this.redis.zinterstore(dstkey, sets);
		this.logDb.debug("zinterstore " + dstkey + " " + StringUtils.join(sets, " "));
		return result;
	}

	@Override
	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		Long result = this.redis.zinterstore(dstkey, params, sets);
		// TODO 未做params转换
		this.logDb.debug("zinterstore params " + dstkey + " " + StringUtils.join(sets, " "));
		return result;
	}

	@Override
	public Set<String> keys(String pattern) {
		return this.redis.keys(pattern);
	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		Long result = this.redis.zunionstore(dstkey, sets);
		this.logDb.debug("zunionstore " + dstkey + " " + StringUtils.join(sets, " "));
		return result;
	}

	@Override
	public String getServerInfo() {
		return this.server;
	}

	@Override
	public Long zrem(String key, long member) {
		return this.zrem(key, Long.toString(member));
	}

	@Override
	public Set<String> zunionStoreInJava(String... sets) {
		return redis.zunionStoreInJava(sets);
	}

	@Override
	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets) {
		return redis.zunionStoreByScoreInJava(min, max, sets);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		Long result = this.redis.zadd(key, scoreMembers);

		Iterator<Entry<String, Double>> iterator = scoreMembers.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Double> entry = iterator.next();
			Double score = entry.getValue();
			String member = entry.getKey();
			this.logDb.debug("zadd " + key + " " + format(score) + " " + encode(member));
		}

		return result;
	}

	// @Override
	// public Long zadd2(String key, Map<String, Double> scoreMembers) {
	// return this.zadd(key, scoreMembers);
	// // Long result = this.redis.zadd2(key, scoreMembers);
	// //
	// // Iterator<Entry<String, Double>> iterator =
	// scoreMembers.entrySet().iterator();
	// // while (iterator.hasNext()) {
	// // Entry<String, Double> entry = iterator.next();
	// // Double score = entry.getValue();
	// // String member = entry.getKey();
	// // this.logDb.debug("zadd " + key + " " + format(score) + " " +
	// encode(member));
	// // }
	// //
	// // return result;
	// }

	@Override
	public Long zcount(String key, String min, String max) {
		Long result = this.redis.zcount(key, min, max);
		return result;
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		return this.redis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return this.redis.zrevrangeByScore(key, max, min);

	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return this.redis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return this.redis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return this.redis.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		return this.redis.zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		return this.redis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		return this.zrevrangeByScoreWithScores(key, Double.parseDouble(max), Double.parseDouble(min), offset, count);
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		Long result = this.redis.zremrangeByScore(key, start, end);
		this.logDb.debug("zremrangeByScore " + key + " " + encode(start) + " " + encode(end));
		return result;
	}

	@Override
	public Long lpushx(String key, String string) {
		Long result = redis.lpushx(key, string);
		this.logDb.debug("lpushx " + key + " " + encode(string));
		return result;
	}

	@Override
	public Long rpushx(String key, String string) {
		Long result = this.redis.rpushx(key, string);
		this.logDb.debug("rpushx " + key + " " + encode(string));
		return result;
	}

	@Override
	public Long setrange(String key, int offset, String value) {
		Long temp = (long) offset;
		return this.setrange(key, temp, value);
	}

	@Override
	public Object evalsha(String script) {
		return this.redis.evalsha(script);
	}

	@Override
	public Object eval(String script) {
		return this.redis.eval(script);
	}

	@Override
	public Object evalsha(String sha1, List<String> keys, List<String> args) {
		return this.redis.evalsha(sha1, keys, args);
	}

	@Override
	public Object evalsha(String sha1, int keyCount, String... params) {
		return this.redis.evalsha(sha1, keyCount, params);
	}

	// @Override
	// public Ludis getLudis() {
	// return this.redis.getLudis();
	// }

	@Override
	public String evalReturnSha(String script) {
		return this.redis.evalReturnSha(script);
	}

	@Override
	public Object evalAssertSha(String sha, String script) {
		return this.redis.evalAssertSha(sha, script);
	}

	@Override
	public Object eval(String script, int keyCount, String... params) {
		return this.redis.eval(script, keyCount, params);
	}

	@Override
	public String bgrewriteaof() {
		return this.redis.bgrewriteaof();
	}

	@Override
	public String bgsave() {
		return this.redis.bgsave();
	}

	@Override
	public String save() {
		return this.redis.save();
	}

	@Override
	public IJedisPool getJedisPool() {
		return this.redis.getJedisPool();
	}

	@Override
	public Long publish(String channel, String message) {
		return this.redis.publish(channel, message);
	}

	@Override
	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		this.redis.psubscribe(jedisPubSub, patterns);
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		this.redis.subscribe(jedisPubSub, channels);
	}

	@Override
	public Set<String> sdiff(String... keys) {
		return this.redis.sdiff(keys);
	}

	@Override
	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		Long result = this.redis.zunionstore(dstkey, params, sets);
		// TODO 未做params转换
		this.logDb.debug("zunionstore " + dstkey + " " + StringUtils.join(sets, " "));
		return result;
	}

	@Override
	public String randomKey() {
		return this.redis.randomKey();
	}

	@Override
	public Long persist(String key) {
		return this.redis.persist(key);
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		return this.redis.setbit(key, offset, value);
	}

	@Override
	public Long strlen(String key) {
		return this.redis.strlen(key);
	}

	@Override
	public Long lpushx(String key, String... string) {
		return this.redis.lpushx(key, string);
	}

	@Override
	public Long rpushx(String key, String... string) {
		return this.redis.rpushx(key, string);
	}

	@Override
	public List<String> blpop(String arg) {
		return this.redis.blpop(arg);
	}

	@Override
	public List<String> brpop(String arg) {
		return this.redis.brpop(arg);
	}

	@Override
	public String echo(String string) {
		return this.redis.echo(string);
	}

	@Override
	public Long move(String key, int dbIndex) {
		return this.redis.move(key, dbIndex);
	}

	@Override
	public Long bitcount(String key) {
		return this.redis.bitcount(key);
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		return this.redis.bitcount(key, start, end);
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		// return this.redis.hscan(key, cursor);
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		// return this.redis.sscan(key, cursor);
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		// return this.redis.zscan(key, cursor);
		throw new NotImplementedException();
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		return this.redis.hscan(key, cursor);
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		return this.redis.sscan(key, cursor);
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		return this.redis.zscan(key, cursor);
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

	@Override
	public List<String> srandmember(String key, int count) {
		return this.redis.srandmember(key, count);
	}

}
