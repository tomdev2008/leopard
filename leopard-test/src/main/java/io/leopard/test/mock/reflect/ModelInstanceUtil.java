package io.leopard.test.mock.reflect;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;
import io.leopard.util.ClassTypeUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javassist.Modifier;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author 阿海
 * 
 */
public class ModelInstanceUtil {

	// @SuppressWarnings("unchecked")
	public static <T> Class<T> toRealClass(Class<T> clazz) {
		return clazz;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getNewInstance(Class<T> clazz) {
		clazz = toRealClass(clazz);

		if (clazz.isEnum()) {
			return null;
		}
		T bean;
		try {
			bean = clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		while (true) {
			Field[] fields = clazz.getDeclaredFields();
			try {
				setFieldValue(bean, fields);
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			if (clazz.getSuperclass() == null) {
				break;
			}
			clazz = (Class<T>) clazz.getSuperclass();
		}
		return bean;
	}

	private static void setFieldValue(Object bean, Field[] fields) throws IllegalArgumentException, IllegalAccessException {
		for (Field field : fields) {
			String propertyName = field.getName();

			if (propertyName.indexOf("$") != -1) {
				// 忽略代理方法
				continue;
			}
			if ("logger".equals(propertyName)) {
				// TODO 这里忽略logger对象是特殊实现
				continue;
			}
			int modifiers = field.getModifiers();
			// System.out.println("propertyName:" + propertyName + " modifiers:" + modifiers);

			if (modifiers != Modifier.PRIVATE && modifiers != Modifier.PROTECTED) {
				// 忽略非private&&protected属性
				continue;
			}
			field.setAccessible(true);
			{
				// int类型默认值判断
				if (field.getType().getName().equals(int.class.getName())) {
					Integer num = (Integer) field.get(bean);
					if (num != 0) {
						// 已有默认值
						continue;
					}
				}
			}

			Object value = getDefaultValueByFieldName(field);// 按字段名称获取默认值
			if (value == null) {
				value = getDefaultValue(field.getType());
			}
			field.set(bean, value);
		}
	}

	// 常用字段默认值
	// private static final Map<String, Object> FIELD_DEFAULT_VALUE = new HashMap<String, Object>();
	// static {
	// FIELD_DEFAULT_VALUE.put("yyuid", 1L);
	// FIELD_DEFAULT_VALUE.put("opyyuid", 1L);
	// FIELD_DEFAULT_VALUE.put("username", "username");
	// FIELD_DEFAULT_VALUE.put("opusername", "hctan");
	// FIELD_DEFAULT_VALUE.put("gameId", "ddt");
	// FIELD_DEFAULT_VALUE.put("serverId", "s1");
	// }

	private static final FieldDefaultValue fieldDefaultValue = new FieldDefaultValueImpl();

	public static Object getDefaultValueByFieldName(Field field) {
		return fieldDefaultValue.get(field, field.getName());
	}

	@SuppressWarnings("unchecked")
	public static <T> T to(String value, Class<T> clazz) {
		if (clazz.equals(String.class)) {
			return (T) value;
		}
		if (ClassTypeUtil.isInteger(clazz)) {
			// if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			return (T) (Integer) (Integer.parseInt(value));
		}
		if (ClassTypeUtil.isLong(clazz)) {
			// if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			return (T) (Long) (Long.parseLong(value));
		}
		throw new RuntimeException("未知类型[" + clazz.getName() + "].");
	}

	public static Object[] getDefaultValue(Class<?>[] params) {
		Object[] args = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			args[i] = getDefaultValue(params[i]);
		}
		return args;
	}

	@SuppressWarnings("rawtypes")
	public static Object getDefaultValue(Class<?> clazz) {
		ParameterType param = new ParameterType(clazz);
		Object arg;
		if (param.isString()) {
			arg = "str";
		}
		else if (param.isStrings()) {
			arg = new String[] {};
		}
		else if (param.isInteger()) {
			arg = 0;
		}
		else if (param.isInts()) {
			arg = new int[] {};
		}
		else if (param.isIntegers()) {
			arg = new int[] {};
		}
		else if (param.isShort()) {
			arg = (short) 0;
		}
		else if (param.isFloat()) {
			arg = 0F;
		}
		else if (param.isDouble()) {
			arg = 0D;
		}
		else if (param.isLong()) {
			arg = 0L;
		}
		else if (param.isBigDecimal()) {
			arg = 0;
		}
		else if (param.isBoolean()) {
			arg = false;
		}
		else if (param.isCharacter()) {
			arg = 'a';
		}
		else if (param.isDate()) {
			arg = new Date(1);
		}
		else if (param.isTimestamp()) {
			arg = new Timestamp(1);
		}
		else if (param.isOnlyDate()) {
			arg = new OnlyDate("2000-01-01");
		}
		else if (param.isMonth()) {
			arg = new Month("2000-01");
		}
		else if (param.isList()) {
			arg = new ArrayList();
		}
		else if (param.isCollection()) {
			throw new NotImplementedException();
		}
		else if (param.isSet()) {
			throw new NotImplementedException();
		}
		else if (param.isMap()) {
			throw new NotImplementedException();
		}
		else if (param.isHttpServletRequest()) {
			throw new NotImplementedException();
		}
		else if (param.isHttpServletResponse()) {
			throw new NotImplementedException();
		}
		else if (param.isMultipartFile()) {
			throw new NotImplementedException();
		}
		else if (param.isWebDataBinder()) {
			throw new NotImplementedException();
		}
		else if (param.isModelMap()) {
			arg = new ModelMap();
		}

		else if (param.isModelAndView()) {
			arg = new ModelAndView();
		}
		else if (param.isEnum()) {
			arg = EnumUtil.getFirstElement(clazz);
		}
		else {
			throw new RuntimeException("参数解析类型失败[" + clazz.getName() + "].");
		}
		return arg;
	}
}
