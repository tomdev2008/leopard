package io.leopard.commons.utility;

import io.leopard.core.exception.IORuntimeException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ResourceUtil {
	/**
	 * 获取classpath资源.
	 * 
	 * 这个方法是为了方便测试而提供.
	 * 
	 * @param path
	 * @return
	 */
	public static ClassPathResource getClassPathResource(String path) {
		return new ClassPathResource(path);
	}

	// public static URL getSystemResource(String name) {
	// return ClassLoader.getSystemResource(name);
	// }

	public static File getFile(String filename) {
		return new File(filename);
	}

	public static String toString(Resource resource) {
		try {
			InputStream is = resource.getInputStream();
			String content = IOUtils.toString(is);
			is.close();
			return content;
		}
		catch (IOException e) {
			throw new IORuntimeException(e.getMessage(), e);
		}

	}

}
