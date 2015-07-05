package com.hallila.teller.service;

import com.hallila.teller.TransactionBuilder;
import com.hallila.teller.dao.Dao;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
   private Dao dao;

   @InjectMocks
   private AccountService accountService = new AccountService();

   private static final BigDecimal LODGEMENT_AMOUNT = BigDecimal.ONE;

   @Test
   public void shouldCallDaoWhenCreatingAccount() {
      accountService.create(new Account());
      verify(dao).create(any(Account.class));
   }

   @Test
   public void shouldReturnResponseFromDaoToCaller() {
      when(dao.create(any())).thenReturn(true);
      Boolean response = accountService.create(new Account());
      assertThat(response, is(true));
   }

   @Test
   public void shouldCallDaoLoadWhenLoadingAccount() {
      accountService.load(1l);
      verify(dao).load(any());
   }

   @Test
   public void shouldCallLodgeWhenLodging() {
      Transaction transaction = TransactionBuilder.defaultValues().build();
      accountService.lodge(transaction.getAccountTo().getId(), transaction.getAmount());
      ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
      verify(dao).lodge(captor.capture());
      assertThat(captor.getValue().getAccountTo().getId(), is(transaction.getAccountTo().getId()));
      assertThat(captor.getValue().getAmount(), is(transaction.getAmount()));
   }

   @Test
   public void shouldCallTransactWhenTransferring() {
      accountService.transfer(1l, 2l, LODGEMENT_AMOUNT);
      verify(dao).transact(any());
   }

   @Test
   public void shouldCallTransferWithCorrectTransactionObject() {
      Transaction transaction = TransactionBuilder.defaultValues().build();
      transaction.getAccountTo().setId(2l);
      Account accountFrom = new Account();
      accountFrom.setId(1l);
      transaction.setAccountFrom(accountFrom);
      accountService.transfer(1l, 2l, LODGEMENT_AMOUNT);
      ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
      verify(dao).transact(captor.capture());
      assertThat(captor.getValue().getAccountFrom().getId(), is(transaction.getAccountFrom().getId()));
      assertThat(captor.getValue().getAccountTo().getId(), is(transaction.getAccountTo().getId()));
      assertThat(captor.getValue().getAmount(), is(transaction.getAmount()));

   }

}
