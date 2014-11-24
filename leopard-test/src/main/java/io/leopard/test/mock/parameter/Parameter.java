package io.leopard.test.mock.parameter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Parameter {

	String defaultString(NbField field);

	String[] defaultStrings(NbField field);

	Integer defaultInteger(NbField field);

	int[] defaultInts(NbField field);

	Integer[] defaultIntegers(NbField field);

	Float defaultFloat(NbField field);

	Double defaultDouble(NbField field);

	Long defaultLong(NbField field);

	Boolean defaultBoolean(NbField field);

	Date defaultDate(NbField field);

	List<Object> defaultList(NbField field);

	Collection<Object> defaultCollection(NbField field);

	Set<Object> defaultSet(NbField field);

	Map<Object, Object> defaultMap(NbField field);

	Character defaultCharacter(NbField field);

	/**
	 * 数组大小.
	 * 
	 * @param fieldName
	 * @return
	 */
	int size(String fieldName, String dataType);

	BigDecimal defaultBigDecimal(NbField field);
}
