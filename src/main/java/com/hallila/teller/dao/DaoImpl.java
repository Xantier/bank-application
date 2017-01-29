package com.hallila.teller.dao;

import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        // Intentionally unoptimized with multiple calls
        Account acc = (Account) getSession().get(Account.class, account.getId());
        Criteria criteria = getSession().createCriteria(Transaction.class)
              .add(Restrictions.or(Restrictions.eq("accountTo", acc), Restrictions.eq("accountFrom", acc)));
        return (List<Transaction>) criteria.list();
    }

    @Override
    public Account loadAccountById(Long id) {
        return (Account) getSession().get(Account.class, id);
    }

    @Override
    public boolean create(Account account) {
        Serializable id = getSession().save(account);
        return id != null;
    }

    @Override
    public BigDecimal lodge(Transaction transaction) {
        // Intentionally unoptimized with multiple calls
        BigDecimal transactionAmount = transaction.getAmount();
        getSession().save(transaction);
        Account accountTo = updateAccount(transaction.getAccountTo(), transactionAmount);
        return accountTo.getBalance();
    }

    @Override
    public List<Account> transact(Transaction transaction) {
        Assert.notNull(transaction.getAccountFrom());
        // Intentionally unoptimized with multiple calls
        BigDecimal transactionAmount = transaction.getAmount();
        getSession().save(transaction);
        List<Account> returnable = new ArrayList<>();
        returnable.add(updateAccount(transaction.getAccountTo(), transactionAmount));
        returnable.add(updateAccount(transaction.getAccountFrom(), transactionAmount.negate()));
        return returnable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Account> loadAll() {
        return (List<Account>) getSession().createCriteria(Account.class).list();
    }

    private Account updateAccount(Account accountTo, BigDecimal transactionAmount) {
        Session session = getSession();
        Account loadedAccount = (Account) session.get(Account.class, accountTo.getId());
        loadedAccount.setBalance(loadedAccount.getBalance().add(transactionAmount));
        session.save(loadedAccount);
        return loadedAccount;
    }

}
