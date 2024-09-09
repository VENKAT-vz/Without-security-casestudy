package com.example.demo;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@Autowired
	private LoginService lservice;
	
//	 @PostMapping("/register")
//	    public String registeruser(@RequestBody Login login) throws ClassNotFoundException, SQLException {
//	        return lservice.registeruser(login);
//	    }
	 
	 @GetMapping("/login/{username}/{password}")
	 public String loginuser(@PathVariable String username,@PathVariable String password) throws ClassNotFoundException, SQLException {
		 return lservice.loginuser(username, password);
	 }

}
