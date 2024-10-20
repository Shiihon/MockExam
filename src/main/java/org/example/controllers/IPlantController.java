package org.example.controllers;
import io.javalin.http.Context;

public interface IPlantController {
    public void getAll(Context ctx);
    public void getById(Context ctx);
    public void getByType(Context ctx);
    public void create(Context ctx);
}
