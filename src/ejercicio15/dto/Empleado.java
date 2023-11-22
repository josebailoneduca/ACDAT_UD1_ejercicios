/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio15.dto;





/**
 *
 * DTO de empleado.
 * 
 * 
 * @author Jose Javier BO
 */
public class Empleado {

    //VALORES DE CONFIGURACION
    //limite de trabajos asignados
    public static int limiteTrabajos=2;
    //limite de longitud del nombre
    public static int limiteNombre=20;
    //limite de longitud de apellidos
    public static int limiteApellidos=30;
    //calculo de la longitud en bytes total para un registro de empleado
    public static int longitudBytes = 4+2*limiteNombre+2*limiteApellidos+4+4*limiteTrabajos;//id+nombre+apellido+sueldo+trabajos
    //calculo de la longitud desde el inicio del registro hasta el inicio de trabajos asignados
    public static int longitudBytesHastaTrabajos = 4+2*limiteNombre+2*limiteApellidos+4;//id+nombre+apellido+sueldo+trabajos
    
    //CAMPOS DE EMPLEADO
    private int id;
    private String nombre;
    private String apellidos;
    private int sueldo;
    private int[] trabajos;
    
    /**
     * Constructor
     * @param id
     * @param nombre
     * @param apellidos
     * @param sueldo
     * @param trabajos 
     */
    public Empleado(int id, String nombre, String apellidos, int sueldo,int[]trabajos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sueldo = sueldo;
        this.trabajos = trabajos;
    }

    //GETTERS Y SETTERS
    public String getNombre() {
        return nombre.replace("\0","");//limpieza de caracteres nulos
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos.replace("\0","");//limpieza de caracteres nulos
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 

    public int[] getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(int[] trabajos) {
        this.trabajos = trabajos;
    }

    @Override
    public String toString() {
        return "Empleado: " + nombre + " " + apellidos + ", sueldo: " + sueldo;
    }

 
    /**
     * Devuelve el numero de trabajos asignados
     * @return El numero de trabajos
     */
    public int nTrabajosAsignados() {
        int total = 0;
        for (int trabajo : trabajos) {
            if (trabajo != 0) {
                total++;
            }
        }
        return total;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
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
        final Empleado other = (Empleado) obj;
        return this.id == other.id;
    }
    
    
}
