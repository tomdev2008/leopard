package io.leopard.web4j.trynb;

import java.util.ArrayList;
import java.util.List;

public class ErrorPageDaoImpl implements ErrorPageDao {

	private List<ErrorConfig> errorPageList = new ArrayList<ErrorConfig>();

	@Override
	public boolean add(ErrorConfig errorConfig) {
		this.errorPageList.add(errorConfig);
		return true;
	}

	@Override
	public ErrorConfig findErrorInfo(String url) {
		for (ErrorConfig error : errorPageList) {
			String prefix = error.getUrl();
			if (url.startsWith(prefix)) {
				return error;
			}
		}
		return null;
	}
}
