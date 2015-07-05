package com.hallila.teller.service;

import com.hallila.teller.dao.Dao;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import com.hallila.teller.entity.TransactionType;
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
      accountService.load();
      verify(dao).load(any());
   }

   @Test
   public void shouldCallLodgeWhenLodging(){
      Transaction transaction = createTransaction(TransactionType.LODGEMENT);
      accountService.lodge(transaction.getAccountTo().getId(), transaction.getAmount());
      ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
      verify(dao).lodge(captor.capture());
      assertThat(captor.getValue().getAccountTo().getId(), is(transaction.getAccountTo().getId()));
      assertThat(captor.getValue().getAmount(), is(transaction.getAmount()));
   }

   @Test
   public void shouldCallTransactWhenTransferring(){
      Transaction transaction = createTransaction(TransactionType.LODGEMENT);
      accountService.transfer(1l, 2l, LODGEMENT_AMOUNT);
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
