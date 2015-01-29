package io.leopard.data4j.log.avgtime;

import java.util.Date;

/**
 * xxx
 * 
 * @author zhangkaiwei
 * 
 */
public class RunInfo {

	/** 类型 */
	private int type;
	/** 代码块名称 */
	private String blockName;

	private Date startTime;// 开始运行时间,精确到纳秒
	private int count;// 运行次数
	private long runTime;// 代码运行时间

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getRunTime() {
		return runTime;
	}

	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
