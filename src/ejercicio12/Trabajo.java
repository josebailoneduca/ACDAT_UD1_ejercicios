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
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1;
    
    /**
     * Nombre del trabajo
     */
    private String nombre;
    /**
     * Fecha del trabajo
     */
    private String fecha;
    /**
     * Numero de empleados
     */
    private int numsEmpleados;

    /**
     * Constructor de trabajo
     * @param nombre Nombre del trabajo
     * @param fecha Fecha del trabajo
     * @param numsEmpleados Numero de empleados
     */
    public Trabajo(String nombre, String fecha, int numsEmpleados) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.numsEmpleados = numsEmpleados;
    }

    //GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumsEmpleados() {
        return numsEmpleados;
    }

    public void setNumsEmpleados(int numsEmpleados) {
        this.numsEmpleados = numsEmpleados;
    }

    @Override
    public String toString() {
        return "Nombre del trabajo: " + nombre + ", fecha: " + fecha + ", Empleados: " + numsEmpleados;
    }

}
