package io.leopard.test.mock.reflect;

import java.lang.reflect.Field;

/**
 * 字段默认值.
 * 
 * @author 阿海
 * 
 */
public interface FieldDefaultValue {

	Object get(Field field, String fieldName);
}
