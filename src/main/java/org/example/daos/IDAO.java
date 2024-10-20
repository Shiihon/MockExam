package org.example.daos;

import org.example.dtos.PlantDTO;
import org.example.entities.Plant;

import java.util.Set;

public interface IDAO <T, J>{

    Set<T> getAll();

    T getById(Long id);

    Set<T> getByType(J j);

    T create (T t);
}
