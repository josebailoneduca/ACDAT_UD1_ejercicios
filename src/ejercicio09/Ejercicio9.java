/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio09;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Crear un programa que lea el contenido de un fichero de texto y a partir de
 * Èl cree un nuevo fichero binario con el mismo nombre, y con extensiÛn cod. El
 * archivo COD, debe contener el mismo contenido que el fichero original, pero
 * cambiando las a por e, las e por i, las i por o, las o por u y las u por a.
 * Adem·s, sÌ en el cambio, resulta que la palabra contiene la e, la o y la u,
 * Èsta ser· puesta en may˙sculas. Muestra los resultados por pantalla. ObtÈn el
 * fichero original de alg˙n texto de internet, asegur·ndote que se puedan
 * comprobar las condiciones anteriores e incorpÛralo al proyecto en una carpeta
 * llamada recursos, con el nombre textoEjer9.txt. El fichero binario .cod se
 * guardar· en ese mismo directorio.
 *
 * @author Bailon
 */
public class Ejercicio9 {

    /**
     * MAIN INICIO DEL PROGRAMA
     *
     * @param args
     */
    public static void main(String[] args) {
        //leer fichero y cambiar vocales
        String textoConVocalesCambiadas = leerFicheroTexto();
        //detectar palabras que deben ir en mayuscula
        String textoConMayusculasAjustadas = cambiarMayusculasTexto(textoConVocalesCambiadas);
        //guardar binario
        guardarFicheroBinario(textoConMayusculasAjustadas);
        //leer binario
        leerFicheroBinario();
    }

    /**
     * Lee el fichero y cambia sus vocales segun el patron del ejercicio
     *
     * @return El texto leido con las vocales cambiadas
     */
    private static String leerFicheroTexto() {
        //PREPARACION 
        File archivo = new File("./src/ejercicio09/recursos/textoEjer9.txt");
        System.out.println("\n-------------------------------------------------\nCargando archivo " + archivo.getAbsolutePath() + "\n-------------------------------------------------");
        FileReader fr = null;
        BufferedReader br = null;
        String salida = "";
        int c = -1;
        //LECTURA Y CAMBIO
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while ((c = br.read()) != -1) {
                System.out.print((char) c);
                //lectura de caracter y cambio de vocal
                salida += checkCaracter((char) c);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Arvhivo no encontrado: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de E/S: " + ex.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                System.out.println("Error de E/S: " + ex.getMessage());
            }
        }
        System.out.println("\n\n----------------FIN DE ARCHIVO-----------------");

