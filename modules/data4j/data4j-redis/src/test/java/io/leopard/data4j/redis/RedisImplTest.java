/**
 * This class has been generated by Fast Code Eclipse Plugin 
 * For more information please go to http://fast-code.sourceforge.net/
 * @author : Administrator
 * Created : 04/10/2013
 */

package io.leopard.data4j.redis;

import io.leopard.burrow.lang.Json;
import io.leopard.data4j.redis.util.RedisFactory;
import io.leopard.data4j.redis.util.RedisUtil;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

//@Ignore
public class RedisImplTest {

	private RedisImpl redis = RedisFactory.create("172.17.1.236:6311");

	private RedisImpl redisTransactionImpl = redis;
	private final String key = "key";

	@Before
	public void before() {
		redis.del("key");
	}

	@Test
	public void rename() {

	}

	@Test
	public void zrange() {
		redisTransactionImpl.zadd(key, 1, "member1");
		redisTransactionImpl.zadd(key, 1, "member2");
		// redisTransactionImpl.zadd(key, 1, "member2");

		Set<String> set = redisTransactionImpl.zrange("key", 0, 10);
		Assert.assertNotNull(set);
		Assert.assertEquals(set.size(), 2);
		System.out.println(set);
	}

	@Test
	public void set() {
		this.redis.set(key, "value");
		Assert.assertEquals("value", this.redis.get(key));
	}

	@Test
	public void setnx() {
		this.redisTransactionImpl.setnx(key, "value");
		this.redisTransactionImpl.setnx(key, "value1");
		Assert.assertEquals("value", this.redisTransactionImpl.get(key));

	}

	@Test
	public void setex() {
		this.redisTransactionImpl.setex(key, 100, "value");
		this.redisTransactionImpl.setex(key, 100, "value1");
		Assert.assertEquals("value1", this.redisTransactionImpl.get(key));

	}

	@Test
	public void exists() {
		Assert.assertFalse(this.redisTransactionImpl.exists(key));
		this.redisTransactionImpl.set(key, "value");
		Assert.assertTrue(this.redisTransactionImpl.exists(key));
	}

	@Test
	public void decr() {
		Assert.assertEquals(-1L, (long) this.redisTransactionImpl.decr(key));
		Assert.assertEquals(-2L, (long) this.redisTransactionImpl.decr(key));
	}

