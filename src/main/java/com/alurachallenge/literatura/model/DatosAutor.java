package com.alurachallenge.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer añoNacimiento,
        @JsonAlias("death_year") Integer añoFallecimiento
) {
    @Override
    public String toString() {
        return """
                Autor: %s
                Fecha de nacimiento: %s
                Fecha de fallecimiento: %s
                """.formatted(
                nombre != null ? nombre : "Desconocido",
                añoNacimiento != null ? añoNacimiento : "Desconocida",
                añoFallecimiento != null ? añoFallecimiento : "Desconocida"
        );
    }
}
