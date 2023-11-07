/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio13;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jose Javier BO
 */
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;
    private String apellidos;
    private Date fechaNac;
    private String nombreCiclo;
    private int curso;
    private ArrayList<Modulo> modulos = new ArrayList<Modulo>();

    public Alumno(int id, String nombre, String apellidos, Date fechaNac, String nombreCiclo, int curso, ArrayList<Modulo> modulos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNac = fechaNac;
        this.nombreCiclo = nombreCiclo;
        this.curso = curso;
        this.modulos = modulos;
    }

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

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getNombreCurso() {
        return nombreCiclo;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCiclo = nombreCurso;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public ArrayList<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(ArrayList<Modulo> modulos) {
        this.modulos = modulos;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYY");
        String out= "ID: " + id + "\nNombre:" + nombre + "\napellidos:" + apellidos + "\nfechaNac:" + sdf.format(fechaNac) + "\nnombreCurso:" + nombreCiclo + "\ncurso:" + curso;
        for (Modulo modulo : modulos) {
            out += "\n" + modulo;
        }
        return out;
    }
 

    public ArrayList<String> getProfesores() {
        ArrayList<String> profesores = new ArrayList<String>();
        for (Modulo modulo : modulos) {
            if (!profesores.contains(modulo.getProfesor())) {
                profesores.add(modulo.getProfesor());
            }
        }
        return profesores;
    }

    public float getNotaMedia(){
        float media = 0;
        for (Modulo modulo : modulos) {
            media += modulo.getNotaFinal();
        }
        media = media / modulos.size();
        return media;
    }

}
