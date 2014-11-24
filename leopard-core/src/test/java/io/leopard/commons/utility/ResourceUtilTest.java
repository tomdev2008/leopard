package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;

public class ResourceUtilTest {

	@Test
	public void ResourceUtil() {
		new ResourceUtil();
	}

	@Test
	public void getClassPathResource() {
		Resource resource = ResourceUtil.getClassPathResource("/env/config.xml");
		Assert.assertNotNull(resource);
	}

}