	@Test
	public void incr() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.incr(key));
		Assert.assertEquals(2L, (long) this.redisTransactionImpl.incr(key));
	}

	@Test
	public void hset() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hset(key, "field1", "value"));
		Assert.assertEquals(0L, (long) this.redisTransactionImpl.hset(key, "field1", "value2"));
		Assert.assertEquals("value2", this.redisTransactionImpl.hget(key, "field1"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hset(key, "field2", "value"));

	}

	@Test
	public void hsetnx() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field1", "value"));
		Assert.assertEquals(0L, (long) this.redisTransactionImpl.hsetnx(key, "field1", "value2"));
		Assert.assertEquals("value", this.redisTransactionImpl.hget(key, "field1"));

	}

	@Test
	public void hmget() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field2", "value2"));

		List<String> list = this.redisTransactionImpl.hmget(key, "field1", "field2");

		Assert.assertEquals("value1,value2", StringUtils.join(list, ","));
	}

	@Test
	public void hdel() {
		Assert.assertEquals(0L, (long) this.redisTransactionImpl.hdel(key, "field1"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hdel(key, "field1"));

	}

	@Test
	public void hkeys() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field2", "value2"));
		Set<String> keys = this.redisTransactionImpl.hkeys(key);
		Assert.assertEquals("field1,field2", StringUtils.join(keys, ","));

	}

	@Test
	public void hvals() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field2", "value2"));
		List<String> values = this.redisTransactionImpl.hvals(key);
		Assert.assertEquals("value1,value2", StringUtils.join(values, ","));
	}

	@Ignore
	@Test
	public void hgetAll() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.hsetnx(key, "field2", "value2"));
		Map<String, String> map = this.redisTransactionImpl.hgetAll(key);

		Assert.assertEquals(2, map.size());
		Assert.assertEquals("field1,field2", StringUtils.join(map.keySet(), ","));
		Assert.assertEquals("value1,value2", StringUtils.join(map.values(), ","));
	}

	@Test
	public void zincrby() {
		Assert.assertEquals(1D, this.redisTransactionImpl.zincrby(key, 1, "member1"), 0);
		Assert.assertEquals(2D, this.redisTransactionImpl.zincrby(key, 1, "member1"), 0);

	}

	@Test
	public void zrank() {
		this.redisTransactionImpl.zadd(key, 1, "one");
		this.redisTransactionImpl.zadd(key, 2, "two");
		this.redisTransactionImpl.zadd(key, 3, "three");

		Assert.assertEquals(0L, (long) this.redisTransactionImpl.zrank(key, "one"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.zrank(key, "two"));
		Assert.assertEquals(2L, (long) this.redisTransactionImpl.zrank(key, "three"));
		Assert.assertNull(this.redisTransactionImpl.zrank(key, "four"));
	}

	@Test
	public void zrevrank() {
		this.redisTransactionImpl.zadd(key, 1, "one");
		this.redisTransactionImpl.zadd(key, 2, "two");
		this.redisTransactionImpl.zadd(key, 3, "three");

		Assert.assertEquals(2L, (long) this.redisTransactionImpl.zrevrank(key, "one"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.zrevrank(key, "two"));
		Assert.assertEquals(0L, (long) this.redisTransactionImpl.zrevrank(key, "three"));
		Assert.assertNull(this.redisTransactionImpl.zrank(key, "four"));
	}

	@Test
	public void zcard() {
		this.redisTransactionImpl.zadd(key, 1, "member1");
		this.redisTransactionImpl.zadd(key, 1, "member2");
		Assert.assertEquals(2L, (long) this.redisTransactionImpl.zcard(key));

	}

	@Test
	public void zrevrangeByScore() {
		this.redisTransactionImpl.zadd(key, 1, "one");
		this.redisTransactionImpl.zadd(key, 2, "two");
		this.redisTransactionImpl.zadd(key, 3, "three");

		Set<String> set = redisTransactionImpl.zrevrangeByScore(key, 2, 1, 0, 10);
		Assert.assertEquals("two,one", StringUtils.join(set, ","));
	}

	@Ignore
	@Test
	public void zrangeByScore() {
		this.redisTransactionImpl.zadd(key, 1, "one");
		this.redisTransactionImpl.zadd(key, 2, "two");
		this.redisTransactionImpl.zadd(key, 3, "three");

		Set<String> set = redisTransactionImpl.zrangeByScore(key, 0, -1);
		Assert.assertEquals("one,two,three", StringUtils.join(set, ","));
	}

	@Test
	public void zscore() {
		this.redisTransactionImpl.zscore(key, "member1");
	}

	@Test
	public void lpush() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.lpush(key, "world"));
		Assert.assertEquals(2L, (long) this.redisTransactionImpl.lpush(key, "hello"));

		Assert.assertEquals("hello,world", StringUtils.join(this.redisTransactionImpl.lrange(key, 0, -1), ","));
		Assert.assertEquals("hello,world", StringUtils.join(this.redisTransactionImpl.lrange(key, 0, 1), ","));
		Assert.assertEquals("hello", StringUtils.join(this.redisTransactionImpl.lrange(key, 0, 0), ","));
		Assert.assertEquals("world", StringUtils.join(this.redisTransactionImpl.lrange(key, 1, 1), ","));
	}

	@Test
	public void lrange() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.rpush(key, "one"));
		Assert.assertEquals(2L, (long) this.redisTransactionImpl.rpush(key, "two"));
		Assert.assertEquals(3L, (long) this.redisTransactionImpl.rpush(key, "three"));

		List<String> list = this.redisTransactionImpl.lrange(key, 0, 0);
		Assert.assertEquals("one", StringUtils.join(list, ","));
	}

	@Test
	public void rpush() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.rpush(key, "hello"));
		Assert.assertEquals(2L, (long) this.redisTransactionImpl.rpush(key, "world"));

		Assert.assertEquals("hello,world", StringUtils.join(this.redisTransactionImpl.lrange(key, 0, -1), ","));
		Assert.assertEquals("hello,world", StringUtils.join(this.redisTransactionImpl.lrange(key, 0, 1), ","));
		Assert.assertEquals("hello", StringUtils.join(this.redisTransactionImpl.lrange(key, 0, 0), ","));
		Assert.assertEquals("world", StringUtils.join(this.redisTransactionImpl.lrange(key, 1, 1), ","));
	}

	@Test
	public void lpop() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.rpush(key, "one"));
		Assert.assertEquals(2L, (long) this.redisTransactionImpl.rpush(key, "two"));
		Assert.assertEquals(3L, (long) this.redisTransactionImpl.rpush(key, "three"));

		Assert.assertEquals("one", this.redisTransactionImpl.lpop(key));

		Assert.assertEquals("two,three", StringUtils.join(this.redisTransactionImpl.lrange(key, 0, -1), ","));
	}

	@Test
	public void rpop() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.rpush(key, "one"));
		Assert.assertEquals(2L, (long) this.redisTransactionImpl.rpush(key, "two"));
		Assert.assertEquals(3L, (long) this.redisTransactionImpl.rpush(key, "three"));

		Assert.assertEquals("three", this.redisTransactionImpl.rpop(key));

		Assert.assertEquals("one,two", StringUtils.join(this.redisTransactionImpl.lrange(key, 0, -1), ","));
	}

	@Test
	public void zrevrange() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.zadd(key, 1, "one"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.zadd(key, 2, "two"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.zadd(key, 3, "three"));

		Assert.assertEquals("three,two,one", StringUtils.join(this.redisTransactionImpl.zrevrange(key, 0, -1), ","));
	}

	@Test
	public void setrange() {
		this.redisTransactionImpl.set(key, "Hello World");
		Assert.assertEquals(11L, (long) this.redisTransactionImpl.setrange(key, 6, "Redis"));
		Assert.assertEquals("Hello Redis", this.redisTransactionImpl.get(key));
	}

	@Test
	public void zinterstore() {
		redisTransactionImpl.zadd("zset1", 1, "one");
		redisTransactionImpl.zadd("zset1", 2, "two");
		redisTransactionImpl.zadd("zset2", 1, "one");
		redisTransactionImpl.zadd("zset2", 2, "two");
		redisTransactionImpl.zadd("zset2", 3, "three");

		ZParams params = new ZParams().aggregate(ZParams.Aggregate.SUM);
		params.weights(2, 3);
		this.redisTransactionImpl.zinterstore(key, params, "zset1", "zset2");
		Set<Tuple> set = this.redisTransactionImpl.zrangeWithScores(key, 0, -1);

		Json.print(set, "set");

		Assert.assertEquals("one,two", StringUtils.join(RedisUtil.tupleToString(set), ","));
		Assert.assertEquals("5.0,10.0", StringUtils.join(RedisUtil.tupleToScores(set), ","));
	}

	@Test
	public void zParams() {
		ZParams zParams = new ZParams().aggregate(ZParams.Aggregate.SUM);
		zParams.weights(1, 1);
		Collection<byte[]> collect = zParams.getParams();
		System.out.println("######################");
		for (byte[] b : collect) {
			System.out.println("collect :" + new String(b));
		}
		System.out.println("######################");
	}

	@Test
	public void hget() {
		Assert.assertNull(this.redisTransactionImpl.hget(key, "field"));
	}

	@Test
	public void smembers() {
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.sadd(key, "Hello"));
		Assert.assertEquals(1L, (long) this.redisTransactionImpl.sadd(key, "World"));
		Assert.assertEquals("World,Hello", StringUtils.join(this.redisTransactionImpl.smembers(key), ","));

	}

	@Test
	public void zrem() {
		this.redisTransactionImpl.zadd(key, 1, "one");
		this.redisTransactionImpl.zadd(key, 2, "two");
		this.redisTransactionImpl.zadd(key, 3, "three");

		Assert.assertEquals(1L, this.redisTransactionImpl.zrem(key, "one"), 0);
	}

	@Test
	public void hincrBy() {
		redisTransactionImpl.hset("key", "field", "1");
		Assert.assertEquals(2L, redisTransactionImpl.hincrBy("key", "field", 1), 0);
		Assert.assertEquals(5L, redisTransactionImpl.hincrBy("key", "field", 3), 0);
		Assert.assertEquals(2L, redisTransactionImpl.hincrBy("key", "field", -3), 0);
		Assert.assertEquals("2", redisTransactionImpl.hget("key", "field"));
	}

	@Test
	public void sdiff() {
		redis.del("key1", "key2", "key3");
		redis.sadd("key1", "a", "b", "c");
		redis.sadd("key2", "c", "d", "e");
		// redis.sadd("key3", "c", "a", "g");
		Set<String> set = redis.sdiff("key1", "key2");
		System.out.println("set:" + set);
		Assert.assertEquals(2, set.size());
		Assert.assertTrue(set.contains("a"));
		Assert.assertTrue(set.contains("b"));
	}

	@Test
	public void sadd() {
		redis.del(key);
		long result = redis.sadd(key, "a", "b", "c");
		Assert.assertEquals(3L, result);
	}

	@Test
	public void zadd2() {
		Map<String, Double> scoreMembers = new LinkedHashMap<String, Double>();
		scoreMembers.put("member1", 1D);
		scoreMembers.put("member2", 2D);
		Assert.assertEquals(2, redis.zadd(key, scoreMembers).intValue());
		Assert.assertEquals(2, redis.zcard(key).intValue());
	}

	@Test
	public void get() {
		// AUTO
	}

}
