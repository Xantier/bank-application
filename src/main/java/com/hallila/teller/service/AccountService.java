package com.hallila.teller.service;

import com.hallila.teller.dao.AccountDao;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class AccountService {

   @Autowired
   private AccountDao accountDao;

   @Transactional
   public Boolean create(Account account) {
      return accountDao.create(account);
   }

   public Account load() {
      return accountDao.load();
   }

   public BigDecimal lodge(Transaction transaction) {
      Account accountTo = transaction.getAccountTo();
      accountTo.setBalance(accountTo.getBalance().add(transaction.getAmount()));
      return accountTo.getBalance();
   }
}
