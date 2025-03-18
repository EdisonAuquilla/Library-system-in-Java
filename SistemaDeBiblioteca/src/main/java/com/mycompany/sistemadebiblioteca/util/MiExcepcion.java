/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadebiblioteca.util;

/**
 *
 * @author User
 */
public class MiExcepcion extends Exception {
    private final int codigoError;

    /**
     * Constructor que recibe un código de error y un mensaje.
     * 
     * @param codigoError Código numérico que representa el tipo de error.
     * @param mensaje Descripción del error.
     */
    public MiExcepcion(int codigoError, String mensaje) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    /**
     * Constructor que permite encadenar excepciones.
     * 
     * @param codigoError Código numérico del error.
     * @param mensaje Descripción del error.
     * @param causa Excepción original que causó el error (opcional).
     */
    public MiExcepcion(int codigoError, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = codigoError;
    }

    /**
     * Obtiene el código de error asociado a la excepción.
     * 
     * @return Código numérico del error.
     */
    public int getCodigoError() {
        return codigoError;
    }
}