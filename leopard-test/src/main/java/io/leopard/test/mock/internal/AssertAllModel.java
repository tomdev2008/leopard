package io.leopard.test.mock.internal;

import io.leopard.test.mock.reflect.PackageUtil;

import java.lang.reflect.Modifier;
import java.util.List;

public class AssertAllModel {
	public static void assertAllModels(String packageName) {
		List<Class<?>> classList = PackageUtil.getClassList(packageName);
		for (Class<?> clazz : classList) {
			try {
				assertModel(clazz);
			}
			catch (RuntimeException e) {
				System.err.println("error model:" + clazz.getName());
				// throw e;
				e.printStackTrace();
			}
		}
	}

	protected static void assertModel(Class<?> clazz) {
		int mod = clazz.getModifiers();
		if (Modifier.isAbstract(mod)) {
			return;
		}
		if (clazz.isEnum()) {
			AssertEnum.assertEnum(clazz);
		}
		else if (clazz.getName().endsWith("Key")) {
			AssertKeyModel.assertKeyModel(clazz);
		}
		else {
			AssertModel.assertModel(clazz);
		}
	}
}
