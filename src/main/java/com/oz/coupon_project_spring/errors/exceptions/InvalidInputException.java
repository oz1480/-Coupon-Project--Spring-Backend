package com.oz.coupon_project_spring.errors.exceptions;

public class InvalidInputException extends Exception {
    public InvalidInputException(Constraint constraint) {
        super(constraint.getErrorMsq());
    }
}