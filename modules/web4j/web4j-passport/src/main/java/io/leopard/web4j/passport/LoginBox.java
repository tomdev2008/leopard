package io.leopard.web4j.passport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录框.
 * 
 * @author 阿海
 * 
 */
public interface LoginBox {

	void showLoginBox(HttpServletRequest request, HttpServletResponse response);

}
