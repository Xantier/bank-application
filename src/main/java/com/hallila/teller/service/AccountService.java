package com.hallila.teller.service;

import com.hallila.teller.dao.Dao;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

   @Autowired
   private Dao accountDao;

   @Transactional
   public Boolean create(Account account) {
      return accountDao.create(account);
   }

   @Transactional(readOnly = true)
   public List<Transaction> load() {
      return accountDao.load(null);
   }

   @Transactional
   public BigDecimal lodge(Account account, BigDecimal amount) {
      Transaction transaction = new Transaction();
      transaction.setAccountTo(account);
      transaction.setAmount(amount);
      transaction.setDate(LocalDate.now());
      return accountDao.lodge(transaction);
   }

   @Transactional
   public List<Account> transfer(Transaction transaction){
      accountDao.transact(transaction);
      return new ArrayList<>();
   }
}
