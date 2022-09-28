package com.oz.coupon_project_spring.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.oz.coupon_project_spring.enums.Category;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "coupons")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "customers")
@Builder

public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column
    private String description;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int price;

    @Column
    private String image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "customer_to_coupons",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customer;
}
