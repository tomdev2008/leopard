package io.leopard.web.userinfo;

import io.leopard.test4j.mock.BeanAssert;
import io.leopard.web4j.passport.PassportUser;

import org.junit.Test;

public class UdbUserTest {

	@Test
	public void UdbUser() {
		BeanAssert.assertModel(PassportUser.class);
	}

}