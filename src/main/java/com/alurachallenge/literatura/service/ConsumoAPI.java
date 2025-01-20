package com.alurachallenge.literatura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Error al consumir la API. Código HTTP: " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consumir la API: " + e.getMessage(), e);
        }
    }
}