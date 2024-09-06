package com.example.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

	@Autowired
	private LoginRepository lrepo;
	
	@Autowired
	private UserRepository userrepo;
	
//	@Autowired
//	private JdbcTemplate jdbctemplate;
	
	public String registeruser(Login login ) throws ClassNotFoundException, SQLException {
		if((userrepo.findByEmailid(login.getEmailid())!=null || userrepo.findByUsername(login.getUsername())!=null)
				&& (lrepo.findByUsername(login.getUsername())==null || lrepo.findByEmailid(login.getEmailid())==null)){
			
			
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

}
