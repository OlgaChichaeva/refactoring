package edu.refactor.demo.services;

import edu.refactor.demo.entities.BillingAccount;
import edu.refactor.demo.entities.Customer;

import java.util.List;

public interface CustomerService {
    String NAME = "demo_CustomerService";

    List<Customer> getAllCustomers();

    Customer retrieveBy(String login, String email);

    void createCustomer(Customer customer);

    void freezeCustomer(String login, String email);

    void deleteCustomer(String login, String email);

    void activateCustomer(String login, String email);

    List<BillingAccount> getBillingAccount(String login, String email);
}
