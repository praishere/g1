package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByCustomer(Long customerId) {
        return petRepository.findAllByCustomerId(customerId);
    }
}
