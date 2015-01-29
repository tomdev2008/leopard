package io.leopard.data4j.log.avgtime;

import io.leopard.burrow.timer.SimpleTimer;
import io.leopard.data4j.log.Log4jFactory;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;

public class RunInfoServiceImpl implements RunInfoService {

	protected final Log logger = Log4jFactory.getTimeAvgLogger(this.getClass());

	private RunInfoDao runInfoDao = new RunInfoDaoMemoryImpl();

	private static boolean isInitialized = false;

	public RunInfoServiceImpl() {
		// System.out.println("new instance RunInfoServiceImpl");
	}

	@Override
	public boolean add(String blockName, long runTime) {
		Type[] types = Type.values();
		for (Type type : types) {// 遍历所有类型
			RunInfo runInfo = new RunInfo();
			runInfo.setBlockName(blockName);
			runInfo.setType(type.getKey());
			runInfo.setRunTime(runTime);
			runInfoDao.add(runInfo);
		}
		return true;
	}

	@Override
	public List<RunInfo> listAll() {
		return this.runInfoDao.listAll();
	}

	@Override
	public boolean remove(String blockName, int type) {
		return this.runInfoDao.remove(blockName, type);
	}

	@Override
	public void startTimer() {
		if (isInitialized) {
			Exception e = new Exception("定时器已经启动");
			logger.debug(e.getMessage(), e);
			return;
		}
		logger.debug("startTimer:" + Thread.currentThread().getName());
		isInitialized = true;
		// Timer timer = new Timer();
		// timer.schedule(new TimerTask() {
		// @Override
		// public void run() {
		// writeLog();
		// }
		// }, 0, 1000);

		new SimpleTimer(1) {
			@Override
			public void start() {
				writeLog();
			}
		};

	}

	@Override
	public void writeLog() {
		Date currentDate = new Date();
		if (!RunInfoUtil.isCriticalPoint(1, currentDate)) {
			// 还没有到临街秒数
			return;
		}
		List<RunInfo> runInfoList = this.listAll();
		for (RunInfo runInfo : runInfoList) {
			int minute = RunInfoUtil.getMinute(runInfo);
			String message = RunInfoUtil.runInfo(runInfo, minute);// 获取日志输出信息
			if (RunInfoUtil.isCriticalPoint(runInfo.getType(), currentDate)) {// 统计已经超过所属“类型”时间
				this.remove(runInfo.getBlockName(), runInfo.getType());
			}
			logger.debug(message);
			// System.out.println(message);
		}

	}
}
