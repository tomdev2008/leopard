package io.leopard.monitor;

import io.leopard.burrow.timer.SecondPeriod;
import io.leopard.burrow.timer.SimpleTimer;
import io.leopard.commons.utility.SystemUtil;
import io.leopard.monitor.alarm.AlarmDaoSmsImpl;
import io.leopard.schema.model.MonitorConfig;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MonitorServiceImpl implements MonitorService, BeanFactoryAware {

	private static MonitorConfig monitorConfig;

	public static MonitorConfig getMonitorConfig() {
		return monitorConfig;
	}

	public static void setMonitorConfig(MonitorConfig monitorConfig) {
		MonitorServiceImpl.monitorConfig = monitorConfig;
	}

	private IMonitor[] monitors;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) beanFactory;
		Collection<IMonitor> monitorList = factory.getBeansOfType(IMonitor.class).values();
		monitors = new IMonitor[monitorList.size()];
		monitorList.toArray(monitors);
	}

	@PostConstruct
	public void init() {
		// System.err.println("MonitorServiceImpl init monitorConfig:" + monitorConfig);
		// new Exception("MonitorServiceImpl init monitorConfig:" + monitorConfig).printStackTrace();
		if (monitorConfig == null) {
			return;
		}
		boolean smsEnable = monitorConfig.getAlarmInfo().isSms();
		if (SystemUtil.isWindows()) {
			Boolean windows = monitorConfig.getAlarmInfo().getWindows();
			if (windows != null) {
				smsEnable = windows;
			}
		}
		// System.err.println("smsEnable:" + smsEnable);
		AlarmDaoSmsImpl.setSmsEnable(smsEnable);

		if (monitorConfig.isEnable()) {
			new SimpleTimer(new SecondPeriod(3)) {
				@Override
				public void start() {
					for (IMonitor monitor : monitors) {
						monitor.monitor();
					}
				}

			}.run();
		}
	}

}
