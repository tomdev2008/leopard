package io.leopard.util.ubb;

import io.leopard.burrow.io.FileUtil;

import java.io.IOException;

import org.junit.Test;

public class StrongUbbIntegrationTest {

	@Test
	public void parse() throws IOException {
		String content = FileUtil.readByClassPath("/ubbtest.txt");
		System.out.println("content:" + content);

		{
			String html = new StrongUbb().parse(content);
			System.out.println("html:" + html);
		}

	}

}