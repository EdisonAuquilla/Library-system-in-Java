/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.dto;

import com.mycompany.sistemadebiblioteca.dto.estado.EstadoLibro;
import com.mycompany.sistemadebiblioteca.util.MiExcepcion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Reserva {
    private final Usuario usuario;
    private final Libro libro;
    private final Date fechaReserva;

    private static final List<Reserva> listaReservas = new ArrayList<>();

    public Reserva(Usuario usuario, Libro libro, Date fechaReserva) {
        this.usuario = usuario;
        this.libro = libro;
        this.fechaReserva = fechaReserva;
    }

    public static boolean realizarReserva(Usuario usuario, Libro libro, Date fechaReserva) {
        try {
            validarDatosReserva(usuario, libro);

            Reserva nuevaReserva = new Reserva(usuario, libro, fechaReserva);
            listaReservas.add(nuevaReserva);
            libro.actualizarEstado(EstadoLibro.RESERVADO);

            System.out.println("Reserva realizada con éxito: '" + libro.getTitulo() + "' reservado por " + usuario.getNombre());
            return true;

        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
            return false;
        }
    }

    private static void validarDatosReserva(Usuario usuario, Libro libro) throws MiExcepcion {
        if (usuario == null || libro == null) {
            throw new MiExcepcion(301, "Usuario o libro no válido.");
        }

        for (Reserva r : listaReservas) {
            if (r.usuario.equals(usuario) && r.libro.equals(libro)) {
                throw new MiExcepcion(302, "Ya tienes una reserva activa para este libro.");
            }
        }

        if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
            throw new MiExcepcion(303, "El libro '" + libro.getTitulo() + "' no está disponible para reserva.");
        }
    }

    public static boolean eliminarReserva(Usuario usuario, Libro libro) {
        try {
            if (!validarReservaExistente(usuario, libro)) {
                throw new MiExcepcion(402, "No se encontró una reserva activa para este libro.");
            }

            listaReservas.removeIf(r -> r.usuario.equals(usuario) && r.libro.equals(libro));
            libro.actualizarEstado(EstadoLibro.DISPONIBLE);
            System.out.println("Reserva eliminada: '" + libro.getTitulo() + "' ya no está reservado.");
            return true;

        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
            return false;
        }
    }

    private static boolean validarReservaExistente(Usuario usuario, Libro libro) {
        for (Reserva r : listaReservas) {
            if (r.usuario.equals(usuario) && r.libro.equals(libro)) {
                return true;
            }
        }
        return false;
    }

    public static void cancelarReservaUsuario(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);

        List<Reserva> reservasUsuario = obtenerReservasUsuario(usuario);

        if (reservasUsuario.isEmpty()) {
            System.out.println("No tienes reservas activas.");
            return;
        }

        System.out.println("* Historial de Reservas de " + usuario.getNombre() + ":");
        for (Reserva r : reservasUsuario) {
            System.out.println("* " + r.getLibro().getTitulo() + " - Fecha: " + r.getFechaReserva());
        }

        System.out.print("Ingrese el título del libro cuya reserva desea cancelar: ");
        String titulo = scanner.nextLine();
        Libro libro = Libro.buscarPorTitulo(titulo);

        if (libro == null) {
            System.out.println("No se encontró un libro con ese título.");
            return;
        }

        eliminarReserva(usuario, libro);
    }

    public static List<Reserva> obtenerReservasUsuario(Usuario usuario) {
        List<Reserva> reservasUsuario = new ArrayList<>();
        for (Reserva r : listaReservas) {
            if (r.usuario.equals(usuario)) {
                reservasUsuario.add(r);
            }
        }
        return reservasUsuario;
    }

    public static void consultarReservasUsuario(Usuario usuario) {
        List<Reserva> reservasUsuario = obtenerReservasUsuario(usuario);

        if (reservasUsuario.isEmpty()) {
            System.out.println(usuario.getNombre() + " no tiene reservas activas.");
            return;
        }

        System.out.println("* Historial de Reservas de " + usuario.getNombre() + ":");
        for (Reserva r : reservasUsuario) {
            System.out.println("* " + r.getLibro().getTitulo() + " - Fecha: " + r.getFechaReserva());
        }
    }

    public static void listarHistorialReservas() {
        if (listaReservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            System.out.println("**** Historial de Reservas de Todos los Usuarios ****");
            for (Reserva reserva : listaReservas) {
                System.out.println("Libro: '" + reserva.getLibro().getTitulo() + "'");
                System.out.println("Usuario: " + reserva.getUsuario().getNombre());
                System.out.println("Fecha de reserva: " + reserva.getFechaReserva());
                System.out.println("---------------------------------");
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }
}