package com.oz.coupon_project_spring.dto;

import com.oz.coupon_project_spring.entities.Coupon;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CompanyDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<CouponDto> coupons;

}
