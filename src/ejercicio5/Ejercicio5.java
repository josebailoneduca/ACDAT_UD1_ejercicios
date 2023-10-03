/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio5;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ejercicio 5
 * Crea un programa que gestione y pru1ebe los siguientes métodos desde un menú:
 *  a) Un método que visualice el contenido de un fichero de texto cuyo nombre3 se pasa como argumento. 
 *  Hacerlo carácter a carácter.
 * 
 *  b) Un método que visualice el contenido de un fichero de texto cuyo nombre se pasa com argumento.
 *     Hacerlo línea a línea utilizando la clase BufferedReader y su método readLine().
 * 
 *  c) Un método que guarde en un fichero de texto, un texto introducido desde una cadena de texto.
 *     Esta cadena de texto será separada en líneas de 20 caracteres o cuando se encuentre un "." en el texto.
 *     Hacerlo utilizando la clase BuffreredWriter y su método newLine() o el método Println de PrintWriter.
 *     El texto a añadir será obtenido de: https://www.loremipzum.com/en/text-generator
 *   
 *  Nota:todo el programa debe ser seguro, e decir, realizar todas las comprobaciones necesarias sobre excepciones.
 * @author Jose Javier Bailón Ortiz
 */
public class Ejercicio5 {
 
    Scanner s = new Scanner(System.in);
    
        public Ejercicio5() {


        //Mostrarmenu mientras se elija opcion valida
        while (true) {
            int opcionElegida = this.pedirOpcion();
            switch (opcionElegida) {
                case 1:
                    verCaracterPorCaracter();
                    break;
                case 2:
                    verLineaPorLinea();
                    break;
                case 3:
                    guardarTxt();
                    break;
                default:
                    //salir del programa
                    System.exit(0);
            }
        }
    }

    


