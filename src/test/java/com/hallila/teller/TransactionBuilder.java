package com.hallila.teller;

import com.hallila.teller.entity.Account;
import com.hallila.teller.entity.Transaction;
import com.hallila.teller.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionBuilder {

   private Transaction transaction;

   private TransactionBuilder() {
      transaction = new Transaction();
   }

   public static TransactionBuilder defaultValues(){
      TransactionBuilder builder = new TransactionBuilder();
      Account account = new Account();
      builder.transactionType(TransactionType.LODGEMENT);
      builder.accountTo(account);
      builder.amount(BigDecimal.ONE);
      return builder;
   }

   public static TransactionBuilder createTransaction() {
      return new TransactionBuilder();
   }

   public TransactionBuilder transactionType(TransactionType transactionType) {
      transaction.setTransactionType(transactionType);
      return this;
   }

   public TransactionBuilder amount(BigDecimal amount) {
      transaction.setAmount(amount);
      return this;
   }

   public TransactionBuilder accountTo(Account account) {
      transaction.setAccountTo(account);
      return this;
   }

   public TransactionBuilder accountFrom(Account account) {
      transaction.setAccountFrom(account);
      return this;
   }

   public TransactionBuilder date(LocalDate date) {
      transaction.setDate(date);
      return this;
   }

   public Transaction build() {
      return transaction;
   }

}
