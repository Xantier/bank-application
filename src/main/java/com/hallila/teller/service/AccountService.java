package com.hallila.teller.service;

import com.hallila.teller.dao.AccountDao;
import com.hallila.teller.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountService {

   @Autowired
   private AccountDao accountDao;

   @Transactional
   public Boolean create(Account account) {
      return accountDao.create(account);
   }

}
