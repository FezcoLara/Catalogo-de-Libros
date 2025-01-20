package com.alurachallenge.literatura.principal;

import com.alurachallenge.literatura.model.Datos;
import com.alurachallenge.literatura.model.DatosLibros;
import com.alurachallenge.literatura.service.ConsumoAPI;
import com.alurachallenge.literatura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    public void muestraElMenu() {
        String menu = """
                ------------------------------------------------------
                El mundo de la literatura
                
                Bienvenido/a, elija una opción:
                1. Buscar libro por título
                2. Listar libros registrados
                3. Listar autores registrados
                4. Listar autores en un determinado año
                5. Listar libros por idioma
                0. Salir
                
                ------------------------------------------------------
                """;

        while (true) {
            System.out.println(menu);
            int opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresPorAno();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> {
                    System.out.println("¡Gracias por usar la aplicación!. ¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("----------------Libro----------------");
            DatosLibros libro = libroBuscado.get();
            System.out.println("Titulo: " + libro.titulo());
            System.out.println("Autor: " + libro.autores().stream()
                    .map(a -> a.nombre())
                    .collect(Collectors.joining(", ")));
            System.out.println("Idioma: " + String.join(", ", libro.idiomas()));
            System.out.println("Numero de descargas: " + libro.numeroDeDescargas());
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void listarLibros() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);

        System.out.println("----------------Libros Registrados----------------");
        datos.resultados().forEach(libro -> {
            System.out.println("Titulo: " + libro.titulo());
            System.out.println("Autor: " + libro.autores().stream()
                    .map(a -> a.nombre())
                    .collect(Collectors.joining(", ")));
            System.out.println("Idioma: " + String.join(", ", libro.idiomas()));
            System.out.println("Numero de descargas: " + libro.numeroDeDescargas());
            System.out.println("-------------------------------------");
        });
    }

    private void listarAutores() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);

        System.out.println("----------------Autores Registrados----------------");
        datos.resultados().forEach(libro -> libro.autores().forEach(autor -> {
            System.out.println("Autor: " + autor.nombre());
            System.out.println("Fecha de nacimiento: " + autor.añoNacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.añoFallecimiento());
            System.out.println("Libro: " + libro.titulo());
            System.out.println("-------------------------------------");
        }));
    }

    private void listarAutoresPorAno() {
        System.out.println("Ingrese el año que desea buscar:");
        int ano = Integer.parseInt(teclado.nextLine());

        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);

        System.out.println("------Autores en el año " + ano + "------");
        datos.resultados().forEach(libro -> libro.autores().stream()
                .filter(autor -> autor.añoNacimiento() != null && autor.añoNacimiento() <= ano
                        && (autor.añoFallecimiento() == null || autor.añoFallecimiento() >= ano))
                .forEach(autor -> {
                    System.out.println("Autor: " + autor.nombre());
                    System.out.println("Fecha de nacimiento: " + autor.añoNacimiento());
                    System.out.println("Fecha de fallecimiento: " + autor.añoFallecimiento());
                    System.out.println("Libro: " + libro.titulo());
                    System.out.println("-------------------------------------");
                }));
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma que desea buscar (ejemplo: 'en', 'fr'):");
        String idioma = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);

        System.out.println("------Libros en idioma " + idioma + "------");
        datos.resultados().stream()
                .filter(libro -> libro.idiomas().contains(idioma))
                .forEach(libro -> {
                    System.out.println("Titulo: " + libro.titulo());
                    System.out.println("Autor: " + libro.autores().stream()
                            .map(a -> a.nombre())
                            .collect(Collectors.joining(", ")));
                    System.out.println("Idioma: " + String.join(", ", libro.idiomas()));
                    System.out.println("Numero de descargas: " + libro.numeroDeDescargas());
                    System.out.println("-------------------------------------");
                });
    }
}
