package io.leopard.util;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ViewUtil {
	/**
	 * 获取ModelAndView</br> view等于dir/方法名
	 * 
	 * @param dir
	 *            路径
	 * @return ModelAndView
	 */
	public static ModelAndView getView(String dir) {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		String methodName = stacks[2].getMethodName();
		ModelAndView model = new ModelAndView(dir + "/" + methodName);
		return model;
	}

	/**
	 * 获取ModelAndView</br> 将message置入model
	 * 
	 * @return ModelAndView
	 */
	public static ModelAndView getTextView(String message) {
		ModelAndView model = new ModelAndView("message");
		model.addObject("message", message);
		return model;
	}

	/**
	 * 获取ModelAndView</br> 将message置入model
	 * 
	 * @return ModelAndView
	 */
	public static ModelAndView getMessageModelAndView(String message) {
		ModelAndView model = new ModelAndView("message");
		model.addObject("message", message);
		return model;
	}

	/**
	 * 获取ModelAndView</br>
	 * 
	 * @return ModelAndView
	 */
	public static ModelAndView getMessageModelAndView() {
		ModelAndView model = new ModelAndView("message");
		return model;
	}

	/**
	 * 返回转入url的ModelAndView
	 * 
	 * @param url
	 * @return ModelAndView
	 */
	@Deprecated
	public static ModelAndView getRedirectModelAndView(String url) {
		return new ModelAndView(new RedirectView(url));
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> list(ModelAndView model, String fieldName, Class<T> clazz) {
		Object obj = model.getModel().get(fieldName);
		if (obj == null) {
			return null;
		}
		return (List<T>) obj;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(ModelAndView model, String fieldName, Class<T> clazz) {
		Object obj = model.getModel().get(fieldName);
		if (obj == null) {
			return null;
		}
		return (T) obj;
	}

	public static Integer getInteger(ModelAndView model, String fieldName) {
		Object obj = model.getModel().get(fieldName);
		if (obj == null) {
			return null;
		}
		return (Integer) obj;
	}

	public static Object getObject(ModelAndView model, String fieldName) {
		if (model == null) {
			return null;
		}
		Map<String, Object> map = model.getModel();
		if (map == null) {
			return null;
		}
		return map.get(fieldName);
	}

	public static String getString(ModelAndView model, String fieldName) {
		Object obj = getObject(model, fieldName);
		if (obj == null) {
			return null;
		}
		return (String) obj;
	}
	// public static ModelAndView getRefererModelAndView(String defaultUrl) {
	// return new ModelAndView(new RedirectView(defaultUrl));
	// }
}
