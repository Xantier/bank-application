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
public class DaoImpl implements Dao {

   @Autowired
   private SessionFactory sessionFactory;

   private Session getSession() {
      return sessionFactory.getCurrentSession();
   }

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
      return id != null;
   }

   @Override
   public BigDecimal lodge(Transaction transaction) {
      BigDecimal transactionAmount = transaction.getAmount();
      sessionFactory.getCurrentSession().save(transaction);
      Account accountTo = updateAccount(transaction.getAccountTo(), transactionAmount);
      return accountTo.getBalance();
   }

   @Override
   public BigDecimal transact(Transaction transaction) {
      // Intentionally unoptimized
      BigDecimal transactionAmount = transaction.getAmount();
      getSession().save(transaction);
      Account accountTo = updateAccount(transaction.getAccountTo(), transactionAmount);
      if (transaction.getAccountFrom() != null) {
         updateAccount(transaction.getAccountFrom(), transactionAmount.negate());
      }
      return accountTo.getBalance();
   }

   private Account updateAccount(Account accountTo, BigDecimal transactionAmount) {
      Session session = getSession();
      Account loadedAccount = (Account) session.load(Account.class, accountTo.getId());
      loadedAccount.setBalance(loadedAccount.getBalance().add(transactionAmount));
      session.save(loadedAccount);
      return loadedAccount;
   }

}
