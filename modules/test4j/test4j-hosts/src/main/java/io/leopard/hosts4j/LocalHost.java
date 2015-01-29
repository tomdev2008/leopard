package io.leopard.hosts4j;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public class LocalHost {

	private static Set<String> LOCAL_HOSTS = null;

	protected static String getContent() throws IOException {
		String filename;
		if (SystemUtils.IS_OS_WINDOWS) {
			filename = "c:/windows/System32/drivers/etc/hosts";
		}
		else {
			filename = "/etc/hosts";
		}

		String content = FileUtils.readFileToString(new File(filename));
		content = content.replaceAll("#.*", "");
		// System.out.println("content :" + content);
		return content;
	}

	protected static Set<String> getLocalHosts() {
		if (LOCAL_HOSTS != null) {
			return LOCAL_HOSTS;
		}

		String content;
		try {
			content = getContent();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		String[] lines = StringUtils.split(content, "\n");

		Set<String> set = new HashSet<String>();
		for (String line : lines) {
			line = line.trim();
			if (StringUtils.isEmpty(line)) {
				continue;
			}
			// System.out.println("line:" + line);

			String[] hosts = line.split("\\s+");
			for (int i = 1; i < hosts.length; i++) {
				set.add(hosts[i]);
			}
		}
		LOCAL_HOSTS = set;
		return LOCAL_HOSTS;
	}

	/**
	 * 是否本地设置的host?
	 * 
	 * @param host
	 * @return
	 */
	public static boolean isLocalHost(String host) {
		return getLocalHosts().contains(host);
	}
}
