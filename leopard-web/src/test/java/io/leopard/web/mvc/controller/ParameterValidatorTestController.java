package io.leopard.web.mvc.controller;

import io.leopard.web4j.view.HtmlView;
import io.leopard.web4j.view.JsonView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validator/")
public class ParameterValidatorTestController {

	@RequestMapping
	public JsonView gameId2(String gameId) {
		return new JsonView(gameId);
	}

	@RequestMapping
	public HtmlView gameId(String gameId) {
		System.err.println("gameId:" + gameId);
		return new HtmlView("gameId:" + gameId);
	}
}
