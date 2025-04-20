package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }


    // ✅ Save employee with DTO
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertEmployeeToDTO(savedEmployee);
    }

    // ✅ Get employee by ID
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return employee != null ? convertEmployeeToDTO(employee) : null;
    }

    // ✅ Set availability
    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }
    }

    // ✅ Find employees for service
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO requestDTO) {
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContainsAndSkillsIn(
                requestDTO.getDate().getDayOfWeek(),
                requestDTO.getSkills()
        );

        // Filter employees matching all required skills
        List<Employee> matchingEmployees = employees.stream()
                .filter(e -> e.getSkills().containsAll(requestDTO.getSkills()))
                .collect(Collectors.toList());

        return matchingEmployees.stream()
                .map(this::convertEmployeeToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Convert Employee to DTO
    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());
        dto.setDaysAvailable(employee.getDaysAvailable());
        return dto;
    }

    // ✅ Convert DTO to Employee
    private Employee convertDTOToEmployee(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setSkills(dto.getSkills());
        employee.setDaysAvailable(dto.getDaysAvailable());
        return employee;
    }
}
