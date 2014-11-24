package io.leopard.web.mvc.controller.security;

import io.leopard.web4j.view.HtmlView;
import io.leopard.web4j.view.JsonView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security/csrf/")
public class CsrfSecurityTestController {

	@RequestMapping
	public HtmlView list() {
		StringBuilder sb = new StringBuilder();
		sb.append("<script type='text/javascript' src='/js/jquery-1.6.4.min.js'></script>\n");
		sb.append("<script type='text/javascript' src='/js/csrf.js'></script>\n");
		sb.append("<script type='text/javascript' src='/js/csrf_test.js'></script>\n");
		// sb.append("<script type='text/javascript'>\n");
		// sb.append("Csrf.get(\"/security/data.do\");\n");
		// sb.append("</script>\n");
		
		sb.append("<div id=token></div>");

		return new HtmlView(sb.toString());
	}

	@RequestMapping
	public JsonView data() {
		return new JsonView("ok2");
	}

}
