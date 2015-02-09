package io.leopard.web4j.trynb.resolver;

import io.leopard.web4j.trynb.model.TrynbInfo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface TrynbResolver {

	ModelAndView resolveView(HttpServletRequest request, String uri, Exception exception, TrynbInfo trynbInfo, Class<?> returnType);
}
