package io.leopard.test.mock;

import io.leopard.test4j.mock.DefaultParameter;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;

/**
 * mock测试(用于service、handler、controller、特殊DAO等spring bean的测试).
 * 
 * @author 阿海
 * 
 */
@RunWith(LeopardMockRunner.class)
@PowerMockIgnore({ "org.apache.log4j.*" })
public class MockTests implements DefaultParameter {

}
