/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.servicio;

import com.mycompany.sistemadebiblioteca.dto.Administrador;
import com.mycompany.sistemadebiblioteca.dto.Libro;
import com.mycompany.sistemadebiblioteca.dto.Reserva;
import com.mycompany.sistemadebiblioteca.dto.Usuario;
import com.mycompany.sistemadebiblioteca.dto.Prestamo;
import com.mycompany.sistemadebiblioteca.dto.Historial;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import com.mycompany.sistemadebiblioteca.util.MiExcepcion;
import java.util.ArrayList;
/**
 *
 * @author User
 */
public class MenuServicio {
    private static final Scanner scanner = new Scanner(System.in);

    public static void menuUsuario(Usuario usuario) {
        while (true) {
            System.out.println("\n********* Menú Usuario: " + usuario.getNombre() + " *********");
            System.out.println("1. Ver lista de libros");
            System.out.println("2. Buscar libro por título");
            System.out.println("3. Buscar libro por género");
            System.out.println("4. Reservar un libro");
            System.out.println("5. Cancelar una reserva");
            System.out.println("6. Ver historial de reservas");
            System.out.println("7. Tomar prestado un libro");
            System.out.println("8. Devolver un libro");
            System.out.println("9. Consultar historial de préstamos");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        Libro.verLibros();
                        break;
                    case "2":
                        buscarLibroPorTitulo();
                        break;
                    case "3":
                        buscarLibroPorGenero();
                        break;
                    case "4":
                        reservarLibro(usuario);
                        break;
                    case "5":
                        cancelarReserva(usuario);
                        break;
                    case "6":
                        Reserva.consultarReservasUsuario(usuario);
                        break;
                    case "7":
                        Prestamo.realizarPrestamo(usuario);
                        break;
                    case "8":
                        Prestamo.devolverLibro(usuario);
                        break;
                    case "9":
                        Historial.consultarHistorialUsuario(usuario);
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("** Opción no válida.");
                }
            } catch (MiExcepcion e) {
                System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
            }
        }
    }

    private static void buscarLibroPorTitulo() throws MiExcepcion {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        Libro libro = Libro.buscarPorTitulo(titulo);
        if (libro != null) {
            System.out.println("*** Libro encontrado: ");
            System.out.println("- Título: '" + libro.getTitulo() + "' de " + libro.getAutor());
            System.out.println("- Cantidad de ejemplares: " + libro.getCantidad());
            System.out.println("- Estado: " + libro.getEstado());
        }
    }

    private static void buscarLibroPorGenero() {
        Libro.mostrarGenerosDisponibles();
        System.out.print("Ingrese el género del libro: ");
        String genero = scanner.nextLine();
        List<Libro> libros = Libro.buscarPorGenero(genero);
        if (!libros.isEmpty()) {
            System.out.println("Libros encontrados:");
            for (Libro libro : libros) {
                System.out.println("- '" + libro.getTitulo() + "' de " + libro.getAutor() + " (Cantidad: " + libro.getCantidad() + ")");
            }
        }
    }

    private static void reservarLibro(Usuario usuario) throws MiExcepcion {
        System.out.print("Ingrese el título del libro que desea reservar: ");
        String titulo = scanner.nextLine();
        Libro libro = Libro.buscarPorTitulo(titulo);
        if (libro != null) {
            Reserva.realizarReserva(usuario, libro, new Date());
        }
    }

    private static void cancelarReserva(Usuario usuario) throws MiExcepcion {
        List<Reserva> reservasUsuario = Reserva.obtenerReservasUsuario(usuario);
        if (reservasUsuario.isEmpty()) {
            System.out.println("No tienes reservas activas para cancelar.");
            return;
        }

        System.out.println("Tus reservas activas:");
        for (Reserva reserva : reservasUsuario) {
            System.out.println("- " + reserva.getLibro().getTitulo());
        }

        System.out.print("Ingrese el título del libro cuya reserva desea cancelar: ");
        String titulo = scanner.nextLine();
        Libro libro = Libro.buscarPorTitulo(titulo);
        if (libro != null) {
            Reserva.eliminarReserva(usuario, libro);
        }
    }

    public static void menuAdministrador() {
        System.out.print("Ingrese la contraseña de administrador: ");
        String contraseña = scanner.nextLine();

        if (!contraseña.equals("admin123")) {
            System.out.println("** Contraseña incorrecta.");
            return;
        }

        System.out.println("**** Acceso concedido ****");

        while (true) {
            System.out.println("\n**** Modo ADMINISTRADOR **** ");
            System.out.println("1. Ver lista de libros");
            System.out.println("2. Buscar libro por título");
            System.out.println("3. Buscar libro por género");
            System.out.println("4. Agregar un libro");
            System.out.println("5. Eliminar un libro");
            System.out.println("6. Aumentar cantidad de libros");
            System.out.println("7. Disminuir cantidad de libros");
            System.out.println("8. Ver historial de préstamos");
            System.out.println("9. Ver historial de reservas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        Libro.verLibros();
                        break;
                    case "2":
                        buscarLibroPorTitulo();
                        break;
                    case "3":
                        buscarLibroPorGenero();
                        break;
                    case "4":
                        Administrador.agregarLibro();
                        break;
                    case "5":
                        eliminarLibro();
                        break;
                    case "6":
                        Administrador.aumentarCantidad();
                        break;
                    case "7":
                        Administrador.disminuirCantidad();
                        break;
                    case "8":
                        Historial.listarHistorial();
                        break;
                    case "9":
                        Reserva.listarHistorialReservas();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("** Opción no válida.");
                }
            } catch (MiExcepcion e) {
                System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
            }
        }
    }

    private static void eliminarLibro() throws MiExcepcion {
        System.out.println("** Libros disponibles:");
        for (Libro libro : Libro.getBiblioteca()) {
            System.out.println("- " + libro.getTitulo() + " de " + libro.getAutor() + " (Cantidad: " + libro.getCantidad() + ")");
        }

        System.out.print("Ingrese el título del libro a eliminar: ");
        String tituloEliminar = scanner.nextLine();
        Administrador.eliminarLibro(tituloEliminar);
    }
}
