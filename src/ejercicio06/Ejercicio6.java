/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ejercicio 6
 *
 * Muestra un menu y da a elegir entre 4 opciones o salir 1 Va pidiendo entradas
 * de texto y luego las escribe en disco en una sola linea separando cada linea
 * de la entrada con un "*" 2 Lee un archivo escrito con el formato del apartado
 * 1 lo muestra y cuenta sus palabras conforme lo lee 3 Lee un archivo y lo
 * muestra. Cuenta las palabras en un metodo independiente 4 Ofrece la
 * informacion de 2 archivos nombre-tamaño-nº palabras
 *
 * @author Jose Javier Bailon Ortiz
 */
public class Ejercicio6 {

    /**
     * Scanner para la entrada de teclado
     */
    Scanner s = new Scanner(System.in);

    public Ejercicio6() {

        //Muestra el menu mientras se elija una opcion del 1 al 4(acorde a las secciones del ejercicio). 
        //La opocion 5 Termina el programa
        while (true) {
            int opcionElegida = this.mostrarMenu();
            switch (opcionElegida) {
                //escribir texto
                case 1 ->
                    apartado1();
                //leer archivo escrito segun apartado 1
                case 2 ->
                    apartado2();
                //Contar palabras de fichero
                case 3 ->
                    apartado3();
                //Comparar ficheros
                case 4 ->
                    apartado4();
                default -> //salir del programa
                    System.exit(0);
            }
        }
    }

    /**
     * Inicio del apartado 1 de escritura de un texto en un archivo
     */
    private void apartado1() {
        //instrucciones para el usuario
        System.out.println("******************************************************");
        System.out.println("Introduzca las lineas de texto que desee. \n"
                + "Cuando quiera terimar pulse intro en una linea vacia:");
        System.out.println("******************************************************");

        //variables locales
        ArrayList<String> lineas = new ArrayList<String>();
        String linea = "";

        //entrada de lineas por teclado
        while ((linea = this.s.nextLine()) != "") {
            lineas.add(linea);
        }
        //escritura del texto
        escritura(pedirRuta(), lineas);
    }

