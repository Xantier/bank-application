package com.hallila.teller.dao;

import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
   List<Transaction> load(Account account);

   boolean create(Account account);

   BigDecimal transact(Transaction transaction);
}
