package io.leopard.data.dfs;

import io.leopard.core.context.ContextImpl;

import java.io.IOException;

public class DfsCacheImpl extends ContextImpl implements Dfs {

	private DfsGridImpl dfsGridImpl;
	private DfsFileImpl dfsFileImpl;

	private String server;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public void init() {
		dfsGridImpl = new DfsGridImpl();
		dfsGridImpl.setServer(server);
		dfsGridImpl.init();

		dfsFileImpl = new DfsFileImpl();
		dfsFileImpl.init();
	}

	@Override
	public void destroy() {
		super.destroy();
		dfsGridImpl.destroy();
	}

	@Override
	public boolean create(String filename, byte[] data) {
		dfsFileImpl.create(filename, data);
		return dfsGridImpl.create(filename, data);
	}

	@Override
	public byte[] read(String filename) throws IOException {
		try {
			return dfsFileImpl.read(filename);
		}
		catch (IOException e) {
			byte[] data = dfsGridImpl.read(filename);
			dfsFileImpl.create(filename, data);
			return data;
		}
	}

	@Override
	public boolean delete(String filename) {
		dfsFileImpl.delete(filename);
		return dfsGridImpl.delete(filename);
	}

}
