package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private JdbcTemplate jdbcTemplate;

    public Account addAccount(Account account, String aadhaarNumber, String panNumber) throws ClassNotFoundException, SQLException {
    	account.setAccountNumber(generateAccountNo());
    	Date currentDate = new Date(System.currentTimeMillis());
    	account.setDateCreated(currentDate);
    	account.setStatus("NotApproved");
    	
    	//here we need to have a list like combinations of branches and their ifsc codes and 
    	//whenever they enter branchname it should automatically set the ifsc codes.
    	//account.setIfscCode(null);
    	Account savedAccount=accountRepository.save(account);
        User user = userRepository.findByUsername(account.getUsername()); 
        if (user != null) {
            user.setAadharNum(aadhaarNumber);
            user.setPanNum(panNumber);
            userRepository.save(user);
        }
    	return savedAccount;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findById(accountNumber).orElse(null);
    }
    
    public List<Account> getAccountByNumberByusername(String username) {
        return accountRepository.findByUsername(username);
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
	 
	 public List<Map<String, Object>> getNotApprovedAccounts() {
		    String query = "SELECT a.account_number, a.account_type, a.balance, a.branch_name, a.ifsc_code, a.date_created, " +
		                   "a.status, u.username, u.emailid, u.aadhar_num, u.pan_num " +
		                   "FROM accounts a " +
		                   "JOIN users u ON a.username = u.username " +
		                   "WHERE a.status = 'NotApproved'";

		    return jdbcTemplate.queryForList(query);
		}
	 
	 public String approveAccounts(String accountNumber) {
		    String query = "UPDATE accounts SET status = 'active' WHERE account_number = ?";
		    
		    int rowsAffected = jdbcTemplate.update(query, accountNumber);
		    
		    if (rowsAffected > 0) {
		        return "Account approved successfully.";
		    } else {
		        return "Account approval failed. Account not found.";
		    }
		}

}
