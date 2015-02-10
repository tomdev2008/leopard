package io.leopard.data4j.redis;

import io.leopard.data4j.redis.util.IJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

/**
 * Redis负载均衡实现(指令按key hash切分到不同Redis服务器执行)
 * 
 * @author 阿海
 * 
 */
public class RedisHashImpl extends AbstractRedis implements Redis {

	protected Redis[] redisList;

	protected String[] serverList;

	protected HashType hashType;

	protected boolean log;

	public void setServerList(String[] serverList) {
		// System.out.println("serverList:" + StringUtils.join(serverList,","));
		this.serverList = serverList;
	}

	public Redis[] getRedisList() {
		return redisList;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	/**
	 * 
	 * @param hashType
	 *            string,long,default,其他自定义实现类名.
	 */
	public void setHashType(String hashType) {
		String className = getHashTypeClassName(hashType);
		Class<?> clazz;// = ClassUtil.forName(className);
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		try {
			this.hashType = (HashType) clazz.newInstance();
		}
		catch (Exception e) {
			throw new IllegalArgumentException("不是合法的HashType[" + className + "]?", e);
		}
	}

	protected String getHashTypeClassName(String hashType) {
		String className;
		if ("string".equalsIgnoreCase(hashType)) {
			className = StringHashType.class.getName();
		}
		else if ("long".equalsIgnoreCase(hashType)) {
			className = LongHashType.class.getName();
		}
		else if ("default".equalsIgnoreCase(hashType)) {
			className = DefaultHashType.class.getName();
		}
		else if (StringUtils.isEmpty(hashType)) {
			className = DefaultHashType.class.getName();
		}
		else {
			className = hashType;
		}
		return className;
	}

	@Override
	public void init() {
		if (serverList != null) {
			Redis[] redisList = new Redis[serverList.length];
			for (int i = 0; i < serverList.length; i++) {
				String server = serverList[i];
				Redis redis = this.initRedis(server);
				redisList[i] = redis;
			}
			this.redisList = redisList;
		}
	}

	protected Redis initRedis(String server) {
		if (log) {
			RedisLog4jImpl redis = new RedisLog4jImpl();
			redis.setServer(server);
			redis.setTimeout(timeout);
			redis.setMaxActive(maxActive);
			redis.setInitialPoolSize(this.initialPoolSize);
			redis.setEnableBackup(enableBackup);
			redis.setBackupTime(this.backupTime);
			redis.init();
			return redis;
		}
		else {
			RedisImpl redis = new RedisImpl(server, maxActive, initialPoolSize, enableBackup, backupTime, timeout);
			redis.init();
			return redis;
		}
	}

	@Override
	public void destroy() {
		if (redisList != null) {
			for (Redis redis : redisList) {
				redis.destroy();
			}
		}
	}

	protected int getIndex(String key) {
		{
			if (redisList.length == 1) {
				return 0;
			}
		}

		long hashCode = hashType.getHashCode(key);
		int index = (int) (hashCode % this.redisList.length);
		return index;

		//
		// if (this.hashType == HASH_TYPE_DEFAULT) {
		// long hashCode = StringUtil.getHashCode(key);
		// int index = (int) (hashCode % this.redisList.length);
		// return index;
		// }
		// else if (this.hashType == HASH_TYPE_STRING) {
		// String param = key.substring(key.lastIndexOf(":") + 1);
		// // System.out.println("param:" + param);
		// long hashCode = StringUtil.getHashCode(param);
		// int index = (int) (hashCode % this.redisList.length);
		// return index;
		// }
		// else if (this.hashType == HASH_TYPE_LONG) {
		// String param = key.substring(key.lastIndexOf(":") + 1);
		// System.out.println("param:" + param);
		// long id = Long.parseLong(param);
		// int index = (int) (id % this.redisList.length);
		// return index;
		// }
		// else {
		// throw new IllegalArgumentException("未知hashType[" + this.hashType +
		// "].");
		// }
	}

	protected Redis getRedis(String key) {
		int index = this.getIndex(key);
		return redisList[index];
	}

	@Override
	public Long append(String key, String value) {
		return this.getRedis(key).append(key, value);
	}

	@Override
	public String set(String key, String value) {
		return this.getRedis(key).set(key, value);
	}

	@Override
	public Long expire(String key, int seconds) {
		return this.getRedis(key).expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return this.getRedis(key).ttl(key);
	}

	@Override
	public Long incr(String key) {
		return this.getRedis(key).incr(key);
	}

	@Override
	public String get(String key) {
		return this.getRedis(key).get(key);
	}

	@Override
	public String getSet(String key, String value) {
		return this.getRedis(key).getSet(key, value);
	}

	@Override
	public Long zcard(String key) {
		return this.getRedis(key).zcard(key);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return this.getRedis(key).zrevrangeWithScores(key, start, end);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		return this.getRedis(key).zrevrange(key, start, end);
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		return this.getRedis(key).zrange(key, start, end);
	}

	@Override
	public Long zadd(String key, double score, String member) {
		return this.getRedis(key).zadd(key, score, member);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		return this.getRedis(key).zadd(key, scoreMembers);
	}

	@Override
	public Long srem(String key, String... member) {
		return this.getRedis(key).srem(key, member);
	}

	@Override
	public Boolean exists(String key) {
		return this.getRedis(key).exists(key);
	}

	@Override
	public String type(String key) {
		return this.getRedis(key).type(key);
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		return this.getRedis(key).expireAt(key, unixTime);
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		return this.getRedis(key).setbit(key, offset, value);
	}

	@Override
	public Boolean getbit(String key, long offset) {
		return this.getRedis(key).getbit(key, offset);
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		return this.getRedis(key).getrange(key, startOffset, endOffset);
	}

	@Override
	public Long setnx(String key, String value) {
		return this.getRedis(key).setnx(key, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return this.getRedis(key).setex(key, seconds, value);
	}

	@Override
	public Long decrBy(String key, long integer) {
		return this.getRedis(key).decrBy(key, integer);
	}

	@Override
	public Long decr(String key) {
		return this.getRedis(key).decr(key);
	}

	@Override
	public Long incrBy(String key, long integer) {
		return this.getRedis(key).incrBy(key, integer);
	}

	@Override
	public String substr(String key, int start, int end) {
		return this.getRedis(key).substr(key, start, end);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return this.getRedis(key).hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return this.getRedis(key).hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		return this.getRedis(key).hsetnx(key, field, value);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return this.getRedis(key).hmset(key, hash);
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return this.getRedis(key).hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		return this.getRedis(key).hincrBy(key, field, value);
	}

	@Override
	public Boolean hexists(String key, String field) {
		return this.getRedis(key).hexists(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return this.getRedis(key).hdel(key, field);
	}

	@Override
	public Long hlen(String key) {
		return this.getRedis(key).hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) {
		return this.getRedis(key).hkeys(key);
	}

	@Override
	public List<String> hvals(String key) {
		return this.getRedis(key).hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return this.getRedis(key).hgetAll(key);
	}

	@Override
	public Long rpush(String key, String... strings) {
		return this.getRedis(key).rpush(key, strings);
	}

	@Override
	public Long lpush(String key, String... strings) {
		return this.getRedis(key).lpush(key, strings);
	}

	@Override
	public Long llen(String key) {
		return this.getRedis(key).llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return this.getRedis(key).lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return this.getRedis(key).ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) {
		return this.getRedis(key).lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) {
		return this.getRedis(key).lset(key, index, value);
	}

	@Override
	public Long lrem(String key, long count, String value) {
		return this.getRedis(key).lrem(key, count, value);
	}

	@Override
	public String lpop(String key) {
		return this.getRedis(key).lpop(key);
	}

	@Override
	public String rpop(String key) {
		return this.getRedis(key).rpop(key);
	}

	@Override
	public Set<String> smembers(String key) {
		return this.getRedis(key).smembers(key);
	}

	@Override
	public String spop(String key) {
		return this.getRedis(key).spop(key);
	}

	@Override
	public Long scard(String key) {
		return this.getRedis(key).scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {
		return this.getRedis(key).sismember(key, member);
	}

	@Override
	public String srandmember(String key) {
		return this.getRedis(key).srandmember(key);
	}

	@Override
	public Long zrem(String key, String... members) {
		return this.getRedis(key).zrem(key, members);
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		return this.getRedis(key).zincrby(key, score, member);
	}

	@Override
	public Long zrank(String key, String member) {
		return this.getRedis(key).zrank(key, member);
	}

	@Override
	public Long zrevrank(String key, String member) {
		return this.getRedis(key).zrevrank(key, member);
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return this.getRedis(key).zrangeWithScores(key, start, end);
	}

	@Override
	public Double zscore(String key, String member) {
		// System.err.println("zscore key:" + key + " member:" + member);
		return this.getRedis(key).zscore(key, member);
	}

	@Override
	public Double zscore(final String key, final long member) {
		return this.zscore(key, Long.toString(member));
	}

	@Override
	public List<String> sort(String key) {
		return this.getRedis(key).sort(key);
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		return this.getRedis(key).sort(key, sortingParameters);
	}

	@Override
	public Long zcount(String key, double min, double max) {
		return this.getRedis(key).zcount(key, min, max);
	}

	@Override
	public Long zcount(String key, String min, String max) {
		return this.getRedis(key).zcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return this.getRedis(key).zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		return this.getRedis(key).zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return this.getRedis(key).zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return this.getRedis(key).zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return this.getRedis(key).zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return this.getRedis(key).zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		return this.getRedis(key).zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		return this.getRedis(key).zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return this.getRedis(key).zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		return this.getRedis(key).zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return this.getRedis(key).zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		return this.getRedis(key).zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		return this.getRedis(key).zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		return this.getRedis(key).zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		return this.getRedis(key).zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		return this.getRedis(key).zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		return this.getRedis(key).zremrangeByRank(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		return this.getRedis(key).zremrangeByScore(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		return this.getRedis(key).zremrangeByScore(key, start, end);
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		return this.getRedis(key).linsert(key, where, pivot, value);
	}

	@Override
	public Long lpushx(String key, String string) {
		return this.getRedis(key).lpushx(key, string);
	}

	@Override
	public Long rpushx(String key, String string) {
		return this.getRedis(key).rpushx(key, string);
	}

	@Override
	public Jedis getResource() {
		throw new NotImplementedException();
	}

	@Override
	public boolean append(String key, String value, int seconds) {
		return this.getRedis(key).append(key, value, seconds);
	}

	@Override
	public boolean rename(String oldkey, String newkey) {
		throw new NotImplementedException();
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		return this.getRedis(key).setrange(key, offset, value);
	}

	@Override
	public Long setrange(String key, int offset, String value) {
		return this.setrange(key, (long) offset, value);
	}

	@Override
	public Long sadd(String key, String... members) {
		return this.getRedis(key).sadd(key, members);
	}

	@Override
	public Transaction multi() {
		throw new NotImplementedException();
	}

	@Override
	public boolean flushDB() {
		throw new NotImplementedException();
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
		return this.getRedis(key).set(key, value, seconds);
	}

	@Override
	public boolean flushAll() {
		throw new NotImplementedException();
	}

	@Override
	public boolean set(List<String> keyList, List<String> valueList) {
		throw new NotImplementedException();
	}

	@Override
	public boolean append(List<String> keyList, List<String> valueList, int seconds) {
		throw new NotImplementedException();
	}

	@Override
	public Long del(String... keys) {
		long total = 0;
		for (String key : keys) {
			total += this.del(key);
		}
		return total;
	}

	@Override
	public Long del(String key) {
		return this.getRedis(key).del(key);
	}

	@Override
	public void returnResource(Jedis jedis) {
		throw new NotImplementedException();
	}

	@Override
	public List<String> mget(String... keys) {
		List<String> result = new ArrayList<String>();
		for (String key : keys) {
			String value = this.get(key);
			result.add(value);
		}
		return result;
	}

	@Override
	public Long zinterstore(String dstkey, String... sets) {
		throw new NotImplementedException();
	}

	@Override
	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> keys(String pattern) {
		throw new NotImplementedException();
	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		throw new NotImplementedException();
	}

	@Override
	public String getServerInfo() {
		throw new NotImplementedException();
	}

	// @Override
	// public String hget(String key, int field) {
	// return this.getRedis(key).hget(key, field);
	// }

	@Override
	public String hget(String key, long field) {
		return this.getRedis(key).hget(key, field);
	}

	// @Override
	// public Long hset(String key, int field, String value) {
	// return this.getRedis(key).hset(key, field, value);
	// }

	@Override
	public Long hset(String key, long field, String value) {
		return this.getRedis(key).hset(key, field, value);
	}

	// @Override
	// public Long hdel(String key, int field) {
	// return this.getRedis(key).hdel(key, field);
	// }

	@Override
	public Long hdel(String key, long field) {
		return this.getRedis(key).hdel(key, field);
	}

	@Override
	public Long zadd(String key, double score, long member) {
		return this.getRedis(key).zadd(key, score, member);
	}

	@Override
	public Long zrem(String key, long member) {
		return this.getRedis(key).zrem(key, member);
	}

	@Override
	public Set<String> zunionStoreInJava(String... sets) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets) {
		throw new NotImplementedException();
	}

	// @Override
	// public Long zadd2(String key, Map<String, Double> scoreMembers) {
	// return this.getRedis(key).zadd2(key, scoreMembers);
	// }

	@Override
	public Object evalsha(String script) {
		throw new NotImplementedException();
	}

	@Override
	public Object eval(String script) {
		throw new NotImplementedException();
	}

	@Override
	public Object eval(String script, int keyCount, String... params) {
		throw new NotImplementedException();
	}

	@Override
	public Object evalsha(String sha1, List<String> keys, List<String> args) {
		throw new NotImplementedException();
	}

	@Override
	public Object evalsha(String sha1, int keyCount, String... params) {
		throw new NotImplementedException();
	}

	// @Override
	// public Ludis getLudis() {
	// throw new NotImplementedException();
	// }

	@Override
	public String evalReturnSha(String script) {
		throw new NotImplementedException();
	}

	@Override
	public Object evalAssertSha(String sha, String script) {
		throw new NotImplementedException();
	}

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
		throw new NotImplementedException();
	}

	@Override
	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		throw new NotImplementedException();
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> sdiff(String... keys) {
		throw new NotImplementedException();
	}

	@Override
	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		throw new NotImplementedException();
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

	@Override
	public List<String> srandmember(String key, int count) {
		return this.getRedis(key).srandmember(key, count);
	}

}
