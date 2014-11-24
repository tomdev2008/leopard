package io.leopard.test.mock;

import java.util.List;

/**
 * 
 * @author 阿海
 * 
 */
public class MockHelp {

	protected static class Model {

	}

	/**
	 * 返回List<String>.
	 * 
	 * <pre>
	 * Mock.doReturn(&quot;[a,b,c]&quot;).when(service).list();
	 * </pre>
	 * 
	 * @return
	 */
	public static List<String> doReturnListString() {
		return null;
	}

	/**
	 * 返回List<Integer>.
	 * 
	 * <pre>
	 * Mock.doReturn(&quot;[1,2,3]&quot;).when(service).list();
	 * </pre>
	 * 
	 * @return
	 */
	public static List<Integer> doReturnListInteger() {
		return null;
	}

	/**
	 * 返回List<Model>.
	 * 
	 * <pre>
	 * Mock.doReturn(&quot;[gameId:ddt;gameId:sxd,userCount:1]&quot;).when(service).list();
	 * </pre>
	 * 
	 * @return
	 */
	public static List<Model> doReturnListModel() {
		return null;
	}

	/**
	 * 例子.
	 */
	public static void example() {
		// 代理方法Assert(程序会自动查找articleService第一个以Dao结尾的属性类型，作为Dao)
		// Assert.when(articleService).updateGameNickname(1, "gameNickname");
		// Assert.when(articleService, articleDaoXxxImpl).updateGameNickname(1, "gameNickname");//和前面一行代码是相等的
		// Assert.when(articleService, otherService).updateGameNickname(1, "gameNickname");//指定代理其他service方法
		// Assert.when(articleService, "otherService.otherMethodName").updateGameNickname(1, "nick");//指定代理其他service的其他方法

		// mock返回值
		// Mock.doReturn("[1,2,3]").when(articleService).listArticleId();//return List<Integer>
		// Mock.doReturn("[a,b,c]").when(articleService).listUsername();//return List<String>
		// Mock.doReturn("[gameId:ddt;gameId:sxd,userCount:1]").when(gameService).listAllGame();//return List<Model>
		// now会自动解析成当前时间，如果系统指定固定的时间可以使用long类型.
		// Mock.doReturn("[1,now;2,now]").when(this.outsideService).listMyGroups("hctan"); //List<Entry<Integer, Date>>

		// mock静态类方法的返回值
		// Mock.doStaticReturn("[newsId:1,posttime:1;newsId:2,posttime:2]").whenStatic("WebUtil.filterExpireNews");

		// 验证调用次数
		// userService.methodName必须调用1次，getUser方法返回的昵称等于"阿海"。verify方法第一个参数没有输入service名称，则用当前测试类要测试service对象.
		// Assert.verify("methodName", 1).equals("阿海", userService.getUser("hctan").getNickname());
		// /##############################################
		// otherService.methodName必须调用1次，getUser方法返回的昵称等于"阿海"
		// Assert.verify("otherService.methodName", 1).equals("阿海", userService.getUser("hctan").getNickname());
		// /##############################################
		// 如果要verify的方法名会有重复，可以指定方法的参数个数
		// Assert.verify("otherService.methodName(2)", 1).equals("阿海", userService.getUser("hctan").getNickname());

		// 异常assert,异常类型只支持类名equals判断.
		// Assert.except(RuntimeException.class, "message前缀");

		// 返回值验证
		// Assert.equals("{posttime:22,newsId:1}", newsServie.get("hctan"));

		// DAO测试
		// Assert.dao(messageDaoMysqlImpl).get(1);
		// Assert.dao(messageDaoMysqlImpl).delete(1);
		// Assert.dao(messageDaoMysqlImpl).remove(1);
		//

	}
}
