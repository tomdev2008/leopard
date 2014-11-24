package io.leopard.commons.utility;

import java.util.UUID;

import io.leopard.commons.utility.StringUtil;

import org.junit.Test;

public class StringUtilIntegrationTest {

	@Test
	public void urlDecode() {
		String str = StringUtil.urlDecode("aa996434610&#039;暴龙");
		System.out.println("str:" + str);
	}

	@Test
	public void uuid() {
		String uuid = StringUtil.uuid();
		System.out.println("uuid:" + uuid + " len:" + uuid.length());

		System.out.println("uuid2:" + UUID.randomUUID().toString());
	}

	@Test
	public void getIntString() {
		String str = StringUtil.getIntString("guangzhou");
		System.out.println("str:" + str);
	}

}