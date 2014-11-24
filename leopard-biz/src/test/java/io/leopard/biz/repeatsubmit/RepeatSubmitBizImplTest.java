package io.leopard.biz.repeatsubmit;

import io.leopard.core.exception.other.RepeatSubmitException;
import io.leopard.data4j.memcache.MemcacheMemoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class RepeatSubmitBizImplTest {

	protected RepeatSubmitBizImpl repeatSubmitBizImpl = new RepeatSubmitBizImpl(new MemcacheMemoryImpl());

	@Test
	public void add() {
		RepeatSubmit repeatSubmit = new RepeatSubmit();
		repeatSubmit.setMd5("md5");
		repeatSubmitBizImpl.add(repeatSubmit);

		repeatSubmitBizImpl.checkSubmit("md5-2");

		try {
			repeatSubmitBizImpl.checkSubmit("md5");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RepeatSubmitException e) {

		}

	}

}