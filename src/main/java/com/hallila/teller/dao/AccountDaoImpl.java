package com.hallila.teller.dao;

import com.hallila.teller.entity.Account;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountDaoImpl implements AccountDao{

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   @Transactional
   public void save(){
      Account a = new Account();
      a.setName("Name");
      sessionFactory.getCurrentSession().save(a);
   }

   @Override
   @Transactional(readOnly = true)
   public Account load(){
      return (Account) sessionFactory.getCurrentSession().get(Account.class, 1l);
   }


}