        //RETORNO
        return salida;
    }

    /**
     * Dada una letra devuelve la letra que toca seg˙n el ejercicio
     *
     * @param c Letra a comprobar
     * @return Letra correspondiente
     */
    public static char checkCaracter(char c) {
        String antes = "aeiouAEIOU·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹‡ËÏÚ˘¿»Ã“Ÿ";
        String despu = "eiouaEIOUAÈÌÛ˙·…Õ”⁄¡ÎÔˆ¸‰Àœ÷‹ƒËÏÚ˘‡»Ã“Ÿ¿";
        int pos = antes.indexOf(c);
        if (pos == -1) {
            return c;
        } else {
            return despu.charAt(pos);
        }
    }

    /**
     * Dado un texto cambia las palabras definidas por el ejercicio a mayusculas
     *
     * @param texto Texto a analizar
     * @return Texto con las mayusculas ajustadas
     */
    private static String cambiarMayusculasTexto(String texto) {
        //preparacion de variables
        boolean enPalabra = false;
        String separadores = " \n\r\f,;:.";
        String salida = "";

        String palabraActual = "";
        //lectura del archivo caracter a caracter para ir extrayendo palabras
        for (int i = 0; i < texto.length(); i++) {

            String caracter = Character.toString(texto.charAt(i));
            //si el caracter actual es un separador comprobamos 
            //si venimos de una palabra
            if (separadores.contains(caracter)) {
                palabraActual += caracter;
                if (enPalabra) {
                    //como venimos de una palabra
                    //damos por terminada la palabra y comprobamos
                    salida += checkMayusculasPalabra(palabraActual);
                    enPalabra = false;
                    palabraActual = "";
                }
            } else {
                //si no es un separador nos encontramos en una palabra
                palabraActual += caracter;
                enPalabra = true;
            }
        }
        //si en este punto se esta en una palabra 
        //es porque al final de la cadena hay una palabra
        //se agrega al conteo
        salida += checkMayusculasPalabra(palabraActual);
        return salida;
    }

    /**
     * Comprueba una palabra y la cambia a mayusculas si corresponde
     *
     * @param palabra Palabra a analizar
     * @return Palabra ajustada
     */
    private static String checkMayusculasPalabra(String palabra) {
        String e = "eEÈ…ÎÀË»";
        String o = "oOÛ”ˆ÷Ú“";
        String u = "uU˙⁄¸‹˘Ÿ";

        boolean tieneE = false;
        boolean tieneO = false;
        boolean tieneU = false;
        //contiene e o u
        for (int i = 0; i < palabra.length(); i++) {
            if (e.indexOf(palabra.charAt(i)) != -1) {
                tieneE = true;
            }
            if (o.indexOf(palabra.charAt(i)) != -1) {
                tieneO = true;
            }
            if (u.indexOf(palabra.charAt(i)) != -1) {
                tieneU = true;
            }
        }
        return (tieneE && tieneO && tieneU) ? palabra.toUpperCase() : palabra;
    }

    /**
     * Guarda un texto como archivo binario
     *
     * @param texto El texto a guardar
     */
    private static void guardarFicheroBinario(String texto) {
        System.out.println("\n-------------------------------------------------\nGuardando archivo binario:\n-------------------------------------------------");

        //1- GESTI”N DEL FICHERO       
        File archivo = new File("./src/ejercicio09/recursos/textoEjer9.cod");
        // 2- CreaciÛn del fichero:
        // Si estaba ANTES, lo eliminamos
        if (archivo.exists()) {
            archivo.delete();
        }
        // Igualmente lo creamos...      
        try {
            //Lo creamos
            archivo.createNewFile();
            System.out.println("Fichero creado:  " + archivo.getAbsolutePath());
        } catch (IOException ioe) {
            //No se ha podido crear
            System.out.println("No se pudo crear el fichero: " + archivo.getAbsolutePath());
            ioe.printStackTrace();
            System.exit(0);
        }//end try-catch

        //3-PREPARACI”N ESCRITURA     
        FileOutputStream fileOS = null;
        DataOutputStream dataOS = null;

        try {
            fileOS = new FileOutputStream("./src/ejercicio09/recursos/textoEjer9.cod");
            dataOS = new DataOutputStream(fileOS);
            //4-INSERTAR DATOS RECORRIENDO EL TEXTO Y GUARDANDO CADA CARACTER COMO BYTE
            for (int i = 0; i < texto.length(); i++) {
                //guardar caracter como byte
                dataOS.writeByte((byte) texto.charAt(i));
            }
        } catch (IOException ioe) {
            System.out.println(" ---> ERROR EN ACCESO al fichero o en mÈtodos escritura: " + archivo.getAbsolutePath());
            ioe.printStackTrace();
        } finally {
            try {
                //5-CERRAR STREAMS
                if (dataOS != null) {
                    dataOS.close();
                }
                if (fileOS != null) {
                    fileOS.close();
                }
            } catch (IOException ioe) {
                //System.out.println(" ---> ERROR en el cierre: "+archivo.getAbsolutePath()+"\n");
                ioe.printStackTrace();
            }
        }//end finally

    }

    /**
     * Lee un archivo binario y muestra su contenido en pantalla
     */
    private static void leerFicheroBinario() {
        File archivo = new File("./src/ejercicio09/recursos/textoEjer9.cod");
        System.out.println("\n-------------------------------------------------\nCargando archivo " + archivo.getAbsolutePath() + "\n-------------------------------------------------");
        //- B˙squeda del fichero:
        //Si no existe avisamos y terminamos
        if (archivo.exists() == false) {
            System.out.println("El fichero: " + archivo.getAbsolutePath());
            System.out.println("No existe en la ruta: " + archivo.getAbsolutePath());
            return;
        }

        //3-PREPARACI”N LECTURA: streams y datos        
        FileInputStream fis = null;
        DataInputStream dis = null;

        //4-LECTURA DATOS por pares nombre-edad:
        try {
            fis = new FileInputStream(archivo);
            dis = new DataInputStream(fis);

            // Bucle de lectura:          
            // Se intenta leer de 2 en 2 bytes y 
            // mientras la cantidad de bytes leidos no sea 0 se continua leyendo
            //
            byte[] b;
            while ((b = dis.readNBytes(2)).length != 0) {
                //convertir los dos bytes leidos a string segun el charset adecuado
                System.out.print(new String(b, StandardCharsets.ISO_8859_1));
            }
            System.out.println("\n\n----------------FIN DE ARCHIVO-----------------");
        } catch (FileNotFoundException fnfe) {//ExcepciÛn del FileInputStream
            System.out.println(" ---> ERROR EN ACCESO al fichero: " + archivo.getAbsolutePath() + "\n");
            fnfe.printStackTrace();
        } catch (IOException ieo) {////ExcepciÛn de mÈtodos readUTF y readInt
            System.out.println(" ---> ERROR mÈtodos de lectura: " + archivo.getAbsolutePath() + "\n");
            ieo.printStackTrace();
        } finally {
            try {
                //5-CERRAR STREAMS
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ioe) {
                System.out.println(" ---> ERROR en el cierre: " + archivo.getAbsolutePath() + "\n");
                ioe.printStackTrace();
            }
        }//end finally
    }

}
