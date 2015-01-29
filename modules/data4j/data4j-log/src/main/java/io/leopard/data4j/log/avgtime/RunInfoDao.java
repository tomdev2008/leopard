package io.leopard.data4j.log.avgtime;

import java.util.List;

/**
 * 运行信息类
 * 
 * @author 张凯伟
 * 
 */
public interface RunInfoDao {
	/**
	 * 查询所有的代码块运行信息
	 * 
	 * @return
	 */

	public List<RunInfo> listAll();

	/**
	 * 删除指定的代码块运行信息
	 * 
	 * @param blockName
	 * @param type
	 * @return
	 */

	public boolean remove(String blockName, int type);

	/**
	 * 添加代码块运行信息
	 * 
	 * @param runInfo
	 * @return
	 */
	public boolean add(RunInfo runInfo);

	/**
	 * 得到指定运行块信息
	 * 
	 * @param mapKey
	 * @return
	 */

	public RunInfo get(String mapKey);

}
