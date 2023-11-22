/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio03;

import java.io.File;

/**
 * En el ejercicio anterior, ten�amos que mostrar la lista de ficheros del directorio que se pasa como
 * par�metro desde la l�nea de comandos, pero no realizamos ning�n tipo de filtro.
 * Queremos filtrar de la lista, s�lo aquellos ficheros que sean del tipo .xml.
 * Para ello, vamos a crear y aplicar nosotros un filtro, haciendo uso de la interfaz FilenameFilter vamos a mostrar
 * solo aquellos ficheros que cumplan el requisito.
 * Hemos de crearnos una clase Filtro que implemente la interfaz FilenameFilter y
 * modificaremos su método accept para indicar el filtro que deben cumplir el nombre de los ficheros.
 * Posteriormente en nuestra aplicaci�n principal le pasaremos un objeto de la clase Filtro al
 * método list() de nuestro objeto File para que solo muestre los ficheros que cumplan el filtro indicado.
 * 
 * @author Jose Javier Bailón Ortiz
 */
public class Ejercicio3 {
    
    public static void main(String[] args) {
            //check args
            if (args==null||args.length<1){
                System.out.println("Agrege la ruta del directorio a explorar en la linea de comandos al ejecutar el programa");
                System.exit(0);
            }
            //crea el File a partir del parametro pasado en la ejecucion de la linea de comandos
            File directorio = new File(args[0]);
            //el filtro
            FiltroXML filtroXml=new FiltroXML();
            //crea la lista de archivos 
            String[] listaArchivos = directorio.list(filtroXml);
            System.out.println("Ficheros XML en el directorio: "+directorio.getAbsolutePath());
            //muestra la lista en pantalla
            for (String archivo : listaArchivos)
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
}//end Ejercicio3
