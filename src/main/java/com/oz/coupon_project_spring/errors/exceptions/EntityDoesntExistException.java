package com.oz.coupon_project_spring.errors.exceptions;

import com.oz.coupon_project_spring.enums.EntityType;

public class EntityDoesntExistException extends Exception {

    public EntityDoesntExistException(EntityType entityType, Constraint constraint){
        super(entityType + constraint.getErrorMsq());
    }
}
