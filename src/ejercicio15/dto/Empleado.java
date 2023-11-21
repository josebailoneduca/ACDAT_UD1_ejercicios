/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio15.dto;





/**
 *
 * @author Jose Javier BO
 */
public class Empleado {


    private int id;
    private String nombre;
    private String apellidos;
    private int sueldo;
    private int[] trabajos;
    public static int limiteTrabajos=2;
    public static int limiteNombre=20;
    public static int limiteApellidos=30;
    public static int longitudBytes = 4+2*limiteNombre+2*limiteApellidos+4+4*limiteTrabajos;
    public static int longitudBytesHastaTrabajos = 4+2*limiteNombre+2*limiteApellidos+4;
    public Empleado(int id, String nombre, String apellidos, int sueldo,int[]trabajos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sueldo = sueldo;
        this.trabajos = new int[limiteTrabajos];
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

    public boolean addTrabajo(int idTrabajo) {
         for (int trabajo : trabajos) {
            if (trabajo==idTrabajo)
                return true;
        }
        int nTrabajos = nTrabajosAsignados();
        if (nTrabajos < limiteTrabajos) {
            trabajos[nTrabajos] = idTrabajo;
            return true;
        } else {
            return false;
        }
    }

    private int nTrabajosAsignados() {
        int total = 0;
        for (int trabajo : trabajos) {
            if (trabajo != 0) {
                total++;
            }
        }
        return total;
    }
    
    public void deleteTrabajo(int idTrabajo){
        for (int i=0;i<limiteTrabajos;i++) {
            if (trabajos[i]==idTrabajo)
                trabajos[i]=-trabajos[i];
        }
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
