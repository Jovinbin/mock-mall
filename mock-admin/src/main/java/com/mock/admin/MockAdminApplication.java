package com.mock.admin;

import com.mock.common.handle.MyMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.mock.admin.netclass.mapper")
@Import(value = MyMetaObjectHandler.class)
public class MockAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockAdminApplication.class, args);
	}

}
