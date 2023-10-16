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
import java.util.logging.Level;
import java.util.logging.Logger;

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

    //almacena el nombre de pila usado para el bloc de notas
    private String nombrePila = "";
    
    //guarda si esta inicializado o no el block
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
        //inicializar el bloc de notas
        inicializarBloc();
        //Muestra el menu mientras se elija una opcion del 1 al 3(acorde a las secciones del ejercicio). 
        //Otras opciones terminan el programa
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
     * Hace las comprobaciones iniciales sobre el bloc de notas
     * Comprobando si existe o no.
     * Si no existe lo crea y pide el nombre de pila
     * Si existe comprueba cual es la ultima linea
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
            //ver si existe
            if (!archivo.exists()) {
                //si no existe crearlo y pedir el nombre de pila
                archivo.createNewFile();
                agregarLinea(pedirEntrada("Introduce tu nombre de pila"));
                this.inicializado = true;
            } else {
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea = "";
                pasarPagina();
                while ((linea = br.readLine()) != null) {
                    this.ultimaLinea++;
                }
                //si esta vacio pedimos igualmente el nombre de pila
                if (this.ultimaLinea < 0) {
                    agregarLinea(pedirEntrada("Introduce tu nombre de pila"));
                }
                //marcar que esta inicializado
                this.inicializado = true;
            }
        } catch (IOException ex) {
            pedirIntro("Error leyendo el archivo ("+archivo.getAbsolutePath()+"):"+ex.getMessage());
        }finally{
            try {
                if (br!=null)
                    br.close();
                if (fr!=null)
                    fr.close();
            } catch (IOException ex) {
                pedirIntro(ex.getMessage());
            }
        }
    }

    /**
     * Compone y retorna el path al bloc
     * @return La ruta
     */
    private String getRutaBloc() {
        return Path.of(rutaBloc, nombreBloc).toString();
    }

    
    /**
     * Agrega una linea al bloc
     * @param texto Texto a agregar
     */
    private void agregarLinea(String texto) {
        
        //prepara variables
        File archivo = new File(getRutaBloc());
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            //prepara writters
            fw = new FileWriter(archivo,true);
            bw = new BufferedWriter(fw);
            //incrementa el numero de linea
            this.ultimaLinea++;
            //prepara la cabecera
            String cabecera = "\n" + this.ultimaLinea + "-";
            //si es la primera linea implica que se trata del nombre del bloc
            //por tanto no incluimos el numero de linea
            if (this.ultimaLinea == 0) {
                cabecera = "Nombre del bloc:";
            }
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

    private void agregarNota() {
        inicializarBloc();
        agregarLinea(pedirEntrada("Introduce el contenido de la nota"));
    }

    private void leerBloc() {
        inicializarBloc();
        File archivo = new File(getRutaBloc());
        FileReader fr = null;
        BufferedReader br = null;
        try {
            System.out.println("******************************************************");
            System.out.println("              INICIO DEL BLOC                 ");
            System.out.println("******************************************************");
            fr = new FileReader(archivo);// Envío datos texto
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

    private void eliminarBloc() {
        File archivo = new File(getRutaBloc());
        if (archivo.exists())
           archivo.delete();
        
        this.ultimaLinea=-1;
        this.inicializado=false;

    }
    
    
    
    
    //UTILES DE ENTRADA DE DATOS POR CONSOLA Y MENU
    /**
     * Pide una opcion filtrando que sea valida
     *
     * @return la opcion elegida
     */
    public int mostrarMenu() {
        int opcion = -1;
        while (opcion < 1 || opcion > 5) {
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
     * Muestra un mensaje y pide una entrada
     *
     * @param msg El mensaje a mostrar
     * @return Lo introducido por el usuario
     */
    public String pedirIntro(String msg) {
        System.out.println("******************************************************");
        System.out.println(msg + "");
        System.out.println("Pulsa intro para continuar");
        System.out.println("******************************************************");
        return this.s.nextLine();
    }
    
    public static void main(String[] args) {
        new Ejercicio7();
    }

}
