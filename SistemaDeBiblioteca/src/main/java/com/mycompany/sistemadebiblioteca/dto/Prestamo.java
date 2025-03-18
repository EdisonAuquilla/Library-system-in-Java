/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.dto;
import com.mycompany.sistemadebiblioteca.dto.estado.EstadoLibro;
import com.mycompany.sistemadebiblioteca.dto.estado.EstadoPrestamo;
import com.mycompany.sistemadebiblioteca.util.MiExcepcion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class Prestamo {
    private final Usuario usuario;
    private final Libro libro;
    private final Date fechaInicio;
    private final Date fechaFin;
    private EstadoPrestamo estado;

    private static final List<Prestamo> listaPrestamos = new ArrayList<>();

    public Prestamo(Usuario usuario, Libro libro, Date fechaInicio, Date fechaFin) {
        this.usuario = usuario;
        this.libro = libro;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoPrestamo.ACTIVO;
    }

    public static void realizarPrestamo(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingrese el título del libro que desea tomar prestado: ");
            String titulo = scanner.nextLine();
            Libro libro = Libro.buscarPorTitulo(titulo);

            if (libro == null) {
                throw new MiExcepcion(101, "El libro no se encontró en la biblioteca.");
            }

            if (libro.getCantidad() <= 0) {
                throw new MiExcepcion(103, "El libro '" + libro.getTitulo() + "' no tiene ejemplares disponibles.");
            }

            System.out.print("Ingrese la fecha de devolución (dd/MM/yyyy): ");
            String fechaStr = scanner.nextLine();

            Date fechaInicio = new Date();
            Date fechaFin = parsearFecha(fechaStr);

            registrarPrestamo(usuario, libro, fechaInicio, fechaFin);

        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
        }

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    private static Date parsearFecha(String fechaStr) throws MiExcepcion {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
        } catch (ParseException e) {
            throw new MiExcepcion(102, "Formato de fecha inválido. Use dd/MM/yyyy.");
        }
    }

    public static boolean registrarPrestamo(Usuario usuario, Libro libro, Date fechaInicio, Date fechaFin) throws MiExcepcion {
        Prestamo nuevoPrestamo = new Prestamo(usuario, libro, fechaInicio, fechaFin);
        listaPrestamos.add(nuevoPrestamo);
        libro.setCantidad(libro.getCantidad() - 1);

        if (libro.getCantidad() == 0) {
            libro.actualizarEstado(EstadoLibro.PRESTADO);
        }

        System.out.println("Préstamo registrado: " + usuario.getNombre() + " ha tomado '" + libro.getTitulo() + "' hasta " + fechaFin);
        return true;
    }

    public static void devolverLibro(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);

        try {
            List<Prestamo> prestamosUsuario = consultarPrestamosUsuario(usuario);

            if (prestamosUsuario.isEmpty()) {
                throw new MiExcepcion(104, "No tienes libros en préstamo.");
            }

            System.out.println("\nLibros prestados por " + usuario.getNombre() + ":");
            for (Prestamo prestamo : prestamosUsuario) {
                System.out.println("- " + prestamo.getLibro().getTitulo() + " (Devolución: " + prestamo.getFechaFin() + ")");
            }

            System.out.print("\nIngrese el título del libro que desea devolver: ");
            String titulo = scanner.nextLine();
            Libro libro = Libro.buscarPorTitulo(titulo);

            if (libro == null) {
                throw new MiExcepcion(105, "No se encontró el libro.");
            }

            devolverLibro(usuario, libro);

        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
        }
    }

    public static boolean devolverLibro(Usuario usuario, Libro libro) throws MiExcepcion {
        for (Prestamo prestamo : listaPrestamos) {
            if (prestamo.usuario.equals(usuario) && prestamo.libro.equals(libro) && prestamo.estado == EstadoPrestamo.ACTIVO) {
                prestamo.estado = EstadoPrestamo.FINALIZADO;
                libro.actualizarEstado(EstadoLibro.DISPONIBLE);
                libro.setCantidad(libro.getCantidad() + 1);

                Historial.agregarAlHistorial(prestamo);
                System.out.println("El préstamo ha sido registrado en el historial.");
                System.out.println("'" + libro.getTitulo() + "' ha sido devuelto por " + usuario.getNombre());
                return true;
            }
        }
        throw new MiExcepcion(106, "No se encontró un préstamo activo de '" + libro.getTitulo() + "' para " + usuario.getNombre());
    }

    public static List<Prestamo> consultarPrestamosUsuario(Usuario usuario) {
        List<Prestamo> prestamosUsuario = new ArrayList<>();
        for (Prestamo prestamo : listaPrestamos) {
            if (prestamo.usuario.equals(usuario) && prestamo.estado == EstadoPrestamo.ACTIVO) {
                prestamosUsuario.add(prestamo);
            }
        }
        return prestamosUsuario;
    }

    public static List<Prestamo> listarPrestamos() {
        return listaPrestamos;
    }

    public Usuario getUsuario() { return usuario; }
    public Libro getLibro() { return libro; }
    public Date getFechaInicio() { return fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public EstadoPrestamo getEstado() { return estado; }
}