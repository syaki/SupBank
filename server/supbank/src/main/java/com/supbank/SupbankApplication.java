package com.supbank;


import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@ServletComponentScan
public class SupbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupbankApplication.class, args);
		System.out.println("系统启动完成。");
	}

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		dataSource.setInitialSize(5);
		dataSource.setMaxActive(30);
		dataSource.setMinIdle(1);
		dataSource.setMaxWait(60000);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTestOnBorrow(false);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(3600000);
		dataSource.setPoolPreparedStatements(false);
		return dataSource;
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer() {

		return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
			@Override
			public void customize(ConfigurableWebServerFactory factory) {

				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/errors/401");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/errors/404");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errors/500");

				factory.addErrorPages(error401Page, error404Page, error500Page);

			}
		};
	}

}

