package com.hallila.teller.dao;

import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;

import java.math.BigDecimal;

public interface AccountDao {
   public Account load();

   public boolean create(Account account);

   BigDecimal transact(Transaction transaction);
}
