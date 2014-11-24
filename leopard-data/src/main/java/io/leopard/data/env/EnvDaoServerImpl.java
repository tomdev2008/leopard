package io.leopard.data.env;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

/**
 * linux服务器环境(除hudson)
 * 
 * @author 阿海
 * 
 */
public class EnvDaoServerImpl implements EnvDao {

	@Override
	public boolean isEnabled() {
		if (!SystemUtils.IS_OS_LINUX) {
			return false;
		}
		String projectName = EnvUtil.getProjectCode();
		return StringUtils.isNotEmpty(projectName);
	}

	@Override
	public String getEnv() {
		String env = EnvUtil.getEnv();
		if (StringUtils.isEmpty(env)) {
			throw new RuntimeException("未配置环境变量" + EnvUtil.NAME_ENV + ".");
		}
		return env;
	}

	@Override
	public String getRootDir() {
		String projectName = EnvUtil.getProjectCode();
		if (StringUtils.isEmpty(projectName)) {
			throw new IllegalArgumentException("未配置环境变量DWPROJECTNO.");
		}
		String env = this.getEnv();
		if (EnvUtil.ENV_DEV.equalsIgnoreCase(env)) {
			return "/data/src/" + projectName;
		}
		else {
			return "/data/src/" + projectName;
		}
	}

	@Override
	public boolean isDevEnv() {
		return false;
	}

}
