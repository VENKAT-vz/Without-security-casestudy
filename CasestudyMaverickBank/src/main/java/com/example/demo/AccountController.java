package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/addAccounts")
    public Account addAccount(@RequestBody Map<String, Object> userdet) throws ClassNotFoundException, SQLException {
        Map<String, Object> accountdet = (Map<String, Object>) userdet.get("account");
        String aadhaarNumber = (String) userdet.get("aadhaarNumber");
        String panNumber = (String) userdet.get("panNumber");

        Account account = new Account();
        account.setAccountType((String) accountdet.get("accountType"));
        account.setBalance((Double) accountdet.get("balance"));
        account.setBranchName((String) accountdet.get("branchName"));
        account.setIfscCode((String) accountdet.get("ifscCode"));
        account.setUsername((String) accountdet.get("username"));
        account.setEmailid((String) accountdet.get("emailid"));

        return accountService.addAccount(account, aadhaarNumber, panNumber);
    }

    @GetMapping("/showAccounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/searchAccounts/{accountNumber}")
    public Account getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }
    
    @DeleteMapping("/delete/{accountNumber}")
    public String deleteAccount(@PathVariable String accountNumber) {
        boolean isDeleted = accountService.deleteAccount(accountNumber);
        if (isDeleted) {
            return "Account deleted successfully.";
        } else {
            return "Account not found.";
        }
    }

}
