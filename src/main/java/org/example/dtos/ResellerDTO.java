package org.example.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.Plant;
import org.example.entities.Reseller;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResellerDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private Set<PlantDTO> plants;

    public ResellerDTO(Reseller reseller) {
        this.id = reseller.getId();
        this.name = reseller.getName();
        this.address = reseller.getAddress();
        this.phone = reseller.getPhone();
        this.plants = reseller.getListOfPlants().stream().map(PlantDTO::new) // converts Room to RoomDTO
                .collect(Collectors.toSet());
    }

    @JsonIgnore
    public Reseller getAsEntity() {
            Set<Plant> plantEntities;
            if (this.plants != null) {
                plantEntities = this.plants.stream()
                        .map(PlantDTO::getAsEntity) // convert each RoomDTO to Room
                        .collect(Collectors.toSet());
            } else {
                plantEntities = new HashSet<>();
            }

        return Reseller.builder()
                .id(id)
                .name(name)
                .address(address)
                .phone(phone)
                .listOfPlants(plantEntities)
                .build();
    }
}
