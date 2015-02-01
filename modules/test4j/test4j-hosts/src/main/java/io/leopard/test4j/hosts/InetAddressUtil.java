package io.leopard.test4j.hosts;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class InetAddressUtil {
	protected static Object getAddressCache() throws Exception {
		final Field cacheField = InetAddress.class.getDeclaredField("addressCache");
		cacheField.setAccessible(true);
		final Object addressCache = cacheField.get(InetAddress.class);
		return addressCache;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static Map<String, Object> getCache() throws Exception {
		Object addressCache = getAddressCache();
		Class clazz2 = addressCache.getClass();
		final Field cacheMapField = clazz2.getDeclaredField("cache");
		cacheMapField.setAccessible(true);
		final Map cacheMap = (Map) cacheMapField.get(addressCache);
		return cacheMap;
	}

	protected static byte[] toBytes(String ip) {
		byte[] addr = new byte[4];
		{
			String[] strs = ip.split("\\.");
			for (int i = 0; i < strs.length; i++) {
				// System.out.println("strs[i]:" + strs[i]);
				addr[i] = (byte) Integer.parseInt(strs[i]);
			}
		}
		return addr;
	}

	public static void putCache(String host, String ip) {
		try {
			Object cacheEntry = createCacheEntry(host, ip);
			Map<String, Object> cacheMap = getCache();
			cacheMap.put(host, cacheEntry);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected static Object createCacheEntry(String host, String ip) throws Exception {
		long expiration = System.currentTimeMillis() + (3600 * 24 * 1000L);
		return createCacheEntry(host, ip, expiration);
	}

	protected static Object createCacheEntry(String host, String ip, long expiration) throws Exception {
		InetAddress[] address = new InetAddress[] { InetAddress.getByAddress(host, toBytes(ip)) };
		String className = "java.net.InetAddress$CacheEntry";
		Class<?> clazz = Class.forName(className);
		// System.out.println("clazz:" + clazz.toString());

		Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		return constructor.newInstance(address, expiration);
	}

	public static String getIp(String host) {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(host);
		}
		catch (UnknownHostException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return inetAddress.getHostAddress();
	}

}
