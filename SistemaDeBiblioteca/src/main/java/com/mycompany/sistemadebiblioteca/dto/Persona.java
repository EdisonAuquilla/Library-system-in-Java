/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.dto;

import com.mycompany.sistemadebiblioteca.util.MiExcepcion;

/**
 *
 * @author User
 */
public class Persona {
    private String nombre;

    public Persona(String nombre) throws MiExcepcion {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiExcepcion(501, "El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws MiExcepcion {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiExcepcion(502, "El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }
}
