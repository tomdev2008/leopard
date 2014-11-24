package io.leopard.web.editor;

import io.leopard.test.mock.MockTests;

import org.junit.Assert;
import org.junit.Test;

public class BooleanEditorTest extends MockTests {

	@Test
	public void getValue() {
		BooleanEditor booleanEditor = new BooleanEditor();
		{
			booleanEditor.setAsText(null);
			Assert.assertEquals("false", booleanEditor.getAsText());
			Assert.assertFalse((Boolean) booleanEditor.getValue());
		}
		{
			booleanEditor.setAsText("false");
			Assert.assertEquals("false", booleanEditor.getAsText());
			Assert.assertFalse((Boolean) booleanEditor.getValue());
		}
		{
			booleanEditor.setAsText("true");
			Assert.assertEquals("true", booleanEditor.getAsText());
			Assert.assertTrue((Boolean) booleanEditor.getValue());
		}

		{
			booleanEditor.setValue(null);
			Assert.assertFalse((Boolean) booleanEditor.getValue());
		}
	}

	@Test
	public void BooleanEditor() {
		// AUTO
	}

}