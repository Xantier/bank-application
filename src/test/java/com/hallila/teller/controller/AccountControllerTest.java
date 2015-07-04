package com.hallila.teller.controller;

import com.hallila.teller.config.WebAppConfigurationAware;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends WebAppConfigurationAware {

   private MockMvc mockMvc;

   @Resource
   private WebApplicationContext webApplicationContext;

   @Before
   public void setup() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .build();
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
