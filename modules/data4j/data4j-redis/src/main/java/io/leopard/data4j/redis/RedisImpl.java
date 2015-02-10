package io.leopard.data4j.redis;

import io.leopard.burrow.util.EncryptUtil;
import io.leopard.data4j.redis.util.IJedisPool;
import io.leopard.data4j.redis.util.RedisBackup;
import io.leopard.data4j.redis.util.RedisUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.SystemUtils;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Redis实现(单机Redis).
 * 
 * @author 阿海
 * 
 */
public class RedisImpl extends AbstractRedis implements Redis {

	private IJedisPool pool;

	protected String server;

	public RedisImpl() {

	}

	public RedisImpl(String server, int maxActive, int timeout) {
		this(server, maxActive, 0, false, timeout);
	}

	public RedisImpl(String server, int maxActive, int initialPoolSize, boolean enableBackup, int timeout) {
		this(server, maxActive, initialPoolSize, enableBackup, "04:01", timeout);
	}

	/**
	 * 构造redis客户端对象.
	 * 
	 * @param server
	 *            服务器
	 * @param maxActive
	 *            连接池最大连接数
	 * @param enableBackup
	 *            是否开启备份
	 */
	public RedisImpl(String server, int maxActive, int initialPoolSize, boolean enableBackup, String backupTime, int timeout) {
		this.setServer(server);
		this.setMaxActive(maxActive);
		this.setInitialPoolSize(initialPoolSize);
		this.setEnableBackup(enableBackup);
		this.setBackupTime(backupTime);
		this.setTimeout(timeout);
	}

	public void setServer(String server) {
		this.server = server;
	}

	private interface Invoker {
		public Object execute(Jedis jedis);
	}

	@Override
	public void init() {
		// System.err.println("RedisImpl server:" + server);
		try {
			this.pool = RedisUtil.createJedisPool(server, timeout, maxActive);
		}
		catch (RuntimeException e) {
			System.err.println("server:" + server + " timeout:" + timeout);
			logger.error("server:" + server + " timeout:" + timeout);
			throw e;
		}
		// this.ludis = new LudisImpl(this);

		if (enableBackup) {
			RedisBackup backup = new RedisBackup();
			backup.setBackupTime(backupTime);
			backup.run(this);
		}

		try {
			this.initPool();
		}
		catch (Exception e) {
			logger.error("初始化redis[" + this.server + "]连接数出错:" + e.getMessage());
		}
	}

	/**
	 * 初始化默认连接数量.
	 */
	protected void initPool() {
		// System.err.println("initPool server:" + this.server +
		// " initialPoolSize:" + initialPoolSize + " start");
		if (this.initialPoolSize <= 0) {
			return;
		}
		// windows环境关闭初始化默认redis连接功能?
		if (SystemUtils.IS_OS_WINDOWS) {
			return;
		}
		int size;
		if (this.initialPoolSize > this.maxActive) {
			size = this.maxActive;
		}
		else {
			size = this.initialPoolSize;
		}

		Jedis[] jedisArr = new Jedis[size];
		for (int i = 0; i < jedisArr.length; i++) {
			jedisArr[i] = this.getResource();
		}

		// int numActive = pool.getInternalPool().getNumActive();

		for (int i = 0; i < jedisArr.length; i++) {
			this.returnResource(jedisArr[i]);
		}
		// int numActive2 = pool.getInternalPool().getNumActive();
		// System.err.println("initPool server:" + this.server +
		// " initialPoolSize:" + initialPoolSize + " numActive:" + numActive +
		// " numActive2:" + numActive2 + " end");
	}

