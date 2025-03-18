/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.dto;

import com.mycompany.sistemadebiblioteca.dto.estado.EstadoLibro;
import com.mycompany.sistemadebiblioteca.util.MiExcepcion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Libro {
    private String titulo;
    private String autor;
    private EstadoLibro estado;
    private int cantidad;
    private String genero;

    private static final List<Libro> biblioteca = new ArrayList<>();

    static {
        try {
            biblioteca.add(new Libro("Cien años de soledad", "Gabriel García Márquez", "Realismo mágico", 3));
            biblioteca.add(new Libro("1984", "George Orwell", "Distopía", 5));
            biblioteca.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "Clásico", 2));
            biblioteca.add(new Libro("El principito", "Antoine de Saint-Exupéry", "Infantil", 4));
        } catch (MiExcepcion e) {
            System.out.println("Error al cargar libros iniciales: " + e.getMessage());
        }
    }

    public Libro(String titulo, String autor, String genero, int cantidad) throws MiExcepcion {
        if (titulo == null || titulo.trim().isEmpty()) throw new MiExcepcion(601, "El título no puede estar vacío.");
        if (autor == null || autor.trim().isEmpty()) throw new MiExcepcion(602, "El autor no puede estar vacío.");
        if (genero == null || genero.trim().isEmpty()) throw new MiExcepcion(603, "El género no puede estar vacío.");
        if (cantidad < 0) throw new MiExcepcion(604, "La cantidad de libros no puede ser negativa.");

        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.cantidad = cantidad;
        this.estado = cantidad > 0 ? EstadoLibro.DISPONIBLE : EstadoLibro.PRESTADO;
    }

    public void actualizarEstado(EstadoLibro nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void incrementarCantidad() {
        this.cantidad++;
    }

    public static void registrarLibro(String titulo, String genero, String autor) {
        try {
            Libro nuevoLibro = new Libro(titulo, autor, genero, 1);
            biblioteca.add(nuevoLibro);
            System.out.println("Libro agregado con éxito: " + titulo);
        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
        }
    }

    public static Libro buscarPorTitulo(String titulo) {
        for (Libro libro : biblioteca) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        try {
            throw new MiExcepcion(605, "No se encontró un libro con el título: " + titulo);
        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
        }
        return null;
    }

    public static List<Libro> buscarPorGenero(String genero) {
        List<Libro> librosEncontrados = new ArrayList<>();
        for (Libro libro : biblioteca) {
            if (libro.getGenero().equalsIgnoreCase(genero)) {
                librosEncontrados.add(libro);
            }
        }
        if (librosEncontrados.isEmpty()) {
            try {
                throw new MiExcepcion(606, "No hay libros disponibles en el género: " + genero);
            } catch (MiExcepcion e) {
                System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
            }
        }
        return librosEncontrados;
    }

    public static void mostrarGenerosDisponibles() {
        List<String> generos = new ArrayList<>();
        for (Libro libro : biblioteca) {
            if (!generos.contains(libro.getGenero())) {
                generos.add(libro.getGenero());
            }
        }
        System.out.println("** Géneros disponibles en la biblioteca **");
        for (String genero : generos) {
            System.out.println("- " + genero);
        }
    }

    public static boolean eliminarLibro(String titulo) {
        return biblioteca.removeIf(libro -> libro.getTitulo().equalsIgnoreCase(titulo));
    }

    public static void verLibros() {
        if (biblioteca.isEmpty()) {
            System.out.println("No hay libros registrados en la biblioteca.");
            return;
        }
        System.out.println("Lista de libros disponibles:");
        for (Libro libro : biblioteca) {
            System.out.println("- " + libro.getTitulo() + " de " + libro.getAutor() +
                               " (Género: " + libro.getGenero() + ", Cantidad: " + libro.getCantidad() + ")");
        }
    }

    public void disminuirCantidad() {
        if (cantidad > 0) {
            this.cantidad--;
        }
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public EstadoLibro getEstado() { return estado; }
    public int getCantidad() { return cantidad; }
    public String getGenero() { return genero; }

    public void setCantidad(int cantidad) throws MiExcepcion {
        if (cantidad < 0) throw new MiExcepcion(607, "No se puede establecer una cantidad negativa.");
        this.cantidad = cantidad;
        this.estado = cantidad > 0 ? EstadoLibro.DISPONIBLE : EstadoLibro.PRESTADO;
    }

    public static List<Libro> getBiblioteca() { return biblioteca; }
}


