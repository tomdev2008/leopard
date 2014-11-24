package io.leopard.commons.utility;

import org.junit.Test;

public class GenericUtilTest {

	@Test
	public void GenericUtil() {
		new GenericUtil();
	}

	// public static interface IMap<BEAN, KEYTYPE> {
	//
	// }
	//
	// public static class TestMap<BEAN, KEYTYPE> extends ContextImpl implements
	// IMap<BEAN, KEYTYPE> {
	// public Class<?> beanClazz;
	// public Class<?> keyClazz;
	//
	// public TestMap() {
	// Type[] types = GenericUtil.getActualTypeArguments(this);
	// this.beanClazz = (Class<?>) types[0];
	// this.keyClazz = (Class<?>) types[1];
	// }
	//
	// }
	//
	// @Test
	// public void getActualTypeArguments() {
	// TestMap<String, Integer> testMap = new TestMap<String, Integer>();
	// System.out.println("beanClazz:" + testMap.beanClazz);
	// System.out.println("keyClazz:" + testMap.keyClazz);
	// }

}