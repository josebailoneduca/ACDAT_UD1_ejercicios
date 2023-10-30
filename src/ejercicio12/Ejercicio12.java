/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio12;

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
 *
 * @author Jose Javier BO
 */
public class Ejercicio12 {

    //ATRIBUTOS
    /**
     * Scanner de consola
     */
    Scanner s = new Scanner(System.in);

    /**
     * Ruta del archivo binario
     */
    String ruta = "./src/ejercicio12/recursos/ListaTrabajos.dat";

    /**
     * Constructor. Agrega los datos iniciales al archivo y muestra el menu
     */
    public Ejercicio12() {
        agregarIniciales();
        menu();
    }

    //METODOS
    /**
     * Agregar datos iniciales si no hay trabajos en el archivo
     */
    private void agregarIniciales() {
        //si ya tiene contenido no se agrega el inicial
        if (leerTrabajosDeDisco(false)) {
            return;
        }
        //T1
        escribirTrabajo(new Trabajo("T1", "1/8/2023", 2));
        escribirEmpleado(new Empleado("Pepe", "Pepillos", 2000));
        escribirEmpleado(new Empleado("Juan", "Juanillos", 2000));
        //T2
        escribirTrabajo(new Trabajo("T2", "15/8/2023", 3));
        escribirEmpleado(new Empleado("Antonio", "Antonillos", 1700));
        escribirEmpleado(new Empleado("Laura", "Laurillas", 1700));
        escribirEmpleado(new Empleado("Jose", "Joselillos", 1500));
        //T3
        escribirTrabajo(new Trabajo("T3", "1/9/2023", 2));
        escribirEmpleado(new Empleado("Sandra", "Sandrilla", 1450));
        escribirEmpleado(new Empleado("Rodrigo", "Rodriguillo", 1450));
    }

    /**
     * Ordena que se muestre el menu y lanza la accion que se ha elegido en el
     * mismo
     */
    private void menu() {
        while (true) {
            int opcionElegida = this.mostrarMenu();
            switch (opcionElegida) {
                //Leer todos los trabajos del disco
                case 1 -> {
                    pasarPagina();
                    leerTrabajosDeDisco(true);
                }
                //introducir trabajo en archivo
                case 2 -> {
                    pasarPagina();
                    pedirDatosTrabajo();
                }
                //salir del programa
                default ->
                    System.exit(0);
            }
        }
    }

    /**
     * Pide los datos para un trabajo. Seguidamente pide los datos de los
     * empleados. Una vez tiene los datos de los empleados crea el objeto
     * Trabajo Por ultimo lanza el grabado a disco del trabajo y de los
     * empleados
     */
    private void pedirDatosTrabajo() {
        pasarPagina();

        //1 PEDIR DATOS DEL TRABAJO
        System.out.println("Introducir trabajo");
        System.out.println("*****************");

        //recoger nombre
        System.out.print("Nombre: ");
        String nombreTrabajo = s.nextLine();

        //recoger fecha
        String fecha = "";
        boolean fechaRecogida = false;
        while (!fechaRecogida) {
            try {
                System.out.print("Fecha (dia/mes/año): ");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String tmpFecha = s.nextLine();
                //si el string introducido no es legible por el formato saltara una excepcion
                //Si es legible recalcula la fecha segun los valores introducidos sumando los excedentes a los limites
                //por ejemplo si se escribe 1/13/2020 pondra el 1/1/2021 al escribir un mes mas allá del 12
                fecha = df.format(df.parse(tmpFecha));
                //comprobar que el usuario está conforme con la fecha entendida
                System.out.println("¿La fecha " + fecha + " es correcta?(s/n)");
                String acepta = s.nextLine();
                if (acepta.toLowerCase().equals("s")) {
                    fechaRecogida = true;
                }
            } catch (ParseException ex) {
            }
        }

        //2 PEDIR DATOS DE EMPLEADOS
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();
        boolean pedirEmpleado = true;
        //bucle para pedir varios empleados
        while (pedirEmpleado) {
            System.out.println("Introducir empleado");
            System.out.println("*****************");

            //nombre del empleado
            System.out.print("Nombre: ");
            String nombreEmpleado = s.nextLine();

            //apellidos del empleado
            System.out.print("Apellidos: ");
            String apellidosEmpleado = s.nextLine();

            //sueldo del empleado
            int sueldo = 0;
            boolean sueldoRecogido = false;
            while (!sueldoRecogido) {
                System.out.print("Sueldo: ");
                try {
                    sueldo = Integer.parseInt(s.nextLine());
                    sueldoRecogido = true;
                } catch (NumberFormatException ex) {
                }
            }

            //agregar empleado al array de empleados
            empleados.add(new Empleado(nombreEmpleado, apellidosEmpleado, sueldo));

            //pregunta si se quiere seguir agregando empleados
            System.out.println("¿Desea introducir mas empleados?(s/n)");
            String acepta = s.nextLine();
            if (acepta.toLowerCase().equals("n")) {
                pedirEmpleado = false;
            }
        }

        //3 ESCRIBIR TRABAJO Y EMPLEADOS EN DISCO
        escribirTrabajo(new Trabajo(nombreTrabajo, fecha, empleados.size()));
        for (Empleado empleado : empleados) {
            escribirEmpleado(empleado);
        }
    }

