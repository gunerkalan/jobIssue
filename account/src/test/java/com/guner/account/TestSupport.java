package com.guner.account;

import com.guner.account.model.Customer;

import java.util.Set;

public class TestSupport {

    public static final String CUSTOMER_API_ENDPOINT = "/v1/customer";
    public static final String ACCOUNT_API_ENDPOINT = "/v1/account";

    public Customer generateCustomer(){
        return new Customer("customer-id","customer-name","customer-surname", Set.of());
    }

}
