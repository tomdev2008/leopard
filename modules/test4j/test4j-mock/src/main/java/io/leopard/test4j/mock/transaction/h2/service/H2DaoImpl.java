package io.leopard.test4j.mock.transaction.h2.service;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.util.ListUtil;
import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.test4j.mock.transaction.h2.TableInfoFile;
import io.leopard.test4j.mock.transaction.h2.TableInfoFileImpl;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.BadSqlGrammarException;

import com.google.inject.Inject;

public class H2DaoImpl extends ContextImpl implements H2Dao {

	@Inject
	private Jdbc jdbc;

	private TableInfoFile tableInfoFile = new TableInfoFileImpl();

	@Override
	public boolean isH2DataSource() {
		String user = jdbc.queryForString("select user() as user");
		// System.out.println("user:" + user);
		return "SA".equals(user);
		// throw new RuntimeException("当前使用的datasource，不像是h2的配置啊?");
	}

	protected String replaceUniqueKeyName(String content) {
		// UNIQUE KEY `un_groupId_activityId_username`
		String regex = "UNIQUE INDEX `(.*?)`";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String keyName = m.group(1);
			int rand = random.nextInt();
			String newKeyName = keyName + "_" + rand + "a'";
			String replacement = "UNIQUE INDEX `" + newKeyName + "`";
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	protected String filter(String content) {
		if (true) {
			// TODO 临时过滤
			content = content.replace("DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", "DEFAULT NULL");
		}
		if (true) {
			content = content.replaceAll("/\\*.*?\\*/;", "");
			content = content.replaceAll("--[^\n]{0,}", "");
			content = content.replaceAll("  KEY [^\n]{0,}", "");
			content = content.replaceAll("  FULLTEXT KEY [^\n]{0,}", "");

			content = content.replace("UNIQUE KEY ", "UNIQUE INDEX ");
			content = content.replace("double(11,0)", "double(11)");
			content = content.replaceAll("double\\([0-9]+,[0-9]+\\)", "double");

			content = this.replaceUniqueKeyName(content);

			content = content.replaceAll("COMMENT='.*?'", "");
			content = content.replaceAll("AUTO_INCREMENT=[0-9]+", "");
			content = content.replace("DEFAULT CHARSET=utf8", "");
			content = content.replace("ENGINE=InnoDB", "");
			content = content.replace("ENGINE=MyISAM", "");

			// ) ENGINE=MyISAM COMMENT='该表仅保存最后将公告同步到公告推送表';

			// create INDEX `idx_username_uu` on message (`game_id`,
			// `server_id`);
			// System.out.println(content);

		}
		return content;
	}

	@Override
	public String getSqlContent() {
		return tableInfoFile.getSqlContent();

	}

	@Override
	public String getAlterSqlContent() {
		return tableInfoFile.getAlterSqlContent();
	}

	@Override
	public boolean existTable(String table) {
		List<Map<String, Object>> list = this.jdbc.queryForMaps("SHOW TABLES;");
		if (ListUtil.isEmpty(list)) {
			return false;
		}
		for (Map<String, Object> map : list) {
			String tableName = (String) map.get("TABLE_NAME");
			if (tableName.equalsIgnoreCase(table)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getH2TableInfoHashCode() {
		String sql = "select hashCode from `h2tableinfo`;";
		try {
			int hashCode = this.jdbc.queryForInt(sql);
			return hashCode;
		} catch (BadSqlGrammarException e) {
			System.out.println("error:" + e.getMessage());
			// 表不存在.
			return -1;
		}
	}

	@Override
	public void createH2TableInfoTable(int hashCode) {
		{
			this.jdbc.update("DROP TABLE IF EXISTS `h2tableinfo`;");
			// memcache table
			String content = "CREATE TABLE `h2tableinfo` (`hashCode` int NOT NULL DEFAULT '0') ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			try {
				this.importDatabaseByContent(content);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		{
			String sql = "insert into `h2tableinfo`(`hashCode`) values('" + hashCode + "');";
			this.jdbc.update(sql);
			System.out.println("sql:" + sql);
		}
		boolean isEquals = (hashCode == this.getH2TableInfoHashCode());
		AssertUtil.assertTrue(isEquals, "hashCode怎么不一样?");
	}

	@Override
	public void importMemcacheTable() {
		if (this.existTable("memcache")) {
			return;
		}
		System.out.println("DROP TABLE IF EXISTS `memcache`;");
		this.jdbc.update("DROP TABLE IF EXISTS `memcache`;");
		// memcache table
		String content = "CREATE TABLE `memcache` (`key` varchar(255) NOT NULL DEFAULT '',`score` varchar(255) NOT NULL DEFAULT '',`value` text,`expiry` datetime DEFAULT '1970-01-01 00:00:00') ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		try {
			this.importDatabaseByContent(content);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (!this.existTable("memcache")) {
			throw new RuntimeException("导入memcache表出错.");
		}
		System.out.println("importMemcacheTable2:" + content);

	}

	protected String getTableName(String content) {
		String regex = "CREATE TABLE `(.*?)`";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		if (m.find()) {
			return m.group(1);
		} else {
			return null;
		}
	}

	@Override
	public void importDatabaseByContent(String content) {
		// AssertUtil.assertNotNull(jdbc, "jdbc未初始化");

		// String file =
		// this.getClass().getResource("/h2/applicationContext.xml").getFile();
		// System.err.println("file:" + file);

		String[] lists;
		if (content.indexOf("Table structure") == -1) {
			lists = content.split(";");
		} else {
			lists = content.split("-- Table structure for table `.*?`");
		}
		for (String block : lists) {
			this.update(block);
		}
	}

	protected void update(String block) {
		// boolean dropTable = true;

		block = this.filter(block);
		block = block.trim();
		{
			// 删除SET...代码
			block = block.replaceAll("SET .*;", "");
			// System.out.println("block:" + block);
		}

		if (StringUtils.isEmpty(block)) {
			return;
		}
		String table = this.getTableName(block);
		if (table == null) {
			return;
		}

		// if (dropTable) {
		jdbc.update("DROP TABLE IF EXISTS " + table + ";");
		// }
		System.out.println("table:" + table);
		try {
			jdbc.update(block);
			// System.out.println(block);
		} catch (RuntimeException e) {
			if (block.startsWith("CREATE TABLE `memcache`")) {
				return;
			}
			System.err.println(block);
			throw e;
		}
	}
}
