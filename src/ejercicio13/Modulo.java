/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio13;

import java.io.Serializable;

/**
 *
 * @author Jose Javier BO
 */
class Modulo implements Serializable{

    //ATRIBUTOS
    private static final long serialVersionUID = 1L;
    private String nombreModulo;
    private String profesor;
    private int notaFinal;

    //CONSTRUCTOR
    public Modulo(String nombre, String profesor, int notaFinal) {
        this.nombreModulo = nombre;
        this.profesor = profesor;
        this.notaFinal = notaFinal;
    }

    public Modulo(String nombre, String profesor) {
        this.nombreModulo = nombre;
        this.profesor = profesor;
        this.notaFinal = 0;
    }
    
    //GETTERS Y SETTERS
    public String getNombre() {
        return nombreModulo;
    }

    public void setNombre(String nombre) {
        this.nombreModulo = nombre;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(int notaFinal) {
        this.notaFinal = notaFinal;
    }

    @Override
    public String toString() {
        return "Modulo: " + nombreModulo + ", Profesor:" + profesor + ", Nota final:" + notaFinal ;
    }

}
