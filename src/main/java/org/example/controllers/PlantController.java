package org.example.controllers;

import io.javalin.http.Context;
import jakarta.persistence.EntityNotFoundException;
import org.example.daos.PlantDAO;
import org.example.dtos.PlantDTO;
import org.example.enums.PlantType;
import org.example.exceptions.ApiException;

import java.util.Set;

public class PlantController implements IPlantController {
    private PlantDAO plantDAO;

    public PlantController(PlantDAO plantDAO) {
        this.plantDAO = plantDAO;
    }

    @Override
    public void getAll(Context ctx) {
        try {
            Set<PlantDTO> plants = plantDAO.getAll();

            if (plants.isEmpty()) {
                throw new EntityNotFoundException("No plants where found");
            } else {
                ctx.res().setStatus(200);
                ctx.json(plants);
            }

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            PlantDTO plant = plantDAO.getById(id);

            if (plant == null) {
                ctx.res().setStatus(404);
                throw new EntityNotFoundException("Plant with id " + id + " not found");
            }
            ctx.res().setStatus(200);
            ctx.json(plant);

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void getByType(Context ctx) {
        try {
            String typeStr = ctx.pathParam("type");
            PlantType type = PlantType.valueOf(typeStr.toUpperCase());

            Set<PlantDTO> plantsByType = plantDAO.getByType(type);

            if (plantsByType.isEmpty()) {
                ctx.res().setStatus(404);
                throw new EntityNotFoundException("Plant type " + type + " not found");
            } else {
                ctx.res().setStatus(200);
                ctx.json(plantsByType);
            }

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void create(Context ctx) {
        try {
            PlantDTO plant = ctx.bodyAsClass(PlantDTO.class);
            PlantDTO newPlant = plantDAO.create(plant);

            if (newPlant != null) {
                ctx.res().setStatus(201);
                ctx.json(newPlant);
            } else {
                ctx.res().setStatus(400);
                throw new IllegalArgumentException("Plant could not be created");
            }
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }
}
