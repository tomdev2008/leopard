package io.leopard.data4j.log.avgtime;

import java.util.Date;

/**
 * util
 * 
 * @author zhangkaiwei
 * 
 */
public class RunInfoUtil {
	public static int getMinute(RunInfo runInfo) {
		Date startTime = runInfo.getStartTime();
		long time = (System.currentTimeMillis() - startTime.getTime());
		int minute = (int) (time / 1000L / 60);
		return minute;
	}

	/**
	 * 得到输出日志信息
	 * 
	 * @param runInfo
	 * @return
	 */
	// name - type - minute - avg
	public static String runInfo(RunInfo runInfo, int minute) {
		StringBuilder message = new StringBuilder();
		long avgTime = (runInfo.getRunTime() / 1000L / 1000L) / runInfo.getCount();
		message.append(runInfo.getBlockName());
		message.append(" " + runInfo.getType());
		message.append(" " + minute);
		message.append(" " + runInfo.getCount());
		message.append(" " + avgTime);
		return message.toString();
	}

	/**
	 * 是否临界点.
	 * 
	 * @param type
	 */
	// public static boolean isCriticalPoint(int type) {
	// Date currentDate = new Date();
	// return RunInfoUtil.isCriticalPoint(type, currentDate);
	// }

	private static final int DAY_SECOND = 60 * 60 * 24;

	public static boolean isCriticalPoint(int type, Date currentDate) {
		int time = (int) (currentDate.getTime() / 1000L + (8 * 60 * 60));
		int secondCount = (time % DAY_SECOND);
		int tmp = secondCount % (type * 60);
		// System.out.println("type:" + type + " tmp:" + tmp + " secondCount:" + secondCount);
		return tmp == 0;
	}

	/**
	 * 得到Map<String,RunInfo>的key
	 * 
	 * @param blockName
	 * @param type
	 * @return
	 */
	public static String getMapKey(String blockName, int type) {
		return (blockName + ":" + type);
	}
}
