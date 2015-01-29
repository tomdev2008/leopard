package io.leopard.test4j.mock.transaction.redis;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.ZParams;

public class RedisTransactionUtil {
	/**
	 * score聚合计算
	 * 
	 * @return
	 */
	public static double aggregateScore(ZParams.Aggregate aggregate, Map<String, Object> map) {
		if (aggregate == ZParams.Aggregate.SUM) {
			return sumScore(map);
		}
		if (aggregate == ZParams.Aggregate.MIN) {
			return minScore(map);
		}
		if (aggregate == ZParams.Aggregate.MAX) {
			return maxScore(map);
		}
		throw new RuntimeException("未知聚合类型.");
	}

	/**
	 * score总和.
	 * 
	 * @param map
	 * @return
	 */
	public static double sumScore(Map<String, Object> map) {
		// Json.printMap(map, "map");
		// AGGREGATE
		double totalScore = 0;
		// int size = map.size() - 1;
		for (int i = 0; i < map.size(); i++) {
			double score = Double.parseDouble(map.get("score" + i).toString());
			totalScore += score;
		}
		return totalScore;
	}

	public static double minScore(Map<String, Object> map) {
		// AGGREGATE
		double minScore = Double.MAX_VALUE;
		// int size = map.size() - 1;
		for (int i = 0; i < map.size(); i++) {
			double score = Double.parseDouble(map.get("score" + i).toString());
			if (score < minScore) {
				minScore = score;
			}
		}
		return minScore;
	}

	public static double maxScore(Map<String, Object> map) {
		// AGGREGATE
		double maxScore = Double.MIN_VALUE;
		// int size = map.size() - 1;
		for (int i = 0; i < map.size(); i++) {
			double score = Double.parseDouble(map.get("score" + i).toString());
			if (score > maxScore) {
				maxScore = score;
			}
		}
		return maxScore;
	}

	public static String getUnionSql(List<Double> weights, String... sets) {
		String prefixSql = getUnionPrefixSql(sets);
		String postfixSql = getUnionPostfixSql(weights, sets);

		String sql = prefixSql + " " + postfixSql;
		return sql;
	}

	public static String getUnionPrefixSql(String... sets) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t0.value");
		for (int i = 0; i < sets.length; i++) {
			sb.append(", t" + i + ".score score" + i);
		}
		// sb.deleteCharAt(sb.length() - 1);
		sb.append(" from");
		return sb.toString();
	}

	public static String getUnionPostfixSql(List<Double> weights, String... sets) {
		if (weights == null || weights.isEmpty()) {
			throw new IllegalArgumentException("参数weights不能为空.");
		}
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (String key : sets) {
			double weight = weights.get(index);
			String sql = getWeightSql(key, weight);
			sb.append(" (" + sql + ") t" + index + ",");
			index++;
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 获取权重sql.
	 * 
	 * @param key
	 * @return
	 */
	public static String getWeightSql(String key, double weight) {
		String sql = "select value, score*" + weight + " score from memcache where `key`='" + key + "'";
		return sql;
	}
}
