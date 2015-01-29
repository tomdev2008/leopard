package io.leopard.data4j.log.avgtime;

import io.leopard.burrow.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class RunInfoDaoMemoryImpl implements RunInfoDao {
	public Map<String, RunInfo> map = new ConcurrentHashMap<String, RunInfo>();

	@Override
	public boolean add(RunInfo runInfo) {
		String mapKey = RunInfoUtil.getMapKey(runInfo.getBlockName(), runInfo.getType());
		RunInfo cache = this.get(mapKey);
		if (cache == null) {// 如果map中不存在，则添加
			runInfo.setStartTime(new Date());
			runInfo.setCount(1);
			return ObjectUtil.isNull(map.put(mapKey, runInfo));
		}
		else {
			// 修改总的运行时间和次数
			cache.setRunTime(cache.getRunTime() + runInfo.getRunTime());
			cache.setCount(cache.getCount() + 1);
			return true;
		}
	}

	@Override
	public RunInfo get(String mapKey) {
		return map.get(mapKey);
	}

	@Override
	public boolean remove(String blockName, int type) {
		String mapKey = RunInfoUtil.getMapKey(blockName, type);
		// System.out.println("remove:" + mapKey);
		return ObjectUtil.isNotNull(map.remove(mapKey));
	}

	@Override
	public List<RunInfo> listAll() {
		List<RunInfo> runInfoList = new ArrayList<RunInfo>();
		for (Entry<String, RunInfo> entry : map.entrySet()) {
			runInfoList.add(entry.getValue());
		}
		return runInfoList;
	}
}
