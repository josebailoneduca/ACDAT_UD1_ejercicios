/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio5.control;

import ejercicio5.vista.Vista;
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
        this.vista=new Vista();
      
        //Mostrarmenu mientras se elija opcion valida
        while(true){
            int opcionElegida = vista.pedirOpcionMenu();
            switch (opcionElegida) {
                case 1:
                    verCaracterPorCaracter();
                    break;
                default:
                    //salir del programa
                    System.exit(0);
            }
        }
    }

    /**
     *  Metodo que visualiza el contenido de un fichero de texto cuyo nombre3 se pasa como argumento caracter a caracter.
     */
    private void verCaracterPorCaracter() {
            vista.pasarPagina();
            String ruta =  vista.pedirRuta();

             System.out.println("ruta: "+ruta);
     
         
         vista.pedirIntro();
    }


}//end Control
