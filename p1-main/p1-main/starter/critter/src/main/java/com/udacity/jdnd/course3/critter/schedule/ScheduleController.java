package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertToEntity(scheduleDTO);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return convertToDTO(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getSchedulesForPet(petId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getSchedulesForEmployee(employeeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getSchedulesForCustomer(customerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper: Convert DTO to Entity
    private Schedule convertToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        List<Employee> employees = scheduleDTO.getEmployeeIds()
                .stream()
                .map(employeeService::getEmployee)
                .collect(Collectors.toList());
        schedule.setEmployees(employees);

        List<Pet> pets = scheduleDTO.getPetIds()
                .stream()
                .map(petService::getPet)
                .collect(Collectors.toList());
        schedule.setPets(pets);

        return schedule;
    }

    // Helper: Convert Entity to DTO
    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setDate(schedule.getDate());
        dto.setActivities(schedule.getActivities());

        List<Long> employeeIds = schedule.getEmployees()
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
        dto.setEmployeeIds(employeeIds);

        List<Long> petIds = schedule.getPets()
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
        dto.setPetIds(petIds);

        return dto;
    }
}
