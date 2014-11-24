package io.leopard.test.mock.parameter;

import org.junit.Assert;
import org.junit.Test;

public class NbFieldTest {

	@Test
	public void NbField() {
		NbField field = new NbField();
		field.setFieldTypeName("fieldTypeName");
		Assert.assertEquals("fieldTypeName", field.getFieldTypeName());
		field.setMethod(null);
		Assert.assertNull(field.getMethod());
		field.setName("name");
		Assert.assertEquals("name", field.getName());
		field.setParam(null);
		Assert.assertNull(field.getParam());
		field.setValue("value");
		Assert.assertEquals("value", field.getValue());
	}

}