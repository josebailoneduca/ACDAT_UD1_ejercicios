/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio03;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Clase que filtra si la extensión del archivo es XML
 * @author Jose Javier Bailón Ortiz
 */
class FiltroXML implements FilenameFilter{

    /**
     * Acepta el archivo si es de extension xml
     * @param dir Directorio del archivo a filtrar
     * @param name nombre del archivo a filtrar
     * @return  True si el nombre termina como .xml, False si el nombre no termina como .xml
     */
    @Override
    public boolean accept(File dir, String name) {
        //Pasar a lowercase
        String nombreLC=name.toLowerCase();
        //devolver la comprobacion de la terminacion en xml
        return nombreLC.endsWith(".xml");
    }
}//end FiltroXML
