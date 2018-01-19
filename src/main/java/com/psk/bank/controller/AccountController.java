package com.psk.bank.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psk.bank.model.Account;
import com.psk.bank.model.AccountEntity;
import com.psk.bank.repository.AccountRepository;


@Controller
public class AccountController {
    
    @Autowired
    AccountRepository accountRepository;
    
    @GetMapping("/getAccount/{id}")
    @ResponseBody
    public Account getAccount(@PathVariable("id") String id){
        return accountRepository.findOne(id);
    }
    
    @DeleteMapping("deleteAccount/{id}")
    @ResponseBody
    public Account deleteAccount(@PathVariable("id") String id){
        Account accountToDelete = accountRepository.findOne(id);
        accountRepository.deleteOne(id);
        return accountToDelete;
    }
    
    @PostMapping(value="/addAccount")
    @ResponseBody
    public String addAccount(@RequestParam(value = "accountNumber", required = false) String accountNumber,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "creationTs", required = false) String creationTs){
        Account account = AccountEntity.newInstance(accountNumber, firstName, lastName, active, null);
        accountRepository.save(account);
        return "dodano";
    }
        
}
