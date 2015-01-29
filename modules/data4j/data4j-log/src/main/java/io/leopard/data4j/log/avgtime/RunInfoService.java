package io.leopard.data4j.log.avgtime;

import java.util.List;

/**
 * 运行信息
 * 
 * @author 张凯伟
 * 
 */
public interface RunInfoService {

	/**
	 * 添加运行信息
	 * 
	 * @param runInfo
	 * @return
	 */
	boolean add(String blockName, long runTime);

	/**
	 * 所有代码块运行信息
	 * 
	 * @return
	 */
	public List<RunInfo> listAll();

	/**
	 * 删除代码块运行信息
	 * 
	 * @param blockName
	 * @param type
	 * @return
	 */

	public boolean remove(String blockName, int type);

	/**
	 * 日志写入
	 * 
	 * @param runInfo
	 */
	void writeLog();

	/**
	 * 启动定时器.
	 */
	void startTimer();

}
