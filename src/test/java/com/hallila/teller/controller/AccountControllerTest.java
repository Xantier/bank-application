package com.hallila.teller.controller;

import com.hallila.teller.config.WebAppConfigurationAware;
import com.hallila.teller.entity.Account;
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
            .andExpect(jsonPath("success", is(true))); //for now
   }

   @Test
   public void shouldRespondToValidPostRequest() throws Exception {
      mockMvc.perform(post("/account/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"hello\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(false))); //for now
   }
}
