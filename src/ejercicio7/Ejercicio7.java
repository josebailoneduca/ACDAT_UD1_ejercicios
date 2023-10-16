/*
 * LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 Lista de paquetes
 */
package ejercicio7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Ejercicio 7
 *
 * @author Jose Javier BO
 */
public class Ejercicio7 {

    /**
     * Ruta y nombre donde se guardara el block de notas
     */
    private String rutaBloc = "./src/ejercicio7/recursos";
    private String nombreBloc = "bloc_notas.txt";

    //guarda si esta inicializado o no el bloc
    private boolean inicializado = false;
    /**
     * Ultima linea del bloc
     */
    private int ultimaLinea = -1;

    /**
     * Scanner para la entrada de teclado
     */
    Scanner s = new Scanner(System.in);

    /**
     * Constructor
     */
    public Ejercicio7() {
        //Muestra el menu mientras se elija una opcion del 1 al 3(acorde a las secciones del ejercicio). 
        //La opcion 4 termina el programa
        while (true) {
            int opcionElegida = this.mostrarMenu();
            switch (opcionElegida) {
                //agregar nota
                case 1 ->
                    agregarNota();
                //leer bloc
                case 2 ->
                    leerBloc();
                //leer bloc
                case 3 ->
                    eliminarBloc();
                //salir del programa
                default ->
                    System.exit(0);
            }
        }
    }

    /**
     * Hace las comprobaciones iniciales sobre el bloc de notas Comprobando si
     * existe o no. Si no existe lo crea y pide el nombre de pila para a
     * continuacion agregarlo Si ya existe comprueba cual es la ultima linea
     * para almacenar ese dato
     *
     * En ambos casos marca en la clase que el bloc esta inicializado
     */
    private void inicializarBloc() {
        //parar si ya esta inicializado
        if (this.inicializado) {
            return;
        }

        //inicializarlo
        File archivo = new File(getRutaBloc());
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //si no existe crearlo y pedir el nombre de pila
            if (!archivo.exists()) 
                archivo.createNewFile();

            //recorrer las lineas del bloc para contarlas
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            while (br.readLine() != null) {
                this.ultimaLinea++;
            }
        } catch (IOException ex) {
            pedirIntro("Error accediento al archivo (" + archivo.getAbsolutePath() + "):" + ex.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                pedirIntro(ex.getMessage());
            }
        }

        //si esta vacio pedimos el nombre de pila
        if (this.ultimaLinea < 0) {
            agregarLinea(pedirEntrada("Introduce tu nombre de pila"));
        }
        //marcar que esta inicializado
        this.inicializado = true;
    }

    /**
     * Compone y devuelve el path al bloc
     *
     * @return La ruta
     */
    private String getRutaBloc() {
        return Path.of(rutaBloc, nombreBloc).toString();
    }

    /**
     * Agrega una nota al bloc pidiendo la entrada de la nota y luego
     * agregandola Antes de agregar la nota inicializa el bloc
     */
    private void agregarNota() {
        pasarPagina();
        //inicializar el bloc
        inicializarBloc();
        //agregar la linea
        agregarLinea(pedirEntrada("Introduce el contenido de la nota"));
    }

    /**
     * Lee el contenido del bloc y lo muestra en pantalla. Antes de hacer la
     * lectura inicializa el bloc
     */
    private void leerBloc() {
        pasarPagina();
        //inicializar el bloc
        inicializarBloc();

        //preparacion de variables
        File archivo = new File(getRutaBloc());
        FileReader fr = null;
        BufferedReader br = null;

        //recorrer el bloc e imprimirlo en pantalla
        try {
            System.out.println("******************************************************");
            System.out.println("              INICIO DEL BLOC                 ");
            System.out.println("******************************************************");

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea = "";
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            pedirIntro("              FIN DEL BLOC                 ");

        } catch (IOException ex) {
            pedirIntro("Error leyendo el archivo(" + archivo.getAbsolutePath() + "): " + ex.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                pedirIntro(ex.getMessage());
            }
        }
    }

    /**
     * Agrega una linea al bloc
     *
     * @param texto Texto a agregar
     */
    private void agregarLinea(String texto) {

        //prepara variables
        File archivo = new File(getRutaBloc());
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            //prepara writters
            fw = new FileWriter(archivo, true);
            bw = new BufferedWriter(fw);
            //incrementa el numero de linea
            this.ultimaLinea++;
            //prepara la cabecera de la linea
            String cabecera = "\n" + this.ultimaLinea + "-";
            //si es la primera linea implica que se trata del nombre del bloc
            //por tanto no incluimos el numero de linea si no un texto
            if (this.ultimaLinea == 0) {
                cabecera = "Nombre del bloc:";
            }
            //agrega la linea
            bw.append(cabecera + texto);
        } catch (IOException ex) {
            pedirIntro("Error de entrada salida escribiendo el fichero(" + archivo.getAbsolutePath() + "): " + ex.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                pedirIntro(ex.getMessage());
            }
        }
    }

    /**
     * Elimina el bloc
     */
    private void eliminarBloc() {
        File archivo = new File(getRutaBloc());
        if (archivo.exists()) {
            archivo.delete();
        }

        this.ultimaLinea = -1;
        this.inicializado = false;

    }

    //UTILES DE ENTRADA DE DATOS POR CONSOLA Y MENU
    /**
     * Pide una opcion filtrando que sea valida(de 1 a 4)
     *
     * @return la opcion elegida
     */
    public int mostrarMenu() {
        int opcion = -1;
        while (opcion < 1 || opcion > 4) {
            this.pasarPagina();
            System.out.println("**************************************************");
            System.out.println("*                    OPCIONES                    *");
            System.out.println("**************************************************");
            System.out.println("1)Agregar nota al bloc");
            System.out.println("2)Leer el bloc");
            System.out.println("3)Eliminar bloc");
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
    public void pasarPagina() {
        for (int i = 0; i < 100; i++) {
            System.out.println("");
        }
    }

    /**
     * Muestra un mensaje y pide una entrada
     *
     * @param msg El mensaje a mostrar
     * @return Lo introducido por el usuario
     */
    public String pedirEntrada(String msg) {
        System.out.println("******************************************************");
        System.out.println(msg + ":");
        System.out.println("******************************************************");
        return this.s.nextLine();
    }

    /**
     * Muestra un mensaje y pide pulsar intro
     *
     * @param msg El mensaje a mostrar
     * @return Lo introducido por el usuario
     */
    public String pedirIntro(String msg) {
        System.out.println("******************************************************");
        System.out.println(msg);
        System.out.println("Pulsa intro para continuar");
        System.out.println("******************************************************");
        return this.s.nextLine();
    }

    /**
     * Main del ejercicio. Simplemente crea una nueva instancia de la clase
     *
     * @param args
     */
    public static void main(String[] args) {
        new Ejercicio7();
    }

}
