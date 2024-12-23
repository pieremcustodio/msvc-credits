package com.piere.bootcamp.credits.model.document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.piere.bootcamp.credits.model.enums.CreditTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCard implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(name = "client_id")
    private String clientId;

    @Field(name = "credit_card_type")
    private CreditTypeEnum creditCardType;

    @Field(name = "credit_limit")
    private Double creditLimit;

    @Field(name = "interest_rate")
    private Double interestRate;

    @Field(name = "available_balance")
    private Double availableBalance;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Field(name = "expiration_date")
    private LocalDate expirationDate;

     @Field(name = "movement_ids")
    private List<String> movementIds;
}
