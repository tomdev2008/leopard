package io.leopard.test4j.mock.transaction.redis;

import java.util.Date;

public class RedisBean {

	private String key;
	private String score;
	private String value;
	private Date expiry;

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getScore() {
		return score;
	}

	public String getField() {
		return score;
	}

	public void setScore(String score) {

		this.score = score;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
