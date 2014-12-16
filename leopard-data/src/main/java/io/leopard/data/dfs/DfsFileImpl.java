package io.leopard.data.dfs;

import io.leopard.burrow.lang.ContextImpl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DfsFileImpl extends ContextImpl implements Dfs {

	private static final File rootDir = new File("/data/dfs");

	@Override
	public boolean create(String filename, byte[] data) {
		File file = new File(rootDir, filename);
		try {
			FileUtils.writeByteArrayToFile(file, data);
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}

	@Override
	public byte[] read(String filename) throws IOException {
		File file = new File(rootDir, filename);
		return FileUtils.readFileToByteArray(file);
	}

	@Override
	public boolean delete(String filename) {
		File file = new File(rootDir, filename);
		FileUtils.deleteQuietly(file);
		return false;
	}

}
