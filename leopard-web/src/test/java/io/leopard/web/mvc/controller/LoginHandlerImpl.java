package io.leopard.web.mvc.controller;

import io.leopard.web.userinfo.service.ConfigHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class LoginHandlerImpl extends ConfigHandler {

	@Override
	public List<String> getExcludeUris() {
		List<String> list = new ArrayList<String>();
		list.add("/captcha/");
		list.add("/upload/");
		list.add("/download/");
		
		list.add("/security/");
		list.add("/security2/");
		list.add("/validator/");
		return list;
	}
}
