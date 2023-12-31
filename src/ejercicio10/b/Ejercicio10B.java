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
 * Ejercicio 10B. Generar numeros primos y guardarlos en disco en forma binaria.
 * 
 * @author Jose Javier BO
 */
public class Ejercicio10B {

    /**
     * Estructura para guardar los datos de una entrada.
     * Cada entrada contiene el nombre de usuario y la lista de primos que ha pedido
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
            this.numeros = new ArrayList<>();
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
     * Usuario actual
     */
    String nombreUsuario = "";

    /**
     * Muestra el menu y gestiona la ejecucioin de la opcion seleccionada
     */
    public void menu() {
        while (true) {
            //si no hay usuario pedirlo
            if (nombreUsuario.equals("")) {
                pedirUsuario();

                //si hay usuario mostrar el menu que corresponda
            } else {
                int opcionElegida = this.mostrarMenu();
                switch (opcionElegida) {
                    //pedir numeros primos
                    case 1 ->
                        pedirNumerosPrimos();
                    //Listar contenido del archivo
                    case 2 ->
                        listarDatosDelArchivo();
                    //Cambiar usuario
                    case 3 ->
                        pedirUsuario();
                    //salir del programa
                    default ->
                        System.exit(0);
                }
            }
        }
    }

    /**
     * Gestiona qu� tipo de peticion de primos debe lanzar. Si el
     * ultimo primo guardado es menor de 97(ultimo antes del 100) lanza la peticion 
     * de un rango. En caso contrario lanza la peticion de 1 a 3 primos
     */
    private void pedirNumerosPrimos() {
        if (ultimoPrimoEnArchivo() < 97) {
            pedirPorRango();
        } else {
            pedirHasta3();
        }
    }

    /**
     * Pide un numero m�ximo para generar los numeros primos. Si el rango
     * ingroducido es menor que el primo actual avisa y vuelve a pedir. Tras
     * recoger el rango solicitado lanza el calculo de los primos y el guardado
     */
    private void pedirPorRango() {
        pasarPagina();
        System.out.println("");

        //Peticion del rango al usuario
        boolean pedido = false;
        int limiteSuperior = 0;
        //repetir bucle hasta que haya dado un rango valido(que sea numero y sea mayor que el ultimo primo agregado)
        while (!pedido) {
            System.out.println("Introduzca el limite m�ximo al que llegar con los numeros primos (al menos " + (ultimoPrimoEnArchivo() + 1) + ")");
            String respuesta = s.nextLine();
            try {
                limiteSuperior = Integer.parseInt(respuesta);
                if (limiteSuperior > ultimoPrimoEnArchivo()) {
                    pedido = true;
                } else {
                    System.out.println("Debe ser m�s alto");
                }
            } catch (NumberFormatException ex) {
                System.out.println(respuesta + " no es un numero");
            }
        }

        //calcular los numeros primos
        ArrayList<Integer> numeros = calcularPrimosDeRango(limiteSuperior);

        //si no hay numeros primos en el rango se avisa
        if (numeros.isEmpty()) {
            pedirIntro("No hay numeros primos de " + ultimoPrimoEnArchivo() + " a " + limiteSuperior);
        } else {
            //si hay primos los guarda a archivo
            guardarEntrada(new Entrada(nombreUsuario, numeros));
            pedirIntro("Se han guardado " + numeros.size() + " numeros primos");
        }
    }

    /**
     * Pide un numero concreto de numero primos de 1 a 3
     *
     * Tras recoger el rango solicitado lanza el calculo de los primos y el
     * guardado
     */
    private void pedirHasta3() {
        pasarPagina();
        System.out.println("");
        boolean pedido = false;
        int cantidad = 0;
        //peticion de cuantos numeros primos se quiere
        while (!pedido) {
            System.out.println("Introduzca la cantidad de numeros primos que quiere (1,2 o 3):");
            String respuesta = s.nextLine();
            try {
                cantidad = Integer.parseInt(respuesta);
                if (cantidad > 0 && cantidad < 4) {
                    pedido = true;
                } else {
                    System.out.println("Debes pedir de 1 a 3");
                }
            } catch (NumberFormatException ex) {
                System.out.println(respuesta + " no es un numero");
            }
        }

        //calculo de los numeros primos que se han pedido
        ArrayList<Integer> numeros = calcularCantidadPrimos(cantidad);

        // guardado a disco de los numeros
        guardarEntrada(new Entrada(nombreUsuario, numeros));
        pedirIntro("Se han guardado " + numeros.size() + " numeros primos");
    }

