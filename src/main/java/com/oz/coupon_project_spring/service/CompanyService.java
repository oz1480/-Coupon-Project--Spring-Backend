package com.oz.coupon_project_spring.service;

import com.oz.coupon_project_spring.dto.CompanyDto;
import com.oz.coupon_project_spring.dto.CouponDto;
import com.oz.coupon_project_spring.entities.Company;
import com.oz.coupon_project_spring.entities.Coupon;
import com.oz.coupon_project_spring.enums.Category;
import com.oz.coupon_project_spring.enums.EntityType;
import com.oz.coupon_project_spring.errors.exceptions.Constraint;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.errors.exceptions.InvalidInputException;
import com.oz.coupon_project_spring.repositories.CompanyRepository;
import com.oz.coupon_project_spring.repositories.CouponRepository;
import com.oz.coupon_project_spring.util.CouponUtil;
import com.oz.coupon_project_spring.util.InputValidationUtil;
import com.oz.coupon_project_spring.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

    public Coupon addCoupon(CouponDto newCoupon) throws InvalidInputException, EntityDoesntExistException {
        for (Coupon coupon : couponRepository.findAll()) {
            if (coupon.getTitle().equals(newCoupon.getTitle()) && coupon.getCompany().getId().equals(newCoupon.getCompanyId())) {
                throw new InvalidInputException(Constraint.EXISTING_COUPON);
            }
        }
        if (!InputValidationUtil.isDateValid((newCoupon.getStartDate().toString()))
                || !InputValidationUtil.isDateValid((newCoupon.getEndDate().toString()))) {
            throw new InvalidInputException(Constraint.INVALID_INPUT);
        }

        if (newCoupon.getEndDate().isBefore(newCoupon.getStartDate())) {
            throw new InvalidInputException(Constraint.END_DATE_AT_OR_BEFORE_START_DATE);
        }
        if (CouponUtil.isCouponExpired(newCoupon.getEndDate())) {
            throw new InvalidInputException(Constraint.WRONG_END_DATE);
        }
        Coupon coupon = couponRepository.save(ObjectMappingUtil.couponDtoToCoupon(newCoupon));
        Optional<Company> optionalCompany = companyRepository.findById(coupon.getCompany().getId());
        if (optionalCompany.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
        }
        Company currentCompany = optionalCompany.get();
        List<Coupon> companyCoupons = new ArrayList<>();
        companyCoupons.add(coupon);
        currentCompany.setCoupons(companyCoupons);

        return coupon;
    }

    public void updateCoupon(Coupon updatedCoupon) throws InvalidInputException, EntityDoesntExistException {
        for (Coupon coupon : couponRepository.findAll()) {
            if (coupon.getId().equals(updatedCoupon.getId())) {
                if (updatedCoupon.getEndDate().isBefore(updatedCoupon.getStartDate())) {
                    throw new InvalidInputException(Constraint.END_DATE_AT_OR_BEFORE_START_DATE);
                }
                if (CouponUtil.isCouponExpired(updatedCoupon.getEndDate())) {
                    throw new InvalidInputException(Constraint.WRONG_END_DATE);
                }
                if (!InputValidationUtil.isDateValid(updatedCoupon.getEndDate().toString()) ||
                        !InputValidationUtil.isDateValid(updatedCoupon.getStartDate().toString())) {
                    throw new InvalidInputException(Constraint.INVALID_INPUT);
                }
                couponRepository.save(updatedCoupon);
            }
            throw new EntityDoesntExistException(EntityType.COUPON, Constraint.ENTITY_DOESNT_EXIST);
        }
    }

    public void deleteCoupon(Long id) throws EntityDoesntExistException {
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isEmpty()) {
            throw new EntityDoesntExistException(EntityType.COUPON, Constraint.ENTITY_DOESNT_EXIST);
        }
        Coupon couponToDelete = coupon.get();
        couponRepository.delete(couponToDelete);
    }

    public CompanyDto getCompany(Long companyId) throws EntityDoesntExistException {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
        }
        Company currentCompany = companyOptional.get();
        currentCompany.setCoupons((couponRepository.getCouponsByCompanyId(companyId)));
        List<CouponDto> couponDtoList = ObjectMappingUtil.couponsToCouponsDto(currentCompany.getCoupons());
        CompanyDto companyDto = ObjectMappingUtil.companyEntityToDto(currentCompany);
        companyDto.setCoupons(couponDtoList);
        return companyDto;
    }

    public List<CouponDto> getCompanyCoupons(Long companyId) throws EntityDoesntExistException {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) {
            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
        }
        List<CouponDto> companyDtoCoupons = ObjectMappingUtil.couponsToCouponsDto(couponRepository.getCouponsByCompanyId(companyId));
        return companyDtoCoupons;
    }

    public List<CouponDto> getCompanyCoupons(Long companyId, int maxPrice) throws EntityDoesntExistException {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            throw new EntityDoesntExistException(EntityType.COUPON, Constraint.ENTITY_DOESNT_EXIST);
        }
        List<CouponDto> companyCouponsByMaxPrice = new ArrayList<>();
        for (Coupon couponToCheck: couponRepository.getCouponsByCompanyId(companyId)) {
            if (couponToCheck.getPrice() <= maxPrice){
                companyCouponsByMaxPrice.add(ObjectMappingUtil.couponEntityTDto(couponToCheck));
            }
        }
        return companyCouponsByMaxPrice;
    }

    public List<CouponDto> getCompanyCoupons(Long companyId, Category category) throws EntityDoesntExistException {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()) {
            throw new EntityDoesntExistException(EntityType.COUPON, Constraint.ENTITY_DOESNT_EXIST);
        }
        List<CouponDto> companyCouponsByCategory = new ArrayList<>();
        for (Coupon couponToCheck: couponRepository.getCouponsByCompanyId(companyId)) {
            if (couponToCheck.getCategory().equals(category)){
                companyCouponsByCategory.add(ObjectMappingUtil.couponEntityTDto(couponToCheck));
            }
        }
        return companyCouponsByCategory;
    }

    public CouponDto getCoupon(long couponId) throws EntityDoesntExistException {
        Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
        if (optionalCoupon.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COUPON, Constraint.ENTITY_DOESNT_EXIST);
        }
        Coupon coupon = optionalCoupon.get();
        return ObjectMappingUtil.couponEntityTDto(coupon);
    }
}
