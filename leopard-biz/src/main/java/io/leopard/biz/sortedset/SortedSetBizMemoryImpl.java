package io.leopard.biz.sortedset;

import io.leopard.burrow.util.ObjectUtil;
import io.leopard.burrow.util.SetUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.Tuple;

public class SortedSetBizMemoryImpl implements SortedSetBiz {

	private Map<String, Double> data = new ConcurrentHashMap<String, Double>();

	@Override
	public boolean zadd(String element, double score) {
		this.data.put(element, score);
		return true;
	}

	@Override
	public Double zscore(String element) {
		return data.get(element);
	}

	@Override
	public boolean zrem(String element) {
		Double score = data.remove(element);
		return ObjectUtil.isNotNull(score);
	}

	@Override
	public Set<Tuple> listAll() {
		Set<Entry<String, Double>> set = data.entrySet();
		if (SetUtil.isEmpty(set)) {
			return null;
		}
		Set<Tuple> result = new HashSet<Tuple>();
		for (Entry<String, Double> entry : set) {
			Tuple tuple = new Tuple(entry.getKey(), entry.getValue());
			result.add(tuple);
		}
		return result;
	}
	
	

}
