package com.piere.bootcamp.credits.model.dto.projection;

import java.io.Serializable;

import com.piere.bootcamp.credits.model.dto.CreditCardDto;
import com.piere.bootcamp.credits.model.dto.CreditDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportProjection implements Serializable {
  
    private static final long serialVersionUID = 1L;

    private CreditCardDto creditCard;
    private CreditDto credit;
    private Double amount;

    public ReportProjection(CreditDto credit, Double amount) {
        this.credit = credit;
        this.amount = amount;
    }

    public ReportProjection(CreditCardDto creditCard, Double amount) {
        this.creditCard = creditCard;
        this.amount = amount;
    }
}
