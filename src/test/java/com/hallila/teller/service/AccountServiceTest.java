package com.hallila.teller.service;

import com.hallila.teller.dao.AccountDao;
import com.hallila.teller.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

}
