/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio5.vista;

import java.util.Scanner;

/**
 *
 * @author Jose Javier Bailón Ortiz
 */
public class Vista {

    //ATRIBUTOS
    /**
     *  El menu de la aplicacion
     */
    Menu menu;

    /**
     * El scanner de la vista
     */
    public static Scanner scanner;

    
    /**
     * Constructor
     */
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
     * Salta 100 lineas en la consola simulando un salto de pagina
     */
    public static void pasarPagina() {
        for (int i = 0; i < 100; i++) {
            System.out.println("");
        }
    }

    /**
     * Pide al usuario la seleccion de una opcion del menu
     *
     * @return La opcion seleccionada
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
     * Muestra un mensaje y pide una entrada
     *
     * @param msg El mensaje a mostrar
     * @return Lo introducido por el usuario
     */
    public String pedirEntrada(String msg) {
        System.out.println("******************************************************");
        System.out.println(msg + ":");
        System.out.println("******************************************************");
        return Vista.getScanner().nextLine();
    }

    /**
     * Imprime en pantalla un texto sin usar nueva linea
     * @param texto El texto a imprimir
     */
    public void imprimirSinSalto(String texto){
        System.out.print(texto);
    }
    
    /**
     * Imprime un texto en pantlla usando nueva linea
     * @param msg 
     */
    public void imprimirConSalto(String msg){
        System.out.println(msg);
    }
    
}//end Vista
