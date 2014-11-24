package io.leopard.web.editor;

import io.leopard.test.mock.MockTests;

import org.junit.Assert;
import org.junit.Test;

public class LongEditorTest extends MockTests {

	@Test
	public void setAsText() {
		LongEditor longEditor = new LongEditor();
		{
			longEditor.setAsText(null);
			Assert.assertEquals(0, longEditor.getValue());
		}
		{
			longEditor.setAsText("0");
			Assert.assertEquals(0L, longEditor.getValue());
		}
		{
			longEditor.setAsText("2");
			Assert.assertEquals(2L, longEditor.getValue());
		}
		{
			longEditor.setValue(null);
			Assert.assertEquals(0L, longEditor.getValue());
		}
	}

	@Test
	public void getValue() {
		// AUTO
	}

}