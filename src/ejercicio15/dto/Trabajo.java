/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio15.dto;




/**
 *
 * DTO de trabajo.
 * 
 * @author Jose Javier BO
 */
public class Trabajo{
   //VALORES DE CONFIGURACION
    //limite de empleados asignados
    public static int limiteEmpleados=5; 
    //limite de longitud del nombre
    public static int limiteNombre=20;
    //Calculo de longitud en bytes del registro de un trabajo.
    public static int longitudBytes = 4+2*limiteNombre+8+4*limiteEmpleados;
    //calculo de la longitud desde el inicio del registro hasta el inicio de empleados asignados
    public static int longitudBytesHastaEmpleados = 4+2*limiteNombre+8;

    //CAMPOS DE EMPLEADO
    private int id;
    private String nombre;
    private long fecha;
    private int[] empleados;
    
    /**
     * Constructor
     * @param id
     * @param nombre
     * @param fecha
     * @param empleados 
     */
    public Trabajo(int id, String nombre, Long fecha,int[]empleados) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.empleados=empleados;
    }

    // GETTERS Y SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.replace("\0","");//limpieza de caracteres nulos
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

 
    public int[] getEmpleados() {
        return empleados;
    }

    public void setEmpleados(int[] empleados) {
        this.empleados = empleados;
    }
 

    public int nEmpleadosAsignados() {
        int total = 0;
        for (int empleado : empleados) {
            if (empleado != 0) {
                total++;
            }
        }
        return total;
    }
   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trabajo other = (Trabajo) obj;
        return this.id == other.id;
    }
    
    
}