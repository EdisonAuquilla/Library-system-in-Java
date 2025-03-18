    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package com.mycompany.sistemadebiblioteca.dto;

    import com.mycompany.sistemadebiblioteca.dto.estado.TipoUsuario;
    import com.mycompany.sistemadebiblioteca.util.MiExcepcion;
import java.util.Map;
import java.util.Scanner;

    /**
     *
     * @author User
     */
  public class Usuario {
    private String nombre;
    private TipoUsuario tipo;

    public Usuario(String nombre, TipoUsuario tipo) throws MiExcepcion {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiExcepcion(501, "El nombre del usuario no puede estar vacío.");
        }
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public static Usuario crearUsuario(Scanner scanner, Map<String, Usuario> usuarios) {
        System.out.print("\nIngrese su nombre: ");
        String nombre = scanner.nextLine().trim();

        if (usuarios.containsKey(nombre)) {
            System.out.println("El usuario ya existe. Intente con otro nombre.");
            return null;
        }

        try {
            Usuario nuevoUsuario = new Usuario(nombre, TipoUsuario.USUARIO);
            usuarios.put(nombre, nuevoUsuario);
            System.out.println("Usuario creado con éxito. Bienvenido, " + nombre + "!");
            return nuevoUsuario;
        } catch (MiExcepcion e) {
            System.out.println("Error " + e.getCodigoError() + ": " + e.getMessage());
            return null;
        }
    }

    public String getNombre() { return nombre; }
    public TipoUsuario getTipo() { return tipo; }
}