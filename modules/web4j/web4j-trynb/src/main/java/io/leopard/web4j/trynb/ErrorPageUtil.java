package io.leopard.web4j.trynb;

public class ErrorPageUtil {

	public static boolean match(String type, String exceptionClassName) {
		if (type.indexOf(".") == -1) {
			if (exceptionClassName.endsWith(type)) {
				return true;
			}
		}
		if (exceptionClassName.equals(type)) {
			return true;
		}
		return false;
	}
}
