package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/deposit/{accountNumber}/{amount}")
    public String deposit(@PathVariable String accountNumber, @PathVariable double amount) throws ClassNotFoundException, SQLException {
        return bankService.deposit(accountNumber, amount);
    }

    @PostMapping("/withdraw/{accountNumber}/{amount}")
    public String withdraw(@PathVariable String accountNumber, @PathVariable double amount) throws ClassNotFoundException, SQLException {
        return bankService.withdraw(accountNumber, amount);
    }

    @PostMapping("/transfer/{phnum1}/{phnum2}/{amount}")
    public String moneyTransfer(@PathVariable String phnum1, @PathVariable String phnum2, @PathVariable double amount) throws ClassNotFoundException, SQLException {
        return bankService.moneyTransfer(phnum1, phnum2, amount);
    }
}
