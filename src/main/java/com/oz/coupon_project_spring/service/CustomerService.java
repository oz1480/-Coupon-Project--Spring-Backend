package com.oz.coupon_project_spring.service;

import com.oz.coupon_project_spring.dto.CompanyDto;
import com.oz.coupon_project_spring.dto.CouponDto;
import com.oz.coupon_project_spring.dto.CustomerDto;
import com.oz.coupon_project_spring.entities.Coupon;
import com.oz.coupon_project_spring.entities.Customer;
import com.oz.coupon_project_spring.enums.Category;
import com.oz.coupon_project_spring.enums.EntityType;
import com.oz.coupon_project_spring.errors.exceptions.Constraint;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.errors.exceptions.PurchaseException;
import com.oz.coupon_project_spring.repositories.CouponRepository;
import com.oz.coupon_project_spring.repositories.CustomerRepository;
import com.oz.coupon_project_spring.util.CouponUtil;
import com.oz.coupon_project_spring.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    public List<CouponDto> getCustomerCoupons(Long customerId) throws EntityDoesntExistException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()){
            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
        }
        List<CouponDto> couponDtoList = new ArrayList<>();
        Customer currentCustomer = customerOptional.get();
        for (Coupon coupon: couponRepository.getCouponsByCustomerId(currentCustomer.getId())) {
            couponDtoList.add(ObjectMappingUtil.couponEntityTDto(coupon));
        }
        return couponDtoList;
    }

    public List<CouponDto> getCustomerCoupons(Long customerId, int maxPrice) throws EntityDoesntExistException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()){
            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
        }
        Customer currentCustomer = customerOptional.get();
        List<CouponDto> currentCustomerCoupons = new ArrayList<>();
        for (Coupon coupon:couponRepository.getCouponsByCustomerId(currentCustomer.getId())) {
            if (coupon.getPrice() <= maxPrice){
                currentCustomerCoupons.add(ObjectMappingUtil.couponEntityTDto(coupon));
            }
        }
        return currentCustomerCoupons;
    }

    public List<CouponDto> getCustomerCoupons(Long customerId, Category category) throws EntityDoesntExistException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()){
            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
        }
        Customer currentCustomer = customerOptional.get();
        List<CouponDto> currentCustomerCoupons = new ArrayList<>();
        for (Coupon coupon:couponRepository.getCouponsByCustomerId(currentCustomer.getId())) {
            if (coupon.getCategory().equals(category)){
                currentCustomerCoupons.add(ObjectMappingUtil.couponEntityTDto(coupon));
            }
        }
        return currentCustomerCoupons;
    }

    public CustomerDto getCustomer(Long customerId) throws EntityDoesntExistException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()){
            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
        }
        Customer customer = customerOptional.get();
        customer.setCoupons((couponRepository.getCouponsByCompanyId(customerId)));
        List<CouponDto> couponDtoList = ObjectMappingUtil.couponsToCouponsDto(customer.getCoupons());
        CustomerDto customerDto = ObjectMappingUtil.customerEntityToDto(customer);
        customerDto.setCoupons(couponDtoList);
        return customerDto;
    }


    public Customer purchase(Long customerId, Long couponId) throws EntityDoesntExistException, PurchaseException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Optional<Coupon> couponOptional = couponRepository.findById(couponId);
        if (customerOptional.isEmpty()){
            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
        }
        if (couponOptional.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COUPON, Constraint.ENTITY_DOESNT_EXIST);
        }
        Customer currentCustomer = customerOptional.get();
        Coupon currentCoupon = couponOptional.get();

        if (currentCoupon.getAmount() <= 0){
            throw new PurchaseException(Constraint.OUT_OF_STOCK);
        }
        if (CouponUtil.isCouponExpired(currentCoupon.getEndDate())){
            throw new PurchaseException(Constraint.COUPON_EXPIRED);
        }
        for (CouponDto couponToCheck:getCustomerCoupons(currentCustomer.getId())) {
            if (couponToCheck.getId().equals(couponId)){
                throw new PurchaseException(Constraint.ALREADY_PURCHASED);
            }
        }
        List<Coupon> currentCustomerCoupons = new ArrayList<>(couponRepository.getCouponsByCustomerId(currentCustomer.getId()));
        CouponUtil.decreaseCouponAmount(currentCoupon);
        currentCustomerCoupons.add(currentCoupon);
        currentCustomer.setCoupons(currentCustomerCoupons);
        customerRepository.save(currentCustomer);
        return currentCustomer;

    }
}
