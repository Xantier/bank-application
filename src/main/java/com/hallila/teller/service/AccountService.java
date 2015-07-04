package com.hallila.teller.service;

import com.hallila.teller.dao.AccountDao;
import com.hallila.teller.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

   @Autowired
   private AccountDao accountDao;

   public Boolean create(Account account) {
      return accountDao.create(account);
   }

}
