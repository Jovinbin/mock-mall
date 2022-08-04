package com.mock.admin;

import com.mock.common.config.GenerateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MockAdminApplicationTests {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${customization.table}")
	private String table;

	@Value("${customization.tablePrefix}")
	private String tablePrefix;

	@Value("${customization.parentPackage}")
	private String parentPackage;

	@Value("${customization.author}")
	private String author;

	@Value("${customization.outputDir}")
	private String outputDir;

	@Test
	public void executeMySQL(){
		System.err.println(url);
		System.err.println(username);
		System.err.println(password);
		System.err.println(driverClassName);
		System.err.println(table);
		System.err.println(tablePrefix);
		System.err.println(parentPackage);
		System.err.println(author);
		System.err.println(outputDir);

		GenerateConfig config = new GenerateConfig();
//		config.setAuthor(author);
//		config.setDbType(DbType.MYSQL);
////		config.setJdbcDriver("org.postgresql.Driver");
//		config.setJdbcDriver(driverClassName);
//		config.setJdbcUrl(url);
//		config.setJdbcUserName(username);
//		config.setJdbcPassword(password);
//		config.setTablePrefix(tablePrefix);
//		config.setParentPackage(parentPackage);
//		config.setOutputDir(outputDir);
//		config.setIncludeTables(table);
//		CodeGeneratorUtil.execute(config);
	}

}
