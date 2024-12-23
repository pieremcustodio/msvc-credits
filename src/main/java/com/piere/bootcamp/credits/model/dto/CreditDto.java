package com.piere.bootcamp.credits.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.piere.bootcamp.credits.model.MovementDto;
import com.piere.bootcamp.credits.model.document.Credit;
import com.piere.bootcamp.credits.model.enums.CreditTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * CreditDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String clientId;

  private CreditTypeEnum creditType;

  private Double balance;

  private Double interestRate;

  private Double outstandingBalance;

  private List<String> movementIds;

  @ApiModelProperty(hidden = true)
  @ToString.Exclude
  @Builder.Default
  private List<MovementDto> movements = new ArrayList();

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate expirationDate;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate disbursementDate;

  public static CreditDto build() {
    return CreditDto.builder().build();
  }

  public CreditDto toDto(Credit credit) {
    return CreditDto.builder()
      .id(credit.getId())
      .clientId(credit.getClientId())
      .creditType(credit.getCreditType())
      .balance(credit.getBalance())
      .interestRate(credit.getInterestRate())
      .outstandingBalance(credit.getOutstandingBalance())
      .movementIds(credit.getMovementIds())
      .expirationDate(credit.getExpirationDate())
      .disbursementDate(credit.getDisbursementDate())
      .build();
  }  

  public Credit toEntity(CreditDto creditDto) {
    return Credit.builder()
      .id(creditDto.getId())
      .clientId(creditDto.getClientId())
      .creditType(creditDto.getCreditType())
      .balance(creditDto.getBalance())
      .interestRate(creditDto.getInterestRate())
      .outstandingBalance(creditDto.getOutstandingBalance())
      .movementIds(creditDto.getMovementIds())
      .expirationDate(creditDto.getExpirationDate())
      .disbursementDate(creditDto.getDisbursementDate())
      .build();
  }
}

