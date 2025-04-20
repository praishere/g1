package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertToEntity(petDTO);
        Pet savedPet = petService.savePet(pet);
        return convertToDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getPets()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByCustomer(ownerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper: Convert DTO to Entity
    private Pet convertToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType().toString());
        Customer customer = customerService.getCustomer(petDTO.getOwnerId());
        pet.setCustomer(customer);
        return pet;
    }

    // Helper: Convert Entity to DTO
    private PetDTO convertToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setNotes(pet.getNotes());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setType(PetType.valueOf(pet.getType()));
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
}
