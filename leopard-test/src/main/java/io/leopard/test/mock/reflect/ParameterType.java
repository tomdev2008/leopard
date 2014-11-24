package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;

import java.sql.Timestamp;

/**
 * 参数类型.
 * 
 * @author 阿海
 * 
 */
public class ParameterType {
	private Class<?> param;// 参数对象

	private String fieldType;

	public ParameterType(Class<?> param) {
		this.fieldType = param.getCanonicalName();
		// System.out.println("fieldType:" + fieldType);
		this.param = param;
	}

	public boolean isString() {
		return fieldType.equalsIgnoreCase("java.lang.String");
	}

	public boolean isStrings() {
		return fieldType.equalsIgnoreCase("java.lang.String[]");
	}

	public boolean isInteger() {
		return fieldType.equalsIgnoreCase("int") || fieldType.equalsIgnoreCase("java.lang.Integer");
	}

	public boolean isInts() {
		return fieldType.equalsIgnoreCase("int[]");
	}

	public boolean isIntegers() {
		return fieldType.equalsIgnoreCase("java.lang.Integer[]");
	}

	public boolean isShort() {
		return fieldType.equalsIgnoreCase("short") || fieldType.equalsIgnoreCase("java.lang.Short");
	}

	public boolean isFloat() {
		return fieldType.equalsIgnoreCase("float") || fieldType.equalsIgnoreCase("java.lang.Float");
	}

	public boolean isDouble() {
		return fieldType.equalsIgnoreCase("double") || fieldType.equalsIgnoreCase("java.lang.Double");
	}

	public boolean isBigDecimal() {
		return fieldType.equalsIgnoreCase("java.math.BigDecimal");
	}

	public boolean isLong() {
		return fieldType.equalsIgnoreCase("long") || fieldType.equalsIgnoreCase("java.lang.Long");
	}

	public boolean isBoolean() {
		return fieldType.equalsIgnoreCase("boolean") || fieldType.equalsIgnoreCase("java.lang.Boolean");
	}

	public boolean isCharacter() {
		return fieldType.equalsIgnoreCase("char") || fieldType.equalsIgnoreCase("java.lang.Character");
	}

	public boolean isDate() {
		return fieldType.equalsIgnoreCase("java.util.Date");
	}

	public boolean isTimestamp() {
		return fieldType.equalsIgnoreCase(Timestamp.class.getName());
	}

	public boolean isOnlyDate() {
		return fieldType.equalsIgnoreCase(OnlyDate.class.getName());
	}

	public boolean isMonth() {
		return fieldType.equalsIgnoreCase(Month.class.getName());
	}

	public boolean isList() {
		return fieldType.equalsIgnoreCase("java.util.List");
	}

	public boolean isCollection() {
		return fieldType.equalsIgnoreCase("java.util.Collection");
	}

	public boolean isSet() {
		return fieldType.equalsIgnoreCase("java.util.Set");
	}

	public boolean isMap() {
		return fieldType.equalsIgnoreCase("java.util.Map");
	}

	public boolean isHttpServletRequest() {
		return fieldType.equalsIgnoreCase("javax.servlet.http.HttpServletRequest") || fieldType.equalsIgnoreCase("org.springframework.web.multipart.MultipartHttpServletRequest");
	}

	public boolean isHttpServletResponse() {
		return fieldType.equalsIgnoreCase("javax.servlet.http.HttpServletResponse");
	}

	public boolean isModelMap() {
		return fieldType.equalsIgnoreCase("org.springframework.ui.ModelMap");
	}

	public boolean isModelAndView() {
		return fieldType.equalsIgnoreCase("org.springframework.web.servlet.ModelAndView");
	}

	public boolean isMultipartFile() {
		return fieldType.equalsIgnoreCase("org.springframework.web.multipart.MultipartFile");
	}

	public boolean isWebDataBinder() {
		return fieldType.equalsIgnoreCase("org.springframework.web.bind.WebDataBinder");
	}

	public boolean isEnum() {
		return param.isEnum();
	}

	/**
	 * 是否基本类型.
	 * 
	 * @return
	 */
	public boolean isBasicType() {
		boolean isBasicType = false;
		isBasicType = isBasicType || this.isInteger() || this.isInts() || this.isIntegers();
		isBasicType = isBasicType || this.isString() || this.isStrings() || this.isBigDecimal();
		isBasicType = isBasicType || this.isLong() || this.isFloat();
		isBasicType = isBasicType || this.isDouble() || this.isCharacter();
		isBasicType = isBasicType || this.isList() || this.isBoolean();
		isBasicType = isBasicType || this.isEnum() || this.isModelMap() || this.isModelAndView() || this.isMultipartFile();
		isBasicType = isBasicType || this.isSet() || this.isList() || this.isMap() || isCollection();
		isBasicType = isBasicType || this.isHttpServletRequest() || this.isHttpServletResponse();
		isBasicType = isBasicType || this.isWebDataBinder();

		return isBasicType;
	}
}
