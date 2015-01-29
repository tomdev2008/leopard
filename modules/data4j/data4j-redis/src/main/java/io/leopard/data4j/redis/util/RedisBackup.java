package io.leopard.data4j.redis.util;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.timer.PerDayPeriod;
import io.leopard.burrow.timer.Period;
import io.leopard.burrow.timer.Timer;
import io.leopard.burrow.timer.TimerUtil;
import io.leopard.data4j.redis.Redis;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

/**
 * redis备份.
 * 
 * @author 阿海
 * 
 */
public class RedisBackup extends ContextImpl implements Timer {

	// private static boolean enableBackup = false;// 是否开启备份.

	private static final String KEY = "system:last_backup_time";

	// private static final int INTERVAL = 2;// 间隔多久算上次备份的时间
	// private static final int INTERVAL = 10 * 60;// 间隔多久算上次备份的时间(10分钟)
	protected String backupTime;

	private int backupHour;
	private int backupMinute;

	private Redis redis;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	// public static void setEnableBackup(boolean enableBackup) {
	// RedisBackup.enableBackup = enableBackup;
	// }

	public void setBackupTime(String backupTime) {
		this.backupTime = backupTime;
		String[] strs = backupTime.split(":");
		this.backupHour = Integer.parseInt(strs[0]);
		this.backupMinute = Integer.parseInt(strs[1]);
		// System.err.println("backupHour:" + backupHour + " backupMinute:" +
		// backupMinute);
	}

	/**
	 * 间隔多久算上次备份的时间(10分钟)
	 * 
	 * @return
	 */
	protected int getInterval() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return 2;
		}
		return 10 * 60;
	}

	@Override
	public Period getPeriod() {
		// if (SystemUtils.IS_OS_WINDOWS) {
		// return new SecondPeriod(10);
		// }
		// 每天凌晨4点1分备份.
		return new PerDayPeriod(backupHour, this.backupMinute);
	}

	/**
	 * 获取最后备份时间.
	 * 
	 * @return
	 */
	public Date getLastBackupTime() {
		long time = System.currentTimeMillis();
		String lastBackupTime = redis.getSet(KEY, Long.toString(time));
		if (StringUtils.isEmpty(lastBackupTime)) {
			return null;
		}
		long backupTime = Long.parseLong(lastBackupTime);
		if (backupTime <= 0) {
			return null;
		}
		return new Date(backupTime);
	}

	/**
	 * 是否上次备份时间.
	 * 
	 * @param lastBackupTime
	 * @return
	 */
	protected boolean isLastTime(Date lastBackupTime) {
		if (lastBackupTime == null) {
			return true;
		}
		long diff = System.currentTimeMillis() - lastBackupTime.getTime();
		if (diff > (this.getInterval() * 1000L)) {
			// 10分钟前的时间算是上次备份时间
			return true;
		}
		return false;
	}

	protected boolean bgsave() {
		String result = this.redis.bgsave();
		logger.info("redis backup,bgsave server:" + this.redis.getServerInfo() + " message:" + result);
		return true;
	}

	protected boolean bgrewriteaof() {
		String result = this.redis.bgrewriteaof();
		logger.info("redis backup,bgrewriteaof server:" + this.redis.getServerInfo() + " message:" + result);
		return true;
	}

	/**
	 * 备份.
	 * 
	 * @return
	 */
	public boolean backup() {
		Date lastBackupTime = this.getLastBackupTime();
		boolean isLastTime = this.isLastTime(lastBackupTime);
		if (!isLastTime) {
			// 别的redis连接已经备份过.
			return false;
		}
		this.bgsave();
		// this.bgrewriteaof();
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void start() {
		this.backup();
	}

	@Override
	public int getThreadCount() {
		return 1;
	}

	public void run(Redis redis) {
		logger.info("run redis:" + redis.getServerInfo());
		this.setRedis(redis);
		TimerUtil.start(this);
	}
}
