package io.leopard.data.dfs;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.ReplaceBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class DfsMysqlImpl extends ContextImpl implements Dfs {

	private static final String TABLE = "file";

	private Jdbc jdbc;

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public boolean create(String filename, byte[] data) {
		ReplaceBuilder builder = new ReplaceBuilder(TABLE);
		builder.setString("filename", filename);
		builder.setInt("size", data.length);
		builder.setBytes("data", data);
		return this.jdbc.insertForBoolean(builder);
	}

	@Override
	public byte[] read(String filename) throws IOException {
		String sql = "select data from " + TABLE + " where filename='" + filename + "';";
		byte[] data = this.jdbc.getJdbcTemplate().query(sql, new ResultSetExtractor<byte[]>() {
			@Override
			public byte[] extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.first()) {
					return rs.getBytes(1);
				}
				else {
					return null;
				}
			}
		});
		if (data == null) {
			throw new FileNotFoundException(filename);
		}
		return data;
	}

	@Override
	public boolean delete(String filename) {
		String sql = "delete from " + TABLE + " where filename=?;";
		return this.jdbc.updateForBoolean(sql, filename);
	}

}
