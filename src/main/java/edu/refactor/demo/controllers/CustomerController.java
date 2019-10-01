package edu.refactor.demo.controllers;

import edu.refactor.demo.entities.BillingAccount;
import edu.refactor.demo.entities.Customer;
import edu.refactor.demo.exceptions.DuplicateObjectException;
import edu.refactor.demo.exceptions.NotFoundException;
import edu.refactor.demo.exceptions.NotModifiedException;
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
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        try {
            customerService.createCustomer(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DuplicateObjectException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/login/{login}/email/{email}")
    public ResponseEntity getCustomerBy(@PathVariable("login") String login,
                                        @PathVariable("email") String email) {
        try {
            Customer customer = customerService.retrieveBy(login, email);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/freeze/login/{login}/email/{email}")
    public ResponseEntity freezeCustomer(@PathVariable("login") String login,
                                         @PathVariable("email") String email) {
        try {
            customerService.freezeCustomer(login, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/delete/login/{login}/email/{email}")
    public ResponseEntity deleteCustomer(@PathVariable("login") String login,
                                         @PathVariable("email") String email) {
        try {
            customerService.deleteCustomer(login, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/active/login/{login}/email/{email}")
    public ResponseEntity activeCustomer(@PathVariable("login") String login,
                                         @PathVariable("email") String email) {
        try {
            customerService.activateCustomer(login, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotModifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/{id}/billingAccount/login/{login}/email/{email}")
    public ResponseEntity getBillingAccount(@PathVariable("login") String login,
                                            @PathVariable("email") String email) {
        try {
            List<BillingAccount> billingAccounts = customerService.getBillingAccount(login, email);
            return new ResponseEntity<>(billingAccounts, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
