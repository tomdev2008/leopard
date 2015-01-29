package io.leopard.web4j.trynb;

import io.leopard.core.exception.ConnectionLimitException;
import io.leopard.core.exception.other.NotLoginException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class ErrorPageServiceImpl implements ErrorPageService {

	protected Log logger = LogFactory.getLog(ErrorPageServiceImpl.class);

	private final ErrorPageDao errorPageDao = new ErrorPageDaoImpl();

	private final List<ExceptionConfig> defaultConfigList = new ArrayList<ExceptionConfig>();

	public ErrorPageServiceImpl() {
		addDefaultConfig(ConnectionLimitException.class, "warn", "ConnectionLimitException");
		// addDefaultConfig(RefererInvalidException.class, "warn", "RefererInvalidException");//FIXME ahai 代码被注释了
		// addDefaultConfig(AdminIpForbiddenRuntimeException.class, "warn", "AdminIpForbiddenRuntimeException");//FIXME ahai 代码被注释了
		addDefaultConfig(NotLoginException.class, "warn", "NotLoginException");
		// addDefaultConfig(RefererInvalidException.class, "warn", 400);//暂时不启用
	}

	// RefererInvalidException:

	protected void addDefaultConfig(Class<?> clazz, String log, String statusCode) {
		ExceptionConfig config = new ExceptionConfig();
		config.setType(clazz.getName());
		config.setLog(log);
		config.setStatusCode(statusCode);
		defaultConfigList.add(config);
	}

	@Override
	public boolean add(ErrorConfig errorConfig) {
		// ConnectionLimitException
		return errorPageDao.add(errorConfig);
	}

	@Override
	public ErrorConfig findErrorInfo(String url) {
		return errorPageDao.findErrorInfo(url);
	}

	@Override
	public ErrorPage parseErrorPage(HttpServletRequest request, String uri, Exception exception) {
		ExceptionConfig exceptionConfig = this.parseExceptionConfig(uri, exception);

		String message;
		if (exceptionConfig == null || StringUtils.isEmpty(exceptionConfig.getMessage())) {
			message = ErrorUtil.parseMessage(exception);
		}
		else {
			message = exceptionConfig.getMessage();
		}

		String statusCode = this.parseStatusCode(exceptionConfig, request, uri, exception);

		ErrorPage errorPage = new ErrorPage();
		errorPage.setMessage(message);
		errorPage.setStatusCode(statusCode);
		return errorPage;
	}

	protected String parseStatusCode(ExceptionConfig exceptionConfig, HttpServletRequest request, String uri, Exception exception) {
		if (exceptionConfig == null) {
			// logger.error("匹配[" + uri + "." + exception.getClass().getName() +
			// "]不到exception配置");
			this.error(request, uri, exception);
			return exception.getClass().getSimpleName();
		}
		String logType = ExceptionConfig.getType(exceptionConfig.getLog());
		if ("error".equals(logType)) {
			this.error(request, uri, exception);
		}
		else if ("warn".equals(logType)) {
			if (exception instanceof NotLoginException) {
				String cookie = request.getHeader("Cookie");
				logger.warn("Cookie:" + cookie);
				logger.warn("uri:" + uri + " message:" + exception.getMessage(), exception);
			}
			else {
				logger.warn("uri:" + uri + " message:" + exception.getMessage());
			}
		}
		else if ("info".equals(logType)) {
			logger.info("uri:" + uri + " message:" + exception.getMessage());
		}
		else if ("debug".equals(logType)) {
			logger.debug("uri:" + uri + " message:" + exception.getMessage());
		}
		if (StringUtils.isEmpty(exceptionConfig.getStatusCode())) {
			return exception.getClass().getSimpleName();
		}
		return exceptionConfig.getStatusCode();
	}

	protected ExceptionConfig parseExceptionConfig(String uri, Exception exception) {
		ErrorConfig errorConfig = this.findErrorInfo(uri);
		List<ExceptionConfig> exceptionConfigList = errorConfig.getExceptionConfigList();
		String exceptionClassName = exception.getClass().getName();

		for (ExceptionConfig exceptionConfig : exceptionConfigList) {
			boolean match = ErrorPageUtil.match(exceptionConfig.getType(), exceptionClassName);
			if (match) {
				return exceptionConfig;
			}
		}
		for (ExceptionConfig exceptionConfig : defaultConfigList) {
			boolean match = ErrorPageUtil.match(exceptionConfig.getType(), exceptionClassName);
			if (match) {
				return exceptionConfig;
			}
		}
		return null;
	}

	protected void error(HttpServletRequest request, String uri, Exception exception) {
		String clientInfo = ErrorUtil.getClientInfo(request, uri, exception.getMessage());
		logger.error(clientInfo, exception);
	}
}
