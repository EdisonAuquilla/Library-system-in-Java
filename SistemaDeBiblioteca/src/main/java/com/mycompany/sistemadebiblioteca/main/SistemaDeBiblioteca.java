/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.main;

import com.mycompany.sistemadebiblioteca.dto.Usuario;
import com.mycompany.sistemadebiblioteca.servicio.MenuServicio;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class SistemaDeBiblioteca {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Usuario> usuarios = new HashMap<>();
    private static Usuario usuarioActual;

    public static void main(String[] args) {
        while (true) {
            System.out.println("****** Bienvenido al Sistema de Biblioteca ******");
            System.out.println("1. Iniciar sesión como usuario");
            System.out.println("2. Crear nuevo usuario");
            System.out.println("3. Ingresar como Administrador");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    iniciarSesion();
                    break;
                case "2":
                    usuarioActual = Usuario.crearUsuario(scanner, usuarios);
                    if (usuarioActual != null) {
                        MenuServicio.menuUsuario(usuarioActual);
                    }
                    break;
                case "3":
                    MenuServicio.menuAdministrador();
                    break;
                case "0":
                    System.out.println("Gracias por usar el sistema. Hasta pronto.");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void iniciarSesion() {
        System.out.print("\nIngrese su nombre de usuario: ");
        String nombre = scanner.nextLine().trim();

        if (usuarios.containsKey(nombre)) {
            usuarioActual = usuarios.get(nombre);
            System.out.println("Bienvenido, " + usuarioActual.getNombre() + "!");
            MenuServicio.menuUsuario(usuarioActual);
        } else {
            System.out.println("Usuario no encontrado. Cree una cuenta primero.");
        }
    }
}