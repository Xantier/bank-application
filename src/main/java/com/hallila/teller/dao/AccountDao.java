package com.hallila.teller.dao;

import com.hallila.teller.entity.Account;

public interface AccountDao {
   void save();
   Account load();
}
