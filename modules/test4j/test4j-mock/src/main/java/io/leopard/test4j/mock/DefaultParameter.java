package io.leopard.test4j.mock;

/**
 * 测试使用的默认参数
 * 
 * @author 阿海
 * 
 */
public interface DefaultParameter {
	
	/** 默认username:username */
	public String username = "username";

	/** 默认passport:passport */
	public String passport = "passport";

	public String sessUsername = "username";

	public int pageId = 1;

	public long sessYyuid = 1;

	public long uid = 1;

	/** 默认游戏ID:ddt */
	public String gameId = "ddt";

	/** 默认游戏serverId:s1 */
	public String serverId = "s1";
	/** 用户IP */
	public String proxyIp = "127.0.0.1";

	public String ANY_STR = "any";

	public int ANY_INT = 8888;

}
