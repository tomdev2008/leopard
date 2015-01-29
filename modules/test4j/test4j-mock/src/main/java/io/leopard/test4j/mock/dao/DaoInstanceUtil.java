package io.leopard.test4j.mock.dao;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.refect.FieldUtil;
import io.leopard.burrow.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mockito.Mockito;

public class DaoInstanceUtil {

	private static InstanceDao instanceDaoMysqlImpl = new InstanceDaoMysqlImpl();
	private static InstanceDao instanceDaoRedisImpl = new InstanceDaoRedisImpl();
	private static InstanceDao instanceDaoHttpImpl = new InstanceDaoHttpImpl();
	private static InstanceDao instanceDaoSphinxImpl = new InstanceDaoSphinxImpl();
	private static InstanceDao instanceDaoMemcachedImpl = new InstanceDaoMemcachedImpl();
	private static InstanceDao instanceDaoMemoryImpl = new InstanceDaoMemoryImpl();
	private static InstanceDao instanceDaoCacheImpl = new InstanceDaoCacheImpl();
	private static InstanceDao instanceDaoVersionImpl = new InstanceDaoVersionImpl();

	public static Object newInstance(Field field) {
		field.setAccessible(true);
		Class<?> interfaceClass = field.getType();

		String fieldName = field.getName();
		Class<?> implClass = getFullClass(interfaceClass, field.getName());
		return newInstance(fieldName, implClass);
	}

	public static <T> T newInstance(String fieldName, Class<T> implClass) {
		if (fieldName.endsWith("DaoRedisImpl")) {

			return instanceDaoRedisImpl.instance(implClass);
		}
		else if (fieldName.endsWith("DaoMysqlImpl")) {
			return instanceDaoMysqlImpl.instance(implClass);
		}
		else if (fieldName.endsWith("DaoMemcachedImpl")) {
			return instanceDaoMemcachedImpl.instance(implClass);
		}
		else if (fieldName.endsWith("DaoMemoryImpl")) {
			return instanceDaoMemoryImpl.instance(implClass);
		}
		else if (fieldName.endsWith("DaoHttpImpl")) {
			return instanceDaoHttpImpl.instance(implClass);
		}
		else if (fieldName.endsWith("DaoOutsideImpl")) {
			return instanceDaoHttpImpl.instance(implClass);
		}
		else if (fieldName.endsWith("DaoSphinxImpl")) {
			return instanceDaoSphinxImpl.instance(implClass);
		}
		else if (fieldName.endsWith("DaoCacheImpl")) {
			return instanceDaoCacheImpl.instance(implClass);
		}
		else if (fieldName.endsWith("DaoVersionImpl")) {
			return instanceDaoVersionImpl.instance(implClass);
		}

		else {
			// throw new RuntimeException("未知dao实现类型[" + fieldName + "].");
			return instanceDaoCacheImpl.instance(implClass);
		}
	}

	public static Class<?> getFullClass(Class<?> interfaceClass, String fieldName) {
		String className = getFullClassName(interfaceClass, fieldName);
		try {
			return Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			System.err.println("fieldName:" + fieldName + " className:" + className);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String getFullClassName(Class<?> interfaceClass, String fieldName) {
		AssertUtil.assertNotNull(interfaceClass, "参数interfaceClass不能为空.");
		// System.out.println("interfaceClass.getPackage():" + interfaceClass.getName());
		String packageName = interfaceClass.getPackage().getName();
		if (fieldName.endsWith("CacheImpl")) {
			return packageName + ".cache." + StringUtil.firstCharToUpperCase(fieldName);
		}
		else if (fieldName.endsWith("RedisImpl")) {
			return packageName + ".redis." + StringUtil.firstCharToUpperCase(fieldName);
		}
		else if (fieldName.endsWith("MysqlImpl")) {
			return packageName + ".mysql." + StringUtil.firstCharToUpperCase(fieldName);
		}
		else if (fieldName.endsWith("MemcachedImpl")) {
			return packageName + ".memcached." + StringUtil.firstCharToUpperCase(fieldName);
		}
		else if (fieldName.endsWith("MemoryImpl")) {
			return packageName + ".memory." + StringUtil.firstCharToUpperCase(fieldName);
		}
		else if (fieldName.endsWith("HttpImpl")) {
			return packageName + ".http." + StringUtil.firstCharToUpperCase(fieldName);
		}
		else if (fieldName.endsWith("SphinxImpl")) {
			return packageName + ".sphinx." + StringUtil.firstCharToUpperCase(fieldName);
		}
		throw new RuntimeException("未知字段名称[" + interfaceClass.getSimpleName() + "." + fieldName + "].");
	}

	private static final Set<String> SIMPLE_TYPE = new HashSet<String>();
	static {
		SIMPLE_TYPE.add(int.class.getName());
		SIMPLE_TYPE.add(long.class.getName());
		SIMPLE_TYPE.add(boolean.class.getName());
		SIMPLE_TYPE.add(String.class.getName());
		SIMPLE_TYPE.add(Map.class.getName());
		SIMPLE_TYPE.add(Set.class.getName());
		SIMPLE_TYPE.add(List.class.getName());
	}

	public static boolean isSimpleType(Class<?> clazz) {
		return SIMPLE_TYPE.contains(clazz.getName());
	}

	public static void mockAllFields(Object bean) {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) && Modifier.isFinal(mod)) {
				continue;
			}
			Class<?> fieldType = field.getType();
			// if (fieldType.equals(Jdbc.class)) {
			// Jdbc jdbc = MockTransactionModule.getInstance(Jdbc.class);
			// FieldUtil.setFieldValue(bean, field, jdbc);
			// }
			if (isSimpleType(fieldType)) {
				// 忽略基本类型
				continue;
			}
			else if (field.getName().equals("$jacocoData")) {
				// 忽略Coverage插件信息
				continue;
			}
			else {
				// System.out.println("field:" + field.getName());
				FieldUtil.setFieldValue(bean, field, Mockito.mock(fieldType));
			}
		}
	}
}
