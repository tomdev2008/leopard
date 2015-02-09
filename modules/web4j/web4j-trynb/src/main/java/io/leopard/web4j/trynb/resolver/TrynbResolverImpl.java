package io.leopard.web4j.trynb.resolver;

import io.leopard.web4j.trynb.model.TrynbInfo;

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
	public ModelAndView resolveView(HttpServletRequest request, String uri, Exception exception, TrynbInfo trynbInfo, Class<?> returnType) {
		ModelAndView view;
		for (TrynbResolver resolver : list) {
			view = resolver.resolveView(request, uri, exception, trynbInfo, returnType);
			if (view != null) {
				return view;
			}
		}
		return null;
	}

}
