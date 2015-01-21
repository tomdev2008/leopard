package io.leopard.test.mock.proxy;

import io.leopard.burrow.refect.ClassTypeUtil;
import io.leopard.test.CustomBeanUtil;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.mockito.Matchers;
import org.mockito.stubbing.Stubber;

public class MockMethodUtil {
	public static <T> void invokeMethod(Stubber stubber, T mock, String methodName) {
		Method[] methods = mock.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				T proxy = stubber.when(mock);
				// System.out.println("method:" + method.toGenericString());
				Object[] args = toAnyArgs(method.getParameterTypes());
				try {
					method.invoke(proxy, args);
					// System.out.println("method:" + method.toGenericString());
				}
				catch (Exception e) {
					System.out.println("method:" + method.toGenericString() + " args:" + args.length);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	//
	// public static <T> void invokeMethod(T proxy, String methodName) {
	// // public int addList(int newsId, List<String> usernameList, final List<String> codeList, String sender, final MessageType.Type type, final int from, String title, String subTitle, String
	// // content, Date expireTime, boolean copyToNotice) {
	// Method[] methods = proxy.getClass().getMethods();
	// for (Method method : methods) {
	// // if (method.getName().equals("addList") && method.getParameterTypes().length != 11) {
	// // continue;
	// // }
	// if (method.getName().equals(methodName)) {
	// System.out.println("method:" + method.toGenericString());
	// Object[] args = toAnyArgs(method.getParameterTypes());
	// try {
	// method.invoke(proxy, args);
	// // System.out.println("method:" + method.toGenericString());
	// }
	// catch (Exception e) {
	// System.out.println("method:" + method.toGenericString() + " args:" + args.length);
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// }
	// }
	// }

	protected static Object[] toAnyArgs(Class<?>[] params) {
		Object[] args = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			Class<?> param = params[i];
			if (param.getName().equals(String.class.getName())) {
				args[i] = Matchers.anyString();
			}
			else if (ClassTypeUtil.isBoolean(param)) {
				args[i] = Matchers.anyBoolean();
			}
			else if (ClassTypeUtil.isInteger(param)) {
				args[i] = Matchers.anyInt();
			}
			else if (ClassTypeUtil.isLong(param)) {
				args[i] = Matchers.anyLong();
			}
			else if (ClassTypeUtil.isFloat(param)) {
				args[i] = Matchers.anyFloat();
			}
			else if (ClassTypeUtil.isDouble(param)) {
				args[i] = Matchers.anyDouble();
			}
			else if (param.getName().equals(Date.class.getName())) {
				args[i] = Matchers.any(Date.class);
			}
			else if (param.getName().equals(List.class.getName())) {
				args[i] = Matchers.anyList();
			}
			else if (CustomBeanUtil.isCustomBean(param.getName())) {
				args[i] = Matchers.any(param);
			}
			else {
				String message = "未知参数类型[" + param.getName() + "]";
				System.out.println("unknown any type:" + message);
				args[i] = Matchers.any(param);
			}
		}
		return args;
	}
}
