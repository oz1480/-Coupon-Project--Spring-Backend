package com.oz.coupon_project_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class CouponListDtoWrapper {
    private List<CouponDto> couponDtoList;
}
