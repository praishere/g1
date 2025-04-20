package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet(Long petId) {
        return scheduleRepository.findAllByPets_Id(petId);
    }

    public List<Schedule> getSchedulesForEmployee(Long employeeId) {
        return scheduleRepository.findAllByEmployees_Id(employeeId);
    }

    public List<Schedule> getSchedulesForCustomer(Long customerId) {
        return scheduleRepository.findAllByPets_Customer_Id(customerId);
    }
}
