/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio5.control;

import ejercicio5.vista.Vista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
                    verLineaPorLinea();
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
        vista.pedirIntro("Abriendo archivo "+fichero.getAbsolutePath());
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //3-Lectura
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);
            Vista.pasarPagina();
            //4-LEER texto caracter a caracter
            String linea;
            while (( linea = br.readLine()) != null) {
                vista.imprimirConSalto(linea);
            }
            vista.imprimirConSalto("");
        } catch (FileNotFoundException fnf) {
            vista.pedirIntro("Archivo no encontrado");
            return;
        } catch(IOException io){
            vista.pedirIntro("Error accediendo al archivo: "+io.getMessage());
            return;
        }
        finally {
            //5-CERRAR STREAMS
                try {
                    if (fr!=null)
                        fr.close();
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
        vista.pedirIntro("Abriendo archivo "+fichero.getAbsolutePath());
        FileReader fr = null;
        try {
            //3-Lectura
            fr = new FileReader(fichero);
            Vista.pasarPagina();
            //4-LEER texto caracter a caracter
            int i;
            while (( i = fr.read()) != -1) {
                vista.imprimirSinSalto(""+((char) i));
            }
            vista.imprimirConSalto("");
        } catch (FileNotFoundException fnf) {
            vista.pedirIntro("Archivo no encontrado");
            return;
        } catch(IOException io){
            vista.pedirIntro("Error accediendo al archivo: "+io.getMessage());
            return;
        }
        finally {
            //5-CERRAR STREAMS
                try {
                    if (fr!=null)
                        fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        vista.pedirIntro("----------FIN DE ARCHIVO------------");
    }

}//end Control
