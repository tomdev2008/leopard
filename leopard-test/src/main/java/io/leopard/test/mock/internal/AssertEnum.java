package io.leopard.test.mock.internal;

public class AssertEnum {
	public static void assertEnum(Class<?> enumClass) {
		// FIXME ahai 未实现
		// Object bean = enumClass.getEnumConstants()[0];
		// if (!(bean instanceof Onum)) {
		// System.err.println("enumClass:" + enumClass.getName() + "没有实现Onum.");
		// return;
		// }
		// @SuppressWarnings("unchecked")
		// Onum<Object, Object> onum = (Onum<Object, Object>) bean;
		// onum.getDesc();
		// onum.getKey();
		// //
		//
		// enumInvoke(onum, "toEnum");
		// enumInvoke(onum, "contains");
	}

	// protected static Object enumInvoke(Onum<Object, Object> onum, String methodName) {
	// Method containsMethod = BeanUtils.findDeclaredMethodWithMinimalParameters(onum.getClass(), methodName);
	// if (containsMethod != null) {
	// return MethodUtil.invoke(onum, containsMethod, onum.getKey());
	// }
	// return null;
	// }
}
