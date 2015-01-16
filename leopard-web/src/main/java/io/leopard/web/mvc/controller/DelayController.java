package io.leopard.web.mvc.controller;

import io.leopard.data4j.env.EnvUtil;
import io.leopard.web.interceptor.PageDelayInterceptor;
import io.leopard.web4j.view.FtlView;
import io.leopard.web4j.view.RedirectView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 接口延迟返回.
 * 
 * @author 阿海
 * 
 */
@Controller
public class DelayController {

	@Autowired
	private PageDelayInterceptor pageDelayInterceptor;

	protected void checkEnv() {
		if (!EnvUtil.isDevEnv()) {
			throw new RuntimeException("开发环境才允许设置页面延迟响应.");
		}
	}

	@RequestMapping(value = "/leopard/delay.do")
	public FtlView delay() {
		this.checkEnv();
		boolean close = !pageDelayInterceptor.isDelayOn();
		FtlView view = new FtlView("/leopard/ftl/", "delay");
		view.addObject("close", close);
		return view;
	}

	@RequestMapping(value = "/leopard/delayConfig.do")
	public RedirectView config(boolean on) {
		this.checkEnv();
		pageDelayInterceptor.setDelayOn(on);
		return new RedirectView("/leopard/delay.do");
	}
}
