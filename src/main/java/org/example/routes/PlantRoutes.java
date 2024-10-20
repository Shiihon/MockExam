package org.example.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.example.controllers.PlantController;
import org.example.daos.PlantDAOMock;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlantRoutes {
    private final PlantController plantController;
    private static PlantDAOMock plantDAOMock;

    public PlantRoutes(EntityManagerFactory emf) {
        plantDAOMock = new PlantDAOMock();
        plantController = new PlantController(plantDAOMock);
    }

    public EndpointGroup getPlantRoutes() {
        return () -> {
            get("/", plantController::getAll);
            get("/{id}", plantController::getById);
            get("/type/{type}", plantController::getByType);
            post("/", plantController::create); // the add plant part.
        };
    }
}
