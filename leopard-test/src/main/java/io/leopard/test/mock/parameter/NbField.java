package io.leopard.test.mock.parameter;

import java.lang.reflect.Method;

/**
 * 类属性信息.
 * 
 * @author 阿海
 * 
 */
public class NbField {
	private Class<?> param;// 参数对象
	private String fieldTypeName;// 类型名称.
	private String name;// 参数名称
	private Object value;// 参数值
	private Method method;// 所属方法

	public Class<?> getParam() {
		return param;
	}

	public void setParam(Class<?> param) {
		this.param = param;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldTypeName() {
		return fieldTypeName;
	}

	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

}
