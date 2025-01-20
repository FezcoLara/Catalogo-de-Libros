package com.alurachallenge.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") int numeroDeDescargas
) {
    @Override
    public String toString() {
        String autoresString = autores != null && !autores.isEmpty()
                ? autores.stream().map(DatosAutor::toString).collect(Collectors.joining("\n"))
                : "Autor: Desconocido";

        return """
                ----------------Libro----------------
                Titulo: %s
                %s
                Idioma: %s
                Numero de descargas: %d
                -------------------------------------
                """.formatted(
                titulo != null ? titulo : "Desconocido",
                autoresString,
                idiomas != null && !idiomas.isEmpty() ? String.join(", ", idiomas) : "Desconocido",
                numeroDeDescargas
        );
    }
}
