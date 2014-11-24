package io.leopard.web.userinfo.service;

public interface SkipFilterService {

	boolean add(String uri);

	boolean isSkipFilter(String uri);
}
