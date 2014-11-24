package io.leopard.util;

import io.leopard.util.Pair;
import org.junit.Assert;

import org.junit.Test;

public class PairTest {

	protected Pair<String, String> pair = new Pair<String, String>("key", "value");

	@Test
	public void Pair() {

	}

	@Test
	public void getKey() {
		Assert.assertEquals("key", pair.getKey());
	}

	@Test
	public void getValue() {
		Assert.assertEquals("value", pair.getValue());
	}

	@Test
	public void setValue() {
		Pair<String, String> pair = new Pair<String, String>("key", "value");
		pair.setValue("value1");
		Assert.assertEquals("value1", pair.getValue());
	}

	@Test
	public void toString2() {
		this.pair.toString();
	}

}