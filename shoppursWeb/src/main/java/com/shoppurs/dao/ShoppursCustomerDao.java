package com.shoppurs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.shoppurs.controller.CustomerApiController;
import com.shoppurs.mapper.CategoryMapper;
import com.shoppurs.mapper.CountryMapper;
import com.shoppurs.mapper.CustomerMapper;
import com.shoppurs.mapper.SimpleItemMapper;
import com.shoppurs.mapper.SubCatMapper;
import com.shoppurs.model.Category;
import com.shoppurs.model.Country;
import com.shoppurs.model.Customer;
import com.shoppurs.model.MyDataSource;
import com.shoppurs.model.MyUser;
import com.shoppurs.model.RetailerCategory;
import com.shoppurs.model.SimpleItem;
import com.shoppurs.model.SubCategory;
import com.shoppurs.model.UserLogin;

public class ShoppursCustomerDao {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerApiController.class);
	
	@Autowired
    private MyDataSource dynamicDataSource;
	
	@Autowired
	@Qualifier("master-database")
    private JdbcTemplate masterjdbcTemplate;
	
	@Autowired
   	@Qualifier("auth-database")
    private JdbcTemplate authJdbcTemplate;
	
	/*
	 * @Autowired
	 * 
	 * @Qualifier("shop-database") private JdbcTemplate shopJdbcTemplate;
	 */
	
	public String manageRegistration(MyUser myUser) {
		String query = "{ call manage_registration(?,?,?,?,?,?,?,?,?,?,?) }";
	    ResultSet rs;
	    
	    int status = 1;
        String message = "";
	    
	    try {
	    	Connection conn = authJdbcTemplate.getDataSource().getConnection();
	        CallableStatement stmt = conn.prepareCall(query);
	    	stmt.setString(1, myUser.getUsername());
	    	stmt.setString(2, myUser.getUser_email());
	    	stmt.setString(3, myUser.getMobile());
	    	stmt.setString(4, myUser.getMpassword());
	    	stmt.setString(5, myUser.getPhoto());
	    	stmt.setString(6, myUser.getUser_type());
	    	stmt.setString(7, myUser.getCreated_by());
	    	stmt.setString(8, myUser.getUpdated_by());
	    	stmt.setInt(9, myUser.getAction());
	    
	    	
	    	//stmt.setInt(10, 1);
	    	stmt.registerOutParameter(10, java.sql.Types.INTEGER);
	    	stmt.registerOutParameter(11, java.sql.Types.VARCHAR);
	        stmt.execute();
	       // rs = stmt.getResultSet();
	        status = stmt.getInt(10);
        	message = stmt.getString(11);
	    } catch (SQLException ex) {
	        System.out.println(ex.getMessage());
	    }
	    return status+"|"+message;
	}
	    

	public String loginCustomer(UserLogin userLogin) {
		String status = "success";
		String sql2="select count(*) from shoppurs_auth where CUST_EMAILID=? and PASSWORD = ?";
		int count=authJdbcTemplate.queryForObject(sql2,Integer.class,userLogin.getEmail(),userLogin.getPassword());
		if(count == 0) {
			status = "Authentication failed";
		}else {
			status = "success";
		}
		return status;
	}
	
	public Customer getCustomerDetails(String email) {
	
		String sql="select * from shoppurs_customer where CUST_EMAILID=?";
		try
		   {
		     List<Customer> customerList=authJdbcTemplate.query(sql, new CustomerMapper(),email);
		       if(customerList.size() > 0) {
			      return customerList.get(0);
		       }else {
		    	   log.info("Customer size is 0");
		    	   return null;
		       }
		   }catch(Exception e)
		     {
			   log.info("Exception "+e.toString());
			     return null;
		    }
	}
	
	private String generateRandom(int count) {
    	String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    	StringBuilder builder = new StringBuilder();
    	while(count-- != 0) {
    		int character = (int)(Math.random() * alphaNumericString.length());
    		builder.append(alphaNumericString.charAt(character));
    	}
    	
    	return builder.toString();
    }
	
	private JdbcTemplate getDynamicDataSource(String dbName,String dbUserName,String dbPassword) {
		dynamicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dynamicDataSource.setUrl("jdbc:mysql://localhost:3306/"+dbName);
		dynamicDataSource.setUserName(dbUserName);
		dynamicDataSource.setPassword(dbPassword);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(dynamicDataSource.getDriverClassName());
	    dataSource.setUrl(dynamicDataSource.getUrl());
	    dataSource.setUsername(dynamicDataSource.getUserName());
	    dataSource.setPassword(dynamicDataSource.getPassword());
	    return new JdbcTemplate(dataSource);
	}

}
