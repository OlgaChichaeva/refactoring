package edu.refactor.demo.controllers;

import edu.refactor.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/customer")
@RestController
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
        try {
            customerService.createCustomer(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/login/{login}/email/{email}")
    public ResponseEntity<Customer> getCustomerBy(@PathVariable("login") String login,
                                                  @PathVariable("email") String email) {
        try {
            Customer customer = customerService.retrieveBy(login, email);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/freeze/login/{login}/email/{email}")
    public ResponseEntity<Void> freezeCustomer(@PathVariable("login") String login,
                                               @PathVariable("email") String email) {
        try {
            customerService.freezeCustomer(login, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/delete/login/{login}/email/{email}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("login") String login,
                                               @PathVariable("email") String email) {
        try {
            customerService.deleteCustomer(login, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/active/login/{login}/email/{email}")
    public ResponseEntity<Void> activeCustomer(@PathVariable("login") String login,
                                               @PathVariable("email") String email) {
        try {
            customerService.activateCustomer(login, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/{id}/billingAccount/login/{login}/email/{email}")
    public ResponseEntity<List<BillingAccount>> getBillingAccount(@PathVariable("login") String login,
                                                                  @PathVariable("email") String email) {
        try {
            List<BillingAccount> billingAccounts = customerService.getBillingAccount(login, email);
            return new ResponseEntity<>(billingAccounts, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
