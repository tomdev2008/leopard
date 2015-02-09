package io.leopard.web4j.trynb.resolver;

import io.leopard.web4j.trynb.model.ErrorPage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public class TrynbResolverImpl implements TrynbResolver {

	private List<TrynbResolver> list = new ArrayList<TrynbResolver>();

	public TrynbResolverImpl() {
		this.list.add(new JsonViewTrynbResolver());
		this.list.add(new WsViewTrynbResolver());
		this.list.add(new OkTextViewTrynbResolver());
	}

	@Override
	public ModelAndView resolveView(HttpServletRequest request, String uri, Exception exception, ErrorPage errorPage, Class<?> returnType) {
		ModelAndView view;
		for (TrynbResolver resolver : list) {
			view = resolver.resolveView(request, uri, exception, errorPage, returnType);
			if (view != null) {
				return view;
			}
		}
		return null;
	}

}
