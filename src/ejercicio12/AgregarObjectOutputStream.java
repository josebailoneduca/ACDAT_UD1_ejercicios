/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio12;

import ejercicio11.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Extension de ObjectOutputStream que anula la escritura de la cabecera
 * @author Jose Javier Bailon Ortiz
 */
public class AgregarObjectOutputStream extends ObjectOutputStream {

     //Constructores
    public AgregarObjectOutputStream(OutputStream out) throws IOException{ super(out); }
    protected AgregarObjectOutputStream() throws IOException, SecurityException{  super();  }

    //evitar que escriba cabecera
     protected void writeStreamHeader() throws IOException{ }
}//end AgregarObjectOutputStream
