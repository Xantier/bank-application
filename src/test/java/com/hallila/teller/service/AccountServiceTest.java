package com.hallila.teller.service;

import com.hallila.teller.dao.AccountDao;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import com.hallila.teller.entity.TransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

   @Mock
   private AccountDao dao;

   @InjectMocks
   private AccountService accountService = new AccountService();

   private static final BigDecimal LODGEMENT_AMOUNT = BigDecimal.ONE;

   @Test
   public void shouldCallDaoWhenCreatingAccount(){
      accountService.create(new Account());
      verify(dao).create(any(Account.class));
   }

   @Test
   public void shouldReturnResponseFromDaoToCaller(){
      when(dao.create(any())).thenReturn(true);
      Boolean response = accountService.create(new Account());
      assertThat(response, is(true));
   }

   @Test
   public void shouldCallDaoLoadWhenLoadingAccount(){
      Account expected = new Account();
      expected.setName("name");
      when(dao.load()).thenReturn(expected);
      Account account = accountService.load();
      assertThat(account.getName(), is(expected.getName()));
   }

   @Test
   public void shouldBeAbleToLodgeIntoAccount(){
      Transaction transaction = createTransaction(TransactionType.LODGEMENT);
      BigDecimal newBalance = accountService.lodge(transaction);
      assertThat(newBalance, is(LODGEMENT_AMOUNT));
   }

   @Test
   public void shouldCallTransactWhenLodging(){
      Transaction transaction = createTransaction(TransactionType.WIRE_TRANSFER);
      accountService.lodge(transaction);
      verify(dao).transact(any());
   }

   @Test
   public void shouldCallTransactWhenTranferring(){
      accountService.transfer(new Transaction());
      verify(dao).transact(any());
   }

   private Transaction createTransaction(TransactionType transactionType) {
      Account account = new Account();
      Transaction transaction = new Transaction();
      transaction.setTransactionType(transactionType);
      transaction.setAccountTo(account);
      transaction.setAmount(LODGEMENT_AMOUNT);
      return transaction;
   }


}
