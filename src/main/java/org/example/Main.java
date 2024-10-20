package org.example;

import jakarta.persistence.EntityManagerFactory;
import org.example.config.AppConfig;
import org.example.config.HibernateConfig;

public class Main {
   private static EntityManagerFactory emf;
    public static void main(String[] args) {
        emf = HibernateConfig.getEntityManagerFactory("garden_center");
        AppConfig.startServer(emf);
    }
}