package io.leopard.web4j.passport;

import io.leopard.web4j.servlet.RequestUtil;
import io.leopard.web4j.view.FtlView;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * UDB 登录页面.
 * 
 * @author 阿海
 * 
 */
@Component
public class LoginBoxLeiImpl implements LoginBoxLei {

	@Override
	public void showLoginBox(HttpServletRequest request, HttpServletResponse response) {
		FtlView view = new FtlView("/passport/ftl", "login");

		String url = RequestUtil.getRequestURL(request);
		String queryString = request.getQueryString();
		if (StringUtils.isNotEmpty(queryString)) {
			url += "?" + queryString;
		}
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("url", url);
		try {
			view.render(model, request, response);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

}
