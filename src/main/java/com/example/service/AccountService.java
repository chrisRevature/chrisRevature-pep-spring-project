package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import java.util.List;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;

    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) {
        return accountRepository.save(account);
    }

    public boolean registerValid(Account account) {
        if(!account.getUsername().isEmpty() && account.getPassword().length() > 3) {
            return true;
        }
        return false;
    }

     public boolean doesExist(String username) {
        List<Account> list = getAllAccount();

        for(Account account: list) {
            if(account.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public Account login(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword());
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }
}
