package com.oz.coupon_project_spring.dto;

import com.oz.coupon_project_spring.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListDtoWrapper {

    private List<CustomerDto> customerDtoList;
}
