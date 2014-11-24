package io.leopard.ext.queue;

public interface Queue<BEAN> {

	void setSyncExecute(boolean syncExecute);

	/**
	 * 是否同步执行.
	 * 
	 * @return
	 */
	boolean isSyncExecute();

	int add(BEAN bean);

	int count();

	String pop();

	BEAN execute();
}
