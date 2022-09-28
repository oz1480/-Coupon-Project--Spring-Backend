package com.oz.coupon_project_spring.service;

import com.oz.coupon_project_spring.dto.CompanyDto;
import com.oz.coupon_project_spring.dto.CustomerDto;
import com.oz.coupon_project_spring.entities.Company;
import com.oz.coupon_project_spring.entities.Customer;
import com.oz.coupon_project_spring.enums.EntityType;
import com.oz.coupon_project_spring.errors.exceptions.Constraint;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.errors.exceptions.InvalidInputException;
import com.oz.coupon_project_spring.repositories.CompanyRepository;
import com.oz.coupon_project_spring.repositories.CustomerRepository;
import com.oz.coupon_project_spring.util.InputValidationUtil;
import com.oz.coupon_project_spring.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    public Company createCompany (Company company) throws InvalidInputException {
        if (!InputValidationUtil.isEmailValid(company.getEmail())){
            throw new InvalidInputException(Constraint.INVALID_EMAIL);
        }
        if (!InputValidationUtil.isPasswordValid(company.getPassword())){
            throw new InvalidInputException(Constraint.INVALID_PASSWORD);
        }
        if (companyRepository.existsByEmail(company.getEmail())){
            throw new InvalidInputException(Constraint.EXISTING_USER);
        }

        return companyRepository.save(company);

    }

    public CompanyDto getCompany(Long id) throws EntityDoesntExistException {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
        }
        return ObjectMappingUtil.companyEntityToDto(optionalCompany.get());
    }

    public CompanyDto getCompanyByEmail(String email) throws EntityDoesntExistException {
        Optional<Company> optionalCompany = companyRepository.findByEmail(email);
        if (optionalCompany.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
        }
        return ObjectMappingUtil.companyEntityToDto(optionalCompany.get());
    }

    public List<CompanyDto> getAllCompanies(){
        List<Company> companies = new ArrayList<>(companyRepository.findAll());
        List<CompanyDto> companiesDto = new ArrayList<>();
        for (Company c : companies){
            companiesDto.add(ObjectMappingUtil.companyEntityToDto(c));
        }

        if (companies.isEmpty()){
            return null;
        }
        return companiesDto;

    }

    public void updateCompany(Company company) throws InvalidInputException, EntityDoesntExistException {
        Optional<Company> companyOptional = companyRepository.findById(company.getId());
        if (companyOptional.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
        }
        if (!InputValidationUtil.isEmailValid(company.getEmail())){
            throw new InvalidInputException(Constraint.INVALID_EMAIL);
        }

        if (companyRepository.findByEmail(company.getEmail()).isPresent()){
            throw new InvalidInputException(Constraint.EXISTING_USER);
        }

        if (!InputValidationUtil.isPasswordValid(company.getPassword())){
            throw new InvalidInputException(Constraint.INVALID_PASSWORD);
        }

        companyRepository.save(company);
    }

    public Customer createCustomer(Customer customer) throws InvalidInputException {
        if (!InputValidationUtil.isEmailValid(customer.getEmail())){
            throw new InvalidInputException(Constraint.INVALID_EMAIL);
        }

        if (!InputValidationUtil.isPasswordValid(customer.getPassword())){
            throw new InvalidInputException(Constraint.INVALID_PASSWORD);
        }

        if (customerRepository.existsByEmail(customer.getEmail())){
            throw new InvalidInputException(Constraint.EXISTING_USER);
        }


        return customerRepository.save(customer);
    }

    public CustomerDto getCustomer(Long id) throws EntityDoesntExistException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()){
            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
        }
        return ObjectMappingUtil.customerEntityToDto(optionalCustomer.get());
    }

    public CustomerDto getCustomerByEmail(String email) throws EntityDoesntExistException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isEmpty()){
            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
        }
        return ObjectMappingUtil.customerEntityToDto(optionalCustomer.get());
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = new ArrayList<>(customerRepository.findAll());
        List<CustomerDto> customersDto = new ArrayList<>();
        for (Customer c : customers){
            customersDto.add(ObjectMappingUtil.customerEntityToDto(c));
        }
        if (customers.isEmpty()){
            return null;
        }
        return customersDto;
    }

    public void updateCustomer(Customer customer) throws InvalidInputException, InvalidInputException, EntityDoesntExistException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isEmpty()){
            throw new EntityDoesntExistException(EntityType.CUSTOMER, Constraint.ENTITY_DOESNT_EXIST);
        }

        if (customerRepository.findByEmail(customer.getEmail()).isPresent()){
            throw new InvalidInputException(Constraint.EXISTING_USER);
        }

        if (!InputValidationUtil.isEmailValid(customer.getEmail())){
            throw new InvalidInputException(Constraint.INVALID_EMAIL);
        }

        if (!InputValidationUtil.isPasswordValid(customer.getPassword())){
            throw new InvalidInputException(Constraint.INVALID_PASSWORD);
        }

        customerRepository.save(customer);
    }

    public void deleteCompany(long id) throws EntityDoesntExistException {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
        }
        companyRepository.delete(optionalCompany.get());
    }

    public void deleteCustomer(long id) throws EntityDoesntExistException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()){
            throw new EntityDoesntExistException(EntityType.COMPANY, Constraint.ENTITY_DOESNT_EXIST);
        }
        customerRepository.delete(optionalCustomer.get());
    }

}

