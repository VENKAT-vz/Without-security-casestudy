package com.example.demo;

import java.sql.SQLException;

public interface BankService {
	
    String deposit(String accountNumber, double amount) throws ClassNotFoundException, SQLException;
    String withdraw(String accountNumber, double amount) throws ClassNotFoundException, SQLException;
    String moneyTransfer(String phnum1, String phnum2, double amount) throws ClassNotFoundException, SQLException;
    
}
