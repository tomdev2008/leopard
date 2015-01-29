package io.leopard.web4j.parameter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

/**
 * 获取cookie中的yyuid(经过UDB登录验证)
 * 
 * @author 阿海
 * 
 */

@Service
public class CookieLoginedYyuidPageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		throw new NotImplementedException();
	}

	@Override
	public String getKey() {
		return "cookieLoginedYyuid";
	}

}
