package com.hallila.teller.controller;

import com.hallila.teller.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HelloWorldController {

   @Autowired
   private AccountDao dao;

   @RequestMapping(value = "hello", method = RequestMethod.GET)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public String hello() {
      dao.save();
      return "hello world";
   }

   @RequestMapping(value = "load", method = RequestMethod.GET)
   @ResponseStatus(value = HttpStatus.OK)
   @ResponseBody
   public String load() {
      return dao.load().getName();
   }

}
