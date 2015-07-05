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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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
      return returnable(response);
   }

   @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public List<Account> loadAll() {
      return accountService.loadAll();
   }

   @RequestMapping(value = "load", method = RequestMethod.GET)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public Map<String, Object> load(Long accountId) {
      List<Transaction> transactions = accountService.load(accountId);
      HashMap<String, Object> returnable = returnable(false);
      returnable.put("transactions", transactions);
      return returnable;
   }

   @RequestMapping(value = "lodge", method = RequestMethod.POST)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public Map<String, Object> lodge(
         @RequestParam(value="amount") Double amount,
         @RequestParam(value="accountId")Long accountId) {
      BigDecimal lodgementAmount = BigDecimal.valueOf(amount);
      BigDecimal balance = accountService.lodge(accountId, lodgementAmount);
      HashMap<String, Object> returnable = returnable(balance!=null);
      returnable.put("balance", balance);
      return returnable;
   }

   @RequestMapping(value = "transfer", method = RequestMethod.POST)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public Map<String, Object> transfer(Double amount, Long accountFromId, Long accountToId) {
      List<Account> accounts = accountService.transfer(accountFromId, accountToId, BigDecimal.valueOf(amount));
      HashMap<String, Object> returnable = returnable(false);
      returnable.put("accounts", accounts);
      return returnable;
   }

   private HashMap<String, Object> returnable(boolean success) {
      HashMap<String, Object> returnable = new HashMap<>();
      returnable.put("success", success);
      return returnable;
   }

}
