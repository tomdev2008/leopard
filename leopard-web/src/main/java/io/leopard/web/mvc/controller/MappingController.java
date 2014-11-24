package io.leopard.web.mvc.controller;

import io.leopard.web4j.view.TextView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mapping")
public class MappingController {

	@RequestMapping
	public TextView index() {
		System.err.println("index.do");
		String message = "Hello Mapping";
		return new TextView(message);
	}

	@RequestMapping
	public TextView welcome() {
		System.err.println("welcome.do");
		String message = "Welcome Mapping";
		return new TextView(message);
	}
}
