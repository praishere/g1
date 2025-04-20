package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired private PetService petService;

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = convertDTOToCustomer(customerDTO);
        customer = customerRepository.save(customer);
        return convertCustomerToDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertCustomerToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getOwnerByPet(long petId) {
        Pet pet = petService.getPet(petId); // assuming petService exists
        Customer customer = pet.getCustomer();
        return convertCustomerToDTO(customer);
    }


    // ===== DTO conversion helpers =====
    private CustomerDTO convertCustomerToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setNotes(customer.getNotes());
        if (customer.getPets() != null) {
            dto.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        }
        return dto;
    }

    private Customer convertDTOToCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
//        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setNotes(dto.getNotes());
        // pets are typically set separately
        return customer;
    }
}
