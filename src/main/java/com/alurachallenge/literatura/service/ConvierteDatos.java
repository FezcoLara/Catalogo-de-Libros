package com.alurachallenge.literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            System.out.println("Mapeando JSON a clase: " + clase.getName());
            return mapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            System.err.println("Error al convertir JSON: " + e.getMessage());
            throw new RuntimeException("No se pudo mapear el JSON a " + clase.getName(), e);
        }
    }
}

