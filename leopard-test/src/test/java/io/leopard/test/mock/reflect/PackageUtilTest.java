package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.Json;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PackageUtilTest {

	@Test
	public void PackageUtil() {
		new PackageUtil();
	}

	@Test
	public void getClassList() {
		List<Class<?>> result = PackageUtil.getClassList("io.leopard.test.mock.reflect");
		Json.printList(result, "result");
		Assert.assertNotNull(result);
	}

}