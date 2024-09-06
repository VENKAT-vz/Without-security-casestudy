package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
	private JdbcTemplate jdbcTemplate;

    public Account addAccount(Account account) throws ClassNotFoundException, SQLException {
    	account.setAccountNumber(generateAccountNo());
    	Date currentDate = new Date(System.currentTimeMillis());
    	account.setDateCreated(currentDate);
    	account.setStatus("NotApproved");
    	
    	//here we need to have a list like combinations of branches and their ifsc codes and 
    	//whenever they enter branchname it should automatically set the ifsc codes.
    	//account.setIfscCode(null);
    	return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findById(accountNumber).orElse(null);
    }
    
    public boolean deleteAccount(String accountNumber) {
        if (accountRepository.existsById(accountNumber)) 
        {
          accountRepository.deleteById(accountNumber);
            return true;
        } 
        else 
            return false;
    }

	 private String generateAccountNo() throws ClassNotFoundException, SQLException {
	      String query = "SELECT CASE WHEN MAX(account_number) IS NULL THEN 1000 ELSE MAX(account_number) + 1 END AS accno FROM accounts";
	        
	            Integer accno = jdbcTemplate.queryForObject(query, Integer.class);
	            return accno != null ? accno.toString() : "1000";  

	 }

}
