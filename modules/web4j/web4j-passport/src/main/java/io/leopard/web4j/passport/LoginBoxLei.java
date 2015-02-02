package io.leopard.web4j.passport;

import io.leopard.burrow.LeopardLei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录框.
 * 
 * @author 阿海
 * 
 */
public interface LoginBoxLei extends LeopardLei {

	void showLoginBox(HttpServletRequest request, HttpServletResponse response);

}
