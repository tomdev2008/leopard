package io.leopard.data.env;

import org.apache.commons.lang.SystemUtils;

/**
 * mac开发环境
 * 
 * @author 阿海
 * 
 */
public class EnvDaoMacImpl extends EnvDaoWindowsImpl implements EnvDao {

	@Override
	public boolean isEnabled() {
		return SystemUtils.IS_OS_MAC;
	}

}
