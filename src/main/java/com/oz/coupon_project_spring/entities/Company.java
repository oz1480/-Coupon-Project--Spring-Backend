package com.oz.coupon_project_spring.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "companies")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Coupon> coupons;

}