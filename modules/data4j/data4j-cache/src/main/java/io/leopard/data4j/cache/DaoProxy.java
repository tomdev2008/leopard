package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IDelete;
import io.leopard.data4j.cache.api.IGet;
import io.leopard.data4j.cache.api.IStatus;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DaoProxy {
	private static Log logger = LogFactory.getLog(DaoProxy.class);

	public interface DeleteSuccess<BEAN, KEYTYPE> {
		public boolean deleteSuccess(KEYTYPE key, String username, BEAN bean);
	}

	public interface UndeleteSuccess<BEAN, KEYTYPE> {
		public boolean undeleteSuccess(KEYTYPE key, String username, BEAN bean);
	}

	public interface UpdateStatusSuccess<BEAN, KEYTYPE> {
		public boolean updateStatusSuccess(KEYTYPE key, int type, boolean on, String username, String reason, BEAN bean);
	}

	// // TODO 此方法为特殊实现，因为悠聊的article和comment的删除状态值还不统一.
	// private static <BEAN, KEYTYPE> int getTypeDeleted(IStatus<BEAN, KEYTYPE>
	// statusDao) {
	// Class<?> interfaceClazz = statusDao.getClass().getInterfaces()[0];
	// String className = interfaceClazz.getName();
	// if ("com.duowan.youliao.dao.ArticleDao".equals(className)) {
	// return 2;
	// }
	// if ("com.duowan.youliao.dao.CommentDao".equals(className)) {
	// return 1;
	// }
	//
	// if (IStatus.class.getName().equals(className)) {
	// return 2;
	// }
	// // System.out.println("statusDao:" +
	// // statusDao.getClass().getInterfaces()[0]);
	// throw new RuntimeException("获取删除状态值出错,未知接口[" + className + "]");
	// }
	//
	// public static <BEAN, KEYTYPE> BEAN updateStatus(IStatus<BEAN, KEYTYPE>
	// statusDao, KEYTYPE key, int type, boolean on, String username, String
	// reason, UpdateStatusSuccess<BEAN, KEYTYPE> invoker) {
	// Date lmodify = new Date();
	// BEAN bean = DaoProxy.updateStatusOny(statusDao, key, type, on, username,
	// lmodify, reason);
	// if (bean == null) {
	// return null;
	// }
	// int typeDeleted = DaoProxy.getTypeDeleted(statusDao);
	// if (type == typeDeleted) {
	// logger.info("updateStatus key:" + key + " on:" + on + " bean:" +
	// Json.toJson(bean));
	// if (on) {
	// statusDao.delete(key, username, lmodify);
	// }
	// else {
	// statusDao.undelete(key, username, lmodify);
	// }
	// }
	// logger.info("updateStatus result key:" + key + " on:" + on + " bean:" +
	// Json.toJson(bean));
	// // this.updateStatusSuccess(key, type, on, username, reason, bean);
	// invoker.updateStatusSuccess(key, type, on, username, reason, bean);
	// return bean;
	// }

	public static <BEAN, KEYTYPE> boolean delete(IDelete<BEAN, KEYTYPE> dao, KEYTYPE key, String username) {
		return dao.delete(key, username, new Date());
	}

	// public static <BEAN, KEYTYPE> boolean deleteAndUpdateStatus(IStatus<BEAN,
	// KEYTYPE> statusDao, KEYTYPE key, String username, int typeDeleted) {
	// return DaoProxy.deleteAndUpdateStatus(statusDao, key, username,
	// typeDeleted, null);
	// }

	// public static <BEAN, KEYTYPE> boolean deleteAndUpdateStatus(IStatus<BEAN,
	// KEYTYPE> statusDao, KEYTYPE key, String username, DeleteSuccess<BEAN,
	// KEYTYPE> invoker) {
	// System.out.println("statusDao:" + statusDao);
	// Date lmodify = new Date();
	// boolean success = statusDao.delete(key, username, lmodify);
	// System.out.println("deleteAndUpdateStatus key:" + key + " success:" +
	// success);
	// if (success) {
	// // 更新状态字段
	// int typeDeleted = DaoProxy.getTypeDeleted(statusDao);
	// DaoProxy.updateStatusOny(statusDao, key, typeDeleted, true, username,
	// lmodify, "");
	// }
	//
	// if (success) {
	// if (invoker != null) {
	// BEAN bean = statusDao.getIncludeDeleted(key);
	// boolean exist = ObjectUtil.isNotNull(bean);
	// AssertUtil.assertTrue(exist, "记录[" + key + "]怎么获取不到.");
	// invoker.deleteSuccess(key, username, bean);
	// }
	// }
	// return success;
	// }

	// public static <BEAN, KEYTYPE> boolean
	// undeleteAndUpdateStatus(IDelete<BEAN, KEYTYPE> dao, IStatus<BEAN,
	// KEYTYPE> statusDao, KEYTYPE key, String username, int typeDeleted) {
	// return DaoProxy.undeleteAndUpdateStatus(dao, statusDao, key, username,
	// typeDeleted, null);
	// }

	// public static <BEAN, KEYTYPE> boolean
	// undeleteAndUpdateStatus(IStatus<BEAN, KEYTYPE> statusDao, KEYTYPE key,
	// String username, UndeleteSuccess<BEAN, KEYTYPE> invoker) {
	// Date lmodify = new Date();
	// boolean success = statusDao.delete(key, username, lmodify);
	// if (success) {
	// // 更新状态字段
	// int typeDeleted = DaoProxy.getTypeDeleted(statusDao);
	// DaoProxy.updateStatusOny(statusDao, key, typeDeleted, false, username,
	// lmodify, "");
	// }
	//
	// if (success) {
	// if (invoker != null) {
	// BEAN bean = statusDao.getIncludeDeleted(key);
	// assert ObjectUtil.isNotNull(bean);
	// invoker.undeleteSuccess(key, username, bean);
	// }
	// }
	// return success;
	// }

	// private static <BEAN, KEYTYPE> BEAN updateStatusOny(IStatus<BEAN,
	// KEYTYPE> statusDao, KEYTYPE key, int type, boolean on, String username,
	// Date lmodify, String reason) {
	// BEAN bean = statusDao.getIncludeDeleted(key);
	// if (bean == null) {
	// return null;
	// }
	// int status = (Integer) BeanUtil.getFieldValue(bean, "status");
	// status = NumberUtil.parseStatus(status, type, on);
	// boolean success = statusDao.updateStatus(key, status, username, lmodify,
	// reason);
	// if (!success) {
	// // TODO 加些日志?
	// logger.error("通过get方法能拿到数据[" + key + "]，为什么更新状态方法会返回false？");
	// return null;
	// }
	// return bean;
	// }

	public static <BEAN, KEYTYPE> List<BEAN> listIncludeDeleted(IStatus<BEAN, KEYTYPE> statusDao, List<KEYTYPE> keyList) {
		if (keyList == null || keyList.isEmpty()) {
			return null;
		}
		List<BEAN> list = statusDao.list(keyList);
		int index = 0;
		for (BEAN bean : list) {
			if (bean == null) {
				KEYTYPE key = keyList.get(index);
				bean = statusDao.getIncludeDeleted(key);
				if (bean == null) {
					logger.warn("记录[" + key + "]不存在.");
				}
				list.set(index, bean);
			}
			index++;
		}
		return list;
	}

	public static <BEAN, KEYTYPE> Map<KEYTYPE, BEAN> map(IGet<BEAN, KEYTYPE> dao, Set<KEYTYPE> keySet) {
		Map<KEYTYPE, BEAN> map = new LinkedHashMap<KEYTYPE, BEAN>();
		if (keySet != null) {
			for (KEYTYPE key : keySet) {
				BEAN bean = dao.get(key);
				// System.out.println("key:" + key + " bean:" +
				// Json.toJson(bean));
				map.put(key, bean);
			}
		}
		return map;
	}
}
