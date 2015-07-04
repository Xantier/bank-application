package com.hallila.teller.dao;

import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;

@Repository
public class AccountDaoImpl implements AccountDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public Account load() {
      return (Account) sessionFactory.getCurrentSession().get(Account.class, 1l);
   }

   @Override
   public boolean create(Account account) {
      Serializable id = sessionFactory.getCurrentSession().save(account);
      return id != null;
   }

   @Override
   public BigDecimal transact(Transaction transaction) {
      // Intentionally unoptimized
      BigDecimal transactionAmount = transaction.getAmount();
      Session session = sessionFactory.getCurrentSession();
      session.save(transaction);
      Account accountTo = updateAccount(transaction.getAccountTo(), transactionAmount, session);
      if (transaction.getAccountFrom() != null) {
         updateAccount(transaction.getAccountFrom(), transactionAmount.negate(), session);
      }
      return accountTo.getBalance();
   }

   private Account updateAccount(Account accountTo, BigDecimal transactionAmount, Session session) {
      Account loadedAccount = (Account) session.load(Account.class, accountTo.getId());
      loadedAccount.setBalance(loadedAccount.getBalance().add(transactionAmount));
      session.save(loadedAccount);
      return loadedAccount;
   }

}
