/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio10.a;

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
 * @author Jose Javier Bailon Ortiz
 */
public class Ejercicio10A {

    //ATRIBUTOS
    
    /**
     * Scanner de consola
     */
    Scanner s = new Scanner(System.in);
    
    /**
     * Ruta del archivo binario
     */
    String ruta = "./src/ejercicio10/recursos/PrimosA.dat";

    /**
     * Constructor
     */
    public Ejercicio10A() {
        //pedir el limite al que se quiere llegar
        while (true) {
            System.out.println("Introduzca el limite hasta el que quiere calcular los primos (max 100)");
            try {
                int limite = Integer.parseInt(s.nextLine());
                //CUANDO EL NUMERO INTRODUCIDO SEA VALIDO SE PROCESA
                if (limite > 1 && limite <= 100) {
                    pasarPagina();
                    System.out.println("Primos existentes del 1 al "+limite+"\n-------------------------------------");
                    //Procesar el numero 
                    procesar(limite);
                    System.exit(0);
                
                } else {
                    pedirIntro("El numero debe estar entre 2 y el 100 ambos incluidos");
                    pasarPagina();
                }
            } catch (NumberFormatException nfe) {
                pedirIntro("Numero no valido");
                pasarPagina();
            }
        }
    }

    /**
     * Calcula los numeros primos menores o iguales que el numero limite.
     * Tras calcularlos los guarda en un archivo binario y despues lee el archivo
     * y lo muestra en pantalla
     * 
     * @param limite Numero limite hasta el que calcular los primos
     */
    private void procesar(int limite) {
 
        //1 CALCULAR LISTA DE PRIMOS
        ArrayList<Integer> listaPrimos = new ArrayList<Integer>();
        for (int i = 2; i <= limite; i++) {
            if (esPrimo(i)) {
                listaPrimos.add(i);
            }
        }

        //2 ESCRIBIR LISTA DE NUMEROS PRIMOS
        //borra si exste el archivo ./src/ejercicio10/recursos/PrimosA.dat
        File f = new File(ruta);
        if (f.exists()) {
            f.delete();
        }

        //crear el archivo
        try {
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println("No se puede crear el archivo " + f.getAbsolutePath());
            System.exit(0);
        }

        //ESCRIBIR
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            //creacion de streams
            fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);

            //escritura
            for (int primo : listaPrimos) {
                dos.writeInt(primo);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("No se puede acceder al archivo " + f.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error escribiendo en " + f.getAbsolutePath());
        } finally {
            //cierre de outputstreams
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

        //LEER
        FileInputStream fis = null;
        DataInputStream dis = null;

        try {
            //creacion de streams
            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);

            //lectura
            while (true) {
                System.out.println(dis.readInt());
            }

        } catch (EOFException eof) {
            //deteccion del final del archivo
        } catch (FileNotFoundException ex) {
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
    }

    /**
     * Devuelve si un numero es primo o no mirando si es divisible por algun
     * numero menor o igual que su raiz cuadrada
     *
     * @param num El numero a comprobar
     * @return True si es primo, False si no es primo
     */
    private boolean esPrimo(int num) {
        int divisor = 2;
        double raiz2 = Math.sqrt(num);
        while (num != 1 && divisor <= raiz2) {
            if (num % divisor == 0) {
                return false;
            } else {
                divisor++;
            }
        }
        return true;
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
     * @param args 
     */
    public static void main(String[] args) {
        new Ejercicio10A();
    }
    
}//end Ejercicio10A
