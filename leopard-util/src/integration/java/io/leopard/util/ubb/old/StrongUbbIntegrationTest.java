package io.leopard.util.ubb.old;

import io.leopard.burrow.io.FileUtil;

import java.io.IOException;

import org.junit.Test;

public class StrongUbbIntegrationTest {

	@Test
	public void strong() throws IOException {
		String content = FileUtil.readByClassPath("/ubbtest.txt");
		// System.out.println("content:" + content);

		String html = StrongUbb.strong(content);
		System.out.println("html:" + html);

	}

}