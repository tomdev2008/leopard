package io.leopard.web.editor;

import io.leopard.test.mock.MockTests;

import org.junit.Assert;
import org.junit.Test;

public class IntegerEditorTest extends MockTests {

	@Test
	public void setAsText() {
		IntegerEditor integerEditor = new IntegerEditor();
		{
			integerEditor.setAsText(null);
			Assert.assertEquals(0, integerEditor.getValue());
		}
		{
			integerEditor.setAsText("0");
			Assert.assertEquals(0, integerEditor.getValue());
		}
		{
			integerEditor.setAsText("2");
			Assert.assertEquals(2, integerEditor.getValue());
		}
		{
			integerEditor.setValue(null);
			Assert.assertEquals(0, integerEditor.getValue());
		}
	}

	@Test
	public void getValue() {
		// AUTO
	}

}