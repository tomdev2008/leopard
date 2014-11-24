package io.leopard.web.mvc.controller;

import io.leopard.web4j.view.DownloadView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/download/")
public class DownloadTestController {

	
	@RequestMapping
	public DownloadView img() {
		return new DownloadView("/tmp/test.jpg", "test.jpg");
	}

	@RequestMapping
	public DownloadView list() {
		String content = "ok";
		InputStream input = new ByteArrayInputStream(content.getBytes());
		return new DownloadView(input, "test.txt");
	}
}
