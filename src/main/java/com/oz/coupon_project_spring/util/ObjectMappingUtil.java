package com.oz.coupon_project_spring.util;


import com.oz.coupon_project_spring.dto.CompanyDto;
import com.oz.coupon_project_spring.dto.CouponDto;
import com.oz.coupon_project_spring.dto.CustomerDto;
import com.oz.coupon_project_spring.entities.Company;
import com.oz.coupon_project_spring.entities.Coupon;
import com.oz.coupon_project_spring.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class ObjectMappingUtil {
    public static Customer customerDtoToEntity(CustomerDto customerDto) {
        return Customer.builder()
                .email(customerDto.getEmail())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .password(customerDto.getPassword())
                .build();
    }

    public static CustomerDto customerEntityToDto(Customer customer) {
       return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .password(customer.getPassword())
                .build();
    }

    public static Company companyDtoToEntity(CompanyDto companyDto){
        return Company.builder()
                .name(companyDto.getName())
                .email(companyDto.getEmail())
                .password(companyDto.getPassword())
                .build();
    }

    public static CompanyDto companyEntityToDto(Company company){
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .email(company.getEmail())
                .password(company.getPassword())
                .build();
    }

    public static Coupon couponDtoToCoupon(final CouponDto couponDto) {
        return Coupon.builder().
                id(couponDto.getId()).
                company(Company.builder().id(couponDto.getCompanyId()).build()).
                title(couponDto.getTitle()).
                category(couponDto.getCategory()).
                description(couponDto.getDescription()).
                startDate(couponDto.getStartDate()).
                endDate(couponDto.getEndDate()).
                amount(couponDto.getAmount()).
                price(couponDto.getPrice()).
                image(couponDto.getImage()).
                build();
    }

    public static CouponDto couponEntityTDto(final Coupon coupon) {

        return CouponDto.builder().
                id(coupon.getId()).
                companyId(coupon.getCompany().getId()).
                title(coupon.getTitle()).
                category(coupon.getCategory()).
                description(coupon.getDescription()).
                startDate(coupon.getStartDate()).
                endDate(coupon.getEndDate()).
                amount(coupon.getAmount()).
                price(coupon.getPrice()).
                image(coupon.getImage()).
                build();
    }

    public static List<CouponDto> couponsToCouponsDto(final List<Coupon> coupons) {

        List<CouponDto> dtoList = new ArrayList<>();

        if (coupons != null) {
            for (Coupon coupon : coupons) {
                CouponDto couponDto = couponEntityTDto(coupon);
                dtoList.add(couponDto);
            }
        }
        return dtoList;
    }

    public static List<Coupon> couponsDtoToCoupons(final List<CouponDto> couponDtoList) {

        List<Coupon> couponsList = new ArrayList<>();

        if (couponDtoList != null) {
            for (CouponDto couponDto : couponDtoList) {
                Coupon coupon = couponDtoToCoupon(couponDto);
                couponsList.add(coupon);
            }
        }
        return couponsList;
    }
}
