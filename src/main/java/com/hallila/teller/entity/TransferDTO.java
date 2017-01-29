package com.hallila.teller.entity;

public class TransferDTO {
    private Double amount;
    private Long accountFromId;
    private Long accountToId;

    public TransferDTO() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(Long accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Long getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(Long accountToId) {
        this.accountToId = accountToId;
    }
}