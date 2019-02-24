package com.shoppurs.configration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.shoppurs.dao.ShoppursCustomerDao;
import com.shoppurs.model.MyDataSource;
import com.shoppurs.model.MyResponse;

@Configuration
public class ShoppursConfigration {
	
	@Autowired
    private Environment env;

	/*@Bean
	@Qualifier("first")
    MyResponse first() {
        return new MyResponse();
    }*/
	
	@Bean
	@Qualifier("firstDao")
	ShoppursCustomerDao dao() {
        return new ShoppursCustomerDao();
    }

	
	@Bean(name = "localDatabase")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource primaryDataSource() {
	    return DataSourceBuilder.create().build();
	}

	@Bean(name = "master-datasource")
//	@ConfigurationProperties(prefix="spring.remote-datasource")
	public DataSource masterDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("spring.remote-datasource.driverClassName"));
	    dataSource.setUrl(env.getProperty("spring.master-datasource.url"));
	    dataSource.setUsername(env.getProperty("spring.remote-datasource.username"));
	    dataSource.setPassword(env.getProperty("spring.remote-datasource.password"));
	    return dataSource;
	   // return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "master-database")
	@Autowired
	public JdbcTemplate masterJdbcTemplate(@Qualifier("master-datasource") DataSource dsSlave) {
	    return new JdbcTemplate(dsSlave);
	}
	
	@Bean(name = "auth-datasource")
//	@ConfigurationProperties(prefix="spring.remote-datasource")
	public DataSource authDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("spring.remote-datasource.driverClassName"));
	    dataSource.setUrl(env.getProperty("spring.auth-datasource.url"));
	    dataSource.setUsername(env.getProperty("spring.remote-datasource.username"));
	    dataSource.setPassword(env.getProperty("spring.remote-datasource.password"));
	    return dataSource;
	   // return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "auth-database")
	@Autowired
	public JdbcTemplate authJdbcTemplate(@Qualifier("auth-datasource") DataSource dsSlave) {
	    return new JdbcTemplate(dsSlave);
	}
	
	@Bean
	MyDataSource dynamicdatasource() {
        return new MyDataSource();
    }
	
}
