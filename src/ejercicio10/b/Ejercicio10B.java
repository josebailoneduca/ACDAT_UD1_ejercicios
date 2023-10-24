/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio10.b;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jose Javier BO
 */
public class Ejercicio10B {

   
    
    
    /**
     * Estructura para guardar los datos de una entrada
     */
    class Entrada {

        //usuario de la entrada
        String usuario;
        //primos de la entrada
        ArrayList<Integer> numeros;

        public Entrada(String usuario, ArrayList<Integer> numeros) {
            this.usuario = usuario;
            this.numeros = numeros;
        }
        public Entrada(){
            this.usuario="";
            this.numeros=new ArrayList<Integer>();
        }
    }

    //ATRIBUTOS
    /**
     * Scanner de consola
     */
    Scanner s = new Scanner(System.in);

    /**
     * Ruta del archivo binario
     */
    String ruta = "./src/ejercicio10/recursos/PrimosB.dat";

    /**
     * Almacerna el ultimo numero primo que hay en el archivo
     */
    int ultimoNumeroPrimo = 0;

    /**
     * Usuario actual
     */
    String nombreUsuario = "";

    
    
   
    /**
     * Constructor
     */
    public Ejercicio10B() {
        inicializarArchivo();
        selectorMenu();
    }
    
    
    /**
     * Muestra el menu apropiado segun se haya llegado al limite de los primos por debajo de 100 o no
     */
    private void selectorMenu(){
        while(true){
            if (ultimoPrimoEnArchivo()<97)
                menuBajo100();
            else{}
                //menuSobre100()
        }
    }
    
     private void menuBajo100() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    /**
     * Graba una nueva entrada en el archivo conteniendo el usuario y los primos
     * @param entrada Entrada a guardar en el archivo
     */
    private void insertarEntrada(Entrada entrada) {
        File f = new File(ruta);

        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            //preparacion de streams
            fos = new FileOutputStream(f,true);
            dos = new DataOutputStream(fos);

            //escribir el usuario
            dos.writeUTF(entrada.usuario);
            //escribir la cantidad de primos
            dos.writeInt(entrada.numeros.size());
            //escribir los primos
            for (int n : entrada.numeros) {
                dos.writeInt(n);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado "+f.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error escribiendo en archivo "+f.getAbsolutePath());
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                System.out.println("Error cerrando streams");
            }
        }
    }

    /**
     * Lee la siguiente entrada en el DataInputStream suministrado
     * @param dis DataInputStream del que leer una entrada
     * @return La entrada con los datos leidos o NULL  se ha llegado al final del archivo
     */
    private Entrada leerSiguienteEntrada(DataInputStream dis) throws IOException {
        try {
            //leer el usuario
            String usuario = dis.readUTF();
            //leer la cantidad de primos
            int cantidad=dis.readInt();
            ArrayList<Integer> numeros=new ArrayList<Integer>();
            //leer los numeros primos
            for (int i=0;i<cantidad;i++){
                numeros.add(dis.readInt());
            }
        return new Entrada(usuario,numeros);
        }catch (EOFException eof){
            //Si se ha llegado al final del archivo leemos null
            return null;
        } 
    }



    /**
     * Devuelve el ultimo primo del archivo leyendo el archivo y devolviendo el
     * ultimo numero
     * @return  El ultimo primo
     */
    private int ultimoPrimoEnArchivo() {
        File f = new File(ruta);

        FileInputStream fis = null;
        DataInputStream dis = null;
        int ultimoPrimo=0;
        try {
            //creacion de streams
            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);
            
            //lectura
            Entrada e = new Entrada();
            while ((e = leerSiguienteEntrada(dis)) !=null) {
                ultimoPrimo=e.numeros.get(e.numeros.size()-1);       
            }
        }catch (FileNotFoundException ex) {
            System.out.println("No se puede acceder al archivo " + f.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error leyendo en " + f.getAbsolutePath());
        } finally {
            //cierre de outputstreams
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                System.out.println("Error cerrando streams");
            }
        }
        return ultimoPrimo;
    }



    //UTILES DE CONSOLA
    /**
     * Salta 100 lineas en la consola simulando un salto de pagina
     */
    private void pasarPagina() {
        for (int i = 0; i < 100; i++) {
            System.out.println("");
        }
    }

    /**
     * Muestra un mensaje y pide pulsar intro
     *
     * @param msg El mensaje a mostrar
     * @return Lo introducido por el usuario
     */
    private String pedirIntro(String msg) {
        System.out.println("******************************************************");
        System.out.println(msg);
        System.out.println("Pulsa intro para continuar");
        System.out.println("******************************************************");
        return this.s.nextLine();
    }

    /**
     * INICIO DEL PROGRAMA
     *
     * @param args
     */
    public static void main(String[] args) {
        new Ejercicio10B();
    }

    private void inicializarArchivo() {
        File f = new File(ruta);
        if (!f.exists())
            try {
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println("");
        } else {
        }

    }
}
