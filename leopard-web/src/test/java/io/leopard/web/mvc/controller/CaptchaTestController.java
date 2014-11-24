package io.leopard.web.mvc.controller;

import io.leopard.web4j.captcha.CaptchaGroup;
import io.leopard.web4j.captcha.CaptchaView;
import io.leopard.web4j.view.HtmlView;
import io.leopard.web4j.view.TextView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/captcha")
public class CaptchaTestController {
	// http://localhost/captcha/index.do

	@RequestMapping
	public HtmlView index() {
		StringBuilder sb = new StringBuilder();
		sb.append("<img src=show.do/><br/>200x70<br/>");
		sb.append("<img src=show2.do/><br/>100x50<br/>");
		sb.append("<img src=show3.do/><br/>100x50<br/>");
		return new HtmlView(sb.toString());
	}

	@RequestMapping
	public CaptchaView show() {
		// if (true) {
		throw new RuntimeException("err");
		// }
		// return new CaptchaView();
	}

	@RequestMapping
	public TextView get(String sessCaptcha) {
		return new TextView(sessCaptcha);
	}

	@RequestMapping
	@CaptchaGroup("captcha2")
	public CaptchaView show2() {
		return new CaptchaView(100, 50);
	}

	@RequestMapping
	@CaptchaGroup("captcha2")
	public TextView get2(String sessCaptcha) {
		return new TextView(sessCaptcha);
	}

	@RequestMapping
	@CaptchaGroup
	public CaptchaView show3() {
		return new CaptchaView(100, 50);
	}

	@RequestMapping
	@CaptchaGroup
	public TextView get3(String sessCaptcha) {
		return new TextView(sessCaptcha);
	}

	@RequestMapping
	@CaptchaGroup("captcha1")
	public TextView check(String captcha, String sessCaptcha) {
		if (sessCaptcha.equals(captcha)) {
			throw new IllegalArgumentException("您提交的验证码不正确[" + captcha + "." + sessCaptcha + "].");
		}
		return new TextView("用户提交的验证码:" + captcha + " session中的验证码:" + sessCaptcha);
	}
}
