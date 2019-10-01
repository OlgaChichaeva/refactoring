package edu.refactor.demo.services.impl;

import edu.refactor.demo.entities.BillingAccount;
import edu.refactor.demo.entities.Customer;
import edu.refactor.demo.entities.Money;
import edu.refactor.demo.exceptions.DuplicateObjectException;
import edu.refactor.demo.exceptions.NotFoundException;
import edu.refactor.demo.projecttypes.CustomerStatus;
import edu.refactor.demo.repository.BillingAccountRepo;
import edu.refactor.demo.repository.CustomerRepo;
import edu.refactor.demo.repository.MoneyRepo;
import edu.refactor.demo.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static edu.refactor.demo.projecttypes.CustomerStatus.*;
import static java.util.Objects.*;

@Slf4j
@Service(CustomerService.NAME)
public class CustomerServiceBean implements CustomerService {


    private final CustomerRepo customerRepo;


    private final BillingAccountRepo billingAccountRepo;

    private final MoneyRepo moneyRepo;


    @Autowired
    public CustomerServiceBean(CustomerRepo customerRepo, BillingAccountRepo billingAccountRepo, MoneyRepo moneyRepo) {
        this.customerRepo = customerRepo;
        this.billingAccountRepo = billingAccountRepo;
        this.moneyRepo = moneyRepo;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findCustomersExcludeStatus(DELETE.getId());
    }

    @Override
    public Customer retrieveBy(String login, String email) {
        Customer customer = customerRepo.findByLoginAndEmail(login, email);
        if (Objects.isNull(customer)) {
            String errorMessage = String.format("Customer: login=%s and email=%s not found", login, email);
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
        return customer;
    }


    @Override
    public void createCustomer(Customer customer) {
        if (isNull(customerRepo.findByLoginAndEmail(customer.getLogin(), customer.getEmail()))) {
            Customer saveCustomer = customerRepo.save(customer);
            createBillingAccount(saveCustomer);
        } else {
            String errorMessage = String.format("Customer: login=%s and email=%s already exists",
                    customer.getLogin(), customer.getEmail());
            log.error(errorMessage);
            throw new DuplicateObjectException(errorMessage);
        }
    }

    private void createBillingAccount(Customer customer) {
        BillingAccount billingAccount = new BillingAccount();
        billingAccount.setCustomer(customer);
        billingAccount.setCreatedDate(Instant.now());
        Money money = createMoney();
        billingAccount.setMoney(money);
        billingAccountRepo.save(billingAccount);
    }

    private Money createMoney() {
        Money money = new Money();
        return moneyRepo.save(money);
    }

    @Override
    public void freezeCustomer(String login, String email) {
        setCustomerStatus(login, email, FREEZE);
    }

    @Override
    public void deleteCustomer(String login, String email) {
        setCustomerStatus(login, email, DELETE);
    }

    @Override
    public void activateCustomer(String login, String email) {
        setCustomerStatus(login, email, ACTIVE);
    }

    private void setCustomerStatus(String login, String email, CustomerStatus status) {
        Customer customer = retrieveBy(login, email);
        customer.setStatus(status);
        customerRepo.save(customer);
    }

    @Override
    public List<BillingAccount> getBillingAccount(String login, String email) {
        List<BillingAccount> billingAccounts = customerRepo.getCustomerBillingAccounts(login, email);
        if (billingAccounts.isEmpty()) {
            String errorMessage = String.format("Billing accounts not found for customer: login=%s and email=%s ", login, email);
            log.error(errorMessage);
            throw new NotFoundException(errorMessage);
        }
        return billingAccounts;
    }
}
