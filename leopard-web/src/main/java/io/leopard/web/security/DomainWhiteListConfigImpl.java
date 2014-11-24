package io.leopard.web.security;

import io.leopard.burrow.timer.SimpleTimer;
import io.leopard.commons.utility.ListUtil;
import io.leopard.core.context.ContextImpl;
import io.leopard.data4j.jdbc.CreateTableUtil;
import io.leopard.data4j.jdbc.CreateTableUtil.GetSql;
import io.leopard.data4j.jdbc.Jdbc;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

public class DomainWhiteListConfigImpl extends ContextImpl implements DomainWhiteListConfig {

	// private static String jdbcName = "jdbc";
	//
	// public static void setJdbcName(String jdbcName) {
	// DoaminWhiteListConfigImpl.jdbcName = jdbcName;
	// }
	//
	// public static String getJdbcName() {
	// return jdbcName;
	// }

	private Jdbc jdbc;

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
		// System.err.println("setJdbc:" + jdbc);
	}

	@Override
	public Set<String> getRefererDomainWhiteSet() {
		String sql = "select domain from leopard_domainwhitelist;";
		List<String> list = jdbc.queryForStrings(sql);
		return ListUtil.toSet(list);
	}

	@PostConstruct
	@Override
	public void init() {
		// System.err.println("DoaminWhiteListConfigImpl");
		super.init();

		createTable();

		new SimpleTimer(60) {
			@Override
			public void start() {
				update();
			}
		}.run();
	}

	protected void update() {
		// System.err.println("update RefererDomainWhiteSet:");
		Set<String> set = this.getRefererDomainWhiteSet();
		RefererSecurityValidator.setDoaminWhiteList(set);
	}

	protected void createTable() {
		CreateTableUtil.createTable(jdbc, "leopard_domainwhitelist", new GetSql() {
			@Override
			public String getSql() {
				StringBuilder sb = new StringBuilder();
				sb.append("CREATE TABLE  if not exists `leopard_domainwhitelist` (");
				sb.append("`domain` varchar(50) NOT NULL DEFAULT '',");
				sb.append("`username` varchar(50) NOT NULL DEFAULT '',");
				sb.append("`posttime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',");
				sb.append("`comment` varchar(255) NOT NULL DEFAULT '',");
				sb.append("PRIMARY KEY (`domain`)");
				sb.append(");");
				return sb.toString();
			}

		});
	}

}
