package com.hallila.teller.dao;

import com.hallila.teller.entity.Account;

public interface AccountDao {
   public void save();
   public Account load();
   public boolean create(Account account);
}
