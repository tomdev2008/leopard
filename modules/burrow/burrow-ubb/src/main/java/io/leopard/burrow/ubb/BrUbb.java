package io.leopard.burrow.ubb;

/**
 * [br] -> <br/>
 * 
 * @author 阿海
 * 
 */
public class BrUbb implements Ubb {

	@Override
	public String parse(String content) {
		content = content.replace("[br]", "<br/>");
		return content;
	}

}
