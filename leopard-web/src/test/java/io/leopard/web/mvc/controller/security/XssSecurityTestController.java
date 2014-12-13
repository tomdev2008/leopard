package io.leopard.web.mvc.controller.security;

import io.leopard.burrow.lang.Json;
import io.leopard.commons.utility.StringUtil;
import io.leopard.web4j.nobug.annotation.NoXss;
import io.leopard.web4j.view.HtmlView;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/security/xss/")
public class XssSecurityTestController {

	@RequestMapping
	public HtmlView list() {
		StringBuilder sb = new StringBuilder();

		{
			String title = "&#60;&#115;&#99;&#114;&#105;&#112;&#116;&#62;&#97;&#108;&#101;&#114;&#116;&#40;&#39;&#111;&#107;&#39;&#41;&#59;&#60;&#47;&#115;&#99;&#114;&#105;&#112;&#116;&#62;";
			sb.append("<a href='" + this.getUrl(title) + "' target='_blank'>" + this.getUrl(title) + "</a><br/>\n");
		}

		{
			String title = "&#x003c;&#x0073;&#x0063;&#x0072;&#x0069;&#x0070;&#x0074;&#x003e;&#x0061;&#x006c;&#x0065;&#x0072;&#x0074;&#x0028;&#x0027;&#x006f;&#x006b;&#x0027;&#x0029;&#x003b;&#x003c;&#x002f;&#x0073;&#x0063;&#x0072;&#x0069;&#x0070;&#x0074;&#x003e;";
			sb.append("<a href='" + this.getUrl(title) + "' target='_blank'>" + this.getUrl(title) + "</a><br/>\n");
		}
		return new HtmlView(sb.toString());
	}

	protected String getUrl(String title) {
		String url = "http://leopard.io/security/xss/jsp.do?title=" + StringUtil.urlEncode(title);
		return url;
	}

	@RequestMapping
	public ModelAndView jsp(String title, @NoXss String content, String[] id, User user) {
		ModelAndView model = new ModelAndView("security/xss/jsp");
		model.addObject("title", title + " id:" + StringUtils.join(id, ", ") + " user:" + Json.toJson(user));

		model.addObject("content", content);
		return model;
	}

	@RequestMapping
	public HtmlView encodeUrl(String url) {
		return new HtmlView("url:" + url);
	}
}
