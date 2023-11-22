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
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Javier BO
 */
public class Ejercicio12 {

    //ATRIBUTOS####################################################
    /**
     * Scanner de consola
     */
    Scanner s = new Scanner(System.in);

    /**
     * Ruta del archivo binario
     */
    String ruta = "./src/ejercicio12/recursos/ListaTrabajos.dat";

    int trabajosExistentes = 0;

    //METODOS###########################################################
    /**
     * Constructor. Agrega los datos iniciales al archivo y muestra el menu
     */
    public Ejercicio12() {
        trabajosExistentes = contarTrabajosEnDisco();
        agregarIniciales();
        menu();
    }

    /**
     * Agregar datos iniciales si no hay trabajos en el archivo
     */
    private void agregarIniciales() {
        //si ya tiene contenido no se agregan los datos iniciales
        if (trabajosExistentes > 0) {
            return;
        }
        //escribir T1
        escribirTrabajo(new Trabajo("T1", "01/08/2023", 2));
        escribirEmpleado(new Empleado("Pepe", "Pepillos", 2000));
        escribirEmpleado(new Empleado("Juan", "Juanillos", 2000));
        //escribir T2
        escribirTrabajo(new Trabajo("T2", "15/08/2023", 3));
        escribirEmpleado(new Empleado("Antonio", "Antonillos", 1700));
        escribirEmpleado(new Empleado("Laura", "Laurillas", 1700));
        escribirEmpleado(new Empleado("Jose", "Joselillos", 1500));
        //escribir T3
        escribirTrabajo(new Trabajo("T3", "01/09/2023", 2));
        escribirEmpleado(new Empleado("Sandra", "Sandrilla", 1450));
        escribirEmpleado(new Empleado("Rodrigo", "Rodriguillo", 1450));
    }

