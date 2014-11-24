package io.leopard.web.userinfo.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class SkipFilterServiceImpl implements SkipFilterService {

	private Map<String, String> map = new ConcurrentHashMap<String, String>();

	@Override
	public boolean add(String uri) {
		map.put(uri, "");
		return true;
	}

	@Override
	public boolean isSkipFilter(String uri) {
		return map.containsKey(uri);
	}

}
