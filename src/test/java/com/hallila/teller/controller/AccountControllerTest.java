package com.hallila.teller.controller;

import com.hallila.teller.TransactionBuilder;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
   public void shouldGetResponseFromServiceWhenLoading() throws Exception {
      when(service.load(any())).thenReturn(transactions());
      mockMvc.perform(get("/account/load")
            .param("accountId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(false))) //for now
            .andExpect(jsonPath("transactions").isArray());
   }

   private List<Transaction> transactions() {
      List<Transaction> returnable = new ArrayList<>();
      returnable.add(TransactionBuilder.defaultValues().build());
      return returnable;
   }

   @Test
   public void shouldResponseWithSuccessMsgAndAmountWhenLodging() throws Exception {
      when(service.lodge(any(), any())).thenReturn(BigDecimal.ONE);
      mockMvc.perform(post("/account/lodge")
            .param("amount", "1.0")
            .param("accountId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("balance", is(1)));
   }

   @Test
   public void shouldCallAccountServiceAccountAndTransactionAmount() throws Exception {
      when(service.lodge(any(), any())).thenReturn(BigDecimal.ONE);
      mockMvc.perform(post("/account/lodge")
            .param("amount", "1.0")
            .param("accountId", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("balance", is(1)));

      ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
      ArgumentCaptor<BigDecimal> bigDecimalCaptor = ArgumentCaptor.forClass(BigDecimal.class);
      verify(service).lodge(captor.capture(), bigDecimalCaptor.capture());
      assertThat(captor.getValue(), is(1l));
      assertThat(bigDecimalCaptor.getValue(), is(BigDecimal.valueOf(1.0)));
   }

   @Test
   public void shouldCallServiceTransferWithCorrectParams() throws Exception {
      mockMvc.perform(post("/account/transfer")
            .param("amount", "1.0")
            .param("accountFromId", "1")
            .param("accountToId", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("accounts").isArray());

      ArgumentCaptor<Long> fromCaptor = ArgumentCaptor.forClass(Long.class);
      ArgumentCaptor<Long> toCaptor = ArgumentCaptor.forClass(Long.class);
      ArgumentCaptor<BigDecimal> bigDecimalCaptor = ArgumentCaptor.forClass(BigDecimal.class);
      verify(service).transfer(fromCaptor.capture(), toCaptor.capture(), bigDecimalCaptor.capture());
      assertThat(fromCaptor.getValue(), is(1l));
      assertThat(toCaptor.getValue(), is(2l));
      assertThat(bigDecimalCaptor.getValue(), is(BigDecimal.valueOf(1.0)));
   }

}
