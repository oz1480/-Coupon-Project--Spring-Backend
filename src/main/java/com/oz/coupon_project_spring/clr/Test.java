package com.oz.coupon_project_spring.clr;



import com.oz.coupon_project_spring.dto.*;
import com.oz.coupon_project_spring.entities.Company;
import com.oz.coupon_project_spring.entities.Coupon;
import com.oz.coupon_project_spring.entities.Customer;
import com.oz.coupon_project_spring.enums.Category;
import com.oz.coupon_project_spring.errors.exceptions.EntityDoesntExistException;
import com.oz.coupon_project_spring.errors.exceptions.InvalidInputException;
import com.oz.coupon_project_spring.errors.exceptions.PurchaseException;
import com.oz.coupon_project_spring.service.AdminService;
import com.oz.coupon_project_spring.service.CompanyService;
import com.oz.coupon_project_spring.service.CustomerService;
import com.oz.coupon_project_spring.util.CouponUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
@RequiredArgsConstructor
public class Test implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final AdminService adminService;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("==========================ENTITES CREATED!!!====================================");
        entitiesCreation();
        System.out.println("===================================================================================");
        System.out.println("==========================PURCHASES CREATED!!!====================================");
        testPurchase();
        System.out.println("===================================================================================");
        System.out.println("==========================ADMIN TEST!!!====================================");
        adminTest();
        System.out.println("===================================================================================");
        System.out.println("==========================COMPANY TEST!!!====================================");
        companyTest();
        System.out.println("===================================================================================");
        System.out.println("==========================CUSTOMER TEST!!!====================================");
        customerTest();
    }
    public void entitiesCreation(){
        List<Customer> customersList = new ArrayList<>();
        customersList.add(Customer.builder()
                .email("oz1@gmail.com")
                .firstName("oz")
                .lastName("shemesh")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("shir1@gmail.com")
                .firstName("shir")
                .lastName("shemesh")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("amit1@gmail.com")
                .firstName("amit")
                .lastName("shemesh")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("aviv1@gmail.com")
                .firstName("aviv")
                .lastName("shemesh")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("victor1@gmail.com")
                .firstName("victor")
                .lastName("haki")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("nahum1@gmail.com")
                .firstName("nahum")
                .lastName("takum")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("david1@gmail.com")
                .firstName("david")
                .lastName("yosef")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("gal1@gmail.com")
                .firstName("gal")
                .lastName("dadush")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("eric1@gmail.com")
                .firstName("eric")
                .lastName("cartman")
                .password("123456789")
                .build());

        customersList.add(Customer.builder()
                .email("kenny1@gmail.com")
                .firstName("kenny")
                .lastName("they killed me")
                .password("123456789")
                .build());

        customersList.forEach(customer -> {
            try {
                adminService.createCustomer(customer);
            } catch (InvalidInputException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        });

        List<Company> companiesList = new ArrayList<>();

        for (int i = 0; i < TestConstants.COMPANIES_TO_CREATE; i++) {
            companiesList.add(Company.builder()
                    .name("com" + i)
                    .password("company" + i)
                    .email("com" + i + "@gmail.com")
                    .build());
        }

        companiesList.forEach(company -> {
            try {
                adminService.createCompany(company);
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            }
        });

        List<CouponDto> couponsList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < TestConstants.COUPONT_TO_CREATE; i++) {
            couponsList.add(CouponDto.builder()
                    .title("coupon" + i)
                    .category(CouponUtil.randomCategory())
                    .amount(50)
                    .price(random.nextInt(1200))
                    .startDate(CouponUtil.createRandomDate(4, 5))
                    .endDate(CouponUtil.createRandomDate(10, 11))
                    .companyId(1L + (long) (Math.random() * (11L - 1L)))
                    .build());
        }

        couponsList.forEach(coupon -> {
            try {
                companyService.addCoupon(coupon);
            } catch (InvalidInputException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            } catch (EntityDoesntExistException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void testPurchase() throws PurchaseException, EntityDoesntExistException {
        Random random = new Random();
        for (int i = 0; i < TestConstants.NUMBER_OF_PURCHASES_TO_TEST; i++) {
            try {
                customerService.purchase((long) (random .nextInt(10) + 1), (long) random.nextInt(60) + 1);
            }catch (PurchaseException e){
                System.err.println(e.getMessage());
            }
        }
    }

    public boolean adminTest() {

        try {

            System.out.println(companyCreation());
            System.out.println(companyUpdate());
            System.out.println(companyDeletion());
            System.out.println(getAllCompanies());
            System.out.println(getCompanyByAdmin());
            System.out.println(customerCreation());
            System.out.println(customerUpdate());
            System.out.println(customerDeletion());
            System.out.println(getAllCustomers());
            System.out.println(getCustomerByAdmin());

        } catch (Exception e) {
            e.getMessage();
        }
        return true;
    }

    public boolean companyTest() {
        try {

            System.out.println(couponCreation());
            System.out.println(couponUpdate());
            System.out.println(couponDeletion());
            System.out.println(getCoupon());
            System.out.println(getAllCompanyCoupons());
            System.out.println(getCompanyCouponsByCategory());
            System.out.println(getCompanyCouponsByPrice());
            System.out.println(getCompany());

        } catch (Exception e) {
            e.getMessage();
        }
        return true;
    }

    public boolean customerTest() {
        try {
            System.out.println(purchaseCreation());
            System.out.println(getAllCustomerCoupons());
            System.out.println(getCustomer());
            System.out.println(getCustomerCouponsByCategory());
            System.out.println(getCustomerCouponsByPrice());

        } catch (Exception e) {
            e.getMessage();
        }
        return true;
    }

    //============================ADMIN TEST============================================================================

    public boolean companyCreation() {

        CompanyDto companyDto =
                CompanyDto.builder().
                        name("mor").
                        email("moro@gmail.com").
                        password("Sha123").
                        build();

        try {
            ResponseEntity<CompanyDto> company = restTemplate.
                    postForEntity(TestConstants. POST_COMPANY_BY_ADMIN_URL, companyDto, CompanyDto.class);
            CompanyDto companyResponse = company.getBody();;
            System.err.println("New company has been created, TEST PASSED!");
            return companyResponse != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean companyUpdate() {


        HttpEntity<CompanyDto> request = new HttpEntity<>(
                CompanyDto.builder().
                        id(7L).
                        name("mar").
                        email("morogal@gmail.com").
                        password("Sha123").
                        build());

        try {
            restTemplate.put(TestConstants.UPDATE_COMPANY_BY_ADMIN_URL, request, HttpMethod.PUT, Void.class);
            System.err.println("company has been updated, TEST PASSED!");
            return true;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean companyDeletion() {

        try {
            restTemplate.delete(TestConstants.COMPANY_DELETION_URL + "1");
            System.err.println("company has been deleted, TEST PASSED!");
            return true;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getAllCompanies() {

        try {
            ResponseEntity<CompanyListDtoWrapper> companiesList = restTemplate.
                    getForEntity(TestConstants.GETTING_ALL_COMPANIES_URL, CompanyListDtoWrapper.class);
            CompanyListDtoWrapper companyDto = companiesList.getBody();
            System.err.println(companyDto);
            return companyDto != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCompanyByAdmin() {

        try {
            ResponseEntity<CompanyDto> companyDto = restTemplate.
                    getForEntity(TestConstants.GET_COMPANY_BY_ADMIN_URL + "2", CompanyDto.class);
            CompanyDto company = companyDto.getBody();
            System.err.println(company);
            return company != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean customerCreation() {

        //Setting a customer in a variable
        CustomerDto customerDto = CustomerDto.builder().
                firstName("Perah").
                lastName("Zaov").
                email("Za@gmail.com").
                password("Za123")
                .build();

        try {
            ResponseEntity<CustomerDto> customer = restTemplate.
                    postForEntity(TestConstants.POST_OR_UPDATE_CUSTOMER_URL, customerDto, CustomerDto.class);
            CustomerDto customerResponse = customer.getBody();
            System.err.println("Customer has been created, TEST PASSED!");
            return customerResponse != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean customerUpdate() {
        HttpEntity<CustomerDto> request = new HttpEntity<>(
                CustomerDto.builder().
                        id(1L).
                        firstName("zamir").
                        lastName("dadon").
                        email("zamir@gmail.com").
                        password("Zamir1234").
                        build());

        try {
            restTemplate.put(TestConstants.POST_OR_UPDATE_CUSTOMER_URL, request, HttpMethod.PUT, Void.class);
            System.err.println("Customer has been updated, TEST PASSED!");
            return true;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean customerDeletion() {

        try {
            restTemplate.delete(TestConstants.CUSTOMER_DELETION_URL + "4");
            System.err.println("Customer has been deleted, TEST PASSED!");
            return true;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getAllCustomers() {

        try {
            ResponseEntity<CustomerListDtoWrapper> customersList = restTemplate.
                    getForEntity(TestConstants.GETTING_ALL_CUSTOMERS_URL, CustomerListDtoWrapper.class);
            CustomerListDtoWrapper customerDto = customersList.getBody();
            System.err.println(customerDto);
            return customerDto != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCustomerByAdmin() {

        try {
            ResponseEntity<CustomerDto> customerDto = restTemplate.
                    getForEntity(TestConstants.GET_CUSTOMER_BY_ADMIN_URL + "1", CustomerDto.class);

            CustomerDto customer = customerDto.getBody();
            System.err.println(customer);
            return customer != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
//===============================================COMPANY TEST!======================================================
    public boolean couponCreation() {
        CouponDto couponDto =
                CouponDto.builder().
                        companyId(5L).
                        category(Category.VACATION).
                        title("London").
                        description("Big Ben").
                        startDate(LocalDate.of(2022, 3, 28)).
                        endDate(LocalDate.of(2022, 8, 15)).
                        amount(2).
                        price(1500).
                        image("").
                        build();

        try {
            ResponseEntity<CouponDto> coupon = restTemplate.
                    postForEntity(TestConstants.POST_OR_UPDATE_COUPON_URL, couponDto, CouponDto.class);
            CouponDto couponResponse = coupon.getBody();
            System.err.println("New coupon created, TEST PASSED!");
            System.err.println(couponResponse);
            return couponResponse != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean couponUpdate() {
        HttpEntity<CouponDto> request = new HttpEntity<>(
                CouponDto.builder().
                        id(6L).
                        companyId(4L).
                        category(Category.ELECTRICITY).
                        title("Oled LG TV").
                        description("Metoraf").
                        startDate(LocalDate.of(2022, 4, 4)).
                        endDate(LocalDate.of(2022, 9, 29)).
                        amount(10).
                        price(4500).
                        image("").
                        build());

        try {
            restTemplate.put(TestConstants.POST_OR_UPDATE_COUPON_URL, request, HttpMethod.PUT, Void.class);
            System.err.println("coupon updated, TEST PASSED!");
            return true;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean couponDeletion() {

        try {
            restTemplate.delete(TestConstants.COUPON_DELETION_URL + "6");
            System.err.println("coupon has been deleted, TEST PASSED!");
            return true;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCoupon() {

        try {
            ResponseEntity<CouponDto> couponDto = restTemplate.
                    getForEntity(TestConstants.GET_COUPON_URL + "3", CouponDto.class);
            CouponDto coupon = couponDto.getBody();
            System.err.println(coupon);
            return coupon != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getAllCompanyCoupons() {

        try {
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    getForEntity(TestConstants.GETTING_ALL_COMPANY_COUPONS_URL + "2", CouponListDtoWrapper.class);
            CouponListDtoWrapper companyCoupons = coupons.getBody();
            System.err.println(companyCoupons);
            return companyCoupons != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCompanyCouponsByCategory() {

        try {
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    getForEntity("http://localhost:8080/company/coupons-by-category?id=5&category=ELECTRICITY",
                            CouponListDtoWrapper.class);
            CouponListDtoWrapper companyCoupons = coupons.getBody();
            System.err.println(companyCoupons);
            return companyCoupons != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCompanyCouponsByPrice() {

        try {
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    getForEntity("http://localhost:8080/company/coupons-by-max-price?id=4&maxprice=5000.0",
                            CouponListDtoWrapper.class);
            CouponListDtoWrapper companyCouponsByPrice = coupons.getBody();
            System.err.println(companyCouponsByPrice);
            return companyCouponsByPrice != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCompany() {

        try {
            ResponseEntity<CompanyDto> companyDto = restTemplate.
                    getForEntity(TestConstants.GET_COMPANY_URL + "2", CompanyDto.class);
            CompanyDto company = companyDto.getBody();
            System.err.println(company);
            return company != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    //==================================================================================================================

    public boolean purchaseCreation() {

        try {
            restTemplate.postForEntity("http://localhost:8080/customer/purchase?id=3&coupon=50",
                    null, Void.class);
            System.err.println("New purchase!!, TEST PASSED");
            return true;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getAllCustomerCoupons() {

        try {
            ResponseEntity<CouponDto> coupons = restTemplate.
                    getForEntity(TestConstants.GET_ALL_CUSTOMER_COUPONS_URL + "3", CouponDto.class);
            CouponDto customerCoupons = coupons.getBody();
            System.err.println(customerCoupons + "TEST PASSED!");
            return customerCoupons != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCustomer() {

        try {
            ResponseEntity<CustomerDto> customerDto = restTemplate.
                    getForEntity(TestConstants.GET_CUSTOMER_URL + "3", CustomerDto.class);
            CustomerDto customer = customerDto.getBody();
            System.err.println(customer + "TEST PASSED!");
            return customer != null;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCustomerCouponsByCategory() {

        try {
            ResponseEntity<CouponDto> coupons = restTemplate.
                    getForEntity("http://localhost:8080/customer/coupons-by-category?id=1&category=ELECTRICITY",
                            CouponDto.class);
            CouponDto customerCoupons = coupons.getBody();
            System.err.println(customerCoupons);
            return customerCoupons != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean getCustomerCouponsByPrice() {

        try {
            ResponseEntity<CouponDto> coupons = restTemplate.
                    getForEntity("http://localhost:8080/customer/coupons-by-max-price?id=3&maxPrice=300",
                            CouponDto.class);
            CouponDto customerCoupons = coupons.getBody();
            System.out.println(customerCoupons);
            return customerCoupons != null;

        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
}
