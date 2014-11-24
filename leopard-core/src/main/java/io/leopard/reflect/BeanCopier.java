package io.leopard.reflect;

import io.leopard.commons.utility.ClassUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean复制.
 * 
 * @author 阿海
 * 
 */
public class BeanCopier {

	private static Map<Integer, net.sf.cglib.beans.BeanCopier> CACHE = new ConcurrentHashMap<Integer, net.sf.cglib.beans.BeanCopier>();

	/**
	 * Bean复制.
	 * 
	 * 性能(2个字段的Bean复制，每秒大约450次).
	 * 
	 * @param from
	 * @param toClazz
	 * @return
	 */
	public static <T> T copy(Object from, Class<T> toClazz) {
		T to = ClassUtil.newInstance(toClazz);

		Class<?> soruceClazz = from.getClass();
		// Class<?> targetClazz = to.getClass();
		net.sf.cglib.beans.BeanCopier beanCopier = getBeanCopier(soruceClazz, toClazz);
		beanCopier.copy(from, to, null);
		return to;
	}

	protected static net.sf.cglib.beans.BeanCopier getBeanCopier(Class<?> soruceClazz, Class<?> targetClazz) {
		int key = soruceClazz.hashCode() + targetClazz.hashCode();
		net.sf.cglib.beans.BeanCopier beanCopier = CACHE.get(key);
		if (beanCopier == null) {
			beanCopier = createBeanCopier(soruceClazz, targetClazz);
		}
		return beanCopier;
	}

	protected static synchronized net.sf.cglib.beans.BeanCopier createBeanCopier(Class<?> soruceClazz, Class<?> targetClazz) {
		int key = soruceClazz.hashCode() + targetClazz.hashCode();
		net.sf.cglib.beans.BeanCopier beanCopier = CACHE.get(key);
		if (beanCopier != null) {
			return beanCopier;
		}
		beanCopier = net.sf.cglib.beans.BeanCopier.create(soruceClazz, targetClazz, false);
		CACHE.put(key, beanCopier);
		return beanCopier;
	}
}
