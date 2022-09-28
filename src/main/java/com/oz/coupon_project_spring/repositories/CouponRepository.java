package com.oz.coupon_project_spring.repositories;

import com.oz.coupon_project_spring.dto.CouponDto;
import com.oz.coupon_project_spring.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> getCouponsByCompanyId (Long companyId);
    List<Coupon> getCouponsByCustomerId(Long customerId);
}
