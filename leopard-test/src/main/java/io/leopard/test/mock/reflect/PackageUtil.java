package io.leopard.test.mock.reflect;

import io.leopard.core.exception.IORuntimeException;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageUtil {
	public static List<Class<?>> getClassList(String packageName) {
		Set<Class<?>> classes = PackageUtil.getClasses(packageName);
		List<Class<?>> result = new ArrayList<Class<?>>();
		for (Class<?> clazz : classes) {
			// if (clazz.isEnum() || clazz.isInterface()) {
			if (clazz.isInterface()) {
				continue;
			}
			if (clazz.getSimpleName().endsWith("Test")) {
				// 忽略测试类
				continue;
			}
			if (clazz.getName().indexOf("$") != -1) {
				// 忽略内部类
				continue;
			}
			result.add(clazz);
		}
		return result;
	}

	protected static Set<Class<?>> getClasses(String packageName, String path) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		String end = ".class";
		File file = new File(path);
		String[] files = file.list();
		for (String s : files) {
			if (!s.endsWith(end)) {
				continue;
			}
			String className = s.substring(0, s.length() - end.length());
			Class<?> c;
			try {
				c = Class.forName(packageName + "." + className);
			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			classes.add(c);
		}
		return classes;
	}

	public static Set<Class<?>> getClasses(String packageName) {

		// 第一个class类的集合
		Set<Class<?>> classes = new HashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// System.err.println("file类型的扫描");
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					if (filePath.indexOf("$") != -1) {
						System.out.println("filePath:" + filePath);
						continue;
					}
					classes.addAll(getClasses(packageName, filePath));

				}
				else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					// System.err.println("jar类型的扫描");
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class.forName(packageName + '.' + className));
										}
										catch (ClassNotFoundException e) {
											// log
											// .error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					}
					catch (IOException e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}
		return classes;
	}

}
