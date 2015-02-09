package io.leopard.web4j.trynb;

import io.leopard.burrow.lang.Json;
import io.leopard.web4j.trynb.model.ErrorConfig;

import java.util.List;

import org.junit.Test;

public class ErrorPageDaoXmlImplTest {

	private ErrorPageDaoXmlImpl errorPageDaoXmlImpl = new ErrorPageDaoXmlImpl();

	@Test
	public void list() {
		List<ErrorConfig> list = errorPageDaoXmlImpl.list();
		Json.printList(list, "list");
	}

}