/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio5.vista;

import java.util.Scanner;

/**
 * Se encarga de mostra el menu principal y gestionar la recogida de la respuesta
 * @author Jose Javier Bailón Ortiz
 */
public class Menu {

    /**
     * Pide una opcion filtrando que sea valida
     *
     * @return la opcion elegida
     */
    public int pedirOpcion() {
        int opcion = -1;
        while (opcion < 1 || opcion > 4) {
            Scanner s = Vista.getScanner();
            mostrar();
            System.out.println("Elije una opcion:");
        
               try {
                String respuesta = s.nextLine();
                opcion = Integer.parseInt(respuesta);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return opcion;
    }

    /**
     * Muestra el menu en pantalla.
     */
    private void mostrar() {
        Vista.pasarPagina();
        System.out.println("**************************************************");
        System.out.println("*                    OPCIONES                    *");
        System.out.println("**************************************************");
        System.out.println("1)Ver contenido de archivo de texto caracter a caracter");
        System.out.println("2)Ver contenido de archivo de texto linea a linea");
        System.out.println("3)Guardar txt limitado a 20 caracteres por linea");
        System.out.println("4)Salir");
    }
 

}//end Menu
