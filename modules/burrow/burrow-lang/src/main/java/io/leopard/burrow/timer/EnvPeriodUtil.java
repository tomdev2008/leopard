package io.leopard.burrow.timer;

import org.apache.commons.lang.SystemUtils;

public class EnvPeriodUtil {

	public static Period getPeriod(Period serverPeriod, Period windowsPeriod) {
		if (SystemUtils.IS_OS_WINDOWS) {
			return windowsPeriod;
		}
		else {
			return serverPeriod;
		}
	}
}
