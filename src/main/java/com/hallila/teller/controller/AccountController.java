package com.hallila.teller.controller;

import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.LodgeDTO;
import com.hallila.teller.entity.Transaction;
import com.hallila.teller.entity.TransferDTO;
import com.hallila.teller.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public Map<String, Object> load(
          @RequestParam(value = "accountId") Long accountId) {
        List<Transaction> transactions = accountService.load(accountId);
        HashMap<String, Object> returnable = returnable(transactions != null);
        returnable.put("transactions", transactions);
        return returnable;
    }

    @RequestMapping(value = "lodge", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Map<String, Object> lodge(@RequestBody LodgeDTO lodgeDTO) {
        BigDecimal lodgementAmount = BigDecimal.valueOf(lodgeDTO.getAmount());
        BigDecimal balance = accountService.lodge(lodgeDTO.getAccountId(), lodgementAmount);
        HashMap<String, Object> returnable = returnable(balance != null);
        returnable.put("balance", balance);
        return returnable;
    }

    @RequestMapping(value = "transfer",
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> transfer(@RequestBody TransferDTO transferDTO) {
        System.out.println(transferDTO);
        List<Account> accounts = accountService.transfer(
              transferDTO.getAccountFromId(), transferDTO.getAccountToId(), BigDecimal.valueOf(transferDTO.getAmount()));
        HashMap<String, Object> returnable = returnable(accounts != null);
        returnable.put("accounts", accounts);
        return returnable;
    }

    private HashMap<String, Object> returnable(boolean success) {
        HashMap<String, Object> returnable = new HashMap<>();
        returnable.put("success", success);
        return returnable;
    }

}
