package com.example.demo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private LoginService lservice;

	
	@PostMapping(value = "/addUser")
	public void addUser(@RequestBody Map<String, Object> requestBody) throws ClassNotFoundException, SQLException {

		Map<String, Object> userMap = (Map<String, Object>) requestBody.get("users");
	    User user = new User();
	    user.setFirstname((String) userMap.get("firstname"));
	    user.setLastname((String) userMap.get("lastname"));

	    Date dateOfBirth = Date.valueOf((String) userMap.get("dateOfBirth")); 
	    user.setDateOfBirth(dateOfBirth);

	    String genderString = (String) userMap.get("gender");
	    if (genderString != null && genderString.length() == 1) {
	        char gender = genderString.charAt(0); 
	        user.setGender(gender); 
	    }
	    
	    user.setContactNumber((String) userMap.get("contactNumber"));
	    user.setAddress((String) userMap.get("address"));
	    user.setCity((String) userMap.get("city"));
	    user.setState((String) userMap.get("state"));
	    user.setUsername((String) userMap.get("username"));
	    user.setEmailid((String) userMap.get("emailid"));

	    String password = (String) requestBody.get("password");
	    String role = (String) requestBody.get("role");

	    service.addUser(user);

	    Login login = new Login();
	    login.setUsername(user.getUsername());
	    login.setEmailid(user.getEmailid());
	    login.setPassword(password);
	    login.setRole(role);
	    
	    lservice.registeruser(login);
	}
	
	@GetMapping(value="/searchUser/{username}")
	public User searchuser(@PathVariable String username) {
		return service.searchUser(username);
	}
	
	@GetMapping(value = "/showAll")
	public List<User> showAllUsers() {
	    return service.showAllUsers();
	}
	
    @PutMapping("/update/{username}/{aadharNum}/{panNum}")
    public String updateAadhaarAndPan(@PathVariable String username,@PathVariable String aadharNum,  @PathVariable String panNum)
      {
        boolean isUpdated = service.updateAadhaarAndPan(username, aadharNum, panNum);
        if (isUpdated) {
            return "Aadhaar and PAN details updated successfully.";
        } else {
            return "User not found.";
        }
    }
	
//	 @DeleteMapping("/delete/{username}")
//	public String deleteUser(@PathVariable String username) {
//	      boolean isDeleted = service.deleteUser(username);
//	        if (isDeleted) {
//	            return "User deleted successfully.";
//	        } else 
//	            return "User not found.";
//	    }
}
