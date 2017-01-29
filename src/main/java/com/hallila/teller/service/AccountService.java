package com.hallila.teller.service;

import com.hallila.teller.dao.Dao;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import com.hallila.teller.entity.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private Dao accountDao;

    @Transactional
    public Boolean create(Account account) {
        return accountDao.create(account);
    }

    @Transactional(readOnly = true)
    public List<Transaction> load(Long accountId) {
        return accountDao.load(account(accountId));
    }

    @Transactional
    public BigDecimal lodge(Long accountId, BigDecimal amount) {
        Account accountTo = account(accountId);
        Transaction transaction = transaction(amount, accountTo);
        transaction.setTransactionType(TransactionType.LODGEMENT);
        return accountDao.lodge(transaction);
    }

    @Transactional
    public List<Account> transfer(Long accountFromId, Long accountToId, BigDecimal amount) {
        Account accountFrom = loadAccount(accountFromId);
        if (amount.compareTo(accountFrom.getBalance()) > 0) {
            return null;
        }
        Account accountTo = loadAccount(accountToId);
        Transaction transaction = transaction(amount, accountTo);
        transaction.setAccountFrom(accountFrom);
        transaction.setTransactionType(TransactionType.WIRE_TRANSFER);
        return accountDao.transact(transaction);
    }

    @Transactional(readOnly = true)
    public List<Account> loadAll() {
        return accountDao.loadAll();
    }


    @Transactional(readOnly = true)
    public Account loadAccount(Long id) {
        return accountDao.loadAccountById(id);
    }


    private Transaction transaction(BigDecimal amount, Account accountTo) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());
        transaction.setAccountTo(accountTo);
        return transaction;
    }

    private Account account(Long id) {
        Account accountFrom = new Account();
        accountFrom.setId(id);
        return accountFrom;
    }

}