    /**
     * Metodo que visualiza caracter a caracter el contenido de un fichero de texto cuyo nombre se
     * pasa como argumento.
     */
    private void verCaracterPorCaracter() {
        String ruta = this.pedirRuta();
        if (ruta == null || ruta.length() == 0) {
            this.pedirIntro("La ruta esta vacía");
            return;
        }

        //2- GESTIÓN DEL FICHERO
        File fichero = new File(ruta);
        this.pedirIntro("Se va a abrir el archivo " + fichero.getAbsolutePath());
        FileReader fr = null;
        try {
            //3-Lectura
            fr = new FileReader(fichero);
            this.pasarPagina();
            //4-LEER texto caracter a caracter
            int i;
            while ((i = fr.read()) != -1) {
                System.out.print("" + ((char) i));
            }
            System.out.println("");
        } catch (FileNotFoundException fnf) {
            this.pedirIntro("Archivo no encontrado");
            return;
        } catch (IOException io) {
            this.pedirIntro("Error accediendo al archivo: " + io.getMessage());
            return;
        } finally {
            //5-CERRAR STREAMS
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio5.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.pedirIntro("----------FIN DE ARCHIVO------------");
    }

    
    
        /**
     * Metodo que visualiza linea a linea, el contenido de un fichero de texto cuyo nombre se
     * pasa como argumento.
     */
    private void verLineaPorLinea() {
        //1-PEDIR LA RUTA A CARGAR
        String ruta = this.pedirRuta();
        if (ruta == null || ruta.length() == 0) {
            this.pedirIntro("La ruta esta vacía");
            return;
        }

        //2- GESTIÓN DEL FICHERO
        File fichero = new File(ruta);
        //avisar de la apertura del archivo
        this.pedirIntro("Se va a abrir el archivo archivo " + fichero.getAbsolutePath());
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //3-Lectura
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);
            //limpiar pantalla antes de mostrar el texto
            this.pasarPagina();
            //4-LEER texto linea a linea
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("");
        } catch (FileNotFoundException fnf) {
            this.pedirIntro("Archivo no encontrado");
            return;
        } catch (IOException io) {
            this.pedirIntro("Error accediendo al archivo: " + io.getMessage());
            return;
        } finally {
            //5-CERRAR STREAMS
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio5.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.pedirIntro("----------FIN DE ARCHIVO------------");
    }
    
    
    /**
     * Lee el arvhico texto.txt y lo guarda en disco como texto_20.txt formateandlo
     * a un limite de 20 caracteres por linea y saltando linea tambien
     * cuando encuentra un "." en el texto
     */
    private void guardarTxt() {

        //archivos de lectura y escritura
        File archivoLectura = new File("texto.txt");
        File archivoEscritura = new File("texto_20.txt");

        this.pasarPagina();
        this.pedirIntro(
                "Se va a intentar cargar el archivo " + archivoLectura.getAbsolutePath()
                + "\nSeguidamente se guardara en el mismo directorio el archivo texto_20.txt"
                + "\nEn ese nuevo archivo estara el contenido formateado de manera que haya 20 caracteres por linea"
                + "\ny creara nueva linea tambien cuando aparezca un \".\""
                + "\nSi el fichero existe sera sobreescrito.");

        //1- preparacion de los readers y writters
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            //2- lectura del archivo
            fr = new FileReader(archivoLectura);
            br = new BufferedReader(fr);
            String texto = "";
            String lineaLectura = "";
            while ((lineaLectura = br.readLine()) != null) {
                texto += lineaLectura + " ";
            }
            //3- generar las nuevas lineas segun el formato de 20 max y "."
            ArrayList<String> lineas = new ArrayList<String>();

            int contador = 0;
            String lineaTemp = "";
            for (int i = 0; i < texto.length(); i++) {
                lineaTemp += texto.charAt(i);
                contador++;
                if (texto.charAt(i) == '.' || contador == 20) {
                    lineas.add(lineaTemp);
                    contador = 0;
                    lineaTemp = "";
                }
            }
            if (lineaTemp.length() > 0) {
                lineas.add(lineaTemp);
            }
            //4- Escritura del archivo
            fw = new FileWriter(archivoEscritura);
            bw = new BufferedWriter(fw);

            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
            //5- aviso de finalizacion
            this.pedirIntro("Fichero creado: "+archivoEscritura.getAbsolutePath());
        } catch (FileNotFoundException fnf) {
            this.pedirIntro("Archivo no encontrado: "+fnf.getMessage());
        } catch (IOException ioe) {
            this.pedirIntro("Error de E/S:"+ioe.getMessage());
        } finally {
            //6- Cierre de streams
            try {
                if (br != null) 
                    br.close();
                if (fr != null) 
                    fr.close();
                if (bw != null) 
                    bw.close();
                if (fw != null) 
                    fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio5.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }




    //UTILES DE ENTRADA DE DATOS Y MENU
    
     /**
     * Pide una opcion filtrando que sea valida
     *
     * @return la opcion elegida
     */
    public int pedirOpcion() {
        int opcion = -1;
        while (opcion < 1 || opcion > 4) {
               this.pasarPagina();
                System.out.println("**************************************************");
                System.out.println("*                    OPCIONES                    *");
                System.out.println("**************************************************");
                System.out.println("1)Ver contenido de archivo de texto caracter a caracter");
                System.out.println("2)Ver contenido de archivo de texto linea a linea");
                System.out.println("3)Guardar txt limitado a 20 caracteres por linea");
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
     * Pide la pulsacion de la tecla intro con un mensaje previo
     * @param msg Mensaje extra a agregar
     */
    public void pedirIntro(String msg) {
        System.out.println(msg);
        pedirIntro();
    }


    /**
     * Pide la pulsacion de la tecla intro sin mensaje previo
     * @return 
     */
    public String pedirIntro() {
        return pedirEntrada("Pulsa intro para continuar");
    }

    /**
     * Salta 100 lineas en la consola simulando un salto de pagina
     */
    public void pasarPagina() {
        for (int i = 0; i < 100; i++) {
            System.out.println("");
        }
    }
// FIN UTILES DE ENTRADA DE DATOS Y MENU
   



 
    
    
    public static void main(String[] args) {
       new Ejercicio5();
    }
}//end Ejercicio5
