package org.example.daos;

import jakarta.persistence.*;
import org.example.dtos.PlantDTO;
import org.example.dtos.ResellerDTO;
import org.example.entities.Plant;
import org.example.entities.Reseller;

import java.util.Set;
import java.util.stream.Collectors;

public class ResellerDAO {
    private static EntityManagerFactory emf;

    public ResellerDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Set<ResellerDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Reseller> query = em.createQuery("SELECT r FROM Reseller r", Reseller.class);

            return query.getResultStream().map(ResellerDTO::new).collect(Collectors.toSet());

        } catch (RollbackException e) {
            throw new RollbackException("Could not get all resellers", e);
        }
    }

    public ResellerDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Reseller reseller = em.find(Reseller.class, id);

            if (reseller == null) {
                throw new EntityNotFoundException("Reseller with id " + id + " not found");
            }
            return new ResellerDTO(reseller);
        }
    }

    public ResellerDTO create(ResellerDTO resellerDTO) {
        Reseller reseller = resellerDTO.getAsEntity();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.persist(reseller);
            em.getTransaction().commit();

            return new ResellerDTO(reseller);

        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating reseller: " + e.getMessage(), e);
        }
    }
}
