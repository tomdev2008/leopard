package io.leopard.web.editor;

import io.leopard.commons.utility.DateUtil;
import io.leopard.test.mock.MockTests;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DefaultDateEditorTest extends MockTests {

	@Test
	public void setAsText() {
		DefaultDateEditor defaultDateEditor = new DefaultDateEditor();
		{
			defaultDateEditor.setAsText(null);
			Assert.assertNull(defaultDateEditor.getValue());
		}
		{
			defaultDateEditor.setAsText("2013-01-01 00:00:01");
			Date date = (Date) defaultDateEditor.getValue();
			Assert.assertEquals("2013-01-01 00:00:01", DateUtil.getTime(date));
		}
		{
			defaultDateEditor.setAsText("2013-01-01");
			Date date = (Date) defaultDateEditor.getValue();
			Assert.assertEquals("2013-01-01 00:00:00", DateUtil.getTime(date));
		}
		{
			try {
				defaultDateEditor.setAsText("2013-01");
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
		}
	}

}