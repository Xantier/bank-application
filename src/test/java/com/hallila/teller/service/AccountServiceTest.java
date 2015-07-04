package com.hallila.teller.service;

import com.hallila.teller.dao.AccountDao;
import com.hallila.teller.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

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

}
