package io.leopard.test.mock.parameter;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.lang.Json;
import io.leopard.burrow.refect.FieldUtil;
import io.leopard.commons.utility.DateUtil;
import io.leopard.core.exception.NoSuchFieldRuntimeException;
import io.leopard.test.mock.reflect.EnumUtil;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterImpl extends ContextImpl implements Parameter {

	private final Parameter parameterDefaultImpl = new ParameterDefaultImpl();

	@Override
	public String defaultString(NbField field) {
		try {
			Object result = this.getFieldValue(field);
			if (result != null) {
				boolean isEnum = result.getClass().isEnum();
				if (isEnum) {
					try {
						return (String) EnumUtil.getId(result);
					}
					catch (RuntimeException e) {
						logger.error(e.getMessage());
						return parameterDefaultImpl.defaultString(field);
					}
				}
			}
			return (String) result;
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultString(field);
		}
		catch (ClassCastException e) {
			this.logClassCastException(field, e);
			return parameterDefaultImpl.defaultString(field);
		}
	}

	protected void logClassCastException(NbField field, ClassCastException e) {
		Method method = field.getMethod();
		String message = "fieldName:" + field.getName() + " getFieldTypeName:" + field.getParam().getName();
		if (method != null) {
			message += " method:" + method.toGenericString();
		}
		logger.error(message);
		// e.printStackTrace();
	}

	@Override
	public String[] defaultStrings(NbField field) {
		try {
			return (String[]) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultStrings(field);
		}
		catch (ClassCastException e) {
			this.logClassCastException(field, e);
			return parameterDefaultImpl.defaultStrings(field);
		}
	}

	@Override
	public Integer defaultInteger(NbField field) {
		try {
			Object result = this.getFieldValue(field);
			if (result != null) {
				boolean isEnum = result.getClass().isEnum();
				if (isEnum) {
					try {
						return (Integer) EnumUtil.getId(result);
					}
					catch (RuntimeException e) {
						logger.error(e.getMessage());
						return parameterDefaultImpl.defaultInteger(field);
					}
				}
			}
			return (Integer) result;
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultInteger(field);
		}
		catch (ClassCastException e) {
			this.logClassCastException(field, e);
			return parameterDefaultImpl.defaultInteger(field);
		}
	}

	@Override
	public int[] defaultInts(NbField field) {
		try {
			return (int[]) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultInts(field);
		}
		catch (ClassCastException e) {
			this.logClassCastException(field, e);
			return parameterDefaultImpl.defaultInts(field);
		}
	}

	@Override
	public Integer[] defaultIntegers(NbField field) {
		try {
			return (Integer[]) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultIntegers(field);
		}
		catch (ClassCastException e) {
			this.logClassCastException(field, e);
			return parameterDefaultImpl.defaultIntegers(field);
		}
	}

	@Override
	public Float defaultFloat(NbField field) {
		try {
			return (Float) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultFloat(field);
		}
	}

	@Override
	public BigDecimal defaultBigDecimal(NbField field) {
		try {
			return (BigDecimal) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultBigDecimal(field);
		}
	}

	@Override
	public Double defaultDouble(NbField field) {
		try {
			return (Double) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultDouble(field);
		}
	}

	@Override
	public Long defaultLong(NbField field) {
		try {
			return (Long) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultLong(field);
		}
		catch (ClassCastException e) {
			this.logClassCastException(field, e);
			return parameterDefaultImpl.defaultLong(field);
		}
	}

	@Override
	public Boolean defaultBoolean(NbField field) {
		try {
			return (Boolean) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultBoolean(field);
		}
	}

	@Override
	public Date defaultDate(NbField field) {
		try {
			Object value = this.getFieldValue(field);
			try {
				return (Date) value;
			}
			catch (ClassCastException e) {
				try {
					String time = (String) value;
					if ("1970-01-01 00:00:00".equals(time)) {
						// time = "1970-01-01 08:00:00";
						return new Date(1);
					}
					return DateUtil.toDate(time);
				}
				catch (ClassCastException e1) {
					this.logClassCastException(field, e1);
					return parameterDefaultImpl.defaultDate(field);
				}
			}

		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultDate(field);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> defaultList(NbField field) {
		try {
			Object result = this.getFieldValue(field);
			if (result instanceof int[]) {
				return toList((int[]) result);
			}
			return (List<Object>) result;
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultList(field);
		}
		catch (ClassCastException e) {
			this.logClassCastException(field, e);
			return parameterDefaultImpl.defaultList(field);
		}
	}

	protected List<Object> toList(int[] nums) {
		if (nums == null || nums.length == 0) {
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		for (int num : nums) {
			list.add(num);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> defaultCollection(NbField field) {
		try {
			return (Collection<Object>) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultList(field);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Object> defaultSet(NbField field) {
		try {
			return (Set<Object>) this.getFieldValue(field);
		}
		catch (ClassCastException e) {
			this.logClassCastException(field, e);
			return parameterDefaultImpl.defaultSet(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultSet(field);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> defaultMap(NbField field) {
		try {
			Object result = this.getFieldValue(field);
			if (result instanceof String) {
				return Json.toObject((String) result, Map.class);
			}
			return (Map<Object, Object>) result;
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultMap(field);
		}
	}

	@Override
	public Character defaultCharacter(NbField field) {
		try {
			return (Character) this.getFieldValue(field);
		}
		catch (NoSuchFieldRuntimeException e) {
			return parameterDefaultImpl.defaultCharacter(field);
		}
	}

	protected Object getFieldValue(NbField field) {
		return FieldUtil.getFieldValue(field, field.getName());
		// return FieldUtil.getFieldValue(bean, field.getName());
	}

	@Override
	public int size(String fieldName, String dataType) {
		throw new RuntimeException("size方法未实现.");
	}

}
