package com.udacity.jdnd.course3.critter.user;

import jakarta.persistence.Entity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Employee extends User {

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    // Getter and Setter for skills
    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    // Getter and Setter for daysAvailable
    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
