package com.oz.coupon_project_spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oz.coupon_project_spring.entities.Company;
import com.oz.coupon_project_spring.enums.Category;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CouponDto {
    private Long id;
    private String title;
    private Long companyId;
    private Category category;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private int amount;
    private int price;
    private String image;
}
