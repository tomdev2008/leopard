package io.leopard.data.env;

import io.leopard.commons.utility.ClassUtil;

public class YtestUtil {

	private static Boolean isYtest = null;

	public static boolean isYtestWebapp() {
		if (isYtest != null) {
			return isYtest;
		}
		ClassLoader classLoader = YtestUtil.class.getClassLoader();
		String className = classLoader.getClass().getName();
		// System.err.println("EnvDaoYtestImpl classLoader:" + className);
		String ytestClassLoaderName = "com.duowan.ytest.jetty.configuration.YTestWebAppClassLoader";
		isYtest = ytestClassLoaderName.equals(className);
		return isYtest;
	}

	public static Class<?> findClass(Class<?> clazz) {
		if (YtestUtil.isYtestWebapp()) {
			String className = clazz.getName();
			String ytestClassName = className.replace("io.leopard.", "com.duowan.ytest.");
			Class<?> c = ClassUtil.forName(ytestClassName);
			// System.err.println("classLoader:" + DataSourceClassUtil.class.getClassLoader() + " className:" + ytestClassName + " c:" + c.getClassLoader());
			return c;
		}
		return clazz;
	}
}
