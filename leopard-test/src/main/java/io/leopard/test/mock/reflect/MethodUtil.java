package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.inum.Inum;
import io.leopard.burrow.refect.FieldUtil;
import io.leopard.commons.utility.ArrayUtil;
import io.leopard.test.CustomBeanUtil;
import io.leopard.util.ClassTypeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.mockito.Matchers;

public class MethodUtil {

	public static Method getMethod(Class<?> clazz, String methodName, int paramCount) {
		while (clazz != null) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					if (paramCount > -1 && method.getParameterTypes().length != paramCount) {
						continue;
					}
					return method;
				}
			}
			clazz = clazz.getSuperclass();
		}
		throw new RuntimeException("找不到方法[" + methodName + "].");
	}

	public static MethodInfo parseMethodInfo(String beanAndMethodName) {

		String fieldName = null;
		String methodName = null;
		int paramCount = -1;
		String methodInfoStr = null;
		{
			String[] strs = StringUtils.split(beanAndMethodName, ".");
			if (strs.length == 1) {
				methodInfoStr = strs[0];
			}
			else {
				fieldName = strs[0];
				methodInfoStr = strs[1];
			}
		}
		{
			String[] strs = StringUtils.split(methodInfoStr, "(");
			methodName = strs[0];
			if (strs.length > 1) {
				String count = strs[1].replace(")", "").trim();
				paramCount = Integer.parseInt(count);
			}
		}
		MethodInfo methodInfo = new MethodInfo();
		methodInfo.setFieldName(fieldName);
		methodInfo.setMethodName(methodName);
		methodInfo.setParamCount(paramCount);
		return methodInfo;
	}

	public static Object getDaoValue(Object service, String fieldName) {
		if (StringUtils.isEmpty(fieldName)) {
			return getDaoValue(service);
		}
		else {
			return FieldUtil.getFieldValue(service, fieldName);
		}
	}

	public static Object getDaoValue(Object service) {
		Field field = getDaoField(service);

		if (field == null) {
			return null;
		}
		field.setAccessible(true);
		try {
			Object value = field.get(service);
			System.out.println("fieldName:" + field.getName() + " value:" + value);
			return value;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Field getDaoField(Object serviceMock) {
		Field[] fields = serviceMock.getClass().getSuperclass().getDeclaredFields();
		// System.out.println("fields:" + fields);
		for (Field field : fields) {
			// System.out.println("field:" + field.getName() + " type:" + field.getType().getName());
			if (field.getType().getName().endsWith("Dao")) {
				// System.out.println("field2:" + field.getName() + " type:" + field.getType().getName());
				return field;
			}
		}
		return null;
	}

	public static Object invoke(Object obj, Method method, Object... args) {
		try {
			return method.invoke(obj, args);
		}
		catch (Exception e) {
			throw new RuntimeException(e.toString(), e);
		}
	}

	public static void invokeMethod(Object service, String methodName) {
		Method[] methods = service.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (!method.getName().equals(methodName)) {
				continue;
			}
			Object[] args = MethodUtil.getAny(method.getParameterTypes());
			try {
				Object result = method.invoke(service, args);
				System.out.println("result:" + result + " method:" + method.toGenericString());
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

	}

	public static Object invokeMethod(Object service, String methodName, Object[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException,
			NoSuchMethodException {
		Method serviceMethod = null;
		while (true) {
			serviceMethod = getServiceMethod(service, methodName, args);
			if (serviceMethod == null && args.length == 0) {
				throw new RuntimeException("找不到对应的方法[" + methodName + "]");
			}

			if (serviceMethod != null) {

				break;
			}
			else {
				// Object[] args2 = new Object[args.length - 1];
				// System.arraycopy(args, 0, args2, 0, args2.length);
				args = ArrayUtil.removeLast(args);
				// System.out.println("args:" + args);
			}
		}
		// System.out.println("serviceMethod:" + serviceMethod.toGenericString());
		Object serviceValue = serviceMethod.invoke(service, args);
		// System.out.println("serviceValue:" + serviceValue);
		return serviceValue;
	}

	public static Class<?>[] getTypes(Object[] args) {
		Class<?>[] parameterTypes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = args[i].getClass();
		}
		return parameterTypes;
	}

	public static Method getServiceMethod(Object service, String methodName, Object[] args) throws SecurityException, NoSuchMethodException {
		Class<?>[] parameterTypes = getTypes(args);
		try {
			Method serviceMethod = service.getClass().getMethod(methodName, parameterTypes);
			return serviceMethod;
		}
		catch (NoSuchMethodException e) {
			// return BeanUtils.findMethodWithMinimalParameters(service.getClass(), methodName);
			return null;
		}
	}

	public static Object[] getDefaultValue(Class<?>[] params) {
		Object[] args = null;
		if (params != null) {
			args = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				args[i] = MethodUtil.getDefaultValue(params[i]);
			}
		}
		return args;
	}

	@SuppressWarnings("rawtypes")
	public static Object getDefaultValue(Class<?> clazz) {
		String clazzName = clazz.getName();
		if (clazzName.equals(String.class.getName())) {
			return "value";
		}
		if (ClassTypeUtil.isBoolean(clazzName)) {
			return true;
		}
		if (ClassTypeUtil.isInteger(clazzName)) {
			return 1;
		}
		if (ClassTypeUtil.isLong(clazzName)) {
			return 1L;
		}
		if (ClassTypeUtil.isFloat(clazzName)) {
			return 1F;
		}
		if (ClassTypeUtil.isDouble(clazzName)) {
			return 1D;
		}
		if (clazzName.equals(Date.class.getName())) {
			return new Date(1);
		}
		if (clazzName.equals(List.class.getName())) {
			return new ArrayList();
		}
		if (clazzName.equals(void.class.getName())) {
			return null;
		}
		if (clazzName.equals(Map.class.getName())) {
			return new HashMap();
		}
		if (clazzName.equals(Set.class.getName())) {
			return new HashSet();
		}
		if (clazzName.equals(Pattern.class.getName())) {
			return Pattern.compile("regex");
		}

		Object obj = ModelInstanceUtil.getNewInstance(clazz);
		return obj;
	}

	public static Object[] getAny(Class<?>[] params) {
		Object[] args = new Object[params.length];
		for (int i = 0; i < args.length; i++) {
			args[i] = getAny(params[i]);
		}
		return args;
	}

	public static Object getAny(Class<?> clazz) {
		String clazzName = clazz.getName();
		if (clazzName.equals(String.class.getName())) {
			return Matchers.anyString();
		}
		if (clazzName.equals(boolean.class.getName()) || clazzName.equals(Boolean.class.getName())) {
			return Matchers.anyBoolean();
		}
		if (clazzName.equals(int.class.getName()) || clazzName.equals(Integer.class.getName())) {
			return Matchers.anyInt();
		}
		if (clazzName.equals(long.class.getName()) || clazzName.equals(Long.class.getName())) {
			return Matchers.anyLong();
		}
		if (clazzName.equals(Date.class.getName())) {
			return Matchers.any(Date.class);
		}
		if (clazzName.equals(List.class.getName())) {
			return Matchers.anyList();
		}
		if (clazzName.equals(Object.class.getName())) {
			return Matchers.any();
		}
		if (clazzName.equals(Class.class.getName())) {
			return Matchers.any();
		}
		if (CustomBeanUtil.isCustomBean(clazzName)) {
			return Matchers.any();
		}
		throw new RuntimeException("未知数据类型[" + clazz.getName() + "].");
	}

	public static Method getGreaterEqualMethod(Object self, Method method, Object[] args) {
		AssertUtil.assertNotNull(self, "参数self不能为空.");
		Method result = getMethod(self, method.getName(), args);
		// System.out.println("result:" + result.toGenericString() + " self:" + self.getClass().getName() + " method:" + method.getName());
		if (result == null) {
			Method[] methods = self.getClass().getDeclaredMethods();
			for (Method m : methods) {
				if (!m.getName().equals(method.getName())) {
					continue;
				}
				boolean isLikeMethod = MethodUtil.isLikeMethod(method, m);
				// System.out.println("methodName:" + m.getName() + " isLikeMethod:" + isLikeMethod + " method:" + method.toGenericString());
				// System.out.println("methodName:" + m.getName() + " isLikeMethod:" + isLikeMethod + " method:" + m.toGenericString());
				if (!isLikeMethod) {
					continue;
				}
				result = m;
				break;
			}
		}
		if (result == null) {
			Method[] methods = self.getClass().getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals(method.getName())) {
					if (!hasObjectParam(m.getParameterTypes())) {
						result = m;
						System.out.println("result:" + result.toGenericString());
						// new Exception("ok").printStackTrace();
					}

				}
			}
		}

		return result;
	}

	protected static boolean hasObjectParam(Class<?>[] params) {
		for (Class<?> param : params) {
			if (param.equals(Object.class)) {
				return true;
			}
		}
		return false;
	}

	protected static boolean isLikeMethod(Method smallMethod, Method bigMethod) {
		Class<?>[] params1 = smallMethod.getParameterTypes();
		Class<?>[] params2 = bigMethod.getParameterTypes();
		for (int i = 0; i < params1.length && i < params2.length; i++) {
			String classTypeName1 = getClassTypeName(params1[i]);
			String classTypeName2 = getClassTypeName(params2[i]);
			// System.out.println("classTypeName1:" + classTypeName1 + " classTypeName2:" + classTypeName2);
			if (!classTypeName1.equals(classTypeName2)) {
				return false;
			}
		}
		return true;
	}

	protected static String getClassTypeName(Class<?> clazz) {
		// System.out.println("clazz:" + clazz + " isEnum:" + clazz.isEnum());
		if (clazz.isEnum()) {
			return getClassTypeNameByEnum(clazz);
		}

		if (clazz.equals(int.class)) {
			return Integer.class.getName();
		}
		else if (clazz.equals(long.class)) {
			return Long.class.getName();
		}
		else if (clazz.equals(float.class)) {
			return Float.class.getName();
		}
		else if (clazz.equals(double.class)) {
			return Double.class.getName();
		}
		else {
			return clazz.getName();
		}
	}

	protected static String getClassTypeNameByEnum(Class<?> clazz) {
		Class<?>[] interfaces = clazz.getInterfaces();
		// System.out.println("interfaces:" + interfaces);
		if (interfaces.length == 0) {
			throw new RuntimeException("枚举[" + clazz.getName() + "]没有实现Onum?");
		}
		if (interfaces[0].equals(Inum.class)) {
			return Integer.class.getName();
		}
		throw new RuntimeException("未知枚举类型[" + interfaces[0].getName() + "].");
	}

	// public static Method getLessEqualMethod(Object self, Method method, Object[] args) {
	// Method result = null;
	// while (true) {
	// result = getMethod(self, method.getName(), args);
	// if (result == null && args.length == 0) {
	// // throw new RuntimeException("找不到对应的方法[" + method.getName() + "]");
	// return null;
	// }
	// if (result != null) {
	// break;
	// }
	// else {
	// args = ArrayUtil.removeLast(args);
	// // System.out.println("args:" + args);
	// }
	// }
	// return result;
	// }

	public static Method getMethod(Class<?> clazz, String methodName) {
		AssertUtil.assertNotNull(clazz, "参数clazz不能为空.");
		Method[] methods = clazz.getMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}

	public static Method getMethod(Object self, String methodName, Object[] args) {
		AssertUtil.assertNotNull(self, "参数self不能为空.");
		Method[] methods = self.getClass().getMethods();
		for (Method m : methods) {
			if (!m.getName().equals(methodName)) {
				continue;
			}
			boolean isEqualsType = isEqualsType(m.getParameterTypes(), args);
			if (isEqualsType) {
				return m;
			}
		}
		return null;
	}

	protected static boolean isEqualsType(Class<?>[] params, Object[] args) {
		if (params.length != args.length) {
			return false;
		}
		// System.out.println("isEqualsType params:" + params);
		for (int i = 0; i < params.length; i++) {
			if (args[i] == null) {
				return false;
			}
			String name1 = replaceType(params[i].getName());
			String name2 = replaceType(args[i].getClass().getName());
			// System.out.println("name1:" + name1 + " name2:" + name2);
			if (!name1.equals(name2)) {
				return false;
			}
		}
		return true;
	}

	private static final Map<String, String> CLASSNAME_MAPPING = new HashMap<String, String>();
	static {
		CLASSNAME_MAPPING.put(int.class.getName(), Integer.class.getName());
		CLASSNAME_MAPPING.put(long.class.getName(), Long.class.getName());
		CLASSNAME_MAPPING.put(float.class.getName(), Float.class.getName());
		CLASSNAME_MAPPING.put(double.class.getName(), Double.class.getName());
	}

	protected static String replaceType(String className) {
		String name = CLASSNAME_MAPPING.get(className);
		if (name == null) {
			return className;
		}
		else {
			return name;
		}
	}

	public static List<Method> listMethod(Class<?> clazz, String methodName) {
		List<Method> methodList = new ArrayList<Method>();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				methodList.add(method);
			}
		}
		return methodList;
	}
}
