package io.leopard.web4j.frequency;

import io.leopard.burrow.LeopardLei;

/**
 * 并发(连接数)限制，相同URI一个用户3秒只能访问1次.
 * 
 * @author 阿海
 * 
 */
public interface FrequencyLei extends LeopardLei {

	void request(String user, String uri, int seconds) throws FrequencyException;

}
