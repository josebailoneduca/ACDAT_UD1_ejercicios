/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio11;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Realiza un mismo programa: A. Cree un fichero binario con objetos
 * serializados llamado ?Alumnos.dat? para guardar los datos de alumnos. Los
 * datos a guardar de cada alumno son: Nombre, Apellidos, FechaNac, Teléfono,
 * Curso, NotaMediaFinal B. Crear programa que lea fichero anterior y muestre
 * los datos por pantalla.
 *
 * @author Jose Javier BO
 */
public class Ejercicio11A {

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

        @Override
        public String toString() {
            return "Alumno{" + "nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNac=" + fechaNac + ", telefono=" + telefono + ", curso=" + curso + ", notaMediaFinal=" + notaMediaFinal + '}';
        }
    }

    //ATRIBUTOS
    /**
     * Scanner de consola
     */
    Scanner s = new Scanner(System.in);

    ArrayList<Alumno> alumnos = new ArrayList<Alumno>();

    
    
    
    //METODOS
    
    /**
     * Constructor
     */
    

    public void menu() {
        while (true) {
            int opcionElegida = this.mostrarMenu();
            switch (opcionElegida) {
                //pedir numeros primos
                case 1 ->
                    pedirNumerosPrimos();
                //Listar contenido del archivo
                case 2 ->
                    listarDatosDelArchivo();
                //Cambiar usuario
                case 3 ->
                    pedirUsuario();
                //salir del programa
                default ->
                    System.exit(0);

            }
        }

        entradaDeDatos();
        //escribir();
        //leer(alumnos);
    }

    private void entradaDeDatos() {

    }
//UTILES DE ENTRADA DE DATOS POR CONSOLA Y MENU

    /**
     * Pide una opcion filtrando que sea valida(de 1 a 4)
     *
     * @return la opcion elegida
     */
    private int mostrarMenu() {
        int opcion = -1;
        while (opcion < 1 || opcion > 4) {
            this.pasarPagina();
            System.out.println("**************************************************");
            System.out.println("*                    OPCIONES                    *");
            System.out.println("**************************************************");
            System.out.println("Usuarios actuales: " + alumnos.size());
            System.out.println("1)Introducir alumno");
            System.out.println("2)Guardar alumnos en archivo");
            System.out.println("3)Leer archivo de alumnos");
            System.out.println("4)Salir");
            System.out.println("Elije una opcion:");
            try {
                String respuesta = this.s.nextLine();
                opcion = Integer.parseInt(respuesta);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return opcion;
    }

    /**
     * Salta 100 lineas en la consola simulando un salto de pagina
     */
    private void pasarPagina() {
        for (int i = 0; i < 100; i++) {
            System.out.println("");
        }
    }

    /**
     * Muestra un mensaje y pide pulsar intro
     *
     * @param msg El mensaje a mostrar
     * @return Lo introducido por el usuario
     */
    private String pedirIntro(String msg) {
        System.out.println("******************************************************");
        System.out.println(msg);
        System.out.println("Pulsa intro para continuar");
        System.out.println("******************************************************");
        return this.s.nextLine();
    }

    public static void main(String[] args) {
        new Ejercicio11A().menu();
    }

}
