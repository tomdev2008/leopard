package io.leopard.util.ubb.old;

import org.apache.commons.lang.StringUtils;

public class OldUbbUtil {

	public static String parse(String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		// content = HtmlUtils.htmlEscape(content);
		content = content.replace("<", "&lt;");
		content = content.replace(">", "&gt;");

		String result = content;
		result = StrongUbb.strong(result); // 转换[b][/b]
		result = UnderLineUbb.underLine(result); // 转换[u][/u]
		result = ColorUbb.color(result); // 转换[color][/color]
		result = BrUbb.br(result); // 转换[br]

		result = CopyUbb.copy(result); // 转换[copy][/copy]

		return result;
	}
}
