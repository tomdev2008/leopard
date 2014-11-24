package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;

public class EncryptUtilTest {

	@Test
	public void md5() {
		String md5 = EncryptUtil.md5("123456");
		System.out.println("md5:" + md5);
		Assert.assertEquals("e10adc3949ba59abbe56e057f20f883e", md5);
	}

	// @Test
	// public void md52() {
	// String md5 = EncryptUtil.md5("P)+d,m\"7?*+-WUPQZ{Xtask1395457201168");
	// System.out.println("md5:" + md5);
	// Assert.assertEquals("e10adc3949ba59abbe56e057f20f883e", md5);
	// }

	@Test
	public void sha1() {
		String sha1 = EncryptUtil.sha1("des");
		System.out.println("sha1:" + sha1);
		Assert.assertEquals("E9D596E7807A846BC76A51E845FCC844F24DFDAA", sha1);
	}

	// @Test
	// public void uuid() {
	// String[] strs = new String[] { "a", "b", "c" };
	// String uuid = EncryptUtil.uuid(strs);
	// System.out.println("uuid:" + uuid);
	// Assert.assertEquals("A9993E364706816ABA3E25717850C26C9CD0D89D", uuid);
	// }

	@Test
	public void toHexString() {
		String hex = EncryptUtil.toHexString("abc");
		System.out.println("hex:" + hex);
		Assert.assertEquals("616263", hex);
	}

	@Test
	public void toStringHex() {
		String str = EncryptUtil.toStringHex("616263");
		System.out.println("str:" + str);
		Assert.assertEquals("abc", str);
	}
}