package org.example.daos;

import org.example.dtos.PlantDTO;
import org.example.enums.PlantType;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PlantDAOMock implements IDAO<PlantDTO, PlantType> {
    private static Set<PlantDTO> plants;
    private static Long nextId;

    public PlantDAOMock() {
        plants = new HashSet<PlantDTO>();
        nextId = 1L;

        // Pre-populate the list with some plants
        plants.add(new PlantDTO(nextId++, PlantType.ROSE, "Albertine", 400, 199.50));
        plants.add(new PlantDTO(nextId++, PlantType.BUSH, "Yellow Emperor", 40, 99.99));
    }

    @Override
    public Set<PlantDTO> getAll() {
        return plants;
    }

    @Override
    public PlantDTO getById(Long id) {
        return plants.stream()
                .filter(plant -> plant.getId().equals(id)).findFirst()
                .orElse(null);
    }

    @Override
    public Set<PlantDTO> getByType(PlantType type) {
        return plants.stream()
                .filter(plant -> plant.getPlantType().equals(type))
                .collect(Collectors.toSet());
    }

    @Override
    public PlantDTO create(PlantDTO plant) {
        plant.setId(nextId++);
        plants.add(plant);
        return plant;
    }

//    3.1 returns a list of plants with a maximum height of 100 cm using the stream API, filter() and a
//    predicate function.
    public Set<PlantDTO> getPlantsMaxHeight100 (){
        return plants.stream()
                .filter(plantDTO -> plantDTO.getMaxHeight().equals(100))
                .collect(Collectors.toSet());
    }

//    3.2 maps / converts a list of PlantDTOs to a list of Strings containing the plant names. Again use the stream
//    API and the map function.
    public Set<String> convertToListOfStrings(Set<PlantDTO> plants) {
        return plants.stream()
                .map(PlantDTO::getName)
                .collect(Collectors.toSet());
    }

//    3.3 sorts a list of PlantDTOs by name using streams, sorted(), and a Comparator.
    public Set<PlantDTO> sortedListOfPlants (){
        return plants.stream()
                //.sorted((plant1, plant2) -> plant1.getName().compareTo(plant2.getName())), one way to write it or the one under.
                .sorted(Comparator.comparing(PlantDTO::getName))
                .collect(Collectors.toSet());
    }
}
