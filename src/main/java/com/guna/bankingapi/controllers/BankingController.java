package com.guna.bankingapi.controllers;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.guna.bankingapi.entities.Banking;
import com.guna.bankingapi.entities.Deposit;
import com.guna.bankingapi.entities.LoginUser;
import com.guna.bankingapi.entities.Withdraw;
import com.guna.bankingapi.repos.BankingRepository;
import com.guna.bankingapi.services.BankingServices;


@Controller
@RequestMapping("/")
public class BankingController {
	
	@Autowired
	BankingRepository repository;
	
	int curruser=0;
	
	@Autowired
	BankingServices service;
	
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
	@GetMapping("/login")
	public ModelAndView log() {
		ModelAndView mav=new ModelAndView("login");
		LoginUser loginuser=new LoginUser();
		mav.addObject("loginuser",loginuser);
		return mav;
	}
	
	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute LoginUser loginuser) {
		ModelAndView mav;
		int user=service.login(loginuser.getAccno(), loginuser.getPin());
		if(user != 0) {
			mav = new ModelAndView("home");
			curruser=user;
			System.out.println(user);
		}
		else {
			mav = new ModelAndView("invalid");
		}
	    return mav;
	}
	
	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}
		
	
	//@RequestMapping(value = "/accountform",method = RequestMethod.GET)
	@GetMapping("/accountform")
	public ModelAndView newacc() {
		ModelAndView mav = new ModelAndView("accountform");
		Banking banking =new Banking();
		
		System.out.println("GET Passed");
		long low = 10000000000l;
	    long high = 99999999999l;
		long accno = low + (long) (Math.random() * (high-low));
		
		
		banking.setAccountnumber(accno);
		banking.setAccountbalance(0.00);
		
		mav.addObject("banking",banking);
		return mav;
	  }

	//@RequestMapping(value = "/accountform", method = RequestMethod.POST)
	@PostMapping("/accountform")
	public ModelAndView saveacc(@ModelAttribute Banking banking) {
		System.out.println("POST Strted");
		ModelAndView mav = new ModelAndView("accountcreated");
		
		long low = 10000000000l;
	    long high = 99999999999l;
		long accno = low + (long) (Math.random() * (high-low));
		banking.setAccountnumber(accno);
		banking.setAccountbalance(0.00);
		
		
		repository.save(banking);
		mav.addObject("accountnumber", banking.getAccountnumber());
		
		return mav;
		
	  }
	
	
	
	
	@GetMapping("/withdraw")
	public ModelAndView withdraw() {
		ModelAndView mav = new ModelAndView("withdraw");
		Withdraw withdraw=new Withdraw();
		mav.addObject("withdraw", withdraw);
		return mav;
	}
	@PostMapping("/withdraw")
	public ModelAndView withdrawsuccess(@ModelAttribute Withdraw withdraw) {
		
		ModelAndView mav;
		if(withdraw.getAmount()<=0) {
			mav = new ModelAndView("fail");
			return mav;
		}
			
		double balance=service.withdraw(withdraw.getAmount(),curruser);
		if(balance != 0.1) {
			mav = new ModelAndView("success");
			String a="Rs. "+balance;
			mav.addObject("balance", a);
			//mav.addObject("balance", balance);
			//curruser=user;
			System.out.println(curruser);
		}
		else {
			mav = new ModelAndView("amountmuch");
		}
	    return mav;
	}
	
	
	@GetMapping("/deposit")
	public ModelAndView deposit() {
		ModelAndView mav = new ModelAndView("deposit");
		Deposit deposit=new Deposit();
		mav.addObject("deposit", deposit);
		System.out.println("Get Deposit");
		return mav;
	}
	@PostMapping("/deposit")
	public ModelAndView depositsuccess(@ModelAttribute Deposit deposit) {
		System.out.println("Post Deposit");
		ModelAndView mav;
		System.out.println(deposit.getAmount());
		if(deposit.getAmount()<=0) {
			mav = new ModelAndView("fail");
			return mav;
		}
			
		double balance=service.deposit(deposit.getAmount(),curruser);
		if(balance != 0.1) {
			mav = new ModelAndView("success");
			String a="Rs. "+balance;
			mav.addObject("balance", a);
			//curruser=user;
			System.out.println(curruser);
		}
		else {
			mav = new ModelAndView("fail");
		}
	    return mav;
	}
	
	
	
	
	
	
	/*@RequestMapping("/deposit")
	public ModelAndView deposit() {
		ModelAndView mav = new ModelAndView("deposit");
		return mav;
	}*/
	
	
	@RequestMapping("/balance")
	public ModelAndView balanceenq() {
		ModelAndView mav = new ModelAndView("balance");
		double balance=service.balance(curruser);
		String a="Rs. "+balance;
		mav.addObject("balance", a);
		return mav;
	}
	/*@RequestMapping("/success")
	public ModelAndView success() {
		ModelAndView mav = new ModelAndView("success");
		return mav;
	}
	@RequestMapping("/fail")
	public ModelAndView fail() {
		ModelAndView mav = new ModelAndView("fail");
		return mav;
	}*/
}
