package com.oz.coupon_project_spring.errors;

import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.errors.exceptions.PurchaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponSystemExceptionHandler {

    @ExceptionHandler
    public ErrorDetails entityDoesntExistExceptionHandling(EntityDoesntExistException e){
        return new ErrorDetails(e.getMessage());
    }

    @ExceptionHandler
    public ErrorDetails purchaseExceptionHandling(PurchaseException e){
        return new ErrorDetails(e.getMessage());
    }
}
