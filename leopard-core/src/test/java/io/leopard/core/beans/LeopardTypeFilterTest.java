package io.leopard.core.beans;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class LeopardTypeFilterTest {

	protected LeopardTypeFilter leopardTypeFilter = new LeopardTypeFilter();

	@Test
	public void match() throws IOException {
		Assert.assertFalse(leopardTypeFilter.match(null, null));
	}

}