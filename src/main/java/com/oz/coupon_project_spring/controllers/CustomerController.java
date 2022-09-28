package com.oz.coupon_project_spring.controllers;

import com.oz.coupon_project_spring.dto.CouponDto;
import com.oz.coupon_project_spring.dto.CustomerDto;
import com.oz.coupon_project_spring.entities.Coupon;
import com.oz.coupon_project_spring.entities.Customer;
import com.oz.coupon_project_spring.enums.Category;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.errors.exceptions.PurchaseException;
import com.oz.coupon_project_spring.repositories.CouponRepository;
import com.oz.coupon_project_spring.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@RequestMapping("customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CouponRepository couponRepository;

    @GetMapping("{id}")
    public CustomerDto getCustomer(@PathVariable long id) throws EntityDoesntExistException {
        return customerService.getCustomer(id);
    }

    @GetMapping("/coupons")
    public List<CouponDto> getCustomerCoupons(@RequestParam long id) throws EntityDoesntExistException {
        return customerService.getCustomerCoupons(id);
    }

    @GetMapping("/coupons-by-max-price")
    public List<CouponDto> getCustomerCoupons(@RequestParam long id,
                                              @RequestParam (name = "maxprice") int maxPrice) throws EntityDoesntExistException {
        return customerService.getCustomerCoupons(id, maxPrice);
    }

    @GetMapping("/coupons-by-category")
    public List<CouponDto> getCustomerCoupons(@RequestParam long id,
                                              @RequestParam Category category) throws EntityDoesntExistException {
        return customerService.getCustomerCoupons(id, category);
    }

    @GetMapping("/purchase")
    public Customer purchaseCoupon(@RequestParam (name = "id")long customerId,
                                  @RequestParam (name = "coupon")long couponId) throws PurchaseException, EntityDoesntExistException {
        System.out.println(LocalDate.now() + "Coupon: " + couponRepository.findById(couponId) + " purchased!");
        return customerService.purchase(customerId, couponId);
    }




}
