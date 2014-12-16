package io.leopard.core.context;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextImpl {
	protected Log logger = LogFactory.getLog(this.getClass());
	private Log beanLogger = LogFactory.getLog("BEANLOG." + this.getClass().getName());

	@PostConstruct
	public void init() {
		beanLogger.info("init");
		// try {
		// this.checkDaoName();
		// }
		// catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// }
		// catch (IllegalAccessException e) {
		// e.printStackTrace();
		// }
	}

	@PreDestroy
	public void destroy() {
		beanLogger.info("destroy");
	}
	//
	// protected void checkDaoName(Object bean, Field field) throws IllegalArgumentException, IllegalAccessException {
	// String fieldName = field.getName();
	// if (fieldName.indexOf("Dao") == -1) {
	// return;
	// }
	// if (fieldName.endsWith("Dao")) {
	// // 忽略以DAO结尾的Bean
	// return;
	// }
	// field.setAccessible(true);
	//
	// Object obj = field.get(bean);
	//
	// if (obj == null) {
	// throw new NullPointerException("field[" + this.getClass().getSimpleName() + "." + fieldName + "]未初始化.");
	// }
	// field.setAccessible(false);
	//
	// String className = obj.getClass().getSimpleName();
	//
	// if ("QueueDaoRedisImpl".equals(className)) {
	// return;
	// }
	//
	// if (!className.equalsIgnoreCase(fieldName)) {
	// if (className.endsWith("MockImpl")) {
	// return;
	// }
	// if (className.startsWith("$Proxy")) {
	// return;
	// }
	//
	// String currentClassName = this.getClass().getName();
	//
	// throw new RuntimeException("属性[" + currentClassName + "." + fieldName + "]自动注入错了[" + className + "]");
	// }
	// }
	//
	// protected void checkDaoName() throws IllegalArgumentException, IllegalAccessException {
	// Field[] fields = this.getClass().getDeclaredFields();
	// for (Field field : fields) {
	// checkDaoName(this, field);
	// }
	//
	// }
}
