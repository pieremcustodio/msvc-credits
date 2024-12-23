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
import com.piere.bootcamp.credits.model.document.CreditCard;
import com.piere.bootcamp.credits.model.enums.CreditTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * CreditCardDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String clientId;

  private CreditTypeEnum creditCardType;

  private Double creditLimit;

  private Double interestRate;

  private Double availableBalance;

  private List<String> movementIds;

  @ApiModelProperty(hidden = true)
  @ToString.Exclude
  @Builder.Default
  private List<MovementDto> movements = new ArrayList();

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate expirationDate;

  public static CreditCardDto build() {
    return CreditCardDto.builder().build();
  }

  public CreditCardDto toDto(CreditCard creditCard) {
    return CreditCardDto.builder()
      .id(creditCard.getId())
      .clientId(creditCard.getClientId())
      .creditCardType(creditCard.getCreditCardType())
      .creditLimit(creditCard.getCreditLimit())
      .interestRate(creditCard.getInterestRate())
      .availableBalance(creditCard.getAvailableBalance())
      .movementIds(creditCard.getMovementIds())
      .expirationDate(creditCard.getExpirationDate())
      .build();
  }

  public CreditCard toEntity(CreditCardDto creditCardDto) {
    return CreditCard.builder()
      .id(creditCardDto.getId())
      .clientId(creditCardDto.getClientId())
      .creditCardType(creditCardDto.getCreditCardType())
      .creditLimit(creditCardDto.getCreditLimit())
      .interestRate(creditCardDto.getInterestRate())
      .availableBalance(creditCardDto.getAvailableBalance())
      .movementIds(creditCardDto.getMovementIds())
      .expirationDate(creditCardDto.getExpirationDate())
      .build();
  }
}

