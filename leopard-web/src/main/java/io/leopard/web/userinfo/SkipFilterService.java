package io.leopard.web.userinfo;

public interface SkipFilterService {

	boolean add(String uri);

	boolean isSkipFilter(String uri);
}
