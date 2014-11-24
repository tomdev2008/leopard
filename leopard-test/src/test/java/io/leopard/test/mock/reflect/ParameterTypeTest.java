package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class ParameterTypeTest {

	@Test
	public void isString() {
		Assert.assertTrue(new ParameterType(String.class).isString());
	}

	@Test
	public void isStrings() {
		Assert.assertTrue(new ParameterType(String[].class).isStrings());
	}

	@Test
	public void isInteger() {
		Assert.assertTrue(new ParameterType(Integer.class).isInteger());
	}

	@Test
	public void isInts() {
		Assert.assertTrue(new ParameterType(int[].class).isInts());
	}

	@Test
	public void isIntegers() {
		Assert.assertTrue(new ParameterType(Integer[].class).isIntegers());
	}

	@Test
	public void isFloat() {
		Assert.assertTrue(new ParameterType(Float.class).isFloat());
	}

	@Test
	public void isDouble() {
		Assert.assertTrue(new ParameterType(Double.class).isDouble());
	}

	@Test
	public void isBigDecimal() {
		Assert.assertTrue(new ParameterType(BigDecimal.class).isBigDecimal());
	}

	@Test
	public void isLong() {
		Assert.assertTrue(new ParameterType(Long.class).isLong());
	}

	@Test
	public void isBoolean() {
		Assert.assertTrue(new ParameterType(Boolean.class).isBoolean());
	}

	@Test
	public void isCharacter() {
		Assert.assertTrue(new ParameterType(Character.class).isCharacter());
	}

	@Test
	public void isDate() {
		Assert.assertTrue(new ParameterType(Date.class).isDate());
	}

	@Test
	public void isTimestamp() {
		Assert.assertTrue(new ParameterType(Timestamp.class).isTimestamp());
	}

	@Test
	public void isOnlyDate() {
		Assert.assertTrue(new ParameterType(OnlyDate.class).isOnlyDate());
	}

	@Test
	public void isMonth() {
		Assert.assertTrue(new ParameterType(Month.class).isMonth());
	}

	@Test
	public void isList() {
		Assert.assertTrue(new ParameterType(List.class).isList());
	}

	@Test
	public void isCollection() {
		Assert.assertTrue(new ParameterType(Collection.class).isCollection());
	}

	@Test
	public void isSet() {
		Assert.assertTrue(new ParameterType(Set.class).isSet());
	}

	@Test
	public void isMap() {
		Assert.assertTrue(new ParameterType(Map.class).isMap());
	}

	// @Test
	// public void isHttpServletRequest() {
	// Assert.assertTrue(new ParameterType(HttpServletRequest.class).isHttpServletRequest());
	// }
	//
	// @Test
	// public void isHttpServletResponse() {
	// Assert.assertTrue(new ParameterType(HttpServletResponse.class).isHttpServletResponse());
	// }

	@Test
	public void isModelMap() {
		Assert.assertTrue(new ParameterType(ModelMap.class).isModelMap());
	}

	@Test
	public void isModelAndView() {
		Assert.assertTrue(new ParameterType(ModelAndView.class).isModelAndView());
	}

	@Test
	public void isMultipartFile() {
		Assert.assertTrue(new ParameterType(MultipartFile.class).isMultipartFile());
	}

	@Test
	public void isWebDataBinder() {
		Assert.assertTrue(new ParameterType(WebDataBinder.class).isWebDataBinder());
	}

	public static enum TestEnum {
		ALL
	}

	@Test
	public void isEnum() {
		Assert.assertTrue(new ParameterType(TestEnum.class).isEnum());
	}

	@Test
	public void isBasicType() {
		Assert.assertTrue(new ParameterType(String.class).isBasicType());
	}

}