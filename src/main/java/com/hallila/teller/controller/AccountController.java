package com.hallila.teller.controller;

import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import com.hallila.teller.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "account")
public class AccountController {

   @Autowired
   private AccountService accountService;

   @RequestMapping(value = "/create", method = RequestMethod.POST)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public Map<String, Object> createAccount(@RequestBody Account account) {
      Boolean response = accountService.create(account);
      HashMap<String, Object> returnable = new HashMap<>();
      returnable.put("success", response);
      return returnable;
   }

   @RequestMapping(value = "load", method = RequestMethod.GET)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public String load() {
      return "";
   }

   @RequestMapping(value = "lodge", method = RequestMethod.POST)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public Map<String, Object> lodge(Double amount, Long accountId){
      BigDecimal lodgementAmount = BigDecimal.valueOf(amount);
      Transaction transaction = new Transaction();
      Account accountTo = new Account();
      accountTo.setId(accountId);
      transaction.setAccountTo(accountTo);
      transaction.setAmount(lodgementAmount);
      transaction.setDate(LocalDate.now());
      accountService.lodge(transaction);
      HashMap<String, Object> returnable = new HashMap<>();
      returnable.put("success", false);
      returnable.put("balance", amount);
      return returnable;
   }


}
