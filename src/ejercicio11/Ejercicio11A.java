/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio11;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private Alumno pedirAlumno() {
        pasarPagina();
        System.out.println("Introducir alumno");
        System.out.println("*****************");
        System.out.print("Nombre: ");
        String nombre = s.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = s.nextLine();
        System.out.print("fechaNac: ");
        String fechaNac = s.nextLine();
        System.out.print("Telefono: ");
        String telefono = s.nextLine();
        System.out.print("Curso: ");
        String curso = s.nextLine();
        float notaMediaFinal = 0;
        boolean recogido = false;
        while (!recogido) {
            System.out.print("Nota media final: ");
            try {
                notaMediaFinal = Float.parseFloat(s.nextLine().replace(',', '.'));
                recogido = true;
            } catch (NumberFormatException ex) {
            }
        }
        return new Alumno(nombre, apellidos, fechaNac, telefono, curso, notaMediaFinal);
    }

    private void guardarAlumno(Alumno alumno) {
        File f = new File(ruta);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(f, true);
            //comprobar si ya hay alumnos
            if (leerUsuariosDeDisco().size() == 0) {
                //Si no hay alumnos crear un objectoutputstream normal
                oos = new ObjectOutputStream(fos);
            } else {
                //guardar siguientes
                oos = new AgregarObjectOutputStream(fos);
            }

            oos.writeObject(alumno);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ejercicio11A.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio11A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Alumno> leerUsuariosDeDisco() {
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        File f = new File(ruta);
        //si no existe retorna lista vacia
        if (!f.exists()) {
            return alumnos;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            while(true){
                alumnos.add((Alumno) ois.readObject());
            }
        } catch (EOFException eofe) {
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio11A.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ejercicio11A.class.getName()).log(Level.SEVERE, null, ex);
        }

        return alumnos;
    }

    private void leerUsuarios() {
        pasarPagina();
        ArrayList<Alumno> alumnos = leerUsuariosDeDisco();
        if (alumnos.size() == 0) {
            System.out.println("No hay alumnos almacenados");
        }

        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
        pedirIntro("");
    }

    //ATRIBUTOS
    /**
     * Scanner de consola
     */
    Scanner s = new Scanner(System.in);

    String ruta = "./src/ejercicio11/recursos/Alumnos.dat";

    //METODOS
    /**
     * Constructor
     */
    public void menu() {
        while (true) {
            int opcionElegida = this.mostrarMenu();
            switch (opcionElegida) {
                //introducir alumnos
                case 1 ->
                    guardarAlumno(pedirAlumno());
                //Leer alumnos en disco
                case 2 ->
                    leerUsuarios();
                //salir del programa
                default ->
                    System.exit(0);

            }
        }

        //escribir();
        //leer(alumnos);
    }

//UTILES DE ENTRADA DE DATOS POR CONSOLA Y MENU
    /**
     * Pide una opcion filtrando que sea valida(de 1 a 4)
     *
     * @return la opcion elegida
     */
    private int mostrarMenu() {
        int opcion = -1;
        while (opcion < 1 || opcion > 3) {
            this.pasarPagina();
            System.out.println("**************************************************");
            System.out.println("*                    OPCIONES                    *");
            System.out.println("**************************************************");
            System.out.println("Alumnos almacenados: " + leerUsuariosDeDisco().size());
            System.out.println("1)Introducir alumno");
            System.out.println("2)Leer archivo de alumnos");
            System.out.println("3)Salir");
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
        if (msg != null && msg.length() > 0) {
            System.out.println(msg);
        }
        System.out.println("Pulsa intro para continuar");
        System.out.println("******************************************************");
        return this.s.nextLine();
    }

    public static void main(String[] args) {
        new Ejercicio11A().menu();
    }

}
