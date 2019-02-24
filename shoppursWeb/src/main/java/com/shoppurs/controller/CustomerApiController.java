package com.shoppurs.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppurs.dao.ShoppursCustomerDao;
import com.shoppurs.model.Category;
import com.shoppurs.model.Country;
import com.shoppurs.model.Customer;
import com.shoppurs.model.Greeting;
import com.shoppurs.model.MyResponse;
import com.shoppurs.model.MyUser;
import com.shoppurs.model.RetailerCategory;
import com.shoppurs.model.SimpleItem;
import com.shoppurs.model.SubCategory;
import com.shoppurs.model.UserLogin;

@RestController("/api")
public class CustomerApiController {
    
    private static final Logger log = LoggerFactory.getLogger(CustomerApiController.class);
    
    //@Autowired
	//private MyResponse myResponse;
    
    @Autowired
   	private ShoppursCustomerDao shoppursDao;
    
    
 //API to new customer registration
    
    @RequestMapping("/api/registerCustomer")
    public MyResponse registerCustomer(@RequestBody  MyUser myUser) {
    	
    	String status = shoppursDao.manageRegistration(myUser);
    	
    	if(status.contains("1")) {
    		return generateResponse(true,"Retailer created successfully.",myUser);
    	}else {
    		return generateResponse(false,status,null);
    	}
    	
    }
    
    // Api to customer login
	  @RequestMapping("/api/loginCustomer") 
	  public MyResponse getCities(@RequestBody UserLogin user) {
		/*
		 * UserLogin user = new UserLogin(); user.setEmail(email);
		 * user.setPassword(password);
		 */
	   
	  String status = shoppursDao.loginCustomer(user); 
	  return generateResponse(true,"Cities fetched successfuly",user);
	  }
    

  //API to get all cities
    
	/*
	 * @RequestMapping("/api/cities") public MyResponse getCities(@RequestParam int
	 * stateId) {
	 * 
	 * List<SimpleItem> itemList = shoppursDao.getCityList(stateId); return
	 * generateResponse(true,"Cities fetched successfuly",itemList);
	 * 
	 * }
	 */
    
    
  //This method generates response body
    private MyResponse generateResponse(boolean status,String message,Object data) {
    	MyResponse myResponse = new MyResponse();
    	myResponse.setStatus(status);
    	myResponse.setMessage(message);
    	myResponse.setResult(data);
    	return myResponse;
    }
    
    
}
