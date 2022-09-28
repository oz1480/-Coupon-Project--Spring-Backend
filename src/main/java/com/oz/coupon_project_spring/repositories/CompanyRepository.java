package com.oz.coupon_project_spring.repositories;

import com.oz.coupon_project_spring.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
    Boolean existsByEmail(String email);
    }
