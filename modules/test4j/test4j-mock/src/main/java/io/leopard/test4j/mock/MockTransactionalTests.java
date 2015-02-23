package io.leopard.test4j.mock;

import io.leopard.autounit.AutoUnit;

import java.lang.reflect.Field;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * mock事务测试(用于DAO层的Jdbc、Memcache、Redis、MemDB等实现类的测试).
 * 
 * @author 阿海
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/mock/applicationContext-tx.xml", inheritLocations = false)
// @ActiveProfiles(value = EnvUtil.ENV_DEV, inheritProfiles = false)
public class MockTransactionalTests extends AbstractTransactionalJUnit4SpringContextTests implements DefaultParameter {

	protected Log logger = LogFactory.getLog(this.getClass());

	static {
		// MockTransactionModule.getInstance(H2ServiceImpl.class).importDatabase();
	}

	public MockTransactionalTests() {
		try {
			this.inject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void inject() throws IllegalArgumentException, IllegalAccessException {
		String classSimpleName = this.getClass().getSimpleName();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Class<?> type = field.getType();
			if (classSimpleName.startsWith(type.getSimpleName())) {
				// System.out.println("classSimpleName:" + classSimpleName +
				// " type:" + type.getSimpleName());
				Object value = this.mock(type);
				field.setAccessible(true);
				field.set(this, value);
			}
		}
	}

	protected <T> T mock(Class<T> clazz) {
		// String beanName = StringUtil.firstCharToLowerCase(clazz.getName());
		// @SuppressWarnings("unchecked")
		// T mock = DaoInstanceUtil.newInstance(beanName, clazz);
		T mock = AutoUnit.mock(clazz);
		mock = Mockito.spy(mock);

		// System.out.println("mock:" + mock);
		MockTransactionalSpy.copyToTestObject(this, mock); // 将mock属性复制到测试类.
		return mock;
	}

	public void setDataSource(DataSource dataSource) {
	}
}
