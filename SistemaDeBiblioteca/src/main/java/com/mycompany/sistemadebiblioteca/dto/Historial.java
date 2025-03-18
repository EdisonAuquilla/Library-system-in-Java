/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Historial {
        private static final List<Prestamo> prestamosFinalizados = new ArrayList<>();
       // Agregar un préstamo al historial


    // Consultar historial de un usuario
  public static void consultarHistorialUsuario(Usuario usuario) {
    boolean tieneHistorial = false;
    System.out.println("*** Historial de préstamos de " + usuario.getNombre() + " ***");

    // 🔹 Mostrar préstamos activos (aún no devueltos)
    for (Prestamo prestamo : Prestamo.listarPrestamos()) {
        if (prestamo.getUsuario().getNombre().equals(usuario.getNombre())) {
            tieneHistorial = true;
            System.out.println("📖 " + prestamo.getLibro().getTitulo() + " - Estado: " + prestamo.getEstado() + " (No devuelto)");
        }
    }

    // 🔹 Mostrar préstamos finalizados (devueltos)
    for (Prestamo prestamo : prestamosFinalizados) {
        if (prestamo.getUsuario().getNombre().equals(usuario.getNombre())) {
            tieneHistorial = true;
            System.out.println("📖 " + prestamo.getLibro().getTitulo() + " - Estado: " + prestamo.getEstado() + " (Devuelto)");
        }
    }

    if (!tieneHistorial) {
        System.out.println("No tienes préstamos en tu historial.");
    }
}



   // Mostrar TODOS los préstamos (activos y finalizados)
public static List<Prestamo> listarHistorial() {
    List<Prestamo> historialCompleto = new ArrayList<>();
    
    System.out.println("*** Historial de TODOS los préstamos ***");

    // 🔹 Agregar préstamos activos
    for (Prestamo prestamo : Prestamo.listarPrestamos()) {
        historialCompleto.add(prestamo);
        System.out.println("📖 " + prestamo.getLibro().getTitulo() +
                           " - Usuario: " + prestamo.getUsuario().getNombre() +
                           " - Estado: " + prestamo.getEstado() + " (No devuelto)");
    }

    // 🔹 Agregar préstamos finalizados
    for (Prestamo prestamo : prestamosFinalizados) {
        historialCompleto.add(prestamo);
        System.out.println("📖 " + prestamo.getLibro().getTitulo() +
                           " - Usuario: " + prestamo.getUsuario().getNombre() +
                           " - Estado: " + prestamo.getEstado() + " (Devuelto)");
    }

    if (historialCompleto.isEmpty()) {
        System.out.println("No hay préstamos registrados.");
    }

    return historialCompleto; // Ahora devuelve una lista
}



    
    public static void agregarAlHistorial(Prestamo prestamo) {
        if (prestamo != null) {
            prestamosFinalizados.add(prestamo);
            System.out.println("Prestamo agregado al historial correctamente."); // Debug
        } else {
            System.out.println(" Error: intento de agregar un préstamo nulo al historial.");
        }
    }
}
