package io.leopard.data4j.log.avgtime;

/**
 * 平均耗时统计.
 * 
 * @author 阿海
 * 
 */
public class AvgTime {
	private final static RunInfoService runInfoService = new RunInfoServiceImpl();

	// 日志格式
	// name - type - minute - avg
	private final String blockName;
	private final long startTime;

	static {
		runInfoService.startTimer();
	}

	public AvgTime(String blockName, long startTime) {
		this.blockName = blockName;
		this.startTime = startTime;
	}

	public static AvgTime start(String blockName) {
		return new AvgTime(blockName, System.nanoTime());
	}

	public long end() {
		long endTime = System.nanoTime();
		long time = endTime - startTime;
		runInfoService.add(blockName, time);
		return time;
	}

	protected static void writeLog() {
		runInfoService.writeLog();
	}

}
