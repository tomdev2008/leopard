package io.leopard.core.exception.other;

import io.leopard.core.exception.LeopardRuntimeException;

/**
 * 重复提交.
 * 
 * @author 阿海
 * 
 */
public class RepeatSubmitException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public RepeatSubmitException(String message) {
		super(message);
	}

}
