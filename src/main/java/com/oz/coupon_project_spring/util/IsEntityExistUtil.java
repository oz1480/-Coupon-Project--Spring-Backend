package com.oz.coupon_project_spring.util;

import com.oz.coupon_project_spring.entities.Company;

import com.oz.coupon_project_spring.entities.Customer;
import com.oz.coupon_project_spring.enums.EntityType;
import com.oz.coupon_project_spring.errors.exceptions.Constraint;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.repositories.CompanyRepository;
import com.oz.coupon_project_spring.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
//@RequiredArgsConstructor
//public class IsEntityExistUtil {
//    private final CompanyRepository companyRepository;
//    private final CustomerRepository customerRepository;
//
//    public  Company isCompanyExist(Long id) throws EntityDoesntExistException {
//        Optional<Company> companyOptional = companyRepository.findById(id);
//        if (companyOptional.isEmpty()) {
//            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
//        }
//        return companyOptional.get();
//    }
//
//    public Customer isCustomerExist(Long id) throws EntityDoesntExistException {
//        Optional<Customer> customerOptional = customerRepository.findById(id);
//        if (customerOptional.isEmpty()){
//            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
//        }
//        return customerOptional.get();
//    }
//}