    /**
     * Escribe en disco en una sola lina una lista de string separandolos
     * elementos con un "*"
     *
     * @param ruta Ruta para almacenar el archivo
     * @param lineas Lista de strings a escribir en el archivo
     */
    private void escritura(String ruta, List<String> lineas) {

        //1 preparacion de variables
        File archivo = new File(ruta);
        FileWriter fw = null;
        BufferedWriter bw = null;
        long totalPalabras = 0;

        try {
            //2 escritura del archivo y conteo de palabras
            fw = new FileWriter(archivo);
            bw = new BufferedWriter(fw);
            for (String linea : lineas) {
                StringTokenizer st = new StringTokenizer(linea, "\t\n\r\f,;:. ");
                totalPalabras += st.countTokens();
                bw.write(linea + "*");
            }

            //3 Impresion en pantalla del resultado
            System.out.println("Archivo guardado a disco: " + archivo.getAbsolutePath());
            System.out.println("Total de palabras: " + totalPalabras);
            System.out.println("Pulse intro para continuar");
            this.s.nextLine();
        } catch (IOException ex) {
            System.out.println("Error escribiendo el archivo " + archivo.getAbsolutePath());
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio6.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Inicio del apartado 2 del ejercicio
     */
    private void apartado2() {
        String ruta = pedirRuta();
        lectura(ruta);

    }

    /**
     * Lee un fichero e interpreta cada asterisco "*" como si de un salto de
     * linea se tratase Lee el archivo caracter a caracter y va detectando y
     * contando las palabras y mostrandolo en pantalla
     *
     * @param ruta La ruta del archivo a cargar
     */
    private void lectura(String ruta) {

        //variables
        File archivo = new File(ruta);
        FileReader fr = null;
        long totalPalabras = 0;
        String separadores = "\t\n\r\f,;:. ";
        try {
            //preparacion de variables
            fr = new FileReader(archivo);
            int i = 0;
            boolean enPalabra = false;

            //lectura del archivo cracter a caracter
            while ((i = fr.read()) > -1) {
                //si encuentra un asterisco lo cambia por un salto de linea
                if (i == '*') {
                    i = '\n';
                }
                String caracter = Character.toString(i);

                //si el caracter actual es un separador comprobamos 
                //si venimos de una palabra
                if (separadores.contains(caracter)) {
                    if (enPalabra) {
                        //como venimos de una palabra
                        //agregamos una palabra mas en el conteo
                        totalPalabras++;
                    }
                    enPalabra = false;
                } else {
                    //si no es un separador nos encontramos en una palabra
                    enPalabra = true;
                }
                System.out.print(caracter);
            }
            //si en este punto se esta en una palabra 
            //es porque al final de la cadena hay una palabra
            //se agrega al conteo
            if (enPalabra) {
                totalPalabras++;
            }
            //imprimir resultado
            System.out.println("Total de palabras: " + totalPalabras);
            pedirEntrada("Pulse intro para continuar");
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida " + ex.getMessage());
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio6.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Apartado 3 del ejercicio. Carga un fichero y lo muestra. 
     * Usa un metodo aparte para contar las palabras del fichero.
     */
    private void apartado3() {
        //preparacion de variables
        String ruta = pedirRuta();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            //lectura del archivo
            archivo = new File(ruta);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String textoTotal = "";
            String linea;
            //impresion y recopilacion del texto para contar
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                textoTotal += linea + "\n";
            }
            //resultado en pantalla
            System.out.println("---------------------------------");
            System.out.println("Total de palabras: " + contarPalabras(textoTotal));
            pedirEntrada("Pulse intro para continuar");

        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida " + ex.getMessage());
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio6.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo para contar palabras
     *
     * @param texto Texto a analizar
     * @return Cantidad de palabras en el texto
     */
    private long contarPalabras(String texto) {
        StringTokenizer st = new StringTokenizer(texto, "\t\n\r\f,;:. ");
        return st.countTokens();
    }

    
    /**
     * Punto de entrada del apartado 4 del ejercicio
     * Pide las dos rutas y ejecuta el analisis de cada ruta
     */
    private void apartado4() {
        //peticion de rutas
        String ruta1 = pedirRuta();
        String ruta2 = pedirRuta();
        
        //analisis
        analizar(ruta1);
        analizar(ruta2);
        pedirEntrada("Pulse intro para continuar");
    }

    /**
     * Analiza un archivo y muestra en pantalla su nombre, cantidad de KB y nº de palabras
     * @param ruta Ruta del archivo a analizar
     */
    private void analizar(String ruta) {
        File arch = new File(ruta);
        //comprobacion de que exista y sea un archivo
        if (!arch.exists() || !arch.isFile()) {
            System.out.println("El archivo de texto " + arch.getAbsolutePath() + " no existe");
            return;
        }
        
        //preparacion de variables
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //lectura del archivo 
            fr = new FileReader(arch);
            br = new BufferedReader(fr);

            String textoTotal = "";
            String linea;
            while ((linea = br.readLine()) != null) {
                textoTotal += linea + "\n";
            }
            
            //conteo de sus palabras
            long nPalabras = contarPalabras(textoTotal);

            //recogida de nombre y tamano
            String nombre = arch.getName();
            long tamano = arch.length();
            
            //imprimir en pantalla el resultado
            System.out.println(nombre + " " + tamano + " KB - " + nPalabras+" palabras");
            
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida " + ex.getMessage());
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio6.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //UTILES DE ENTRADA DE DATOS Y MENU
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
            System.out.println("1)Escribir un texto en un archivo");
            System.out.println("2)Leer archivo escrito con el metodo del apartado 1");
            System.out.println("3)Contar palabras de un fichero");
            System.out.println("4)Comparar ficheros");
            System.out.println("5)Salir");
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
     * Pide la ruta de un archivo de texto
     *
     * @return la ruta introducida
     */
    public String pedirRuta() {
        return pedirEntrada("Introduzca la ruta de un archivo de texto");
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
     * MAIN DEL PROGRAMA
     *
     * @param args
     */
    public static void main(String[] args) {
        Ejercicio6 ejercicio6 = new Ejercicio6();
    }

}//end Ejercicio6
