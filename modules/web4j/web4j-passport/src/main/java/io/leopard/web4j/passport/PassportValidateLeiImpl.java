package io.leopard.web4j.passport;

import io.leopard.burrow.DefaultBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
@DefaultBean
public class PassportValidateLeiImpl implements PassportValidateLei {

	@Override
	public PassportUser validate(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showLoginBox(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
