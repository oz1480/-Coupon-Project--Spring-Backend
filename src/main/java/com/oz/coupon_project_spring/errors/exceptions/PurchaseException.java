package com.oz.coupon_project_spring.errors.exceptions;

public class PurchaseException extends Exception {

    public PurchaseException(Constraint constraint){
        super(constraint.getErrorMsq());
    }
}
