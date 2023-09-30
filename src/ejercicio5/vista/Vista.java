/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio5.vista;

import ejercicio5.control.Control;
import java.util.Scanner;

/**
 *
 * @author Jose Javier Bailón Ortiz
 */
public class Vista {

    /*
     *  Atributos 
     */
    //El menu de la aplicacion
    Menu menu;

    //El scanner de la vista
    public static Scanner scanner;

    public Vista() {
        this.menu = new Menu();
    }

    /**
     * Devuelve Scanner singleton
     *
     * @return el Scanner
     */
    public static Scanner getScanner() {
        if (Vista.scanner == null) {
            Vista.scanner = new Scanner(System.in);
        }
        return Vista.scanner;
    }

    /**
     * Salta 100 lineas en la consola
     */
    public static void pasarPagina() {
        for (int i = 0; i < 100; i++) {
            System.out.println("");
        }
    }

    /**
     * Pide una opcion del menu
     *
     * @return La opcion elegida
     */
    public int pedirOpcionMenu() {
        return menu.pedirOpcion();
    }

    /**
     * Pide la ruta de un archivo de texto
     *
     * @return la ruta introducida
     */
    public String pedirRuta() {
        return pedirEntrada("Introduzca la ruta de un archivo de texto");
    }
    public String pedirIntro() {
        return pedirEntrada("Pulsa intro para continuar");
    }

    /**
     * Pasa pagina, muestra un mensaje y pide una entrada
     *
     * @param msg El mensaje a mostrar
     * @return Lo introducido por el usuario
     */
    public String pedirEntrada(String msg) {
        System.out.println(msg + ":");
        return Vista.getScanner().nextLine();
    }

}//end Vista
