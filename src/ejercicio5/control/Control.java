/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio5.control;

import ejercicio5.vista.Vista;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class Control {

    Vista vista;

    public Control() {
        //inicializacion de la vista
        this.vista = new Vista();

        //Mostrarmenu mientras se elija opcion valida
        while (true) {
            int opcionElegida = vista.pedirOpcionMenu();
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
     * Metodo que visualiza el contenido de un fichero de texto cuyo nombre se
     * pasa como argumento linea a linea.
     */
    private void verLineaPorLinea() {
        String ruta = vista.pedirRuta();
        if (ruta == null || ruta.length() == 0) {
            vista.pedirIntro("La ruta esta vacía");
            return;
        }

        //2- GESTIÓN DEL FICHERO
        File fichero = new File(ruta);
        vista.pedirIntro("Abriendo archivo " + fichero.getAbsolutePath());
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //3-Lectura
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);
            Vista.pasarPagina();
            //4-LEER texto caracter a caracter
            String linea;
            while ((linea = br.readLine()) != null) {
                vista.imprimirConSalto(linea);
            }
            vista.imprimirConSalto("");
        } catch (FileNotFoundException fnf) {
            vista.pedirIntro("Archivo no encontrado");
            return;
        } catch (IOException io) {
            vista.pedirIntro("Error accediendo al archivo: " + io.getMessage());
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
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        vista.pedirIntro("----------FIN DE ARCHIVO------------");
    }

    /**
     * Metodo que visualiza el contenido de un fichero de texto cuyo nombre se
     * pasa como argumento caracter a caracter.
     */
    private void verCaracterPorCaracter() {
        String ruta = vista.pedirRuta();
        if (ruta == null || ruta.length() == 0) {
            vista.pedirIntro("La ruta esta vacía");
            return;
        }

        //2- GESTIÓN DEL FICHERO
        File fichero = new File(ruta);
        vista.pedirIntro("Abriendo archivo " + fichero.getAbsolutePath());
        FileReader fr = null;
        try {
            //3-Lectura
            fr = new FileReader(fichero);
            Vista.pasarPagina();
            //4-LEER texto caracter a caracter
            int i;
            while ((i = fr.read()) != -1) {
                vista.imprimirSinSalto("" + ((char) i));
            }
            vista.imprimirConSalto("");
        } catch (FileNotFoundException fnf) {
            vista.pedirIntro("Archivo no encontrado");
            return;
        } catch (IOException io) {
            vista.pedirIntro("Error accediendo al archivo: " + io.getMessage());
            return;
        } finally {
            //5-CERRAR STREAMS
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        vista.pedirIntro("----------FIN DE ARCHIVO------------");
    }

    private void guardarTxt() {

        //archivos de lectura y escritura
        File archivoLectura = new File("texto.txt");
        File archivoEscritura = new File("texto_20.txt");

        Vista.pasarPagina();
        vista.pedirIntro(
                "Se va a intentar cargar el archivo " + archivoLectura.getAbsolutePath()
                + "\nSeguidamente se guardara en el mismo directorio el archivo texto_20.txt"
                + "\nEn ese nuevo archivo estara el contenido formateado de manera que haya 20 caracteres por linea"
                + "\ny creara nueva linea tambien cuando aparezca un \".\""
                + "\nSi el fichero existe sera sobreescrito.");

        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fr = new FileReader(archivoLectura);
            br = new BufferedReader(fr);
            String texto = "";
            String lineaLectura = "";
            while ((lineaLectura = br.readLine()) != null) {
                texto += lineaLectura + " ";
            }
            //generar las lineas
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
            fw = new FileWriter(archivoEscritura);
            bw = new BufferedWriter(fw);

            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
            
            vista.pedirIntro("Fichero creado: "+archivoEscritura.getAbsolutePath());
        } catch (FileNotFoundException fnf) {
            vista.pedirIntro("Archivo no encontrado: "+fnf.getMessage());
        } catch (IOException ioe) {
            vista.pedirIntro("Error de E/S:"+ioe.getMessage());
        } finally {
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
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}//end Control
