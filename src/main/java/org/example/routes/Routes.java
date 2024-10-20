package org.example.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    private PlantRoutes plantRoutes;

    public Routes(EntityManagerFactory emf) {
        plantRoutes = new PlantRoutes(emf);
    }

    public EndpointGroup getApiRoutes() {
        return () -> {
            path("/plants", plantRoutes.getPlantRoutes());
        };
    }
}

