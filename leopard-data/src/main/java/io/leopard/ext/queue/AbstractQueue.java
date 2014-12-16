package io.leopard.ext.queue;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.lang.Json;
import io.leopard.commons.utility.GenericUtil;
import io.leopard.commons.utility.NumberUtil;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractQueue<BEAN> extends ContextImpl implements Queue<BEAN> {

	// 是否同步执行.
	private static final ThreadLocal<Boolean> syncExecuter = new ThreadLocal<Boolean>();

	private Class<?> beanClazz;

	public AbstractQueue() {
		this.beanClazz = (Class<?>) GenericUtil.getActualTypeArguments(this)[0];
	}

	public void setSyncExecute(boolean syncExecute) {
		syncExecuter.set(syncExecute);
	}

	/**
	 * 是否同步执行.
	 * 
	 * @return
	 */
	public boolean isSyncExecute() {
		Boolean status = syncExecuter.get();
		return NumberUtil.toBool(status);
	}

	@SuppressWarnings("unchecked")
	protected BEAN toBean(String json) {
		return (BEAN) Json.toObject(json, this.beanClazz);
	}

	public abstract BEAN execute(String json, BEAN bean);

	@Override
	public BEAN execute() {
		String json = this.pop();
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		BEAN bean = this.toBean(json);
		return execute(json, bean);
	}
}