    /**
     * Calcula los numeros primos mayores que el ultimo numero primo que hay en
     * el archivo y menor o igual que el limite que se suministra. Si se pasa de
     * 100 solo calcula hasta el 100 y para de calcular. Devuelve los primos
     * calculados en una arraylist de enteros.
     *
     * @param limiteSuperior Limite m�ximo al que llegar
     *
     * @return La lista de numeros primos encontrados
     */
    private ArrayList<Integer> calcularPrimosDeRango(int limiteSuperior) {
        ArrayList<Integer> numeros = new ArrayList<>();
        //chequeo de si es primo cada numero entre el maximo actual y el limite superior
        for (int i = (ultimoPrimoEnArchivo() + 1); i <= limiteSuperior; i++) {
            if (i > 100) {
                pedirIntro("Se ha llegado al limite de 100. Se han generado " + numeros.size() + " numeros primos. A partir de ahora solo podr� pedir 3 numeros como m�ximo");
                return numeros;
            } else //si es primo se agrega a la lista resultado
            if (esPrimo(i)) {
                numeros.add(i);
            }
        }

        //devolver la lista de numeros primos encontrados en el rango
        return numeros;
    }

    /**
     * Calcula una cantidad determinada de numeros primos. Calcula los
     * siguiente al ultimo que exite ya en el archivo
     *
     * @param cantidad Cantidad de primos a generar
     *
     * @return La lista de primos calculados
     */
    private ArrayList<Integer> calcularCantidadPrimos(int cantidad) {

        ArrayList<Integer> numeros = new ArrayList<>();
        int inicio = ultimoPrimoEnArchivo() + 1;

        while (numeros.size() < cantidad) {
            if (esPrimo(inicio)) {
                numeros.add(inicio);
            }
            inicio++;
        }
        return numeros;
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
            //lectura de disco e impresion en pantalla de los datos
            
            //lectura
            ArrayList<Entrada> entradas = new ArrayList<>();
            Entrada entrada = null;
            while ((entrada = leerSiguienteEntrada(dis)) != null) {
                entradas.add(entrada);
            }

            //impresion
            //si no hay entradas se avisa
            if (entradas.isEmpty()) {
                pedirIntro("No hay datos almacenados. Pida numeros primos para agregarlos a " + f.getAbsolutePath());
            } else {
                for (Entrada e : entradas) {
                    String textoEntrada=e.usuario + ": ";
                    for (int p : e.numeros) {
                        textoEntrada+=p+",";
                    }
                //quitar ultima coma
                textoEntrada=textoEntrada.substring(0, (textoEntrada.length()-1));
                    System.out.println(textoEntrada);
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

    /**
     * Devuelve si un numero es primo o no mirando si es divisible por algun
     * numero menor o igual que su raiz cuadrada
     *
     * @param num El numero a comprobar
     * @return True si es primo, False si no es primo
     */
    private boolean esPrimo(int num) {
        if (num < 2) {
            return false;
        }
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
            //leer la cantidad de primos que siguen
            int cantidad = dis.readInt();
            ArrayList<Integer> numeros = new ArrayList<>();
            //leer los numeros primos en si
            for (int i = 0; i < cantidad; i++) {
                numeros.add(dis.readInt());
            }
            //devolver la entrada con los datos de usuario y primos
            return new Entrada(usuario, numeros);
        } catch (EOFException eof) {
            //Si se ha llegado al final del archivo devolvemos null
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
            Entrada e = null;
            //leer entradas del archivo hasta que sean null(habra llegado al final)
            //para cada entrada cogemos el ultimo primo
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
        
        //devolver el ultimo primo leido
        return ultimoPrimo;
    }

    /**
     * Comprueba si existe el archivo y si no existe lo crea
     */
    private void inicializarArchivo() {
        File f = new File(ruta);
        if (!f.exists())
        try {
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println("Error creando archivo " + f.getAbsolutePath());
        }
    }

    //UTILES DE ENTRADA DE DATOS POR CONSOLA Y MENU
    /**
     * Pide una opcion filtrando que sea valida(de 1 a 4)
     *
     * @return la opcion elegida
     */
    private int mostrarMenu() {
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

    /**
     * Solicita un nombre de usuario no vacio
     */
    private void pedirUsuario() {
        while (true) {
            pasarPagina();
            System.out.println("******************************************************");
            System.out.println("Usuario actual: " + ((nombreUsuario.length() > 0) ? nombreUsuario : "Ninguno"));
            System.out.println("******************************************************");
            System.out.println("Escribir nuevo usuario:");
            String nuevoNombre = this.s.nextLine();
            if (nuevoNombre.length() > 0) {
                this.nombreUsuario = nuevoNombre;
                return;
            }
        }
    }

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
