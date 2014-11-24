package io.leopard.web.interceptor;

import io.leopard.test.mock.Mock;
import io.leopard.test.mock.MockTests;
import io.leopard.test4j.mock.LeopardMockRunner;
import io.leopard.test4j.mock.LeopardMockito;
import io.leopard.web.security.CsrfService;
import io.leopard.web.security.DomainWhiteListConfig;
import io.leopard.web.security.RefererSecurityValidator;
import io.leopard.web.security.xss.XssUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ XssUtil.class, RefererSecurityValidator.class })
public class CsrfInterceptorTest extends MockTests {

	protected CsrfInterceptor csrfInterceptor = Mock.spy(this, CsrfInterceptor.class);
	private CsrfService csrfService;

	@Test
	public void preHandle() throws Exception {
		PowerMockito.when(XssUtil.initXSS(null, null)).thenReturn(true);
		csrfInterceptor.preHandle("/index.do", null, null, null);

		Mockito.doReturn(true).when(csrfService).isEnable();
		csrfInterceptor.preHandle("/index.do", null, null, null);
	}

	@Test
	public void init() {
		LeopardMockito.setProperty(csrfInterceptor, null, "domainWhiteListConfig");
		csrfInterceptor.init();

		DomainWhiteListConfig domainWhiteListConfig = Mockito.mock(DomainWhiteListConfig.class);
		LeopardMockito.setProperty(csrfInterceptor, domainWhiteListConfig, "domainWhiteListConfig");

		csrfInterceptor.init();

	}

}