package org.example.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.example.controllers.PlantController;
import org.example.daos.PlantDAO;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlantRoutes {
    private final PlantController plantController;
    private static PlantDAO plantDAO;

    public PlantRoutes(EntityManagerFactory emf) {
        plantDAO = new PlantDAO(emf);
        plantController = new PlantController(plantDAO);
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
