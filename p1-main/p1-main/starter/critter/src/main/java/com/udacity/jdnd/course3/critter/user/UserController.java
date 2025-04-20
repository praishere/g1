package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.http.ResponseEntity;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);
        return ResponseEntity.ok(savedCustomer);
    }

    // Add this method for test compatibility
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return createCustomer(customerDTO).getBody();
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        return customerService.getOwnerByPet(petId);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeDTO);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeService.findEmployeesForService(employeeRequestDTO);
    }
}
