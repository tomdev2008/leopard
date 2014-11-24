package io.leopard.web.mvc.controller;

import io.leopard.burrow.lang.datatype.Month;
import io.leopard.burrow.lang.datatype.OnlyDate;
import io.leopard.commons.utility.Resin;
import io.leopard.core.exception.StatusCodeException;
import io.leopard.core.exception.invalid.UsernameInvalidException;
import io.leopard.core.exception.notfound.UserNotFoundException;
import io.leopard.web.mvc.method.Catch;
import io.leopard.web4j.captcha.CaptchaView;
import io.leopard.web4j.passport.SessionUtil;
import io.leopard.web4j.servlet.CookieUtil;
import io.leopard.web4j.view.JsonView;
import io.leopard.web4j.view.PagingJsonView;
import io.leopard.web4j.view.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@SuppressWarnings("deprecation")
@Controller
public class ParameterController {
	@RequestMapping(value = "/test/mapping.do")
	public TextView mapping() {
		System.err.println("mapping.do");
		String message = "mapping";
		return new TextView(message);
	}

	@RequestMapping("/test/parameter.do")
	public TextView parameter(String requestUri, String userAgent, boolean check, int pageId, String sessionId) {
		String message = "";
		message += "pageId:" + pageId + "\n";
		message += "userAgent:" + userAgent + "\n";
		message += "requestUri:" + requestUri + "\n";
		message += "check:" + check + "\n";
		message += "sessionId:" + sessionId + "\n";
		return new TextView(message);
	}

	// @RequestMapping(value = "/test/jsonp.do")
	// public JsonpView jsonp(String requestUri, String userAgent, int pageId) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("requestUri", requestUri);
	// map.put("userAgent", userAgent);
	// map.put("pageId", pageId);
	// // map.put("username", "hctan");
	// // map.put("nickname", "ahai");
	// return new JsonpView(map);
	// }

	@RequestMapping(value = "/test/json.do")
	@Catch(code598 = UsernameInvalidException.class, code404 = UserNotFoundException.class)
	public JsonView json(String requestUri, String userAgent, int pageId, Boolean exception) {
		if (exception) {
			throw new StatusCodeException("-100", "异常测试.", "异常测试.");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("requestUri", requestUri);
		map.put("userAgent", userAgent);
		map.put("pageId", pageId);
		map.put("time", new Date());
		// map.put("username", "hctan");
		// map.put("nickname", "ahai");
		return new JsonView(map);
	}

	@RequestMapping(value = "/test/pagingJson.do")
	public PagingJsonView pagingJson(String requestUri, String userAgent, int pageId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("requestUri", requestUri);
		map.put("userAgent", userAgent);
		map.put("pageId", pageId);
		// map.put("username", "hctan:" + pageId);
		// map.put("nickname", "ahai");
		PagingJsonView model = new PagingJsonView(pageId, 10);
		model.setData(map, 100);
		return model;
	}

	@RequestMapping(value = "/test/integer.do")
	public TextView integer(Integer cid, int pageid) {
		String message = "cid:" + cid + " pageid:" + pageid;
		return new TextView(message);
	}

	@RequestMapping(value = "/test/int.do")
	public TextView int2(int cid, int pageid) {
		String message = "cid:" + cid + " pageid:" + pageid;
		return new TextView(message);
	}

	@RequestMapping(value = "/test/datatype.do")
	public TextView datatype(OnlyDate onlyDate, Month month) {
		String message = "onlyDate:" + onlyDate.toString();
		message += "\nmonth:" + month.toString();
		return new TextView(message);
	}

	@RequestMapping(value = "/test/cookie.do")
	public TextView cookie(String cookieLoginedUsername) {
		String message = "";
		message += "cookieLoginedUsername:" + cookieLoginedUsername + "\n";
		return new TextView(message);
	}

	@RequestMapping(value = "/test/captcha.do")
	public CaptchaView cookie() {

		return new CaptchaView();
	}

	@RequestMapping(value = "/test/session.do")
	public TextView session(HttpServletRequest request) {
		String sid = CookieUtil.getCookie(SessionUtil.SESSIONID_COOKIE_NAME, request);
		String memcacheValue = null;
		// if (SessionServiceImpl.memcache != null) {
		// memcacheValue = SessionServiceImpl.memcache.get("sid:" + sid);
		// }
		String sessUsername = (String) request.getSession().getAttribute("sessUsername");
		String message = "";
		message += "sid:" + sid + "\n";
		message += "memcacheValue:" + memcacheValue + "\n";
		message += "sessUsername:" + sessUsername + "\n";
		return new TextView(message);
	}

	@RequestMapping(value = "/test/webserver.do")
	public TextView webserver(HttpServletRequest request) {
		boolean isResin = Resin.isResin();
		return new TextView(isResin + "");
	}
}
