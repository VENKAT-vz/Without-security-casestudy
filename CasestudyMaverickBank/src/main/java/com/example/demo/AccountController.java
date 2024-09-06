package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/addAccounts")
    public Account addAccount(@RequestBody Account account) throws ClassNotFoundException, SQLException {
        return accountService.addAccount(account);
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
