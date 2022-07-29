package com.guna.bankingapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guna.bankingapi.entities.Banking;
import com.guna.bankingapi.repos.BankingRepository;

@Service
public class BankingServices {

	@Autowired
	BankingRepository repository;
	
	public int login(long accno,int pin) {
		
		System.out.println("from page: "+accno+":"+pin);
		int username=0;
		List<Banking> users = repository.findAll();
		for(Banking usr:users) {
			System.out.println(usr.getAccountnumber()+":"+usr.getPin());
			if((accno==usr.getAccountnumber()) && (pin==usr.getPin())){
				username=usr.getId();
			}	
		}
		return username;
	}
	
	
	public double withdraw(int amount,int id) {
		
		System.out.println("amount: "+amount);
		double balance=0.1;
		List<Banking> users = repository.findAll();
		for(Banking usr:users) {
			
			if((id==usr.getId())){
				
				System.out.println(usr.getAccountnumber()+":"+usr.getPin());
				
				if(amount<=usr.getAccountbalance()) {
					balance=usr.getAccountbalance()-amount;
				     usr.setAccountbalance(balance);
				     repository.save(usr);
				}
				else {
					return balance;
				}
			}	
		}
		return balance;
		
	}
	
    public double deposit(int amount,int id) {
		
		System.out.println("amount: "+amount);
		double balance=0.1;
		List<Banking> users = repository.findAll();
		for(Banking usr:users) {
			
			if((id==usr.getId())){
				System.out.println(usr.getAccountnumber()+":"+usr.getPin());
				//if(amount<=usr.getAccountbalance()) {
			    balance=usr.getAccountbalance()+amount;
				usr.setAccountbalance(balance);
				repository.save(usr);
				//}
				return balance;
		}
		}
		return balance;	
	}
    
public double balance(int id) {
		
		System.out.println("id: "+id);
		double bal=0;
		List<Banking> users = repository.findAll();
		for(Banking usr:users) {
			
			if((id==usr.getId())){
				System.out.println(usr.getAccountnumber()+":"+usr.getPin());
				//if(amount<=usr.getAccountbalance()) {
			    bal=usr.getAccountbalance();
			    System.out.println(bal+"balance");
				//usr.setAccountbalance(balance);
				//repository.save(usr);
				//}
				return bal;
		}
		}
		return bal;	
	}
}
