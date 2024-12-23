package com.piere.bootcamp.credits.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
   * Type of the credit
   */
public enum CreditTypeEnum {
    PERSONAL("PERSONAL"),

    EMPRESARIAL("EMPRESARIAL");

    private String value;

    CreditTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CreditTypeEnum fromValue(String value) {
        for (CreditTypeEnum b : CreditTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
