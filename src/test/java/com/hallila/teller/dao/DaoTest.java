package com.hallila.teller.dao;

import com.hallila.teller.config.WebAppConfigurationAware;
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

public class DaoTest extends WebAppConfigurationAware {

   @Autowired
   private Dao dao;

   private Account account;

   @Before
   public void populate() {
      Account account = new Account();
      account.setName("To");
      account.setAddress("AccountTo");
      account.setBalance(BigDecimal.TEN);
      dao.create(account);
      this.account = account;
   }

   @Test
   @Transactional
   public void shouldSaveTransactionToDbWithCorrectAccount() {
      dao.transact(createTransaction(TransactionType.LODGEMENT));
      List<Transaction> transactions = dao.load(account);
      assertThat(transactions.size(), is(not(0)));
      assertThat(transactions.get(0).getAccountTo(), is(account));
   }

   @Test
   @Transactional
   public void shouldUpdateAccountToBalance(){
      dao.transact(createTransaction(TransactionType.LODGEMENT));
      List<Transaction> transactions = dao.load(account);
      assertThat(transactions.get(0).getAccountTo().getBalance(), is(BigDecimal.ONE.add(BigDecimal.TEN)));
   }

   @Test
   @Transactional
   public void shouldUpdateAccountFromBalance(){
      Account from = new Account();
      from.setName("From");
      from.setAddress("AccountFrom");
      from.setBalance(BigDecimal.TEN);
      dao.create(from);

      Transaction transaction = createTransaction(TransactionType.WIRE_TRANSFER);
      transaction.setAccountFrom(from);
      dao.transact(transaction);
      List<Transaction> transactions = dao.load(from);
      assertThat(transactions.get(0).getAccountFrom().getBalance(), is(BigDecimal.TEN.subtract(BigDecimal.ONE)));
   }


   private Transaction createTransaction(TransactionType transactionType) {
      Transaction transaction = new Transaction();
      transaction.setTransactionType(transactionType);
      transaction.setAccountTo(account);
      transaction.setAmount(BigDecimal.ONE);
      return transaction;
   }

}
