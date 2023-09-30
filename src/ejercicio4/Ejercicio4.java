/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio4;

import java.io.File;
import java.util.Scanner;

/**
 * Crea un programa que recoja todos los ficheros de un directorio raíz concreto y muestre un listado de SÓLO
 * sus directorios, y para cada uno de esos directorios, se mostrará su contenido.
 *    • El directorio raíz.
 *    • La ruta del directorio raíz será la que indique el usuario, es decir, se pedirá la ruta de un directorio
 *      concreto.
 *    • Se debe averiguar el S.O. en el que se está ejecutando la aplicación, y según sea Linux o Windows
 *      gestionar correctamente la ruta hacia el directorio.
 *  A partir de esa ruta, se debe mostrar:
 *    • La ruta absoluta obtenida.
 *    • Para cada fichero que sea un directorio, mostrar el nombre del directorio y su contenido, es decir cada
 *      uno de sus ficheros, indicando si es un directorio o un fichero junto a su nombre.
 * 
 * @author Jose Javier Bailón Ortiz
 */
public class Ejercicio4 {
    
    public static void main(String[] args) {
            //Deteccion del sistema operativo y preparacion del separador a usar
            String sistemaOperativo=System.getProperty("os.name");
            boolean esWindows=sistemaOperativo.contains("Windows");
            
            
            //recogida de la ruta raiz
            Scanner s = new Scanner(System.in);
            System.out.println("Introduzca el directorio raiz (intro para usar el actual):");
            String rutaRaiz = s.nextLine();
            //ruta por defecto a directorio actual
            if (rutaRaiz.length()<1) rutaRaiz="."+((esWindows)?"\\":"/");
            //limpiar ruta introducida por el usuario
            rutaRaiz = limpiarRuta(rutaRaiz,esWindows);
            
            //crea el File raiz a partir del parametro pasado en la ejecucion de la linea de comandos
            File raiz = new File(rutaRaiz);
            System.out.println("DIRECTORIO RAIZ: "+raiz.getPath());
            System.out.println("->Ruta Absoluta: "+raiz.getAbsolutePath());
            System.out.println("->Sistema Operativo: "+sistemaOperativo);
            //listar los directorios que contiene raiz
            listarDirectorios(raiz);

    }
 
    private static void listarDirectorios(File raiz) {
        //si es nulo o no es un directorio avisamo
        if (raiz==null || !raiz.isDirectory()){
            System.out.println("La ruta no es un directorio");
            return;
        }
        File[]archivos = raiz.listFiles();
        //si el archivo existe listamos su contenidos
        if (archivos!=null){
            for (File archivo : archivos) {
                    listarContenido(archivo);
            }
        }
        else{
            //si no existe avisa
            System.out.println("La ruta no existe");}
    }

    /**
     * Lista el contenido de un archivo si es un directorio
     * @param directorio 
     */
    private static void listarContenido(File directorio) {
        if (!directorio.isDirectory())
            return;
        System.out.println("Contenido de la carpeta: "+directorio.getName());
        File[] archivos = directorio.listFiles();
        if (archivos.length==0)
            System.out.println("        --Directorio vacio");
                    
        for (File archivo : archivos) {

            if (archivo.isDirectory()){
                System.out.println("        --Directorio:"+archivo.getName());
            }else{
                System.out.println("        --Fichero:"+archivo.getName());
            }
        }
    }

    private static String limpiarRuta(String rutaRaiz, boolean esWindows) {
        if (esWindows)
             rutaRaiz = rutaRaiz.replace("/", "\\");
        else
            rutaRaiz = rutaRaiz.replace("\\", "/");
                    
        return rutaRaiz;
    }
}//end Ejercicio4
