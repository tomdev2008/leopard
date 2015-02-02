package io.leopard.web.mvc;

import io.leopard.web.userinfo.SkipFilterService;
import io.leopard.web4j.captcha.CaptchaGroup;
import io.leopard.web4j.method.HandlerMethodRegisterLei;
import io.leopard.web4j.nobug.annotation.NoXss;
import io.leopard.web4j.nobug.annotation.SkipFilter;
import io.leopard.web4j.nobug.xss.XssAttributeData;
import io.leopard.web4j.passport.PassportInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class LeopardHandlerMapping extends RequestMappingHandlerMapping {

	@Autowired(required = false)
	private HandlerMethodRegisterLei monitorHandlerMethodRegisterLei;

	@Override
	protected void initApplicationContext(ApplicationContext context) throws BeansException {
		// 要注意顺序
		List<String> names = new ArrayList<String>();
		names.add("proxyInterceptor");
		names.add("skipFilterInterceptor");
		names.add("timeInterceptor");
		names.add("timeLogInterceptor");
		names.add("passportInterceptor");
		names.add("pageDelayInterceptor");
		names.add("csrfInterceptor");
		names.add("monitorPermissionInterceptor");
		names.add("webservicePermissionInterceptor");
		names.add("frequencyInterceptor");

		List<HandlerInterceptor> list = new ArrayList<HandlerInterceptor>();
		for (String name : names) {
			try {
				HandlerInterceptor bean = context.getBean(name, HandlerInterceptor.class);
				list.add(bean);
			}
			catch (NoSuchBeanDefinitionException e) {
				System.err.println("NoSuchBeanDefinitionException:" + name);
			}
		}

		Object[] interceptors = new Object[list.size()];
		list.toArray(interceptors);

		// System.err.println("checkLoginInterceptor:" + Arrays.asList(interceptors));
		this.setInterceptors(interceptors);

		super.initApplicationContext();
	}

	@Autowired
	private SkipFilterService skipFilterService;

	@Autowired(required = false)
	private PassportInterceptor passportInterceptor;

	/**
	 * 验证码分组
	 */
	@Override
	protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
		HandlerMethod handlerMethod = super.getHandlerInternal(request);
		if (handlerMethod != null) {
			// TODO ahai 每个URL都要做判断是否太浪费？如何解决？
			CaptchaGroup captchaGroup = handlerMethod.getMethod().getAnnotation(CaptchaGroup.class);
			if (captchaGroup != null) {
				String groupId = captchaGroup.value();
				request.setAttribute("captchaGroupId", groupId);
			}
		}
		return handlerMethod;
	}

	private final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	@Override
	protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
		super.registerHandlerMethod(handler, method, mapping);
		HandlerMethod handlerMethod = this.getHandlerMethods().get(mapping);

		// 按接口监控方法耗时配置
		String uri = mapping.getPatternsCondition().getPatterns().iterator().next();
		uri = uri.replaceAll("/+", "/");

		{
			// @SkipFilter
			SkipFilter skipFilter = handlerMethod.getMethodAnnotation(SkipFilter.class);
			if (skipFilter != null) {
				skipFilterService.add(uri);
			}
		}

		if (monitorHandlerMethodRegisterLei != null) {
			monitorHandlerMethodRegisterLei.registerHandlerMethod(handlerMethod, method, mapping, uri);
		}

		MethodParameter[] params = handlerMethod.getMethodParameters();
		for (MethodParameter param : params) {
			param.initParameterNameDiscovery(parameterNameDiscoverer);

			NoXss noXss = param.getParameterAnnotation(NoXss.class);
			if (noXss == null) {
				continue;
			}
			String paramName = param.getParameterName();
			XssAttributeData.add(uri, paramName);
		}

		if (passportInterceptor != null) {
			passportInterceptor.registerHandlerMethod(handlerMethod);
		}
	}

	// protected void checkRequestMapping(String uri, Method method) {
	// Class<?> clazz = method.getDeclaringClass();
	// RequestMapping requestMapping =
	// clazz.getAnnotation(RequestMapping.class);
	// if (requestMapping != null) {
	// String[] values = requestMapping.value();
	// String value = values[0];
	// if (value.endsWith("/")) {
	// throw new IllegalArgumentException(clazz.getName() +
	// " 的@RequestMapping.value不能以/结尾.");
	// }
	// }
	// // String message = clazz.getName() + "." + method.getName();
	// throw new IllegalArgumentException("uri不符合规则[" + uri + "]clazz:" +
	// clazz.getName());
	// }

	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = null;
		RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		if (methodAnnotation != null) {
			RequestCondition<?> methodCondition = getCustomMethodCondition(method);
			info = createRequestMappingInfo(methodAnnotation, methodCondition, method);
			RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
			if (typeAnnotation != null) {
				RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
				info = createRequestMappingInfo(typeAnnotation, typeCondition, null).combine(info);
			}
		}
		return info;
	}

	/**
	 * Created a RequestMappingInfo from a RequestMapping annotation.
	 */
	protected RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition, Method method) {
		String[] patterns;
		if (method != null && annotation.value().length == 0) {
			patterns = new String[] { this.createPattern(method.getName()) };
		}
		else {
			patterns = resolveEmbeddedValuesInPatterns(annotation.value());
		}
		return new RequestMappingInfo(new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), false, this.useTrailingSlashMatch(), this.getFileExtensions()),
				new RequestMethodsRequestCondition(annotation.method()), new ParamsRequestCondition(annotation.params()), new HeadersRequestCondition(annotation.headers()),
				new ConsumesRequestCondition(annotation.consumes(), annotation.headers()), new ProducesRequestCondition(annotation.produces(), annotation.headers(), getContentNegotiationManager()),
				customCondition);
	}

	protected String createPattern(String methodName) {
		return methodName + ".do";
	}
}
