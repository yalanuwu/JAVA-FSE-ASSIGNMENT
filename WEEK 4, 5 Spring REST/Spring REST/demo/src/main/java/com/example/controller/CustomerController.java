package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.mapper.CustomerMapper;
import com.example.bookstoreapi.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    // Create a new customer
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.toCustomer(customerDTO);
        customer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toCustomerDTO(customer);

        // Add self link
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).getCustomerById(savedCustomerDTO.getId())).withSelfRel();
        savedCustomerDTO.add(selfLink);

        return new ResponseEntity<>(savedCustomerDTO, HttpStatus.CREATED);
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream()
                                                  .map(customerMapper::toCustomerDTO)
                                                  .collect(Collectors.toList());

        // Add self links to each customer
        customerDTOs.forEach(customerDTO -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel();
            customerDTO.add(selfLink);
        });

        return new ResponseEntity<>(customerDTOs, HttpStatus.OK);
    }

    // Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(value -> {
            CustomerDTO customerDTO = customerMapper.toCustomerDTO(value);
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).getCustomerById(id)).withSelfRel();
            customerDTO.add(selfLink);
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a customer by ID
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        if (!customerRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Customer customer = customerMapper.toCustomer(customerDTO);
        customer.setId(id);
        customer = customerRepository.save(customer);
        CustomerDTO updatedCustomerDTO = customerMapper.toCustomerDTO(customer);

        // Add self link
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).getCustomerById(id)).withSelfRel();
        updatedCustomerDTO.add(selfLink);

        return new ResponseEntity<>(updatedCustomerDTO, HttpStatus.OK);
    }

    // Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (!customerRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

