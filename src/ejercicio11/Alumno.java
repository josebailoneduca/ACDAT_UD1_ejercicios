/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio11;

import java.io.Serializable;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
    /**
     * Clase para almacenar estructura de datos de alumno
     */
    class Alumno implements Serializable {

        String nombre;
        String apellidos;
        String fechaNac;
        String telefono;
        String curso;
        float notaMediaFinal;

        public Alumno(String nombre, String apellidos, String fechaNac, String telefono, String curso, float notaMediaFinal) {
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.fechaNac = fechaNac;
            this.telefono = telefono;
            this.curso = curso;
            this.notaMediaFinal = notaMediaFinal;
            
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

        public String getFechaNac() {
            return fechaNac;
        }

        public void setFechaNac(String fechaNac) {
            this.fechaNac = fechaNac;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getCurso() {
            return curso;
        }

        public void setCurso(String curso) {
            this.curso = curso;
        }

        public float getNotaMediaFinal() {
            return notaMediaFinal;
        }

        public void setNotaMediaFinal(float notaMediaFinal) {
            this.notaMediaFinal = notaMediaFinal;
        }

        @Override
        public String toString() {
            return "Alumno{" + "nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNac=" + fechaNac + ", telefono=" + telefono + ", curso=" + curso + ", notaMediaFinal=" + notaMediaFinal + '}';
        }
    }