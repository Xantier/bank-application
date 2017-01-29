package com.hallila.teller.entity;

public class LodgeDTO {
    private Double amount;
    private Long accountId;

    public LodgeDTO() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