    private void escribirTrabajo(Trabajo trabajo) {
        File f = new File(ruta);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        //ESCRIBIR TRABAJO
        try {
            //TRABAJO
            boolean archivoTieneDatos = leerTrabajosDeDisco(false);
            //preparacion de streams
            fos = new FileOutputStream(f, true);
            //comprobar si ya hay trabajos
            if (!archivoTieneDatos) {
                //Si no hay trabajos crear un objectoutputstream normal
                oos = new ObjectOutputStream(fos);
            } else {
                //Si ya hay trabajos crear un AgregarObjectOuputStream que evita la escritura de cabecera
                oos = new AgregarObjectOutputStream(fos);
            }
            //escribir el trabajo en disco
            oos.writeObject(trabajo);
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
                Logger.getLogger(Ejercicio12.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Escribe un empleado a disco
     *
     * @param empleado El empleado a escribir
     */
    private void escribirEmpleado(Empleado empleado) {

        //Escribir
        File f = new File(ruta);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        //ESCRIBIR TRABAJO
        try {
            //preparacion de streams
            fos = new FileOutputStream(f, true);
            oos = new AgregarObjectOutputStream(fos);

            //escribir el trabajo en disco
            oos.writeObject(empleado);
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
                Logger.getLogger(Ejercicio12.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Lee los trabajos y empleados de disco.
     *
     * @param imprimir True imprimir los trabajos
     * @return true si hay algun trabajo, false si no lo hay
     */
    private boolean leerTrabajosDeDisco(boolean imprimir) {
        int cantidadDeTrabajos = 0;

        File f = new File(ruta);
        //si no existe el archivo retorna lista vacia
        if (!f.exists()) {
            return false;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            //preparacion de streams
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            //recoger trabajos hasta EOF exception
            while (true) {

                Trabajo t = (Trabajo) ois.readObject();
                cantidadDeTrabajos++;
                if (imprimir) {
                    System.out.println("*********************************************");
                    System.out.println(t);
                }

                for (int i = 0; i < t.getNumsEmpleados(); i++) {
                    Empleado e = (Empleado) ois.readObject();
                    if (imprimir) {
                        System.out.println(e);
                    }
                }

            }
        } catch (EOFException eofe) {
        } catch (IOException ex) {
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ejercicio12.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio12.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (imprimir) {
            pedirIntro("");
        }
        return cantidadDeTrabajos > 0;

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
            System.out.println("1)Leer todos los trabajos");
            System.out.println("2)Introducir un nuevo trabajo");
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
        new Ejercicio12();
    }

}
