package io.leopard.burrow.lang;

import org.junit.Test;

public class JsonTest {

	@Test
	public void toJson() {
		Paging<String> paging = new PagingImpl<String>(true);
		paging.add("A");
		String json = Json.toJson(paging);
		System.out.println(json);
	}

}