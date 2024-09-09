package com.example.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

	@Autowired
	private LoginRepository lrepo;
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
    @Autowired
    private AccountService accountService;
	
	public String registeruser(Login login ) throws ClassNotFoundException, SQLException {
		if((lrepo.findByEmailid(login.getEmailid())==null && lrepo.findByUsername(login.getUsername())==null)){
		
			 String encrytedpassword=getCode(login.getPassword());
			 login.setPassword(encrytedpassword);
			 lrepo.save(login);
			 return "New Login successfully registered";
			 
		}

			return "user with credentials already exists";

	}
	
	public  String getCode(String password) {
        String encryptedpassword = null;  
        try   
        {  
            MessageDigest m = MessageDigest.getInstance("MD5");  
              
            m.update(password.getBytes());  
              
            byte[] bytes = m.digest();  
              
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }  
              
            encryptedpassword = s.toString();  
        }   
        catch (NoSuchAlgorithmException e)   
        {  
            e.printStackTrace();  
        }  
          
        return encryptedpassword;
	}
	
	public String loginuser(String username, String password) throws ClassNotFoundException, SQLException {
	    String encryptedPass = getCode(password);
	    String query = "SELECT COUNT(*) FROM login WHERE username = ? AND password = ?";

	    int count = jdbctemplate.queryForObject(query, new Object[]{username, encryptedPass}, Integer.class);
	    
	    if (count > 0) {
	        List<Account> accounts = accountService.getAccountByNumberByusername(username);

	        StringBuilder result = new StringBuilder("Login successful\n");

	        for (Account account : accounts) {
	            result.append("In the ")
	                  .append(account.getAccountType())
	                  .append(" account of account number ")
	                  .append(account.getAccountNumber())
	                  .append("\nThe available balance is ")
	                  .append(account.getBalance())
	                  .append("\n\n");
	        }

	        return result.toString();  
	    }

	    return "Invalid Credentials. Try again.";
	}

}
