package io.leopard.web;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class LeopardViewResolver implements ViewResolver {

	// <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	// <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
	// <property name="prefix" value="/WEB-INF/jsp/" />
	// <property name="suffix" value=".jsp" />
	// <property name="contentType" value="text/html; charset=UTF-8" />
	// </bean>

	// <bean id="viewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
	// <property name="order" value="1" />
	// <property name="cache" value="false" />
	// <property name="suffix" value=".ftl" />
	// <property name="exposeSpringMacroHelpers" value="true" />
	// <property name="contentType" value="text/html;charset=UTF-8"></property>
	// </bean>

	@Value("${leopard.view.type}")
	private String type;

	private ViewResolver viewResolver;

	@PostConstruct
	public void init() {
		// System.out.println("LeopardViewResolver type:" + type);
		if ("${leopard.view.type}".equals(type)) {
			type = null;
		}
		if ("jsp".equalsIgnoreCase(type)) {
			this.viewResolver = this.createInternalResourceViewResolver();
		}
		else {
			this.viewResolver = this.createFreeMarkerViewResolver();
		}
	}

	protected InternalResourceViewResolver createInternalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setContentType("text/html;charset=UTF-8");
		return viewResolver;
	}

	protected FreeMarkerViewResolver createFreeMarkerViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(false);
		viewResolver.setSuffix(".ftl");
		viewResolver.setExposeSpringMacroHelpers(true);
		viewResolver.setContentType("text/html;charset=UTF-8");
		return viewResolver;
	}

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return viewResolver.resolveViewName(viewName, locale);
	}

}
