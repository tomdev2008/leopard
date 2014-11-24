package io.leopard.web.security;

import io.leopard.commons.utility.EncryptUtil;
import io.leopard.core.context.ContextImpl;

public class CsrfDaoUsernameImpl extends ContextImpl implements CsrfDao {

	// @Override
	// public void checkToken(Object yyuid, String token) {
	// String[] list = token.split("-");
	// if (list.length != 3) {
	// throw new CsrfTokenInvalidException("非法token[" + token + "].");
	// }
	//
	// String prefix = list[1];
	// long time = Long.parseLong(list[2]);
	// String sha1 = EncryptUtil.sha1(time + " " + yyuid + " " + publicKey); // 使用SHA1加密算法
	// if (!sha1.startsWith(prefix)) {
	// // + prefix + "," + sha1 + ","
	// logger.error("prefix:" + prefix + " yyuid:" + yyuid + " time:" + time + " sha1:" + sha1 + " token:" + token);
	// throw new CsrfTokenInvalidException("token[" + token + "]不正确.");
	// }
	// }

	@Override
	public void checkToken(String username, String token) {
		String[] list = token.split("-");
		if (list.length != 3) {
			throw new CsrfTokenInvalidException("非法token[" + token + "].");
		}

		String prefix = list[0];
		long time = Long.parseLong(list[2]);
		String sha1 = EncryptUtil.sha1(time + " " + username + " " + CsrfServiceImpl.publicKey); // 使用SHA1加密算法
		if (!sha1.startsWith(prefix)) {
			// + prefix + "," + sha1 + ","
			logger.error("prefix:" + prefix + " username:" + username + " time:" + time + " sha1:" + sha1 + " token:" + token);
			throw new CsrfTokenInvalidException("token[" + token + "]不正确.");
		}
	}

}
