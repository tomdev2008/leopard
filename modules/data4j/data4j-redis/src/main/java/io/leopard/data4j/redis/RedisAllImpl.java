package io.leopard.data4j.redis;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.data4j.redis.util.IJedisPool;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

/**
 * Redis接口实现(一个指令在所有服务器执行).
 * 
 * @author 阿海
 * 
 */
public class RedisAllImpl extends ContextImpl implements Redis {
	protected String[] serverList;
	protected Redis[] redisList;

	public Redis[] getRedisList() {
		return redisList;
	}

	public void setServerList(String[] serverList) {
		this.serverList = serverList;
	}

	@Override
	public void init() {
		super.init();
		if (serverList != null) {
			Redis[] redisList = new Redis[serverList.length];
			for (int i = 0; i < serverList.length; i++) {
				String server = serverList[i];
				RedisLog4jImpl redis = new RedisLog4jImpl();
				redis.setServer(server);
				redis.init();
				redisList[i] = redis;
			}
			this.redisList = redisList;
		}
	}

	@Override
	public void destroy() {
		if (redisList != null) {
			for (Redis redis : redisList) {
				redis.destroy();
			}
		}
		super.destroy();
	}

	@Override
	public Long append(String key, String value) {
		throw new NotImplementedException();
	}

	@Override
	public String set(String key, String value) {
		throw new NotImplementedException();
	}

	@Override
	public Long expire(String key, int seconds) {
		throw new NotImplementedException();
	}

	@Override
	public Long ttl(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long incr(String key) {
		throw new NotImplementedException();
	}

	@Override
	public String get(String key) {
		throw new NotImplementedException();
	}

	@Override
	public String getSet(String key, String value) {
		throw new NotImplementedException();
	}

	@Override
	public Long zcard(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		throw new NotImplementedException();
	}

	@Override
	public Long zadd(String key, double score, String member) {
		throw new NotImplementedException();
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		throw new NotImplementedException();
	}

	@Override
	public Long srem(String key, String... member) {
		throw new NotImplementedException();
	}

	@Override
	public Boolean exists(String key) {
		throw new NotImplementedException();
	}

	@Override
	public String type(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long expireAt(String key, long unixTime) {
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
	public String getrange(String key, long startOffset, long endOffset) {
		throw new NotImplementedException();
	}

	@Override
	public Long setnx(String key, String value) {
		throw new NotImplementedException();
	}

	@Override
	public String setex(String key, int seconds, String value) {
		throw new NotImplementedException();
	}

	@Override
	public Long decrBy(String key, long integer) {
		throw new NotImplementedException();
	}

	@Override
	public Long decr(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long incrBy(String key, long integer) {
		throw new NotImplementedException();
	}

	@Override
	public String substr(String key, int start, int end) {
		throw new NotImplementedException();
	}

	@Override
	public Long hset(String key, String field, String value) {
		throw new NotImplementedException();
	}

	@Override
	public Long hset(String key, long field, String value) {
		throw new NotImplementedException();
	}

	@Override
	public String hget(String key, String field) {
		throw new NotImplementedException();
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		throw new NotImplementedException();
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		throw new NotImplementedException();
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		throw new NotImplementedException();
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		throw new NotImplementedException();
	}

	@Override
	public Boolean hexists(String key, String field) {
		throw new NotImplementedException();
	}

	@Override
	public Long hdel(String key, String... field) {
		throw new NotImplementedException();
	}

	@Override
	public Long hlen(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> hkeys(String key) {
		throw new NotImplementedException();
	}

	@Override
	public List<String> hvals(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long rpush(String key, String... strings) {
		throw new NotImplementedException();
	}

	@Override
	public Long lpush(String key, String... strings) {
		throw new NotImplementedException();
	}

	@Override
	public Long llen(String key) {
		throw new NotImplementedException();
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		throw new NotImplementedException();
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
		throw new NotImplementedException();
	}

	@Override
	public String rpop(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> smembers(String key) {
		throw new NotImplementedException();
	}

	@Override
	public String spop(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long scard(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Boolean sismember(String key, String member) {
		throw new NotImplementedException();
	}

	@Override
	public String srandmember(String key) {
		throw new NotImplementedException();
	}

	@Override
	public Long zrem(String key, String... members) {
		throw new NotImplementedException();
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		throw new NotImplementedException();
	}

	@Override
	public Long zrank(String key, String member) {
		throw new NotImplementedException();
	}

	@Override
	public Long zrevrank(String key, String member) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		throw new NotImplementedException();
	}

	@Override
	public Double zscore(String key, String member) {
		throw new NotImplementedException();
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
		throw new NotImplementedException();
	}

	@Override
	public Long zcount(String key, String min, String max) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		throw new NotImplementedException();
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
		Long result = null;
		for (Redis redis : this.redisList) {
			result = redis.zremrangeByScore(key, start, end);
		}
		return result;
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		throw new NotImplementedException();
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
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
	public Jedis getResource() {
		throw new NotImplementedException();
	}

	@Override
	public boolean append(String key, String value, int seconds) {
		throw new NotImplementedException();
	}

	@Override
	public boolean rename(String oldkey, String newkey) {
		throw new NotImplementedException();
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		throw new NotImplementedException();
	}

	@Override
	public Long setrange(String key, int offset, String value) {
		throw new NotImplementedException();
	}

	@Override
	public Long sadd(String key, String... members) {
		throw new NotImplementedException();
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
		throw new NotImplementedException();
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
		throw new NotImplementedException();
	}

	@Override
	public Long del(String key) {
		// throw new NotImplementedException();
		long result = 0;
		for (Redis redis : redisList) {
			Long success = redis.del(key);
			if (success != null && success > 0) {
				result = 1;
			}
		}
		return result;
	}

	@Override
	public void returnResource(Jedis jedis) {
		throw new NotImplementedException();
	}

	@Override
	public List<String> mget(String... keys) {
		throw new NotImplementedException();
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
	// throw new NotImplementedException();
	// }

	@Override
	public String hget(String key, long field) {
		throw new NotImplementedException();
	}

	// @Override
	// public Long hset(String key, int field, String value) {
	// throw new NotImplementedException();
	// }
	//
	// @Override
	// public Long hdel(String key, int field) {
	// throw new NotImplementedException();
	// }

	@Override
	public Long hdel(String key, long field) {
		throw new NotImplementedException();
	}

	@Override
	public Long zadd(String key, double score, long member) {
		Long result = null;
		for (Redis redis : this.redisList) {
			result = redis.zadd(key, score, member);
		}
		return result;
	}

	@Override
	public Long zrem(String key, long member) {
		throw new NotImplementedException();
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
	// Long result = null;
	// for (Redis redis : this.redisList) {
	// result = redis.zadd2(key, scoreMembers);
	// }
	// return result;
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
		// TODO Auto-generated method stub
		return null;
	}

}
