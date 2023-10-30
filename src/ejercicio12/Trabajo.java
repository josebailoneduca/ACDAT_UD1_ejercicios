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
    private String nombre;
    private String fecha;
    private int numsEmpleados;

    public Trabajo(String nombre, String fecha, int numsEmpleados) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.numsEmpleados = numsEmpleados;
    }

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
    
    
}
