package com.example.demo;

import java.util.List;

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

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping(value="/addUser")
	public void adduser(@RequestBody User user) {
		service.addUser(user);
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
