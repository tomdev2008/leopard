package io.leopard.test4j.mock.transaction;

import io.leopard.burrow.util.ListUtil;
import io.leopard.test4j.mock.transaction.h2.service.H2ServiceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import redis.clients.jedis.JedisPubSub;

public class RedisTransactionImplTest {

	static {
		MockTransactionModule.getInstance(H2ServiceImpl.class).importDatabase();
	}

	protected RedisTransactionImpl redis = MockTransactionModule.getInstance(RedisTransactionImpl.class);
	private final String key = "key";

	@Test
	public void get() {
		redis.del(key);
		redis.set("key", "value");
		Assert.assertEquals("value", redis.get("key"));
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
	public void set() {
		redis.set("key", "value");
		Assert.assertEquals("value", redis.set("key", "value"));
	}

	@Test
	public void exists() {
		redis.del(key);
		redis.set("key", "value");
		Assert.assertTrue(redis.exists("key"));
	}

	@Test
	public void expire() {
		this.redis.del("key");
		this.redis.set("key", "value");
		redis.expire("key", 1);
		// TODO ahai 未做验证
		// SystemUtil.sleep(2100);
		// Assert.assertNull(redis.get("key"));
	}

	@Test
	public void setrange() {
		this.redis.del("key");
		this.redis.setrange("key", 0, "hello");
		this.redis.setrange("key", 5, "world");
		Assert.assertEquals("helloworld", redis.get("key"));
	}

	@Test
	public void getSet() {
		this.redis.del("key");
		this.redis.set("key", "value");
		Assert.assertEquals("value", redis.getSet("key", "value1"));
		Assert.assertEquals("value1", redis.get("key"));

	}

	@Test
	public void setnx() {
		this.redis.del("key");
		Assert.assertEquals(1, this.redis.setnx("key", "value").intValue());
		Assert.assertEquals(0, this.redis.setnx("key", "value1").intValue());
	}

	@Test
	public void setex() {
		this.redis.del("key");
		this.redis.setex("key", 1, "value");
	}

	@Test
	public void decrBy() {

	}

	@Test
	public void decr() {
		redis.del("key");
		redis.decr("key");
		redis.decr("key");
		Assert.assertEquals("-2", redis.get("key"));
	}

	@Test
	public void incrBy() {

	}

	@Test
	public void incr() {
		redis.del("key");
		redis.incr("key");
		redis.incr("key");
		Assert.assertEquals("2", redis.get("key"));
	}

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void append() {
		{
			redis.del("key");
			this.redis.append("key", "Hello");
			this.redis.append("key", " World");
			Assert.assertEquals("Hello World", redis.get("key"));
		}
		{
			redis.del("key1");
			this.redis.append("key1", "Hello", 0);
			this.redis.append("key1", " World", 0);
			Assert.assertEquals("Hello World", redis.get("key1"));
		}
		{
			redis.del("key2");
			redis.del("key3");

			List<String> keyList = ListUtil.makeList("key2,key3");
			List<String> value1List = ListUtil.makeList("Hello,Hello");
			List<String> value2List = ListUtil.makeList("World,World");
			this.redis.append(keyList, value1List, 0);
			this.redis.append(keyList, value2List, 0);

			Assert.assertEquals("HelloWorld", redis.get("key2"));
			Assert.assertEquals("HelloWorld", redis.get("key3"));

		}
		// Assert.noimpl(redis).append("key", "value");
		// Assert.noimpl(redis).append((String) null, null, 0);
		// Assert.noimpl(redis).append((List) null, null, 0);
	}

	@Test
	public void hset() {

	}

	@Test
	public void hget() {
		redis.del("key");
		redis.hset("key", 1, "value");
		Assert.assertEquals("value", redis.hget("key", 1));
	}

	@Test
	public void hsetnx() {

	}

	@Test
	public void hmset() {
		redis.del("key");
		Map<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("field1", "value1");
		redis.hmset(key, hash);
	}

	@Test
	public void hmget() {

	}

	@Test
	public void hincrBy() {

	}

	@Test
	public void hexists() {
		redis.del("key");
		Map<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("field1", "value1");
		redis.hmset(key, hash);
		Assert.assertTrue(redis.hexists("key", "field1"));
	}

	@Test
	public void hdel() {
		{
			redis.del("key");
			Map<String, String> hash = new LinkedHashMap<String, String>();
			hash.put("field1", "value1");
			redis.hmset(key, hash);
			Assert.assertTrue(redis.hexists("key", "field1"));
			redis.hdel("key", "field1");
			Assert.assertFalse(redis.hexists("key", "field1"));
		}
		{
			redis.del("key");
			this.redis.hset("key", 1, "value1");
			Assert.assertEquals(1L, redis.hlen("key").longValue());
			redis.hdel("key", 1);
			Assert.assertEquals(0L, redis.hlen("key").longValue());
		}
	}

	@Test
	public void hlen() {
		redis.del("key");
		Map<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("field1", "value1");
		redis.hmset(key, hash);
		Assert.assertEquals(1L, redis.hlen("key").longValue());
		redis.hdel("key", "field1");
		Assert.assertEquals(0L, redis.hlen("key").longValue());
	}

	@Test
	public void hkeys() {
		redis.del("key");
		Map<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("field1", "value1");
		hash.put("field2", "value2");
		redis.hmset(key, hash);
		Assert.assertEquals("[field1, field2]", redis.hkeys("key").toString());
	}

	@Test
	public void hvals() {
		redis.del("key");
		Map<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("field1", "value1");
		hash.put("field2", "value2");
		redis.hmset(key, hash);
		Assert.assertEquals("[value1, value2]", redis.hvals("key").toString());
	}

	@Test
	public void hgetAll() {
		redis.del("key");
		Map<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("field1", "value1");
		hash.put("field2", "value2");
		redis.hmset(key, hash);
		Map<String, String> result = redis.hgetAll("key");
		Assert.assertEquals(2, result.size());
		Assert.assertEquals("value1", result.get("field1"));
		Assert.assertEquals("value2", result.get("field2"));
	}

	@Test
	public void rpush() {
		this.redis.del(key);
		redis.rpush(key, "hello");
		redis.rpush(key, "world");

		Assert.assertEquals("[hello, world]", redis.lrange(key, 0, -1).toString());
	}

	@Test
	public void lpush() {
		this.redis.del(key);
		redis.lpush(key, "world");
		redis.lpush(key, "hello");

		Assert.assertEquals("[hello, world]", redis.lrange(key, 0, -1).toString());
	}

	@Test
	public void llen() {
		this.redis.del(key);
		redis.lpush(key, "world");
		redis.lpush(key, "hello");

		Assert.assertEquals(2, redis.llen(key).intValue());
	}

	@Test
	public void lrange() {

	}

	@Test
	public void lpop() {
		// redis> RPUSH mylist "one"
		// (integer) 1
		// redis> RPUSH mylist "two"
		// (integer) 2
		// redis> RPUSH mylist "three"
		// (integer) 3
		// redis> LPOP mylist
		// "one"
		// redis> LRANGE mylist 0 -1
		// 1) "two"
		// 2) "three"
		// redis>

		this.redis.del(key);
		redis.rpush(key, "one");
		redis.rpush(key, "two");
		redis.rpush(key, "three");
		redis.lpop(key);

		Assert.assertEquals("[two, three]", redis.lrange(key, 0, -1).toString());
	}

	@Test
	public void rpop() {
		// redis> RPUSH mylist "one"
		// (integer) 1
		// redis> RPUSH mylist "two"
		// (integer) 2
		// redis> RPUSH mylist "three"
		// (integer) 3
		// redis> RPOP mylist
		// "three"
		// redis> LRANGE mylist 0 -1
		// 1) "one"
		// 2) "two"
		// redis>

		this.redis.del(key);
		redis.rpush(key, "one");
		redis.rpush(key, "two");
		redis.rpush(key, "three");
		redis.rpop(key);

		Assert.assertEquals("[one, two]", redis.lrange(key, 0, -1).toString());
	}

	@Test
	public void smembers() {
		this.redis.del("key");
		this.redis.sadd("key", "member1");
		this.redis.sadd("key", "member2");
		Assert.assertEquals("[member2, member1]", redis.smembers("key").toString());
	}

	@Test
	public void srem() {
		this.redis.del("key");
		this.redis.sadd("key", "member1");
		this.redis.sadd("key", "member2");
		this.redis.srem("key", "member1");
		Assert.assertEquals(1, redis.scard("key").intValue());
	}

	@Test
	public void spop() {
		this.redis.del("key");

		this.redis.sadd("key", "one");
		this.redis.sadd("key", "two");
		this.redis.sadd("key", "three");
		Assert.assertEquals(3, redis.scard("key").intValue());

		Assert.assertEquals("three", redis.spop("key"));
		Assert.assertEquals("[two, one]", redis.smembers("key").toString());
	}

	@Test
	public void scard() {
		this.redis.del("key");
		this.redis.sadd("key", "member1");
		this.redis.sadd("key", "member2");
		Assert.assertEquals(2, redis.scard("key").intValue());

	}

	@Test
	public void sismember() {
		this.redis.del("key");
		redis.zadd("key", 1, "member");
		Assert.assertTrue(redis.sismember(key, "member"));
	}

	@Test
	public void srandmember() {
		this.redis.del("key");
		redis.sadd("key", "member1");
		redis.sadd("key", "member2");
		Assert.assertNotNull(redis.srandmember("key"));
	}

	@Test
	public void zadd() {
		this.redis.del("key");
		redis.zadd("key", 1, 1);
		Assert.assertEquals(1L, redis.zcard(key).longValue());
	}

	@Test
	public void zrange() {
		this.redis.del("key");
		redis.zadd("key", 1, "element1");
		redis.zadd("key", 2, "element2");
		// Assert.equals(1, redis.zcard(key));

		Set<String> set = this.redis.zrange(key, 0, -1);
		Assert.assertEquals("[element1, element2]", set.toString());
	}

	@Test
	public void zrem() {
		this.redis.del("key");
		redis.zadd("key", 1, 1);
		redis.zadd("key", 1, "element2");
		this.redis.zrem(key, 1);
		Assert.assertEquals(1L, redis.zcard(key).longValue());

	}

	@Test
	public void zincrby() {
		this.redis.del("key");
		redis.zincrby("key", 1, "member");
		redis.zincrby("key", 1, "member");

		Assert.assertEquals(2.0D, this.redis.zscore("key", "member").doubleValue(), 0);
	}

	@Test
	public void zrank() {
		this.redis.del("key");
		redis.zincrby("key", 1, "member1");
		redis.zincrby("key", 2, "member2");

		Assert.assertEquals(0, this.redis.zrank("key", "member1").longValue());
		Assert.assertEquals(1, this.redis.zrank("key", "member2").longValue());
	}

	@Test
	public void zrevrank() {
		this.redis.del("key");
		redis.zincrby("key", 1, "member1");
		redis.zincrby("key", 2, "member2");

		Assert.assertEquals(1, this.redis.zrevrank("key", "member1").longValue());
		Assert.assertEquals(0, this.redis.zrevrank("key", "member2").longValue());
	}

	@Test
	public void zrevrange() {
		this.redis.del("key");
		redis.zadd("key", 1, "element1");
		redis.zadd("key", 2, "element2");
		// Assert.equals(1, redis.zcard(key));

		Set<String> set = this.redis.zrevrange(key, 0, -1);

		Assert.assertEquals("[element2, element1]", set.toString());
	}

	@Test
	public void mget() {
		this.redis.del("key1", "key2", "key3");
		redis.set("key1", "value1");
		redis.set("key2", "value2");
		redis.set("key3", "value3");
		List<String> list = this.redis.mget("key1", "key2", "key3");
		Assert.assertEquals("[value1, value2, value3]", list.toString());
	}

	@Test
	public void getServerInfo() {
		Assert.assertEquals("redis.transaction.impl", redis.getServerInfo());
	}

	@Test
	public void getJedisPool() {
		// Assert.noimpl(redis).getJedisPool();
		try {
			redis.getJedisPool();
			Assert.fail("怎么没有抛异常?");
		}
		catch (NotImplementedException e) {

		}
	}

	@Test
	public void publish() {
		// Assert.noimpl(redis).publish("channel", "message");
		JedisPubSub jedisPubSub = Mockito.mock(JedisPubSub.class);
		redis.subscribe(jedisPubSub, "channel");
		redis.publish("channel", "message");
		Mockito.verify(jedisPubSub).onMessage("channel", "message");
	}

}