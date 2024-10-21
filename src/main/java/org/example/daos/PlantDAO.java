package org.example.daos;

import jakarta.persistence.*;
import org.example.dtos.PlantDTO;
import org.example.dtos.ResellerDTO;
import org.example.entities.Plant;
import org.example.entities.Reseller;
import org.example.enums.PlantType;

import java.util.Set;
import java.util.stream.Collectors;

public class PlantDAO implements IDAO<PlantDTO, PlantType> {
    EntityManagerFactory emf;

    public PlantDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Set<PlantDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Plant> query = em.createQuery("SELECT p FROM Plant p", Plant.class);

            return query.getResultStream().map(PlantDTO::new).collect(Collectors.toSet());

        } catch (RollbackException e) {
            throw new RollbackException("Could not get all plants", e);
        }
    }

    @Override
    public PlantDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Plant plant = em.find(Plant.class, id);

            if (plant == null) {
                throw new EntityNotFoundException("Plant with id " + id + " not found");
            }
            return new PlantDTO(plant);
        }
    }

    @Override
    public Set<PlantDTO> getByType(PlantType plantType) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Plant> query = em.createQuery("SELECT p FROM Plant p WHERE p.plantType = :plantType", Plant.class);
            query.setParameter("plantType", plantType);
            Set<PlantDTO> queryResult = query.getResultStream().map(PlantDTO::new).collect(Collectors.toSet());

            if (queryResult.isEmpty()) {
                throw new EntityNotFoundException("Plant type " + plantType + " not found");
            }
            return queryResult;
        }
    }

    @Override
    public PlantDTO create(PlantDTO plantDTO) {
        Plant plant = plantDTO.getAsEntity();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(plant);
            em.getTransaction().commit();
        }
        return new PlantDTO(plant);
    }

    public void deletePlant(Long id) { //use entity
        try (EntityManager em = emf.createEntityManager()) {
            Plant plant = em.find(Plant.class, id);

            if (plant == null) {
                throw new EntityNotFoundException("Plant with id " + id + " not found");
            }
            em.getTransaction().begin();
            em.remove(plant);
            em.getTransaction().commit();

        } catch (RollbackException e) {
            throw new RollbackException(String.format("Could not delete plant with id: %d : %s", id, e.getMessage()));
        }
    }


    public ResellerDTO addPlantToReseller(Long resellerId, Long plantId) { //use entity
        try (EntityManager em = emf.createEntityManager()) {
            Reseller reseller = em.find(Reseller.class, resellerId);

            if(reseller == null) {
                throw new EntityNotFoundException("Reseller with id " + resellerId + " not found");
            }
            Plant plant = em.find(Plant.class, plantId);

            if(plant == null) {
                throw new EntityNotFoundException("Plant with id " + plantId + " not found");
            }
            em.getTransaction().begin();

            reseller.getListOfPlants().add(plant);

            em.merge(reseller);
            em.getTransaction().commit();

            return new ResellerDTO(reseller);

        } catch (RollbackException e) {
            throw new RollbackException(String.format("Could not add plant with id: %d to reseller with id: %d : %s", plantId, resellerId, e.getMessage()));
        }

    }

    public Set<PlantDTO> getPlantsByReseller(Long resellerId) { //use entity
        try (EntityManager em = emf.createEntityManager()) {
            Reseller reseller = em.find(Reseller.class, resellerId);
            if(reseller == null) {
                throw new EntityNotFoundException("Reseller with id " + resellerId + " not found");
            }
            Set<Plant> plants = reseller.getListOfPlants();

            return plants.stream().map(PlantDTO::new).collect(Collectors.toSet());
        } catch (RuntimeException e) {
            throw new RuntimeException("Error fetching plants for reseller ID: " + resellerId + " - " + e.getMessage(), e);
        }
    }
}
