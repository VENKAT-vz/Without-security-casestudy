package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class BankServiceImpl implements BankService {


	    @Autowired
	    private JdbcTemplate jdbcTemplate;

	    @Autowired
	    private TransactionRepository transactionRepository; 

	    public String deposit(String accountNumber, double amount) throws ClassNotFoundException, SQLException {
	        String updateAccountQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
	        jdbcTemplate.update(updateAccountQuery, amount, accountNumber);

	        Transaction transaction = new Transaction();
	        transaction.setAccountNumber(accountNumber);
	        transaction.setAmount(amount);
	        transaction.setTransactionDate(new Date()); 
	        transaction.setTransactionType("deposit");
	        transaction.setDescription("Deposit into account");

	        transactionRepository.save(transaction);

	        return "Amount deposited successfully.";
	    }

	    public String withdraw(String accountNumber, double amount) throws ClassNotFoundException, SQLException {
	        double currentBalance = getCurrentBalance(accountNumber);
	        if (currentBalance < amount) {
	            return "Insufficient balance.";
	        }

	        String updateAccountQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
	        jdbcTemplate.update(updateAccountQuery, amount, accountNumber);

	        Transaction transaction = new Transaction();
	        transaction.setAccountNumber(accountNumber);
	        transaction.setAmount(amount);
	        transaction.setTransactionDate(new Date());
	        transaction.setTransactionType("withdrawal");
	        transaction.setDescription("Withdrawal from account");

	        transactionRepository.save(transaction);

	        return "Amount withdrawn successfully.";
	    }

	    public String moneyTransfer(String senderPhoneNumber, String receiverPhoneNumber, double amount) throws ClassNotFoundException, SQLException {
	        String senderAccountNumber = getAccountNumberByPhone(senderPhoneNumber);
	        String receiverAccountNumber = getAccountNumberByPhone(receiverPhoneNumber);

	        double senderBalance = getCurrentBalance(senderAccountNumber);
	        if (senderBalance < amount) {
	            return "Insufficient balance.";
	        }

	        String updateSenderAccountQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
	        jdbcTemplate.update(updateSenderAccountQuery, amount, senderAccountNumber);

	        String updateReceiverAccountQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
	        jdbcTemplate.update(updateReceiverAccountQuery, amount, receiverAccountNumber);

	        Transaction senderTransaction = new Transaction();
	        senderTransaction.setAccountNumber(senderAccountNumber);
	        senderTransaction.setAmount(amount);
	        senderTransaction.setTransactionDate(new Date());
	        senderTransaction.setTransactionType("transfer-out");
	        senderTransaction.setDescription("Transfer to " + receiverAccountNumber); 
	        transactionRepository.save(senderTransaction);

	        Transaction receiverTransaction = new Transaction();
	        receiverTransaction.setAccountNumber(receiverAccountNumber);
	        receiverTransaction.setAmount(amount);
	        receiverTransaction.setTransactionDate(new Date());
	        receiverTransaction.setTransactionType("transfer-in");
	        receiverTransaction.setDescription("Transfer from " + senderAccountNumber);
	        transactionRepository.save(receiverTransaction); 

	        return "Transfer successful: " + amount + " transferred from " + senderAccountNumber + " to " + receiverAccountNumber;
	    }

	    private String getAccountNumberByPhone(String phoneNumber) throws SQLException {
	        String query = "SELECT a.account_number FROM accounts a JOIN users u ON a.username = u.username WHERE u.contact_number = ?";
	        return jdbcTemplate.queryForObject(query, new Object[]{phoneNumber}, String.class);
	    }

	    private double getCurrentBalance(String accountNumber) throws SQLException {
	        String query = "SELECT balance FROM accounts WHERE account_number = ?";
	        return jdbcTemplate.queryForObject(query, new Object[]{accountNumber}, Double.class);
	    }
	    
}
