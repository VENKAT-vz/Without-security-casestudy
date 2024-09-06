package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="login")
public class Login {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "emailid", nullable = false, unique = true)
    private String emailid;
    
	@Column(name="password",nullable=false)
	private String password;
	@Column(name="role")
	private String role;
	
	public Login() {
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Login [username=" + username + ", emailid=" + emailid + ", password=" + password + ", role=" + role
				+ "]";
	}
}
