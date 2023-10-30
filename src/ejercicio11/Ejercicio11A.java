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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    //ATRIBUTOS
    /**
     * Scanner de consola
     */
    Scanner s = new Scanner(System.in);

    /**
     * Ruta del archivo binario
     */
    String ruta = "./src/ejercicio11/recursos/Alumnos.dat";

    //METODOS
    /**
     * Ordena que se muestre el menu y lanza la accion que se ha elegido en el
     * mismo
     */
    public void menu() {
        while (true) {
            int opcionElegida = this.mostrarMenu();
            switch (opcionElegida) {
                //introducir alumnos en archivo
                case 1 ->
                    guardarAlumno(pedirAlumno());
                //Leer alumnos en disco
                case 2 ->
                    leerAlumnos();
                //salir del programa
                default ->
                    System.exit(0);

            }
        }
    }

    /**
     * Pide los datos para un alumno
     *
     * @return Un objeto Alumno con los datos
     */
    private Alumno pedirAlumno() {
        pasarPagina();
        System.out.println("Introducir alumno");
        System.out.println("*****************");
        System.out.print("Nombre: ");
        String nombre = s.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = s.nextLine();

        //recoger fecha
        String fechaNac = "";
        boolean fechaRecogida = false;
        while (!fechaRecogida) {
            try {
                System.out.print("Fecha de nacimiento (dia/mes/año): ");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String tmpFecha = s.nextLine();
                //si el string introducido no es legible por el formato saltara una excepcion
                //recalcula la fecha segun los valores introducidos sumando los excedentes a los limites
                //por ejemplo si se escribe 1/13/2020 pondra el 1/1/2021 al escribir un mes mas
                fechaNac = df.format(df.parse(tmpFecha));
                System.out.println("¿La fecha " + fechaNac + " es correcta?(s/n)");
                String acepta = s.nextLine();
                if (acepta.toLowerCase().equals("s")) {
                    fechaRecogida = true;
                }
            } catch (ParseException ex) {
            }
        }
        //telefono
        System.out.print("Telefono: ");
        String telefono = s.nextLine();
        //curso
        System.out.print("Curso: ");
        String curso = s.nextLine();
        //nota
        float notaMediaFinal = 0;
        boolean notaRecogida = false;
        while (!notaRecogida) {
            System.out.print("Nota media final: ");
            try {
                notaMediaFinal = Float.parseFloat(s.nextLine().replace(',', '.'));
                notaRecogida = true;
            } catch (NumberFormatException ex) {
            }
        }

        //retorno del alumno recogido
        return new Alumno(nombre, apellidos, fechaNac, telefono, curso, notaMediaFinal);
    }

    /**
     * Guarda un alumno a disco
     *
     * @param alumno El objeto alumno a guardar
     */
    private void guardarAlumno(Alumno alumno) {
        File f = new File(ruta);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            //preparacion de streams
            fos = new FileOutputStream(f, true);
            //comprobar si ya hay alumnos
            if (leerAlumnosDeDisco().isEmpty()) {
                //Si no hay alumnos crear un objectoutputstream normal
                oos = new ObjectOutputStream(fos);
            } else {
                //Si ya hay alumnos crear un AgregarObjectOuputStream que evita la escritura de cabecera
                oos = new AgregarObjectOutputStream(fos);
            }

            //escribir el alumno en disco
            oos.writeObject(alumno);

        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado " + f.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error escribiendo a archivo " + f.getAbsolutePath());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio11A.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Lee los alumnos desde el archivo binario y devuelve un arraylist con
     * ellos
     *
     * @return Un arraylist conteniendo los alumos recogidos o un arraylist
     * vacio si no ha encontrado ninguno
     */
    private ArrayList<Alumno> leerAlumnosDeDisco() {
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        File f = new File(ruta);
        //si no existe el archivo retorna lista vacia
        if (!f.exists()) {
            return alumnos;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            //preparacion de streams
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            //recoger alumnos hasta EOF exception
            while (true) {
                alumnos.add((Alumno) ois.readObject());
            }
        } catch (EOFException eofe) {
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio11A.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ejercicio11A.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio11A.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return alumnos;
    }

    /**
     * Lee los alumnos y los muestra en pantalla
     */
    private void leerAlumnos() {
        pasarPagina();
        //recoger alumnos del archivo
        ArrayList<Alumno> alumnos = leerAlumnosDeDisco();
        //si no hay alumnos avisamos
        if (alumnos.size() == 0) {
            System.out.println("No hay alumnos almacenados");
        }

        //recorrer alumnos en imprimirlos en pantalla
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
        pedirIntro("");
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
            System.out.println("Alumnos almacenados: " + leerAlumnosDeDisco().size());
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
