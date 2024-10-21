package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Reseller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    @ManyToMany
    private Set<Plant> listOfPlants;

    @Builder
    public Reseller(Long id, String name, String address, String phone, Set<Plant> listOfPlants) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        if(listOfPlants != null) {
            this.listOfPlants = new HashSet<>();
            for(Plant plant : listOfPlants) {
                this.listOfPlants.add(plant);
            }
        } else {
            this.listOfPlants = new HashSet<>();
        }
    }
}
