package com.oz.coupon_project_spring.controllers;

import com.oz.coupon_project_spring.dto.CompanyDto;
import com.oz.coupon_project_spring.dto.CouponDto;
import com.oz.coupon_project_spring.dto.CouponListDtoWrapper;
import com.oz.coupon_project_spring.entities.Coupon;
import com.oz.coupon_project_spring.enums.Category;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.errors.exceptions.InvalidInputException;
import com.oz.coupon_project_spring.service.CompanyService;
import com.oz.coupon_project_spring.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("company")
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CouponDto addCoupon(@RequestBody CouponDto coupon) throws InvalidInputException, EntityDoesntExistException {
        return ObjectMappingUtil.couponEntityTDto(companyService.addCoupon(coupon));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCoupon(@RequestBody Coupon coupon) throws InvalidInputException, EntityDoesntExistException {
        companyService.updateCoupon(coupon);
    }

    @DeleteMapping("/coupon")
    public void deleteCoupon(@RequestParam long id) throws EntityDoesntExistException {
        companyService.deleteCoupon(id);
    }


    @GetMapping("{id}")
    public CompanyDto getCompany(@PathVariable Long id) throws EntityDoesntExistException {
        return companyService.getCompany(id);
    }

    @GetMapping("/coupons")
    public CouponListDtoWrapper  getCompanyCoupons(@RequestParam long id) throws EntityDoesntExistException {
        return new CouponListDtoWrapper(companyService.getCompanyCoupons(id));
    }

    @GetMapping("/coupons-by-max-price")
    public CouponListDtoWrapper  getCompanyCoupons(@RequestParam long id,
                                             @RequestParam (name = "maxprice") int maxPrice) throws EntityDoesntExistException {
        return new CouponListDtoWrapper(companyService.getCompanyCoupons(id, maxPrice));
    }

    @GetMapping("/coupons-by-category")
    public CouponListDtoWrapper  getCompanyCoupons(@RequestParam long id,
                                             @RequestParam Category category) throws EntityDoesntExistException {
        return new CouponListDtoWrapper(companyService.getCompanyCoupons(id, category));
    }

    @GetMapping("/coupon")
    public CouponDto getCoupon(@RequestParam long id) throws EntityDoesntExistException {
        return companyService.getCoupon(id);
    }





}
