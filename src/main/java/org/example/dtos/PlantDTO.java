package org.example.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.Plant;
import org.example.enums.PlantType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantDTO {
    private Long id;
    private PlantType plantType;
    private String name;
    private Integer maxHeight;
    private Double price;

    public PlantDTO(Plant plant) {
        this.id = plant.getId();
        this.plantType = plant.getPlantType();
        this.name = plant.getName();
        this.maxHeight = plant.getMaxHeight();
        this.price = plant.getPrice();
    }

    @JsonIgnore
    public Plant getAsEntity() {
        return Plant.builder()
                .id(id)
                .plantType(plantType)
                .name(name)
                .maxHeight(maxHeight)
                .price(price)
                .build();
    }
}
