package com.hallila.teller.dao;

import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

   @Autowired
   private SessionFactory sessionFactory;

   @SuppressWarnings("unchecked")
   @Override
   public List<Transaction> load(Account account) {
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Transaction.class)
            .add(Restrictions.or(Restrictions.eq("accountTo", account), Restrictions.eq("accountFrom", account)));
      return (List<Transaction>) criteria.list();
   }
   @Override
   public boolean create(Account account) {
      Serializable id = sessionFactory.getCurrentSession().save(account);
      System.out.println(account.getId());
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
