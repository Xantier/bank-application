package com.hallila.teller.controller;

import com.hallila.teller.config.WebAppConfigurationAware;
import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import com.hallila.teller.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends WebAppConfigurationAware {

   private MockMvc mockMvc;

   @InjectMocks
   private AccountController accountController;

   @Mock
   private AccountService service;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
   }

   @Test
   public void shouldCallServiceWithAccountToBeCreated() throws Exception {
      mockMvc.perform(post("/account/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"hello\"}"));
      Account expected = new Account();
      expected.setName("hello");
      ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
      verify(service).create(captor.capture());
      assertThat(captor.getValue().getName(), is(expected.getName()));
   }

   @Test
   public void shouldReturnResponseFromService() throws Exception {
      when(service.create(any())).thenReturn(true);
      mockMvc.perform(post("/account/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"hello\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)));
   }

   @Test
   public void shouldRespondToValidPostRequest() throws Exception {
      mockMvc.perform(post("/account/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"hello\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(false))); //for now
   }

   @Test
   public void shouldResponseWithSuccessMsgAndAmountWhenLodging() throws Exception{
      mockMvc.perform(post("/account/lodge")
            .param("amount", "1.0")
            .param("accountId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("balance", is(1.0)));
   }

   @Test
   public void shouldCallAccountServiceWithTransaction() throws Exception {
      Transaction transaction = new Transaction();
      Account accountTo = new Account();
      accountTo.setId(1l);
      transaction.setAccountTo(accountTo);
      transaction.setAmount(BigDecimal.valueOf(1.0));

      mockMvc.perform(post("/account/lodge")
            .param("amount", "1.0")
            .param("accountId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("balance", is(1.0)));

      ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
      verify(service).lodge(captor.capture());
      assertThat(captor.getValue().getAccountTo().getId(), is(transaction.getAccountTo().getId()));
      assertThat(captor.getValue().getAmount(), is(transaction.getAmount()));
   }
}
