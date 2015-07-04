package com.hallila.teller.service;

import com.hallila.teller.dao.AccountDao;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

   @Autowired
   private AccountDao accountDao;

   @Transactional
   public Boolean create(Account account) {
      return accountDao.create(account);
   }

   @Transactional(readOnly = true)
   public Account load() {
      return accountDao.load();
   }

   @Transactional
   public BigDecimal lodge(Transaction transaction) {
      accountDao.transact(transaction);
      Account accountTo = transaction.getAccountTo();
      accountTo.setBalance(accountTo.getBalance().add(transaction.getAmount()));
      return accountTo.getBalance();
   }

   @Transactional
   public List<Account> transfer(Transaction transaction){
      accountDao.transact(transaction);
      return new ArrayList<>();
   }
}
