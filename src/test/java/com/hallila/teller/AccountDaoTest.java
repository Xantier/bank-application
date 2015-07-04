package com.hallila.teller;

import com.hallila.teller.config.WebAppConfigurationAware;
import com.hallila.teller.dao.AccountDao;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import com.hallila.teller.entity.TransactionType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class AccountDaoTest extends WebAppConfigurationAware {

   @Autowired
   private AccountDao dao;
   private Account account;

   @Before
   public void populate(){
      Account account = new Account();
      account.setName("Name");
      account.setAddress("Address");
      account.setBalance(BigDecimal.TEN);
      dao.create(account);
      this.account = account;
      dao.transact(createTransaction(TransactionType.LODGEMENT));
   }

   @Test
   @Transactional
   public void shouldSaveTransactionToDbWithCorrectAccount(){
      List<Transaction> transactions = dao.load(account);
      assertThat(transactions.size(), is(not(0)));
      assertThat(transactions.get(0).getAccountTo(), is(account));
   }


   private Transaction createTransaction(TransactionType transactionType) {
      Transaction transaction = new Transaction();
      transaction.setTransactionType(transactionType);
      transaction.setAccountTo(account);
      transaction.setAmount(BigDecimal.ONE);
      return transaction;
   }


}