	/**
	 * 封装错误信息.
	 * 
	 * @param e
	 * @return
	 */
	protected String getErrorMessage(Exception e) {

		String ip;
		try {
			ip = InetAddress.getByName(server.split(":")[0]).getHostAddress();
		}
		catch (UnknownHostException e1) {
			ip = null;
			// throw new RuntimeException(e1.getMessage(), e1);
		}
		String message = "server:" + server + " ip:" + ip + " messsage:" + e.getMessage();
		logger.error(message);
		return message;
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#getResource()
	 */
	public Jedis getResource() {
		// long startTime = System.nanoTime();
		try {
			return this.pool.getResource();
		}
		// ahai 20131026 新版redis连接池的异常信息已经包含了IP和端口信息.
		catch (JedisConnectionException e) {
			String message = this.getErrorMessage(e);
			throw new JedisConnectionException(message, e);
			// throw e;
		}
		// finally {
		// long endTime = System.nanoTime();
		// long time = (endTime - startTime) / 1000L / 1000L; // time 单位:毫秒
		// if (time >= 10) {
		// String message = "server:" + server;
		// message += " time:" + time;
		// DataSourceLog.debug("getResource", message);
		// }
		// if (time >= 50) {
		// this.getResourceSlowLog(time);
		// }
		// METHOD_TIME.addByStartTime("io.leopard.data.redis.RedisImpl.getResource",
		// startTime);
		// }
	}

	// private int slowCount = 0;

	/**
	 * Redis连接慢日志.
	 * 
	 * @param time
	 */
	protected void getResourceSlowLog(long time) {
		// slowCount++;
		// if (slowCount > 10) {
		// AlarmSenderImpl.getInstance().send("连接Redis太慢,server:" + server +
		// " time[" + time + "]");
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#returnResource(Jedis )
	 */
	public void returnResource(Jedis jedis) {
		this.pool.returnResource(jedis);
	}

	// private synchronized Jedis initJedis() {
	// if (jedis != null) {
	// return jedis;
	// }
	// jedis = new Jedis(server, 6379, 1000 * 10);
	// return jedis;
	// }

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#del(String... )
	 */
	public Long del(final String... keys) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.del(keys);
			}
		});
		// final Jedis jedis = this.getResource();
		// try {
		// return jedis.del(keys);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#del(String )
	 */
	public Long del(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.del(key);
			}
		});
		// final Jedis jedis = this.getResource();
		// try {
		// return jedis.del(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#append(String , String)
	 */
	public Long append(final String key, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.append(key, value);
			}
		});
		// final Jedis jedis = this.getResource();
		// try {
		// return jedis.append(key, value);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#append(String, String, int)
	 */
	public boolean append(final String key, final String value, final int seconds) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Transaction transaction = jedis.multi();
				transaction.append(key, value);
				transaction.expire(key, seconds);
				transaction.exec();
				return true;
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// Transaction transaction = jedis.multi();
		// transaction.append(key, value);
		// transaction.expire(key, seconds);
		// transaction.exec();
		// return true;
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#append(List<String>, List<String>, int)
	 */
	public boolean append(final List<String> keyList, final List<String> valueList, final int seconds) {
		RedisUtil.checkList(keyList, valueList);

		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Transaction transaction = jedis.multi();
				for (int i = 0; i < keyList.size(); i++) {
					transaction.append(keyList.get(i), valueList.get(i));
					transaction.expire(keyList.get(i), seconds);
				}
				logger.info("seconds:123");
				transaction.exec();
				return true;
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// Transaction transaction = jedis.multi();
		// for (int i = 0; i < keyList.size(); i++) {
		// transaction.append(keyList.get(i), valueList.get(i));
		// transaction.expire(keyList.get(i), seconds);
		// }
		// logger.info("seconds:123");
		// transaction.exec();
		// return true;
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#set(String , String)
	 */
	public String set(final String key, final String value) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.set(key, value);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.set(key, value);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#set(List<String>, List<String>)
	 */
	public boolean set(final List<String> keyList, final List<String> valueList) {
		RedisUtil.checkList(keyList, valueList);

		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Transaction transaction = jedis.multi();
				for (int i = 0; i < keyList.size(); i++) {
					transaction.set(keyList.get(i), valueList.get(i));
				}
				transaction.exec();
				return true;
			}
		});

	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#expire(String, int)
	 */
	public Long expire(final String key, final int seconds) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#ttl(String)
	 */
	public Long ttl(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.ttl(key);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#set(String , String , int) 
	 */
	public String set(final String key, final String value, final int seconds) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#rename(String, String)
	 */
	public boolean rename(final String oldkey, final String newkey) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				String result = jedis.rename(oldkey, newkey);
				return "OK".equalsIgnoreCase(result);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		//
		// String result = jedis.rename(oldkey, newkey);
		// return "OK".equalsIgnoreCase(result);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#rename(String, String, int)
	 */
	public boolean rename(String oldkey, String newkey, int seconds) {
		boolean success = this.rename(oldkey, newkey);
		return success;
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#info()
	 */
	public RedisInfo info() {
		String info = (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.info();
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// info = jedis.info();
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }

		// String json = Json.toJson(map);
		// System.out.println("json:" + json);
		// System.out.println("info:" + info);
		RedisInfo redisInfo = new RedisInfo(info);
		return redisInfo;
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#getUsedMemory()
	 */
	public long getUsedMemory() {
		RedisInfo redisInfo = this.info();
		long usedMemory = redisInfo.getUsedMemory();
		return usedMemory;
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#dbSize()
	 */
	public long dbSize() {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.dbSize();
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.dbSize();
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#flushAll()
	 */
	public boolean flushAll() {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				jedis.flushAll();
				return true;
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// jedis.flushAll();
		// return true;
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#flushDB()
	 */
	public boolean flushDB() {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				jedis.flushDB();
				return true;
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// jedis.flushDB();
		// return true;
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }

	}

	// private void start1() {
	// }

	public void start() {
		// this.del("key");
		// this.del("key2");
		// System.out.println("num:" + this.jedis.append("key", "1"));
		// System.out.println("num:" + this.jedis.append("key", "1"));
		// System.out.println("num:" + this.jedis.append("key", "2"));
		//
		// this.jedis.set("key2", "value");
		// System.out.println("list:" + this.getJedis().get("key"));
		// System.out.println("rename:" + this.getJedis().rename("key",
		// "key2"));
		// // jedis.renamenx("key", "key2");
		// System.out.println("key1:" + this.getJedis().get("key"));
		// System.out.println("key2:" + this.getJedis().get("key2"));
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#incr(String)
	 */
	public Long incr(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.incr(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// Long num = jedis.incr(key);
		// return num;
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#get(String )
	 */
	public String get(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.get(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.get(key);
		// }
		// catch (JedisConnectionException e) {
		// this.pool.returnBrokenResource(jedis);
		// String message = this.getErrorMessage(e);
		// message += " key:" + key;
		// throw new JedisConnectionException(message, e);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	/**
	 * 执行jedis的操作.
	 * 
	 * @param invoker
	 *            调度接口
	 * @return
	 */
	protected Object execute(Invoker invoker) {
		Jedis jedis = this.getResource();
		try {
			return invoker.execute(jedis);
		}
		catch (JedisConnectionException e) {
			this.pool.returnBrokenResource(jedis);
			String message = this.getErrorMessage(e);
			// message += " key:" + key;
			throw new JedisConnectionException(message, e);
		}
		catch (RuntimeException e) {
			this.pool.returnBrokenResource(jedis);
			throw e;
		}
		catch (Exception e) {
			this.pool.returnBrokenResource(jedis);
			throw new RuntimeException(e.getMessage(), e);
		}
		finally {
			this.pool.returnResource(jedis);
		}
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#getSet(String , String)
	 */
	public String getSet(final String key, final String value) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.getSet(key, value);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.getSet(key, value);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zcard(String )
	 */
	public Long zcard(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zcard(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zcard(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeWithScores(String, long, long)
	 */
	public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeWithScores(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrangeWithScores(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrange(String, long, long)
	 */
	public Set<String> zrevrange(final String key, final long start, final long end) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrange(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrange(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrange(String, long, long)
	 */
	public Set<String> zrange(final String key, final long start, final long end) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrange(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrange(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#zadd(String , double , int)
	 */
	public Long zadd(final String key, final double score, final long member) {
		return this.zadd(key, score, Long.toString(member));
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zadd(String, double, String)
	 */
	public Long zadd(final String key, final double score, final String member) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zadd(key, score, member);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zadd(key, score, member);
		// }
		// catch (JedisConnectionException e) {
		// this.pool.returnBrokenResource(jedis);
		// String message = this.getErrorMessage(e);
		// message += " key:" + key + " member:" + member;
		// throw new JedisConnectionException(message, e);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zadd(String, Map<Double, String>)
	 */
	public Long zadd(final String key, final Map<String, Double> scoreMembers) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zadd(key, scoreMembers);
			}
		});
	}

	// @Override
	// /**
	// * @see io.leopard.data.redis.Redis#zadd2(String, Map<String, Double>)
	// */
	// public Long zadd2(final String key, final Map<String, Double>
	// scoreMembers) {
	// return this.zadd(key, scoreMembers);
	// }

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#setrange(String, long, String)
	 */
	public Long setrange(final String key, final long offset, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setrange(key, offset, value);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#multi()
	 */
	public Transaction multi() {
		Jedis jedis = this.getResource();
		return jedis.multi();
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#srem(String, String... )
	 */
	public Long srem(final String key, final String... member) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.srem(key, member);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#exists(String)
	 */
	public Boolean exists(final String key) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.exists(key);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#type(String)
	 */
	public String type(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.type(key);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#expireAt(String, long)
	 */
	public Long expireAt(final String key, final long unixTime) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.expireAt(key, unixTime);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#setbit(String, long, boolean)
	 */
	public Boolean setbit(final String key, final long offset, final boolean value) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setbit(key, offset, value);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#getbit(String, long)
	 */
	public Boolean getbit(final String key, final long offset) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.getbit(key, offset);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#getrange(String, long , long)
	 */
	public String getrange(final String key, final long startOffset, final long endOffset) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.getrange(key, startOffset, endOffset);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#setnx(String, String)
	 */
	public Long setnx(final String key, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setnx(key, value);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#setex(String, int, String)
	 */
	public String setex(final String key, final int seconds, final String value) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#decrBy(String, long)
	 */
	public Long decrBy(final String key, final long integer) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.decrBy(key, integer);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#decr(String)
	 */
	public Long decr(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.decr(key);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#incrBy(String, long)
	 */
	public Long incrBy(final String key, final long integer) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.incrBy(key, integer);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#substr(String, int, int)
	 */
	public String substr(final String key, final int start, final int end) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.substr(key, start, end);
			}
		});
	}

	// @Override
	// /**
	// * @see io.leopard.data.redis.Redis#hset(String , int , String)
	// */
	// public Long hset(String key, int field, String value) {
	// return this.hset(key, Integer.toString(field), value);
	// }

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#hset(String , int , String)
	 */
	public Long hset(String key, long field, String value) {
		return this.hset(key, Long.toString(field), value);
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hset(String, String, String )
	 */
	public Long hset(final String key, final String field, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}

	// @Override
	// /**
	// * @see io.leopard.data.redis.Redis#hget(String, int)
	// */
	// public String hget(String key, int field) {
	// return this.hget(key, Integer.toString(field));
	// }

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#hget(String, int)
	 */
	public String hget(String key, long field) {
		return this.hget(key, Long.toString(field));
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hget(String, String)
	 */
	public String hget(final String key, final String field) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hget(key, field);
		// }
		// catch (JedisConnectionException e) {
		// this.pool.returnBrokenResource(jedis);
		// String message = this.getErrorMessage(e);
		// message += " key:" + key + " field:" + field;
		// throw new JedisConnectionException(message, e);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hsetnx(String, String, String)
	 */
	public Long hsetnx(final String key, final String field, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hsetnx(key, field, value);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hsetnx(key, field, value);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hmset(String, Map<String, String>)
	 */
	public String hmset(final String key, final Map<String, String> hash) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hmset(key, hash);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hmset(key, hash);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.Redis#mget(String... )
	 */
	public List<String> mget(final String... keys) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.mget(keys);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.mget(keys);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hmget(String, String...)
	 */
	public List<String> hmget(final String key, final String... fields) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hmget(key, fields);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hmget(key, fields);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hincrBy(String, String, long)
	 */
	public Long hincrBy(final String key, final String field, final long value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hincrBy(key, field, value);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hincrBy(key, field, value);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hexists(String, String)
	 */
	public Boolean hexists(final String key, final String field) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hexists(key, field);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hexists(key, field);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	// @Override
	// /**
	// * @see io.leopard.data.redis.Redis#hdel(String, int)
	// */
	// public Long hdel(String key, int field) {
	// return this.hdel(key, Integer.toString(field));
	// }

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#hdel(String, int)
	 */
	public Long hdel(String key, long field) {
		return this.hdel(key, Long.toString(field));
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hdel(String, String...)
	 */
	public Long hdel(final String key, final String... field) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hdel(key, field);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hdel(key, field);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hlen(String)
	 */
	public Long hlen(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hlen(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hlen(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hkeys(String)
	 */
	public Set<String> hkeys(final String key) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hkeys(key);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hkeys(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hvals(String)
	 */
	public List<String> hvals(final String key) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hvals(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hvals(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hgetAll(String)
	 */
	public Map<String, String> hgetAll(final String key) {
		return (Map<String, String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hgetAll(key);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hgetAll(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#rpush(String, String...)
	 */
	public Long rpush(final String key, final String... strings) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.rpush(key, strings);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.rpush(key, strings);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#lpush(String, String...)
	 */
	public Long lpush(final String key, final String... strings) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lpush(key, strings);
			}
		});
		//
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.lpush(key, strings);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#llen(String )
	 */
	public Long llen(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.llen(key);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.llen(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#lrange(String, long, long)
	 */
	public List<String> lrange(final String key, final long start, final long end) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lrange(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.lrange(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#ltrim(String, long, long)
	 */
	public String ltrim(final String key, final long start, final long end) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.ltrim(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.ltrim(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#lindex(String, long)
	 */
	public String lindex(final String key, final long index) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lindex(key, index);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.lindex(key, index);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#lset(String, long, String) 
	 */
	public String lset(final String key, final long index, final String value) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lset(key, index, value);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.lset(key, index, value);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#lrem(String, long, String)
	 */
	public Long lrem(final String key, final long count, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lrem(key, count, value);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.lrem(key, count, value);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#lpop(String )
	 */
	public String lpop(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lpop(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.lpop(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#rpop(String)
	 */
	public String rpop(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.rpop(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.rpop(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#sadd(String, String...)
	 */
	public Long sadd(final String key, final String... members) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sadd(key, members);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.sadd(key, members);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#smembers(String)
	 */
	public Set<String> smembers(final String key) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.smembers(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.smembers(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#spop(String )
	 */
	public String spop(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.spop(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.spop(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#scard(String )
	 */
	public Long scard(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.scard(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.scard(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#sismember(String, String)
	 */
	public Boolean sismember(final String key, final String member) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sismember(key, member);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.sismember(key, member);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> srandmember(final String key, final int count) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.srandmember(key, count);
			}
		});
	}

	@Override
	public String srandmember(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.srandmember(key);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#zrem(String, int)
	 */
	public Long zrem(String key, long member) {
		return this.zrem(key, Long.toString(member));
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrem(String, String...)
	 */
	public Long zrem(final String key, final String... members) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrem(key, members);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrem(key, members);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zincrby(String, double, String)
	 */
	public Double zincrby(final String key, final double score, final String member) {
		return (Double) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zincrby(key, score, member);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zincrby(key, score, member);
		// }
		// catch (JedisConnectionException e) {
		// this.pool.returnBrokenResource(jedis);
		// String message = this.getErrorMessage(e);
		// throw new JedisConnectionException(message, e);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrank(String, String)
	 */
	public Long zrank(final String key, final String member) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrank(key, member);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrank(key, member);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrank(String, String)
	 */
	public Long zrevrank(final String key, final String member) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrank(key, member);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrank(key, member);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#zinterstore(String, String... )
	 */
	public Long zinterstore(final String dstkey, final String... sets) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				// System.out.println("sets:" + StringUtils.join(sets, ","));
				return jedis.zinterstore(dstkey, sets);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zinterstore(dstkey, sets);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#zinterstore(String, ZParams, String... )
	 */
	public Long zinterstore(final String dstkey, final ZParams params, final String... sets) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zinterstore(dstkey, params, sets);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zinterstore(dstkey, params, sets);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeWithScores(String , long, long)
	 */
	public Set<Tuple> zrangeWithScores(final String key, final long start, final long end) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeWithScores(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		//
		// return jedis.zrangeWithScores(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zscore(String, String)
	 */
	public Double zscore(final String key, final String member) {
		return (Double) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {

				return jedis.zscore(key, member);

			}
		});
	}

	@Override
	public Double zscore(final String key, final long member) {
		return this.zscore(key, Long.toString(member));
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#sort(String )
	 */
	public List<String> sort(final String key) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sort(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.sort(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#sort(String, SortingParams)
	 */
	public List<String> sort(final String key, final SortingParams sortingParameters) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sort(key, sortingParameters);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.sort(key, sortingParameters);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zcount(String, double, double)
	 */
	public Long zcount(final String key, final double min, final double max) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zcount(key, min, max);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zcount(String, String, String)
	 */
	public Long zcount(final String key, final String min, final String max) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zcount(key, min, max);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeByScore(String, double, double)
	 */
	public Set<String> zrangeByScore(final String key, final double min, final double max) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrangeByScore(key, min, max);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeByScore(String, String, String)
	 */
	public Set<String> zrangeByScore(final String key, final String min, final String max) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrangeByScore(key, min, max);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeByScore(String, String, String, int, int)
	 */
	public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset, final int count) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrangeByScore(key, min, max, offset, count);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeByScore(String, double, double)
	 */
	public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrangeByScore(key, max, min);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeByScore(String, String, String, int, int)
	 */
	public Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset, final int count) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrangeByScore(key, max, min, offset, count);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeByScore(String, String, String)
	 */
	public Set<String> zrevrangeByScore(final String key, final String max, final String min) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrangeByScore(key, max, min);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeByScore(String, double, double, int, int)
	 */
	public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset, final int count) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrangeByScore(key, min, max, offset, count);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeByScore(String, double, double, int, int)
	 */
	public Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset, final int count) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrangeByScore(key, max, min, offset, count);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeByScoreWithScores(String, double, double)
	 */
	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrangeByScoreWithScores(key, min, max);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeByScoreWithScores(String, String, String, int, int)
	 */
	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset, final int count) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeByScoreWithScores(String, String, String)
	 */
	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrangeByScoreWithScores(key, min, max);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeByScoreWithScores(String, double, double)
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		//
		// return jedis.zrevrangeByScoreWithScores(key, max, min);
		// }
		// catch (JedisConnectionException e) {
		// this.pool.returnBrokenResource(jedis);
		// String message = this.getErrorMessage(e);
		// throw new JedisConnectionException(message, e);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeByScoreWithScores(String, String, String, int, int)
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset, final int count) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrangeByScoreWithScores(key, max, min, offset,
		// count);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeByScoreWithScores(String, String, String)
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrangeByScoreWithScores(key, max, min);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrangeByScoreWithScores(String, double, double, int, int)
	 */
	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset, final int count) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zrevrangeByScoreWithScores(String, double, double, int, int)
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset, final int count) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zrevrangeByScoreWithScores(key, max, min, offset,
		// count);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zremrangeByRank(String, long, long)
	 */
	public Long zremrangeByRank(final String key, final long start, final long end) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zremrangeByRank(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zremrangeByRank(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zremrangeByScore(String, double, double)
	 */
	public Long zremrangeByScore(final String key, final double start, final double end) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zremrangeByScore(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#zremrangeByScore(String, String, String)
	 */
	public Long zremrangeByScore(final String key, final String start, final String end) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.zremrangeByScore(key, start, end);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#linsert(String, LIST_POSITION, String, String)
	 */
	public Long linsert(final String key, final LIST_POSITION where, final String pivot, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.linsert(key, where, pivot, value);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.linsert(key, where, pivot, value);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.Redis#keys(String)
	 */
	public Set<String> keys(final String pattern) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.keys(pattern);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.keys(pattern);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#zunionstore(String, String...)
	 */
	public Long zunionstore(final String dstkey, final String... sets) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zunionstore(dstkey, sets);
			}
		});
	}

	@Override
	public Long zunionstore(final String dstkey, final ZParams params, final String... sets) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zunionstore(dstkey, params, sets);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#lpushx(String, String)
	 */
	public Long lpushx(final String key, final String string) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lpushx(key, string);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.lpushx(key, string);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#rpushx(String, String) 
	 */
	public Long rpushx(final String key, final String string) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.rpushx(key, string);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.rpushx(key, string);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#getServerInfo()
	 */
	public String getServerInfo() {
		return this.server;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.Redis#zunionStoreInJava(String... )
	 */
	public Set<String> zunionStoreInJava(final String... sets) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Set<String> strings = new HashSet<String>(75 * sets.length);
				for (int i = 1; i < sets.length; i++) {
					Set<String> _sets = jedis.zrange(sets[i], 0, -1);
					strings.addAll(_sets);
				}
				return strings;
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// Set<String> strings = new HashSet<String>(75 * sets.length);
		// for (int i = 1; i < sets.length; i++) {
		// Set<String> _sets = jedis.zrange(sets[i], 0, -1);
		// strings.addAll(_sets);
		// }
		// return strings;
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.Redis#zunionStoreByScoreInJava(double, double, String...)
	 */
	public Set<String> zunionStoreByScoreInJava(final double min, final double max, final String... sets) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Set<String> strings = new HashSet<String>(75 * sets.length);
				for (int i = 0; i < sets.length; i++) {
					Set<String> _sets = jedis.zrangeByScore(sets[i], min, max);
					strings.addAll(_sets);
				}
				return strings;
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// Set<String> strings = new HashSet<String>(75 * sets.length);
		// for (int i = 0; i < sets.length; i++) {
		// Set<String> _sets = jedis.zrangeByScore(sets[i], min, max);
		// strings.addAll(_sets);
		// }
		// return strings;
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#setrange(String, int, String)
	 */
	public Long setrange(String key, int offset, String value) {
		Long temp = (long) offset;
		return this.setrange(key, temp, value);
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#evalsha(String, int, String... )
	 */
	public Object evalsha(final String sha1, final int keyCount, final String... params) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.evalsha(sha1, keyCount, params);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.evalsha(sha1, keyCount, params);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#evalsha(String, List<String>, List<String>)
	 */
	public Object evalsha(final String sha1, final List<String> keys, final List<String> args) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.evalsha(sha1, keys, args);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.evalsha(sha1, keys, args);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#evalAssertSha(String, String)
	 */
	public Object evalAssertSha(String sha, String script) {
		String sha1 = EncryptUtil.sha1(script).toLowerCase();
		if (!sha1.equals(sha)) {
			throw new IllegalArgumentException("sha[" + sha + "][" + sha1 + "]值不对.");
		}
		return this.eval(script);
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#evalReturnSha(String)
	 */
	public String evalReturnSha(String script) {
		this.eval(script);
		return EncryptUtil.sha1(script).toLowerCase();
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#eval(String)
	 */
	public Object eval(final String script) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.eval(script);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.eval(script);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#eval(String, int, String... )
	 */
	public Object eval(final String script, final int keyCount, final String... params) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.eval(script, keyCount, params);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.eval(script, keyCount, params);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#evalsha(String)
	 */
	public Object evalsha(final String script) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.evalsha(script);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.evalsha(script);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	// @Override
	// /**
	// * @see io.leopard.data.redis.Redis#getLudis()
	// */
	// public Ludis getLudis() {
	// return ludis;
	// }

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#bgrewriteaof()
	 */
	public String bgrewriteaof() {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.bgrewriteaof();
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.bgrewriteaof();
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#bgsave()
	 */
	public String bgsave() {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.bgsave();
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.bgsave();
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#save()
	 */
	public String save() {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.save();
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.save();
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#getJedisPool()
	 */
	public IJedisPool getJedisPool() {
		return pool;
	}

	@Override
	public Long publish(final String channel, final String message) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.publish(channel, message);
			}
		});
	}

	@Override
	public void psubscribe(final JedisPubSub jedisPubSub, final String... patterns) {
		this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				jedis.psubscribe(jedisPubSub, patterns);
				return null;
			}
		});
	}

	@Override
	public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
		this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				jedis.subscribe(jedisPubSub, channels);
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> sdiff(final String... keys) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sdiff(keys);
			}
		});
	}

	@Override
	public String randomKey() {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.randomKey();
			}
		});
	}

	@Override
	public Long persist(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.persist(key);
			}
		});
	}

	@Override
	public Boolean setbit(final String key, final long offset, final String value) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setbit(key, offset, value);
			}
		});
	}

	@Override
	public Long strlen(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.strlen(key);
			}
		});
	}

	@Override
	public Long lpushx(final String key, final String... string) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lpushx(key, string);
			}
		});
	}

	@Override
	public Long rpushx(final String key, final String... string) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.rpushx(key, string);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> blpop(final String arg) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.blpop(arg);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> brpop(final String arg) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.brpop(arg);
			}
		});
	}

	@Override
	public String echo(final String string) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.echo(string);
			}
		});
	}

	@Override
	public Long move(final String key, final int dbIndex) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.move(key, dbIndex);
			}
		});
	}

	@Override
	public Long bitcount(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.bitcount(key);
			}
		});
	}

	@Override
	public Long bitcount(final String key, final long start, final long end) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.bitcount(key, start, end);
			}
		});
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(final String key, final int cursor) {
		// int cursor有bug
		throw new NotImplementedException();
		// return (ScanResult<Entry<String, String>>) this.execute(new Invoker()
		// {
		// @SuppressWarnings("deprecation")
		// @Override
		// public Object execute(Jedis jedis) {
		// return jedis.hscan(key, cursor);
		// }
		// });
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScanResult<Entry<String, String>> hscan(final String key, final String cursor) {
		return (ScanResult<Entry<String, String>>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hscan(key, cursor);
			}
		});
	}

	@Override
	public ScanResult<String> sscan(final String key, final int cursor) {
		// int cursor有bug
		throw new NotImplementedException();
		// return (ScanResult<String>) this.execute(new Invoker() {
		// @Override
		// public Object execute(Jedis jedis) {
		// return jedis.sscan(key, cursor);
		// }
		// });
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final int cursor) {
		// int cursor有bug
		throw new NotImplementedException();
		// return (ScanResult<Tuple>) this.execute(new Invoker() {
		// @Override
		// public Object execute(Jedis jedis) {
		// return jedis.zscan(key, cursor);
		// }
		// });
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScanResult<String> sscan(final String key, final String cursor) {
		return (ScanResult<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sscan(key, cursor);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScanResult<Tuple> zscan(final String key, final String cursor) {
		return (ScanResult<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zscan(key, cursor);
			}
		});
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
	public void destroy() {

	}

}
