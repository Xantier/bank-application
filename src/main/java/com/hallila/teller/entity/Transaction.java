package com.hallila.teller.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   private LocalDate date;

   @Enumerated(EnumType.STRING)
   private TransactionType transactionType;

   @Column(nullable = false)
   private BigDecimal amount;

   @ManyToOne
   private Account accountFrom;

   @ManyToOne(optional = false)
   private Account accountTo;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public LocalDate getDate() {
      return date;
   }

   public void setDate(LocalDate date) {
      this.date = date;
   }

   public TransactionType getTransactionType() {
      return transactionType;
   }

   public void setTransactionType(TransactionType transactionType) {
      this.transactionType = transactionType;
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public void setAmount(BigDecimal amount) {
      this.amount = amount;
   }

   public Account getAccountFrom() {
      return accountFrom;
   }

   public void setAccountFrom(Account accountFrom) {
      this.accountFrom = accountFrom;
   }

   public Account getAccountTo() {
      return accountTo;
   }

   public void setAccountTo(Account accountTo) {
      this.accountTo = accountTo;
   }
}
