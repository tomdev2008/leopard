package io.leopard.web.timer;

import io.leopard.burrow.timer.Timer;
import io.leopard.burrow.timer.TimerUtil;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimerServiceImpl implements TimerService {
	protected final Log logger = LogFactory.getLog("TIMERLOG." + this.getClass().getName());

	private static boolean isInitialized = false;
	private Timer[] timers;

	// public TimerServiceImpl() {
	// System.err.println("new TimerServiceImpl");
	// }

	public void setTimers(Timer[] timers) {
		// System.out.println("setTimers:" + timers.length);
		this.timers = timers;
	}

	@PostConstruct
	/**
	 * 初始化，启动所有定时器 .
	 */
	public void init() {
//		System.err.println("TimerServiceImpl init");
		if (isInitialized) {
			// 为了防止spring重复配置导致定时器启动多次。
			throw new RuntimeException("定时器已启动.");
		}
		isInitialized = true;
		if (timers != null) {
			for (int i = 0; i < timers.length; i++) {
				// System.err.println("start timer:" + timers[i].getClass().getName());
				TimerUtil.start(timers[i]);
			}
		}
	}

}
