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

    private void pedirNumerosPrimos() {
        if (ultimoPrimoEnArchivo() < 97) 
            pedirPorRango();
        else 
            pedirHasta3();
        
    }

    private void pedirPorRango() {
        pasarPagina();
        System.out.println("");
        boolean pedido = false;
        int limiteSuperior = 0;
        while (!pedido) {
            System.out.println("Introduzca el limite al que llegar con los numeros primos (limite inferior actual " + (ultimoPrimoEnArchivo() + 1)+")" );
            String respuesta=s.nextLine();
            try {
                limiteSuperior = Integer.parseInt(respuesta);
                if (limiteSuperior > ultimoPrimoEnArchivo()) {
                    pedido = true;
                }else{
                    System.out.println("Debe ser más alto");
                }
            } catch (NumberFormatException ex) {
                System.out.println(respuesta+" no es un numero");
            }
        }
        
        ArrayList<Integer> numeros=calculaPrimosDeRango(limiteSuperior);
        if (numeros.size()==0)
            System.out.println("No hay numeros primos de "+ultimoPrimoEnArchivo()+" a "+limiteSuperior);
        else
            guardarEntrada(new Entrada(nombreUsuario,numeros));
       
    }
    
    
    
    private void pedirHasta3() {
        pasarPagina();
        System.out.println("");
        boolean pedido = false;
        int cantidad = 0;
        while (!pedido) {
            System.out.println("Introduzca la cantidad de numeros primos que quiere (1,2 o 3):" );
            String respuesta = s.nextLine();
            try {
                cantidad = Integer.parseInt(respuesta);
                if (cantidad > 0 && cantidad < 4) {
                    pedido = true;
                }else{
                    System.out.println("Debes pedir de 1 a 3");
                }
            } catch (NumberFormatException ex) {
                System.out.println(respuesta+" no es un numero");
            }
        }
        
        ArrayList<Integer> numeros=calculaCantidadPrimos(cantidad);
        if (numeros.size()==0)
            pedirIntro("No se han calculado numeros primos");
        else
            guardarEntrada(new Entrada(nombreUsuario,numeros));
    }

    private ArrayList<Integer> calculaPrimosDeRango(int limiteSuperior) {
        ArrayList<Integer> numeros =new ArrayList<Integer>();
        for (int i=(ultimoPrimoEnArchivo()+1);i<=limiteSuperior;i++){
            if (i>100){
                pedirIntro("Se ha llegado al limite de 100. Se han generado "+numeros.size()+" numeros primos. A partir de ahora solo podrá pedir 3 numeros como máximo");
                return numeros;
            }
            else
                if(esPrimo(i))
                    numeros.add(i);
        }
        return numeros;
    }


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

        public Entrada() {
            this.usuario = "";
            this.numeros = new ArrayList<Integer>();
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
     * Muestra el menu apropiado segun se haya llegado al limite de los primos
     * por debajo de 100 o no
     */
    private void menu() {
        while (true) {
            //si no hay usuario pedirlo
            if (nombreUsuario.equals("")) {
                pedirUsuario();

                //si hay usuario mostrar el menu que corresponda
            } else {
                int opcionElegida = this.mostrarMenu();
                switch (opcionElegida) {
                    //agregar nota
                    case 1 ->
                        pedirNumerosPrimos();
                    //leer bloc
                    case 2 ->
                        listarDatosDelArchivo();
                    //leer bloc
                    case 3 ->
                        pedirUsuario();
                    //salir del programa
                    default ->
                        System.exit(0);
                }
            }
            //menuSobre100()
        }
    }

    /**
     * Lista en pantalla los datos almacenados en el archivo
     */
    private void listarDatosDelArchivo() {
        inicializarArchivo();
        File f = new File(ruta);
        FileInputStream fis = null;
        DataInputStream dis = null;

        try {
            //preparar streams
            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);
            //lectura y almacenamiento de los datos
            ArrayList<Entrada> entradas = new ArrayList<Entrada>();
            Entrada entrada = null;
            while ((entrada = leerSiguienteEntrada(dis)) != null) {
                entradas.add(entrada);
            }

            //si no hay entradas se avisa
            if (entradas.size() == 0) {
                pedirIntro("No hay datos almacenados. Pida numeros primos para agregarlos a " + f.getAbsolutePath());
            } else {
                for (Entrada e : entradas) {
                    System.out.println(e.usuario + ":");
                    for (int p : e.numeros) {
                        System.out.println(p);
                    }
                }
                pedirIntro("Fin del archivo");
            }

        } catch (FileNotFoundException ex) {
            pedirIntro("No hay numeros almacenados. Pida numeros primos para agregarlos a " + f.getAbsolutePath());
        } catch (IOException ex) {
            pedirIntro("Error leyendo el archivo " + f.getAbsolutePath());
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                pedirIntro("Error cerrando streams");
            }
        }
    }
    
    
    
    private void inicializarArchivo(){
    File f=new File(ruta);
    if (!f.exists())
        try {
            f.createNewFile();
    } catch (IOException ex) {
            System.out.println("Error creando archivo "+f.getAbsolutePath());
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
    /**
     * Graba una nueva entrada en el archivo conteniendo el usuario y los primos
     *
     * @param entrada Entrada a guardar en el archivo
     */
    private void guardarEntrada(Entrada entrada) {
        inicializarArchivo();
        File f = new File(ruta);

        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            //preparacion de streams
            fos = new FileOutputStream(f, true);
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
            System.out.println("Aun no existe el archivo " + f.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error escribiendo en archivo " + f.getAbsolutePath());
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
     *
     * @param dis DataInputStream del que leer una entrada
     * @return La entrada con los datos leidos o NULL se ha llegado al final del
     * archivo
     */
    private Entrada leerSiguienteEntrada(DataInputStream dis) throws IOException {
        try {
            //leer el usuario
            String usuario = dis.readUTF();
            //leer la cantidad de primos
            int cantidad = dis.readInt();
            ArrayList<Integer> numeros = new ArrayList<Integer>();
            //leer los numeros primos
            for (int i = 0; i < cantidad; i++) {
                numeros.add(dis.readInt());
            }
            return new Entrada(usuario, numeros);
        } catch (EOFException eof) {
            //Si se ha llegado al final del archivo leemos null
            return null;
        }
    }

    /**
     * Devuelve el ultimo primo del archivo leyendo el archivo y devolviendo el
     * ultimo numero
     *
     * @return El ultimo primo
     */
    private int ultimoPrimoEnArchivo() {
        inicializarArchivo();
        File f = new File(ruta);

        FileInputStream fis = null;
        DataInputStream dis = null;
        int ultimoPrimo = 0;
        try {
            //creacion de streams
            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);

            //lectura
            Entrada e = new Entrada();
            while ((e = leerSiguienteEntrada(dis)) != null) {
                ultimoPrimo = e.numeros.get(e.numeros.size() - 1);
            }
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
        return ultimoPrimo;
    }

    //UTILES DE ENTRADA DE DATOS POR CONSOLA Y MENU
    /**
     * Pide una opcion filtrando que sea valida(de 1 a 4)
     *
     * @return la opcion elegida
     */
    public int mostrarMenu() {
        int opcion = -1;
        while (opcion < 1 || opcion > 4) {
            this.pasarPagina();
            System.out.println("**************************************************");
            System.out.println("*                    OPCIONES                    *");
            System.out.println("**************************************************");
            System.out.println("Usuario actual: " + nombreUsuario);
            System.out.println("1)Pedir numeros primos");
            System.out.println("2)Listar primos");
            System.out.println("3)Cambiar Usuario");
            System.out.println("4)Salir");
            System.out.println("Elije una opcion:");
            try {
                String respuesta = this.s.nextLine();
                opcion = Integer.parseInt(respuesta);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return opcion;
    }

    private void pedirUsuario() {
        pasarPagina();
        System.out.println("******************************************************");
        System.out.println("Usuario actual: " + nombreUsuario);
        System.out.println("Pulsa intro para continuar");
        System.out.println("******************************************************");
        this.nombreUsuario = this.s.nextLine();
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
        new Ejercicio10B().menu();
    }
}
