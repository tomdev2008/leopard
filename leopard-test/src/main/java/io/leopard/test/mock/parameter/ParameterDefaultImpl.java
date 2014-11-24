package io.leopard.test.mock.parameter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterDefaultImpl implements Parameter {

	public String defaultString(NbField field) {
		// System.err.println("defaultString:" + field.getName());
		return "";
	}

	public String[] defaultStrings(NbField field) {
		return new String[] {};
	}

	public Integer defaultInteger(NbField field) {
		return 0;
	}

	public int[] defaultInts(NbField field) {
		return new int[] {};
	}

	public Integer[] defaultIntegers(NbField field) {
		return new Integer[] {};
	}

	public Float defaultFloat(NbField field) {
		return 0f;
	}

	public Double defaultDouble(NbField field) {
		return 0d;
	}

	public Long defaultLong(NbField field) {
		return 0L;
	}

	public Boolean defaultBoolean(NbField field) {
		return false;
	}

	public Date defaultDate(NbField field) {
		return new Date();
	}

	public Character defaultCharacter(NbField field) {
		return ' ';
	}

	@Override
	public List<Object> defaultList(NbField field) {
		List<Object> list = new ArrayList<Object>();
		return list;
	}

	@Override
	public Collection<Object> defaultCollection(NbField field) {
		List<Object> list = new ArrayList<Object>();
		return list;
	}

	@Override
	public Set<Object> defaultSet(NbField field) {
		Set<Object> list = new HashSet<Object>();
		return list;
	}

	@Override
	public Map<Object, Object> defaultMap(NbField field) {
		Map<Object, Object> list = new HashMap<Object, Object>();
		return list;
	}

	@Override
	public int size(String fieldName, String dataType) {
		return 0;
	}

	@Override
	public BigDecimal defaultBigDecimal(NbField field) {
		return new BigDecimal(0);
	}

}
