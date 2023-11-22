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
public class Trabajo{


    
     /**
     * ID
     */
    private int id;
    /**
     * Nombre del trabajo
     */
    private String nombre;
    /**
     * Fecha del trabajo
     */
    private long fecha;
    
    /**
     * Id de los empleados
     */
    private int[] empleados;
    
    public static int limiteEmpleados=5;
    
    public static int limiteNombre=20;
    
   public static int longitudBytes = 4+2*limiteNombre+8+4*limiteEmpleados;
    public static int longitudBytesHastaEmpleados = 4+2*limiteNombre+8;
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
        return nombre.replace("\0","");
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
 
    
    
    
    public boolean addEmpleado(int idEmpleado) {
        for (int empleado : empleados) {
            if (empleado==idEmpleado)
                return true;
        }
        int nEmpleados = nEmpleadosAsignados();
        if (nEmpleados < limiteEmpleados) {
            empleados[nEmpleados] = idEmpleado;
            nEmpleados++;
            return true;
        } else {
            return false;
        }
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
    
    public void deleteEmpleado(int idEmpleado){
        for (int i=0;i<limiteEmpleados;i++) {
            if (empleados[i]==idEmpleado)
                empleados[i]=-empleados[i];
        }
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