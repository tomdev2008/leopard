package io.leopard.util.ubb.old;

import io.leopard.util.ubb.old.ChannelUbb;
import org.junit.Assert;

import org.junit.Test;

public class ChannelUbbTest {

	@Test
	public void getPCLink() {
		Assert.assertEquals("<a href=\"javascript:void(0)\" onclick=\"YyGameClient.openChannel('123');AnalyzeClick.CHANNEL_NOTIFY();\">111</a>", ChannelUbb.getPCLink("[channel]111,123[/channel]"));
	}

	@Test
	public void ChannelUbb() {
		new ChannelUbb();
	}

}