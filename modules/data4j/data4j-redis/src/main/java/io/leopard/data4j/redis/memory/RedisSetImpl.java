package io.leopard.data4j.redis.memory;

import io.leopard.burrow.util.NumberUtil;
import io.leopard.burrow.util.SetUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RedisSetImpl implements IRedisSet {

	private Map<String, Set<String>> data = new ConcurrentHashMap<String, Set<String>>();

	protected Set<String> getSet(String key) {
		Set<String> set = this.data.get(key);
		if (set == null) {
			set = new LinkedHashSet<String>();
			data.put(key, set);
		}
		return set;
	}

	@Override
	public Long sadd(String key, String... members) {
		long count = 0;
		for (String member : members) {
			Set<String> set = this.getSet(key);
			if (set.contains(member)) {
				continue;
			}
			set.add(member);
			count++;
		}
		return count;
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
	public Set<String> smembers(String key) {
		List<String> list = new ArrayList<String>();
		Set<String> set = this.getSet(key);
		for (String element : set) {
			list.add(0, element);
		}
		Set<String> result = new LinkedHashSet<String>();
		for (String element : list) {
			result.add(element);
		}
		return result;
	}

	@Override
	public String spop(String key) {
		Set<String> set = this.getSet(key);
		if (SetUtil.isEmpty(set)) {
			return null;
		}
		String member = set.iterator().next();
		this.srem(key, member);
		return member;
	}

	@Override
	public Long srem(String key, String... members) {
		long total = 0;
		for (String member : members) {
			total += this.srem(key, member);
		}
		return total;
	}

	protected Long srem(String key, String member) {
		boolean result = this.getSet(key).remove(member);
		System.out.println("result:" + result + " member:" + member);
		if (result) {
			return 1L;
		}
		else {
			return 0L;
		}
	}

	@Override
	public Long scard(String key) {
		return (long) this.getSet(key).size();
	}

	@Override
	public Boolean sismember(String key, String member) {
		return this.getSet(key).contains(member);
	}

	@Override
	public String srandmember(String key) {
		Set<String> set = this.getSet(key);
		if (SetUtil.isEmpty(set)) {
			return null;
		}
		int rand = NumberUtil.random(set.size());
		return SetUtil.elementAt(set, rand);
	}

	@Override
	public Boolean exists(String key) {
		return data.containsKey(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		if (!this.exists(key)) {
			return 0L;
		}
		return 1L;
	}

	@Override
	public Long del(String key) {
		Object value = data.remove(key);
		if (value == null) {
			return 0L;
		}
		else {
			return 1L;
		}
	}

	@Override
	public boolean flushAll() {
		data.clear();
		return true;
	}

}
