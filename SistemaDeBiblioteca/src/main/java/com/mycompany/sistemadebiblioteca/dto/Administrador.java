/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.dto;
import com.mycompany.sistemadebiblioteca.util.MiExcepcion;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Administrador {
    private static final Scanner scanner = new Scanner(System.in);

    // Agregar un nuevo libro con validaciones
    public static void agregarLibro() {
        try {
            System.out.print("Ingrese el título del nuevo libro: ");
            String titulo = scanner.nextLine().trim();
            validarCampoNoVacio(titulo, 601, "El título del libro no puede estar vacío.");

            System.out.print("Ingrese el autor: ");
            String autor = scanner.nextLine().trim();
            validarCampoNoVacio(autor, 602, "El autor del libro no puede estar vacío.");

            System.out.print("Ingrese el género: ");
            String genero = scanner.nextLine().trim();
            validarCampoNoVacio(genero, 603, "El género del libro no puede estar vacío.");

            Libro.registrarLibro(titulo, genero, autor);
            System.out.println("Libro agregado con éxito.");
        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
        }
    }

    // Eliminar un libro por título
    public static void eliminarLibro(String titulo) {
        try {
            validarCampoNoVacio(titulo, 604, "El título del libro no puede estar vacío.");
            Libro libro = Libro.buscarPorTitulo(titulo);
            if (libro == null) {
                throw new MiExcepcion(605, "El libro '" + titulo + "' no existe en la biblioteca.");
            }

            boolean eliminado = Libro.eliminarLibro(titulo);
            System.out.println(eliminado ? "Libro eliminado correctamente." : "No se pudo eliminar el libro.");
        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
        }
    }

    // Mostrar lista de libros disponibles
    private static void mostrarLibrosDisponibles() {
        System.out.println("Libros disponibles: ");
        for (Libro libro : Libro.getBiblioteca()) {
            System.out.println("* " + libro.getTitulo() + " - Cantidad: " + libro.getCantidad());
        }
    }

    // Aumentar cantidad de un libro
    public static void aumentarCantidad() {
        try {
            mostrarLibrosDisponibles();
            System.out.print("Ingrese el título del libro al que quiere aumentar la cantidad: ");
            String titulo = scanner.nextLine().trim();
            validarCampoNoVacio(titulo, 606, "El título no puede estar vacío.");

            Libro libro = Libro.buscarPorTitulo(titulo);
            if (libro == null) throw new MiExcepcion(607, "No se encontró el libro.");

            System.out.print("Ingrese la cantidad a aumentar: ");
            int cantidad = validarNumeroPositivo(scanner.nextLine(), 608, "La cantidad debe ser mayor a 0.");

            libro.setCantidad(libro.getCantidad() + cantidad);
            System.out.println("Se aumentó la cantidad del libro '" + libro.getTitulo() + "' a " + libro.getCantidad() + " ejemplares.");
        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
        }
    }

    // Disminuir cantidad de un libro
    public static void disminuirCantidad() {
        try {
            mostrarLibrosDisponibles();
            System.out.print("Ingrese el título del libro al que quiere disminuir la cantidad: ");
            String titulo = scanner.nextLine().trim();
            validarCampoNoVacio(titulo, 609, "El título no puede estar vacío.");

            Libro libro = Libro.buscarPorTitulo(titulo);
            if (libro == null) throw new MiExcepcion(610, "No se encontró el libro.");

            System.out.print("Ingrese la cantidad a disminuir: ");
            int cantidad = validarNumeroPositivo(scanner.nextLine(), 611, "La cantidad debe ser mayor a 0.");
            if (cantidad > libro.getCantidad()) throw new MiExcepcion(612, "No se puede disminuir más de la cantidad disponible.");

            libro.setCantidad(libro.getCantidad() - cantidad);
            System.out.println("Se disminuyó la cantidad del libro '" + libro.getTitulo() + "' a " + libro.getCantidad() + " ejemplares.");
        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
        }
    }

    // Métodos auxiliares para validaciones
    private static void validarCampoNoVacio(String campo, int codigoError, String mensaje) throws MiExcepcion {
        if (campo == null || campo.trim().isEmpty()) {
            throw new MiExcepcion(codigoError, mensaje);
        }
    }

    private static int validarNumeroPositivo(String entrada, int codigoError, String mensaje) throws MiExcepcion {
        try {
            int numero = Integer.parseInt(entrada.trim());
            if (numero <= 0) throw new MiExcepcion(codigoError, mensaje);
            return numero;
        } catch (NumberFormatException e) {
            throw new MiExcepcion(codigoError, "Debe ingresar un número válido.");
        }
    }
}