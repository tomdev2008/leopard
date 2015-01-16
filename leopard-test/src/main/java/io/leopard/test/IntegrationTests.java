package io.leopard.test;

import io.leopard.data4j.env.EnvUtil;
import io.leopard.test.internal.TestContextLoader;
import io.leopard.test4j.mock.DefaultParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 集成测试基础类(用于开发阶段测试完整流程).
 * 
 * @author 阿海
 * 
 */
@ContextConfiguration(loader = TestContextLoader.class)
@ActiveProfiles(value = EnvUtil.ENV_DEV, inheritProfiles = false)
@RunWith(LeopardJUnit4ClassRunner.class)
public class IntegrationTests extends AbstractJUnit4SpringContextTests implements DefaultParameter {

	protected Log logger = LogFactory.getLog(this.getClass());

	// @BeforeClass
	// public static void before() {
	// DnsConfig.initHosts();
	// }
}