    /**
     * Ordena que se muestre el menu y lanza la accion que se ha elegido en el
     * mismo
     */
    private void menu() {
        while (true) {
            //recoger opcion del menu elegida
            int opcionElegida = this.mostrarMenu();
            switch (opcionElegida) {
                //Leer todos los trabajos del disco
                case 1 -> {
                    pasarPagina();
                    imprimirTrabajosDeDisco(null);
                }
                //Listar empleados de un trabajo
                case 2 -> {
                    pasarPagina();
                    listarEmpleadosDeUnTrabajo();
                }
                //introducir trabajo en archivo
                case 3 -> {
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
     * Lista los empleados de un trabajo
     */
    private void listarEmpleadosDeUnTrabajo() {
        //pedir el nombre a buscar
        String nombreTrabajo = "";
        while (nombreTrabajo.equals("")) {
            System.out.println("Introduzca el nombre de un trabajo para listar sus empleados: ");
            nombreTrabajo = s.nextLine();
        }
        //ordenar la impresion del trabajo 
        imprimirTrabajosDeDisco(nombreTrabajo);
    }

    /**
     * Pide los datos para un trabajo. Seguidamente pide los datos de los
     * empleados. Una vez tiene los datos de los empleados crea el objeto
     * Trabajo. Por ultimo lanza el grabado a disco del trabajo y de los
     * empleados
     */
    private void pedirDatosTrabajo() {
        pasarPagina();

        //1 PEDIR DATOS DEL TRABAJO
        System.out.println("Introducir trabajo");
        System.out.println("*****************");

        //Crear nombre del trabajo con formato T + (numero de trabajos existentes +1)
        String nombreTrabajo = "T" + (trabajosExistentes + 1);

        //recoger fecha reintentando hasta que el usuario introduce una fecha legible y aceptada
        String fecha = "";
        boolean fechaRecogida = false;
        while (!fechaRecogida) {
            try {
                //pedir fecha al usuario
                System.out.print("Fecha del trabajo (dia/mes/año): ");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                df.setLenient(false);
                String tmpFecha = s.nextLine();
                //Si el string introducido no es legible por el formato saltara una excepcion y se volvera a pedir
                //si el string introducio es de una fecha posterior a la actual la volvera a pedir
                Date fechaTemp = df.parse(tmpFecha);
                if (fechaTemp.after(new Date(System.currentTimeMillis()))) {
                    pedirIntro("Fecha incorrecta al ser posterior a la actual");
                    continue;
                }

                //Si es legible recalcula la fecha segun los valores introducidos sumando los excedentes a los limites
                //por ejemplo si el usuario escribe 1/13/2020 pondra el 1/1/2021 al escribir un mes mas allá del 12
                fecha = df.format(fechaTemp);

                fechaRecogida = true;

            } catch (ParseException ex) {
                pedirIntro("Formato de fecha incorrecto");
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

    /**
     * Escribe un trabajo a disco.
     *
     * @param trabajo El trabajo a escribir
     */
    private void escribirTrabajo(Trabajo trabajo) {
        File f = new File(ruta);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        //ESCRIBIR TRABAJO
        try {
            //preparar streams
            fos = new FileOutputStream(f, true);
            //Comprobar si ya hay trabajos para decidir que tipo de objectOutputStream crear
            if (trabajosExistentes == 0) {
                //SI NO HAY TRABAJOS CREA UN OBJECTOUTPUTSTREAM NORMAL
                oos = new ObjectOutputStream(fos);
            } else {
                //SI YA HAY TRABAJOS CREA UN AGREGAROBJECTOUTPUTSTREAM QUE EVITA LA ESCRITURA DE CABECERA
                oos = new AgregarObjectOutputStream(fos);
            }

            //escribir el trabajo en disco
            oos.writeObject(trabajo);

            //incrementar el numero de trabajos que hay en disco
            trabajosExistentes++;

            //tratamiento de excepciones y cierre de streams
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

        File f = new File(ruta);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        //ESCRIBIR EMPLEADO
        try {
            //preparacion de streams
            fos = new FileOutputStream(f, true);
            oos = new AgregarObjectOutputStream(fos);

            //escribir el trabajo en disco
            oos.writeObject(empleado);

            //tratamiento de excepciones y cierre de streams
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
     * Lee los trabajos y empleados de disco. Conforme los lee los imprimira. Si
     * se especifica n nombre de trabajo solo impimira ese trabajo
     *
     * @param nombreTrabajo Filtro del trabajo a imprimir por su nombre. Null si
     * no se quiere filtro y que se impriman todos
     *
     * @return el numero de trabajos que ya existen
     */
    private void imprimirTrabajosDeDisco(String nombreTrabajo) {

        File f = new File(ruta);
        //si no existe el archivo retorna lista vacia
        if (!f.exists()) {
            pedirIntro("No existe el archivo " + f.getAbsolutePath());
            return;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            //preparacion de streams
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);

            //recoger trabajos hasta EOF exception
            while (true) {

                //recoger trabajo
                Trabajo t = (Trabajo) ois.readObject();

                //filtro para trabajos
                //True si el nombreTrabajo es null o coincide con el trabajo leido. Al pasar el filtro se imprimira
                boolean filtroTrabajosOK = nombreTrabajo == null || nombreTrabajo.equals(t.getNombre());

                //imprimir trabajo si pasa el filtro
                if (filtroTrabajosOK) {
                    System.out.println("*********************************************");
                    System.out.println(t);
                }

                //recoger empleados
                for (int i = 0; i < t.getNumsEmpleados(); i++) {
                    Empleado e = (Empleado) ois.readObject();

                    //imprimir empleado si toca
                    if (filtroTrabajosOK) {
                        System.out.println(e);
                    }
                }

                //si pasa el filtro no nulo de trabajos es que se ha encontrado el trabajo 
                //por lo que rompemos el while
                if (nombreTrabajo != null && filtroTrabajosOK) {
                    break;
                }
            }//fin while

            //tratamiento de excepciones y cierre de streams
        } catch (EOFException eofe) {

        } catch (IOException ex) {
            System.out.println("Error accediendo a archivo");
        } catch (ClassNotFoundException ex) {

        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                System.out.println("Error accediendo a archivo");
            }
        }

        //pedir pulsar intro tras terminar la impresion
        pedirIntro("");
    }

    /**
     * Cuenta los trabajos que hay en el disco
     *
     * @return el numero de trabajos que ya existen
     */
    private int contarTrabajosEnDisco() {

        //cantidad de trabajos que hay en disco
        int cantidadDeTrabajos = 0;

        File f = new File(ruta);
        //si no existe el archivo retorna lista vacia
        if (!f.exists()) {
            return 0;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            //preparacion de streams
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);

            //recoger trabajos hasta EOF exception
            while (true) {
                //recoger trabajo y aumentar la cuenta
                Trabajo t = (Trabajo) ois.readObject();
                cantidadDeTrabajos++;

                //recorrer empleados
                for (int i = 0; i < t.getNumsEmpleados(); i++) {
                    ois.readObject();
                }
            }//fin while
            //tratamiento de excepciones y cierre de streams
        } catch (EOFException eofe) {
        } catch (IOException ex) {
            return 0;
        } catch (ClassNotFoundException ex) {
            System.out.println("Error leyendo archivo");
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
            System.out.println("Error leyendo archivo");
            }
        }
        //retorna true si hay trabajos y false si no lo hay
        return cantidadDeTrabajos;
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
            System.out.println("1)Leer todos los trabajos");
            System.out.println("2)Listar empleados de un trabajo");
            System.out.println("3)Introducir un nuevo trabajo");
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
        if (msg != null && msg.length() > 0) {
            System.out.println(msg);
        }
        System.out.println("Pulsa intro para continuar");
        System.out.println("******************************************************");
        return this.s.nextLine();
    }

    /**
     * INICIO DEL PROGRAMA
     *
     * @param args
     */
    public static void main(String[] args) {
        new Ejercicio12();
    }

}
