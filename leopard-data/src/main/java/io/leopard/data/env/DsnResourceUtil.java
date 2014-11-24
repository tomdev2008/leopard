package io.leopard.data.env;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class DsnResourceUtil {

	protected static final Log logger = LogFactory.getLog(DsnResourceUtil.class);

	public static Resource getResource() throws IOException {
		Resource dsnPropertiesResource = AppProperties.getResource("dsn.properties");
		return dsnPropertiesResource;
	}

	/**
	 * dsn解密.
	 * 
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	protected static Resource decrypt(String dsnKey, Resource resource) throws IOException {
		InputStream input = resource.getInputStream();
		byte[] bytes = IOUtils.toByteArray(input);
		input.close();
		return new ByteArrayResource(bytes);
	}

}
