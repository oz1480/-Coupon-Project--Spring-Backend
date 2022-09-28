package com.oz.coupon_project_spring.controllers;

import com.oz.coupon_project_spring.dto.CompanyDto;
import com.oz.coupon_project_spring.dto.CompanyListDtoWrapper;
import com.oz.coupon_project_spring.dto.CustomerDto;
import com.oz.coupon_project_spring.dto.CustomerListDtoWrapper;
import com.oz.coupon_project_spring.entities.Company;
import com.oz.coupon_project_spring.entities.Customer;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.errors.exceptions.InvalidInputException;
import com.oz.coupon_project_spring.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/add-company")
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) throws InvalidInputException {
        return adminService.createCompany(company);
    }

    @GetMapping("/company/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public CompanyDto getCompany(@PathVariable long id) throws EntityDoesntExistException {
        return adminService.getCompany(id);
    }

    @GetMapping("/companies")
    public CompanyListDtoWrapper getAllCompanies() throws EntityDoesntExistException {
        return new CompanyListDtoWrapper(adminService.getAllCompanies());
    }

    @DeleteMapping("/company")
    public void deleteCompany(@RequestParam long id) throws EntityDoesntExistException{
        adminService.deleteCompany(id);
    }

    @GetMapping("/company-by-email")
    @ResponseStatus(HttpStatus.FOUND)
    public CompanyDto getCompanyByEmail(@RequestParam String email) throws EntityDoesntExistException {
        return adminService.getCompanyByEmail(email);
    }

    @PutMapping("update-company")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCompany(@RequestBody Company company) throws InvalidInputException, EntityDoesntExistException {
        adminService.updateCompany(company);
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer (@RequestBody Customer customer) throws InvalidInputException {
        return adminService.createCustomer(customer);
    }

    @GetMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public CustomerDto getCustomer(@PathVariable long id) throws EntityDoesntExistException {
        return adminService.getCustomer(id);
    }

    @GetMapping("/customer-by-email")
    @ResponseStatus(HttpStatus.FOUND)
    public CustomerDto getCustomer(@RequestParam String email) throws EntityDoesntExistException {
        return adminService.getCustomerByEmail(email);
    }

    @GetMapping("/customers")
    public CustomerListDtoWrapper getAllCustomers() throws EntityDoesntExistException {
        return new CustomerListDtoWrapper(adminService.getAllCustomers());
    }

    @PutMapping("customer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCustomer(@RequestBody Customer customer) throws InvalidInputException, EntityDoesntExistException {
        adminService.updateCustomer(customer);
    }

    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable long id) throws EntityDoesntExistException{
        adminService.deleteCustomer(id);
    }
}
