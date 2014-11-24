package io.leopard.data.dfs;

import io.leopard.core.context.ContextImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class DfsGridImpl extends ContextImpl implements Dfs {

	private String server;

	private MongoClient client;
	private DB db;
	private GridFS fs;

	public DfsGridImpl() {

	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public void init() {
		String[] list = server.split(":");
		String host = list[0];
		int port = Integer.parseInt(list[1]);
		try {
			client = new MongoClient(host, port);
		}
		catch (UnknownHostException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		this.db = client.getDB("dfs");
		fs = new GridFS(db, "fs");
	}

	@Override
	public void destroy() {
		this.client.close();
	}

	@Override
	public boolean delete(String filename) {
		fs.remove(filename);
		return true;
	}

	@Override
	public boolean create(String filename, byte[] data) {
		this.delete(filename);
		GridFSInputFile file = fs.createFile(data);
		file.setFilename(filename);
		file.save();
		return true;
	}

	@Override
	public byte[] read(String filename) throws IOException {
		GridFSDBFile file = fs.findOne(filename);
		if (file == null) {
			throw new FileNotFoundException(filename);
		}
		InputStream input = file.getInputStream();
		try {
			return IOUtils.toByteArray(input);
		}
		finally {
			input.close();
		}
	}

}
