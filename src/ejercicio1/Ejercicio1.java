/*
 * LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 * Lista de paquetes:
 */

package ejercicio1;

import java.io.File;

/**
 * Clase que implementa el ejercicio UD1-1:
 * Realiza un programa en Java que muestre los ficheros de un directorio, pero hazlo teniendo en cuenta que el
 * nombre del directorio se pasará al programa desde la línea de comandos al ejecutarlo.
 * 
 * @author Jose Javier Bailón Ortiz
 */
public class Ejercicio1 {

    
    public static void main(String[] args) {
            //crea el File a partir del parametro pasado en la ejecucion de la linea de comandos
            File directorio = new File(args[0]);
            //crea la lista de archivos 
            String[] lista = directorio.list();
            //muestra la lista en pantalla
            for (String elemento : lista)
                System.out.println(elemento);
    }
}//end Ejercicio1
