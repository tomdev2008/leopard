package io.leopard.data.signature;

import java.util.Date;

/**
 * 签名算法.
 * 
 * @author 阿海
 * 
 */
public interface Signature {

	String encode(String user, Date posttime);

	SignatureInfo decode(String encodeString);

}

//
// 获取用户召回新消息接口：
// URL:http://message.game.yy.com/xxx.do?yyuid=${yyuid}&key=urlEncode(${key})
// 参数:
// yyuid:
// key:随机密文，加密后字符会比较长，需要使用POST方法提交。

// 返回:
// {"status":200, "message","", "data":1}
// #data字段:表示新消息数量，客户端可以判断data>1就显示感叹号.

// 加密算法:
// private static final String PUBLIC_KEY_SHA1 = "xxxx";// SHA1加密私钥
// private static final String SPLIT = ",,"; //字段分隔符号
//
// /**
// * 加密算法.
// * @param yyuid
// *
// * @param posttime
// * 格式:2012-01-10 10:10:10
// * @return
// */
// public static String encode(long yyuid, String posttime) {
// String userinfo = yyuid + SPLIT + posttime;
// String sha1 = EncryptUtil.sha1(userinfo + PUBLIC_KEY_SHA1); // 使用SHA1加密算法
// String base64Encode = Base64.encode(userinfo + SPLIT + sha1);// 将明文信息和密文信息一起使用AES编码
// return base64Encode;
// }
//