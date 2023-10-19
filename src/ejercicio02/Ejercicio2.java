/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio02;
import java.io.File;
/**
* Ejercicio 2
* Mostrar la lista de ficheros del directorio que se pasa como parámetro desde la linea de comandos.
* Para cada fichero o directorio indicar al menos: su nombre, tamaño, ruta absoluta, si es un fichero o un
* directorio, …. El resultado debe ser similar a:
* ficheros en el direcotirio: .
* -->build
*       *Nombre:build
*       *Tamanio: 0
*       *RutaAbs.:C:\Users....
*       *Es un directorio
* 
* @author Jose Javier Bailón Ortiz
*/
public class Ejercicio2 {
    
    public static void main(String[] args) {
            //crea el File a partir del parametro pasado en la ejecucion de la linea de comandos
            File directorio = new File(args[0]);
            //crea la lista de archivos 
            String[] lista = directorio.list();
            System.out.println("Ficheros en el directorio: "+directorio.getAbsolutePath());
            //muestra la lista en pantalla
            for (String archivo : lista)
                verArchivo(new File(archivo));
    }
    
    /**
     * Muestra la informacion de un archivo
     * @param archivo El archivo a mostrar
     */
    public static void verArchivo(File archivo){
        System.out.println("-->"+archivo.getName());
        System.out.println("     Nombre: "+archivo.getName());
        System.out.println("     Tamanio: "+archivo.length()+" KB");
        System.out.println("     Ruta absoluta: "+archivo.getAbsolutePath());
        System.out.println("          Es un "+((archivo.isDirectory())?"directorio":"archivo"));
    }
}//end Ejercicio