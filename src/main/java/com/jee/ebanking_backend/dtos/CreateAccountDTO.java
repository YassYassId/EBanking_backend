package com.jee.ebanking_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {
    private String type;
    private Double initialBalance;
    private Long customerId;
    private Double interestRate;
    private Double overDraft;
    private String status;
}
