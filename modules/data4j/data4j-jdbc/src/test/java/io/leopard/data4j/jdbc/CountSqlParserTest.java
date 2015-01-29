package io.leopard.data4j.jdbc;

import io.leopard.data4j.jdbc.CountSqlParser;
import io.leopard.data4j.jdbc.StatementParameter;

import org.junit.Assert;
import org.junit.Test;

public class CountSqlParserTest {

	@Test
	public void getCountSql() {
		StatementParameter param = new StatementParameter();
		CountSqlParser parser = new CountSqlParser("select * from user", param);
		Assert.assertEquals("select count(*) from user", parser.getCountSql());
	}

	@Test
	public void getCountParam() {
		StatementParameter param = new StatementParameter();
		param.setString("a");
		param.setInt(0);
		param.setInt(10);
		CountSqlParser parser = new CountSqlParser("select * from user where user=? limit ?,?", param);
		Assert.assertEquals("select count(*) from user where user=?", parser.getCountSql());
		StatementParameter param1 = parser.getCountParam();
		Assert.assertEquals(1, param1.getArgs().length);
	}

	@Test
	public void getCountParam2() {
		StatementParameter param = new StatementParameter();
		param.setString("a");
		CountSqlParser parser = new CountSqlParser("select * from user where user=?", param);
		Assert.assertEquals("select count(*) from user where user=?", parser.getCountSql());
		StatementParameter param1 = parser.getCountParam();
		Assert.assertEquals(1, param1.getArgs().length);
	}
}