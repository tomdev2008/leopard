package io.leopard.test.mock.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

/**
 * 
 * @author 阿海
 * 
 */
public class Proxy {

	@SuppressWarnings("unchecked")
	public static <T> T newProxyInstance(T mock, MethodHandler methodHandler) {
		Class<?> clazz = mock.getClass();
		if (clazz.getName().indexOf("EnhancerByMockitoWithCGLIB") != -1) {
			clazz = clazz.getSuperclass();
		}
		// System.out.println("mock:" + mock.getClass().getSuperclass().getName());
		return (T) newProxyInstance(clazz, methodHandler);
	}

	public static <T> T newProxyInstance(Class<T> clazz, MethodHandler methodHandler) {
		return Proxy.newProxyInstance(clazz, new ProxyMethodHandlerImpl(methodHandler));
	}

	/**
	 * 模拟从"容器"获取管理的Bean.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newProxyInstance(Class<T> clazz, ProxyMethodHandler mh) {
		ProxyFactory f = new ProxyFactory();
		f.setSuperclass(clazz);
		f.setFilter(mh.getMethodFilter());
		Class<?> c = f.createClass(); // 创建代理类
		T proxy = null;
		try {
			proxy = (T) c.newInstance(); // 使用代理类创建实例
			// System.out.println("create:" + c.getName());
			((ProxyObject) proxy).setHandler(mh.getMethodHandler()); // 设置方法拦截器
			return proxy;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static interface ProxyMethodHandler {
		public MethodFilter getMethodFilter();

		public MethodHandler getMethodHandler();
	}

	public static class ProxyMethodHandlerImpl implements ProxyMethodHandler {

		private MethodHandler methodHandler;

		public ProxyMethodHandlerImpl(MethodHandler methodHandler) {
			this.methodHandler = methodHandler;
		}

		public ProxyMethodHandlerImpl() {
		}

		@Override
		public MethodFilter getMethodFilter() {
			return new MethodFilter() {
				public boolean isHandled(Method method) {
					// System.out.println("isHandled:" + method.toGenericString());
					if (method.getName().equals("finalize")) {
						return false;
					}
					return true;
				}
			};
		}

		@Override
		public MethodHandler getMethodHandler() {
			// System.out.println("getMethodHandler:");
			if (methodHandler == null) {
				return new MethodHandler() {
					public Object invoke(final Object proxy, Method method, final Method proceed, final Object[] args) throws Throwable {
						System.out.println("method:" + method.toGenericString());
						System.out.println("proceed:" + proceed.toGenericString());
						return proceed.invoke(proxy, args);
					}
				};
			} else {
				return methodHandler;
			}
		}
	}

}
