/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio14.dto;

/**
 *
 * @author Jose Javier BO
 */
public class Empleado {
    private int id;
    private String nombre;
    private int departamento;
    private double sueldo;

    public Empleado(int id, String nombre, int departamento, double sueldo) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.sueldo = sueldo;
    }

    public int getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
    
    
}
