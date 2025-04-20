package com.udacity.jdnd.course3.critter.user;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import java.util.List;

import com.udacity.jdnd.course3.critter.pet.Pet;

@Entity
public class Customer extends User {

    private String phoneNumber;

    @Column(length = 1000)
    private String notes;

    @OneToMany(mappedBy = "customer")
    private List<Pet> pets;

    // Getters & Setters

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
