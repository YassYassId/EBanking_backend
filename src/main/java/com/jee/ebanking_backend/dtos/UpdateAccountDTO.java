package com.jee.ebanking_backend.dtos;

import lombok.Data;

@Data
public class UpdateAccountDTO {
    private String status; // "CREATED", "ACTIVATED", "SUSPENDED"
    private Double interestRate;
    private Double overDraft;
}
