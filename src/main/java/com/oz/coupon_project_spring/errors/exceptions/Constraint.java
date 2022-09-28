package com.oz.coupon_project_spring.errors.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Constraint  {
    ENTITY_DOESNT_EXIST(" Not find"),
    OUT_OF_STOCK("Coupon is out of stock!"),
    COUPON_EXPIRED("This coupon is expired!"),
    ALREADY_PURCHASED("You already bought this coupon!"),
    END_DATE_AT_OR_BEFORE_START_DATE("End date cannot be before startDate"),
    WRONG_END_DATE("End date can`t be before today!"),
    EXISTING_COUPON("Coupon is already in your coupon list!"),
    INVALID_EMAIL("Invalid email!"),
    INVALID_PASSWORD("Password has to contain- at least 1 uppercase \n" +
            "at least 1 lower case \n" +
            "at least 1 number!"),
    EXISTING_USER("There is another user with same email"),
    INVALID_INPUT("Invalid input!");

    @Getter
    private final String errorMsq;
}
