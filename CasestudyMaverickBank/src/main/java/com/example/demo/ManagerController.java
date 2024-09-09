package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private AccountService accountService;
	

    @GetMapping("/unapproved-accounts")
    public ResponseEntity<List<Map<String, Object>>> getNotApprovedAccounts() {
        List<Map<String, Object>> notApprovedAccounts = accountService.getNotApprovedAccounts();
        return ResponseEntity.ok(notApprovedAccounts);
    }
    
    @PutMapping("/approve-account/{accountNumber}")
    public ResponseEntity<String> approveAccount(@PathVariable String accountNumber) {
        String result = accountService.approveAccounts(accountNumber);
        return ResponseEntity.ok(result);
    }

}
