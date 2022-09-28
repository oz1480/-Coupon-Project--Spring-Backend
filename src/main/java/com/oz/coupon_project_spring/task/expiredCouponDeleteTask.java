package com.oz.coupon_project_spring.task;


import com.oz.coupon_project_spring.entities.Coupon;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.repositories.CouponRepository;
import com.oz.coupon_project_spring.service.CompanyService;
import com.oz.coupon_project_spring.util.CouponUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class expiredCouponDeleteTask {

    private final CouponRepository couponRepository;
    private final CompanyService companyService;

    @Scheduled(fixedDelay = 86400000)
    public void deleteExpiredCoupons() {

        List<Coupon> coupons = couponRepository.findAll();

        for (Coupon coupon : coupons) {
            if (CouponUtil.isCouponExpired(coupon.getEndDate())) {
                try {
                    companyService.deleteCoupon(coupon.getId());
                } catch (EntityDoesntExistException e) {
                    System.err.println(e.getMessage());
                }
                System.out.println("The coupon: " + coupon.getTitle() +
                        " has been deleted due to its expiration date: " + coupon.getEndDate());
            }
        }
    }
}

