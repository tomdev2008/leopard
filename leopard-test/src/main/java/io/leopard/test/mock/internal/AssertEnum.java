package io.leopard.test.mock.internal;

import io.leopard.burrow.lang.inum.Onum;
import io.leopard.test.mock.reflect.MethodUtil;

import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

public class AssertEnum {
	public static void assertEnum(Class<?> enumClass) {
		// System.out.println("enumClass:" + enumClass.getName());
		Object bean = enumClass.getEnumConstants()[0];
		if (!(bean instanceof Onum)) {
			System.err.println("enumClass:" + enumClass.getName() + "没有实现Onum.");
			return;
		}
		@SuppressWarnings("unchecked")
		Onum<Object, Object> onum = (Onum<Object, Object>) bean;
		// System.out.println("key:" + onum.getKey());
		onum.getDesc();
		onum.getKey();
		//

		enumInvoke(onum, "toEnum");
		enumInvoke(onum, "contains");
	}

	protected static Object enumInvoke(Onum<Object, Object> onum, String methodName) {
		Method containsMethod = BeanUtils.findDeclaredMethodWithMinimalParameters(onum.getClass(), methodName);
		if (containsMethod != null) {
			return MethodUtil.invoke(onum, containsMethod, onum.getKey());
			// try {
			// return containsMethod.invoke(onum, onum.getKey());
			// }
			// catch (Exception e) {
			// throw new RuntimeException(e.getMessage(), e);
			// }
		}
		return null;
	}
}
