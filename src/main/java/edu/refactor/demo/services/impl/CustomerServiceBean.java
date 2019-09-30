package edu.refactor.demo.services.impl;

import edu.refactor.demo.entities.BillingAccount;
import edu.refactor.demo.entities.Customer;
import edu.refactor.demo.repository.BillingAccountRepo;
import edu.refactor.demo.repository.CustomerRepo;
import edu.refactor.demo.services.CustomerService;
import edu.refactor.demo.exeptions.CustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

@Slf4j
@Service(CustomerService.NAME)
public class CustomerServiceBean implements CustomerService {


    private final CustomerRepo customerRepo;


    private final BillingAccountRepo billingAccountRepo;

    @Autowired
    public CustomerServiceBean(CustomerRepo customerRepo, BillingAccountRepo billingAccountRepo) {
        this.customerRepo = customerRepo;
        this.billingAccountRepo = billingAccountRepo;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findCustomersByStatus("delete");
    }

    @Override
    public Customer retrieveBy(String login, String email) {
        Customer customer = customerRepo.findByLoginAndEmail(login, email);
        if (Objects.isNull(customer)) {
            log.error(String.format("Customer: login=%s and email=%s not found", login, email));
        }
        return customer;
    }


    @Override
    public void createCustomer(Customer customer) {
        if (isNull(customerRepo.findByLoginAndEmail(customer.getLogin(), customer.getEmail()))) {
            Customer saveCustomer = customerRepo.save(customer);
            createBillingAccount(saveCustomer);
        } else {
            log.error(String.format("Customer: login=%s and email=%s already exists", customer.getLogin(), customer.getEmail()));
        }
    }

    private void createBillingAccount(Customer customer) {
        try {
            BillingAccount billingAccount = new BillingAccount();
            billingAccount.setCustomer(customer);
            billingAccount.setCreatedDate(Instant.now());
            billingAccount.setPrimary(true);
            billingAccount.setMoney(0);
            billingAccountRepo.save(billingAccount);
        } catch (RuntimeException e) {
            log.error(String.format("BillingAccount d'not save. %s", e.getMessage()));
        }
    }

    @Override
    public void freezeCustomer(String login, String email) {
        setCustomerStatus(login, email, "freeze");
    }

    @Override
    public void deleteCustomer(String login, String email) {
        setCustomerStatus(login, email, "delete");
    }

    @Override
    public void activateCustomer(String login, String email) {
        setCustomerStatus(login, email, "active");
    }

    private void setCustomerStatus(String login, String email, String status) {
        try {
            Customer customer = retrieveBy(login, email);
            customer.setStatus(status);
            customerRepo.save(customer);
        } catch (RuntimeException e) {
            log.error(String.format("Customer don't %s! %s ", status, e.getMessage()));
        }
    }

    @Override
    public List<BillingAccount> getBillingAccount(String login, String email) {
        List<BillingAccount> billingAccounts = customerRepo.getCustomerBillingAccounts(login, email);

        if (billingAccounts.isEmpty()) {
            log.error(String.format("Billing accounts not found for customer: login=%s and email=%s ", login, email));
        }
        return billingAccounts;
    }
}
