/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio12;

import java.io.Serializable;

/**
 *
 * @author Jose Javier BO
 */
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1;
    private String nombre;
    private String apellidos;
    private int sueldo;

    /**
     * Constructor. 
     * @param nombre Nombre del empleado
     * @param apellidos Apellidos del empleado
     * @param sueldo Sueldo del empleado
     */
    public Empleado(String nombre, String apellidos, int sueldo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sueldo = sueldo;
    }

    //GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Empleado: " + nombre + " " + apellidos + ", sueldo: " + sueldo;
    }

}
