package com.example.demo;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@Autowired
	private LoginService lservice;
	
	 @PostMapping("/register")
	    public String registeruser(@RequestBody Login login) throws ClassNotFoundException, SQLException {
	        return lservice.registeruser(login);
	    }

}
