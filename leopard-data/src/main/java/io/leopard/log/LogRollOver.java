package io.leopard.log;

public interface LogRollOver {

	String getFilename();

	void autoRollOver() throws Exception;
}
