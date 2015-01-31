package io.leopard.burrow.timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定时器.
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractTimer implements Timer {
	protected final Log logger = LogFactory.getLog("TIMERLOG." + this.getClass().getName());

	@Override
	public int getThreadCount() {
		return 1;
	}

}
