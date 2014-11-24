package io.leopard.data.env;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

public class EnvDaoImpl implements EnvDao {

	private static EnvDao instance = new EnvDaoImpl();

	public static EnvDao getInstance() {
		return instance;
	}

	private static List<EnvDao> envDaoList = new ArrayList<EnvDao>();

	static {
		envDaoList.add(new EnvDaoWindowsImpl());
		envDaoList.add(new EnvDaoServerImpl());
		envDaoList.add(new EnvDaoMacImpl());
	}

	protected EnvDao getEnvDao() {
		for (EnvDao envDao : envDaoList) {
			if (envDao.isEnabled()) {
				return envDao;
			}
		}
		throw new RuntimeException("未知环境.");

		// if (envDaoYtestImpl.isEnabled()) {
		// // ytest
		// return envDaoYtestImpl;
		// }
		// else if (envDaoHudsonImpl.isEnabled()) {
		// // hudson
		// return envDaoHudsonImpl;
		// }
		// else if (envDaoPmdImpl.isEnabled()) {
		// // pmd
		// return envDaoPmdImpl;
		// }
		// else if (envDaoWindowsImpl.isEnabled()) {
		// // windows
		// return envDaoWindowsImpl;
		// }
		// else if (envDaoMacImpl.isEnabled()) {
		// // mac
		// return envDaoMacImpl;
		// }
		// else if (envDaoUbuntuImpl.isEnabled()) {
		// // ubuntu
		// return envDaoUbuntuImpl;
		// }
		// else if (envDaoServerImpl.isEnabled()) {
		// // 服务器
		// return envDaoServerImpl;
		// }
		// else {
		// throw new RuntimeException("未知环境.");
		// }
	}

	@Override
	public boolean isEnabled() {
		throw new NotImplementedException();
	}

	@Override
	public String getEnv() {
		return this.getEnvDao().getEnv();
	}

	@Override
	public String getRootDir() {
		EnvDao envDao = this.getEnvDao();
		String rootDir = envDao.getRootDir();
		return rootDir;
	}

	@Override
	public boolean isDevEnv() {
		return this.getEnvDao().isDevEnv();
	}

}
