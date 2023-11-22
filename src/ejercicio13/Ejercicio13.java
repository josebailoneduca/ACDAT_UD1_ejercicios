/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio13;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase del ejercicio 13. Guarda de manera binarias datos sobre alumnos y sus
 * asignaturas. Lo hace creando una estructura por alumno el cual contiene sus
 * datos y sus asignaturas. Luego esos datos se guardan de manera binaria en el
 * archivo RegistroCursoAlumnos.dat.
 *
 * Si aún no hay alumnos en ese archivo se pueden cargar de manera automática
 * los alumnos guardados como texto en AlumnosIniciales.txt
 *
 * Se pueden agregar alumnos manualmente tambien.
 *
 * Cuando en el archivo binario hay al menos 3 alumnos guarados se podrá ver los
 * datos de un alumno sus profesores y su nota media
 *
 * Cada Alumno tiene una ID que se calcula automáticamente dependiendo de los
 * alumnos que ya existan
 *
 * @author Jose Javier BO
 */
public class Ejercicio13 {

    //ATRIBUTOS####################################################
    /**
     * Scanner de consola
     */
    Scanner s = new Scanner(System.in);

    //mantiene una referencia de cuantos alumnos hay en el archivo binario
    int alumnosExistentes = 0;

    //lista de nombres usados (Para no repetir nombre+apellidos) 
    ArrayList<String> nombresUsados = new ArrayList<String>();

    //ruta del archivo de texto con los alumnos para carga rapida
    public static String rutaTexto = "./src/ejercicio13/recursos/AlumnosIniciales.txt";

    //ruta del archivo binario donde se guardaran los alumnos
    public static String rutaBinaria = "./src/ejercicio13/recursos/RegistroCursoAlumnos.dat";

    
    
    
    
    //METODOS###########################################################
    /**
     * Constructor. Agrega los datos iniciales al archivo y muestra el menu
     */
    public Ejercicio13() {
        //contar cuantos alumnos hay ya en disco
        alumnosExistentes = contarAlumnosEnDisco();
        //si no hay alumnos pedir los del archivo de texto
        if (alumnosExistentes == 0) {
            preguntarInsercionAlumnosTexto();
        }
        //lanzar el menu
        menu(); 
    }

    
    
    
    /**
     * Insercion automatica de alumnos leidos desde archivo de texto
     */
    private void preguntarInsercionAlumnosTexto() {
        pasarPagina();
        //PREGUNTAR SI SE QUIERE O NO CARGAR LOS ALUMNOS DESDE EL TEXTO
        System.out.println("¿Desea insertar los alumnos del archivo AlumnosIniciales.txt? (s/n)");
        String respuesta = s.nextLine();
        //CARGAR LOS ALUMNOS SI CORRESPONDE
        if (respuesta.toLowerCase().equals("s")) {
            ArrayList<Alumno> alumnos = leerAlumnosDesdeTexto();
            for (Alumno alumno : alumnos) {
                escribirAlumno(alumno);
            }
        }
    }

    
    
    
    
    
    /**
     * Lee los alumnos del archivo de texto y los devuelve como un array list
     *
     * @return El arraylist de alumnos leidos del texto
     */
    public ArrayList<Alumno> leerAlumnosDesdeTexto() {
        //Lista de los leidos
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        
        //PREAPARAR READERS
        File f = new File(rutaTexto);
        FileReader fr = null;
        BufferedReader bis = null;

        try {
            fr = new FileReader(f);
            bis = new BufferedReader(fr);
            String nombreAlumno;
            //id que se le aplicara al alumno leido
            int indice = 1;
            
            //LEER LINEAS CON LA SECUENCIA NOMBRE,APELLIDOS,FECHA,NOMBRE_CICLO, CURSO, Y 6 MODULOS
            while ((nombreAlumno = bis.readLine()) != null) {
                String apellidos = bis.readLine();
                Date FechaNac = new SimpleDateFormat("dd/MM/yyy").parse(bis.readLine());
                String nombreCiclo = bis.readLine();
                int curso = Integer.parseInt(bis.readLine());
                ArrayList<Modulo> modulos = new ArrayList<Modulo>();
                
                //conseguir la lista de modulos para el curso leido
                String[][] tiposModulo = getModulosCurso(curso);
                //recoger tantas notas como modulos se esperan
                for (int i = 0; i < tiposModulo.length; i++) {
                    int nota = Integer.parseInt(bis.readLine());
                    modulos.add(new Modulo(tiposModulo[i][0], tiposModulo[i][1], nota));
                }
                //GENERAR EL ALUMNO CON LOS DATOS RECOGIDOS
                alumnos.add(new Alumno(indice, nombreAlumno, apellidos, FechaNac, nombreCiclo, curso, modulos));
                indice++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el archivo "+f.getAbsolutePath());
        } catch (IOException | ParseException ex) {
            System.out.println("Error leyendo archivo "+f.getAbsolutePath());
        } catch (NumberFormatException ex) {
            System.out.println("Problema leyendo "+f.getAbsolutePath()+": "+ex.getMessage());
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio13.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //DEVOLVER LISTA DE ALUMNOS
        return alumnos;
    }

    
    /**
     * Devuelve un array con los datos de nombre y profesor para los modulos segun curso
     * @param curso Curso para el que objetenr los datos
     * @return  El array con los datos que corresponden
     */
        private String[][] getModulosCurso(int curso) {

        if (curso == 1) {
            String[][] modulos = {
                {"BBDD", "Juan Carlos"},
                {"ENDES", "Miguel Angel"},
                {"FOL", "Miguel Angel"},
                {"LM", "Juan Carlos"},
                {"PROG", "Juan Carlos"},
                {"SISINF", "Daniel"}
            };
            return modulos;
        } else {
            String[][] modulos = {
                {"ACDAT", "Adrian"},
                {"DESINT", "Adrian"},
                {"EIE", "Juan Carlos"},
                {"PSP", "Juan Carlos"},
                {"PMYDM", "Miguel Angel"},
                {"SGE", "Miguel Angel"}
            };
            return modulos;
        }
    }
    
    
    
    
    
    /**
     * Ordena que se muestre el menu y lanza la accion que se ha elegido en el
     * mismo
     */
    private void menu() {
        while (true) {
            pasarPagina();
            //RECOGER LA OPCION ELEGIDA
            int opcionElegida = this.mostrarMenu();
            
            //ACTUAR SEGUN LA OPCION ELEGIDA
            switch (opcionElegida) {
                //introducir alumno
                case 1 -> {
                    pasarPagina();
                    pedirDatosAlumno();
                }
                //Listar alumnos
                case 2 -> {
                    pasarPagina();
                    if (this.alumnosExistentes > 2) {
                        listarAlumnos();
                        pedirIntro("");
                    }
                }
                //Mostrar informacion de un alumno
                case 3 -> {
                    pasarPagina();
                    if (this.alumnosExistentes > 2) {
                        mostrarInformacionDeAlumno();
                    }
                }
                //mostrar profesores de un alumno
                case 4 -> {
                    pasarPagina();
                    if (this.alumnosExistentes > 2) {
                        mostrarProfesoresDeAlumno();
                    }
                }
                //mostrar nota media de un alumno
                case 5 -> {
                    pasarPagina();
                    if (this.alumnosExistentes > 2) {
                        mostrarNotaMediaDeAlumno();
                    }
                }

                //salir del programa
                default ->
                    System.exit(0);
            }
        }
    }

    
    
    
    
    
    /**
     * Pide los datos para un alumno. Tras eso lanza el grabado a disco del
     * alumno
     */
    private void pedirDatosAlumno() {
        pasarPagina();

        //PEDIR DATOS DEL ALUMNO
        System.out.println("Introducir alumno");
        System.out.println("*****************");

        //prearacion de variables
        String nombre = "";
        String apellidos = "";
        Date fechaNac = new Date();
        String nombreCurso = "DAM";
        int curso = 0;
        ArrayList<Modulo> modulos = new ArrayList<Modulo>();

        //recoleccion de nombre y apellidos
        boolean nombreCogido = false;
        while (!nombreCogido) {
            //Nombre
            System.out.println("Nombre:");
            nombre = s.nextLine();
            //apellidos 
            System.out.println("Apellidos:");
            apellidos = s.nextLine();

            //chequear validez de nombre
            if (nombre.length() == 0 || apellidos.length() == 0 || nombresUsados.contains(nombre + " " + apellidos)) {
                pedirIntro("Nombre no valido");
                pasarPagina();
            } else {
                nombreCogido = true;
            }
        }

        //recoger fecha nacimiento
        boolean fechaRecogida = false;
        while (!fechaRecogida) {
            try {
                //pedir fecha al usuario
                System.out.print("Fecha de nacimiento(dia/mes/año): ");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                df.setLenient(false);
                String tmpFecha = s.nextLine();
                //Si el string introducido no es legible por el formato saltara una excepcion y se volvera a pedir
                //si el string introducio es de una fecha posterior a la actual la volvera a pedir
                fechaNac = df.parse(tmpFecha);

                //filtro para aceptar solo fechas pasadas
                if (fechaNac.after(new Date(System.currentTimeMillis()))) {
                    pedirIntro("Fecha incorrecta al ser posterior a la actual");
                } else {
                    fechaRecogida = true;
                }
            } catch (ParseException ex) {
                pedirIntro("Formato de fecha incorrecto");
            }
        }

        //pedir curso
        curso = 0;
        boolean cursoRecogido = false;
        //solo 1 o 2
        while (!cursoRecogido) {
            System.out.print("Curso (1 o 2): ");
            try {
                curso = Integer.parseInt(s.nextLine());
                if (curso == 1 || curso == 2) {
                    cursoRecogido = true;
                }
            } catch (NumberFormatException ex) {
            }
        }

        //CREAR MODULOS PARA EL CURSO ELEGIDO
        for (String[] datosModulo : getModulosCurso(curso)) {
            modulos.add(pedirNotaDeModulo(datosModulo));
        }

        //CREAR ALUMNO
        Alumno alumno = new Alumno(this.alumnosExistentes + 1, nombre, apellidos, fechaNac, nombreCurso, curso, modulos);

        //ESCRIBIR EL ALUMNO A DISCO
        escribirAlumno(alumno);
    }

    /**
     * Recoge la nota de un modulo y devuelve el modulo creado
     *
     * @param datosModulo Nombre y profesor
     * @return El modulo ya creado con su nota
     */
    private Modulo pedirNotaDeModulo(String[] datosModulo) {
        //preparar variables
        int nota = 0;
        boolean notaRecogida = false;

        //RECOGER UNA NOTA VALIDA DE 1 A 10
        while (!notaRecogida) {
        System.out.println("Módulo " + datosModulo[0] + " profesor: " + datosModulo[1]);
            System.out.println("Introducir nota: ");
            try {
                nota = Integer.parseInt(s.nextLine());
                if (nota > 0 && nota < 11) {
                    notaRecogida = true;
                } else {
                    System.out.println("Nota no valida");
                }
            } catch (NumberFormatException ex) {
            }
        }
        
        //CONSTRUIR EL MODULO Y DEVOLVERLO
        return new Modulo(datosModulo[0], datosModulo[1], nota);
    }

    /**
     * Escribe un alumno a disco.
     *
     * @param alumno El alumno a escribir
     */
    private void escribirAlumno(Alumno alumno) {

        File f = new File(rutaBinaria);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            //PREPARAR STREAMS
            fos = new FileOutputStream(f, true);
            //Comprobar si ya hay alumnos  para decidir que tipo de objectOutputStream crear
            if (alumnosExistentes == 0) {
                //SI NO HAY ALUMNOS CREA UN OBJECTOUTPUTSTREAM NORMAL
                oos = new ObjectOutputStream(fos);
            } else {
                //SI YA HAY ALUMNOS CREA UN AGREGAR OBJECTOUTPUTSTREAM QUE EVITA LA ESCRITURA DE CABECERA
                oos = new AgregarObjectOutputStream(fos);
            }

            //ESCRIBIR ALUMNO EN DISCO
            oos.writeObject(alumno);

            //incrementar el numero de alumnos que hay en disco y guardar su nombre+apellidos entre los usados
            this.nombresUsados.add(alumno.getNombre() + " " + alumno.getApellidos());
            alumnosExistentes++;

            //tratamiento de excepciones y cierre de streams
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado " + f.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error escribiendo a archivo " + f.getAbsolutePath());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio13.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Lista los nombres de los alumnos y su id
     */
    private void listarAlumnos() {
        //si no hay alumnos avisar y volver
        if (alumnosExistentes == 0) {
            pedirIntro("No hay alumnos");
            return;
        }

        //LEER LOS DATOS
        File f = new File(rutaBinaria);
        //si no existe el archivo retorna lista vacia
        if (!f.exists()) {
            return;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            //preparacion de streams
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);

            System.out.println("*************************");
            System.out.println("*  Alumnos en la lista  *");
            System.out.println("*************************");

            //RECOGER ALUMNOS HASTA EOF EXCEPTION
            while (true) {
                //recoger alumno
                Alumno alumno = (Alumno) ois.readObject();
                //IMPRIMIR ALUMNO EN PANTALLLA
                System.out.println("ID:" + alumno.getId() + ") " + alumno.getNombre() + " " + alumno.getApellidos());
            }//fin while

            //tratamiento de excepciones y cierre de streams
        } catch (EOFException eofe) {
        } catch (IOException ex) {
            System.out.println("Error leyendo "+f.getAbsolutePath());
        } catch (ClassNotFoundException ex) {
            System.out.println("Problema interpretando alumno de "+f.getAbsolutePath()+": "+ex.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio13.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Muestra toda la información de un alumno
     */
    private void mostrarInformacionDeAlumno() {

        //RECOGER ALUMNO 
        Alumno alumno = buscarAlumnoPorId();
        
        //SI EXISTE EL ALUMNO SE MUESTRA
        if (alumno != null) {
            pasarPagina();
            System.out.println("*************************");
            System.out.println("* Datos del alumno      *");
            System.out.println("*************************");

            System.out.println(alumno);
            pedirIntro("");
        }
    }

    /**
     * Muestra un listado con los profesores que tiene un alumno
     */
    private void mostrarProfesoresDeAlumno() {
        //RECOGER ALUMNO 
        Alumno alumno = buscarAlumnoPorId();
        //SI EXISTE SE MUESTRA EL LISTADO DE PROFESORES
        if (alumno != null) {
            pasarPagina();
            System.out.println("*************************");
            System.out.println(" Profesores del alumno " + alumno.getNombre() + " " + alumno.getApellidos());
            System.out.println("*************************");

            for (String prof : alumno.getProfesores()) {
                System.out.println(prof);
            }
            pedirIntro("");
        }
    }

    /**
     * Muestra la nota media que tiene un alumno
     */
    private void mostrarNotaMediaDeAlumno() {

        //RECOGER ALUMNO 
        Alumno alumno = buscarAlumnoPorId();
        
        //SI EXISTE MUESTRA UN LISTADO DE SUS NOTAS Y LA NOTA MEDIA
        if (alumno != null) {
            pasarPagina();
            System.out.println("*************************************");
            System.out.println(" Nota media del alumno " + alumno.getNombre() + " " + alumno.getApellidos());
            System.out.println("*************************************");
            
            //recorrer los modulos y mostrar su nota
            for (Modulo m : alumno.getModulos()) {
                System.out.println(m.getNombre() + ": " + m.getNotaFinal());
            }
            
            System.out.println("-------------------------");
            
            //mostrar nota media
            System.out.printf("      MEDIA: %.2f", alumno.getNotaMedia());
            
            System.out.println("");
            pedirIntro("");
        }
    }

    /**
     * Busca un alumno en disco por su id
     *
     * @return el alumno encontrado o null si no existe
     */
    private Alumno buscarAlumnoPorId() {
        //SI NO HAY ALUMNOS SE AVISA
        if (alumnosExistentes == 0) {
            pedirIntro("No hay alumnos");
            return null;
        }
        
        //RECOGER LA ID DESDE CONSOLA
        int idBuscada = 0;
        boolean idRecogida = false;
        while (!idRecogida) {
            System.out.println("Introduzca la id del alumno.(0 para mostrar la lista)");
            try {
                idBuscada = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException ex) {
            }
            if (idBuscada == 0) {
                listarAlumnos();
            } else {
                idRecogida = true;
            }
        }

        //LEER DE DISCO Y BUSCARLO
        File f = new File(rutaBinaria);
        //si no existe el archivo retorna lista vacia
        if (!f.exists()) {
            return null;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            //preparacion de streams
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            //recoger alumnos hasta EOF exception
            while (true) {
                //recoger alumno
                Alumno alumno = (Alumno) ois.readObject();
                
                //SI ES EL ALUMNO BUSCADO SE DEVUELVE
                if (alumno.getId() == idBuscada) {
                    return alumno;
                }
                
            }//fin while

            //tratamiento de excepciones y cierre de streams
        } catch (EOFException eofe) {
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ejercicio13.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio13.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //si se llega hasta aquí es que no se ha econtrado el alumno
        pedirIntro("Alumno no encontrado");
        return null;
    }

    /**
     * Cuenta los alumnos que hay en el disco
     *
     * @return el numero de alumnos que ya existen
     */
    private int contarAlumnosEnDisco() {

        //cantidad de alumnos que hay en disco
        int cantidadDeAlumnos = 0;

        File f = new File(rutaBinaria);
        //si no existe el archivo retorna lista vacia
        if (!f.exists()) {
            return 0;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            //preparacion de streams
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);

            //RECORRER ALUMNOS HASTA EOF EXCEPTION
            while (true) {
                //recoger alumno 
                Alumno alumno = (Alumno) ois.readObject();
                
                //SUMAR ALUMNO LEIDO A LA CUENTA
                cantidadDeAlumnos++;
                
                //AGREGAR NOMBRE DEL ALUMNO A LA LISTA DE NOMBRES USADOS
                this.nombresUsados.add(alumno.getNombre() + " " + alumno.getApellidos());
                
            }//fin while

            //tratamiento de excepciones y cierre de streams
        } catch (EOFException eofe) {
        } catch (IOException ex) {
            return 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ejercicio13.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio13.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //retorna la cantidad de alumnos
        return cantidadDeAlumnos;
    }

//UTILES DE ENTRADA DE DATOS POR CONSOLA Y MENU
    /**
     * Pide una opcion filtrando que sea valida(de 1 a 4)
     *
     * @return la opcion elegida
     */
    private int mostrarMenu() {
        int opcion = -1;
        while (opcion < 1 || opcion > 6) {
            this.pasarPagina();
            System.out.println("**************************************************");
            System.out.println("*                    OPCIONES                    *");
            System.out.println("* Alumnos en disco: " + this.alumnosExistentes);
            System.out.println("**************************************************");
            System.out.println("1)Introducir alumno manualmente");
            if (this.alumnosExistentes > 2) {
                System.out.println("2)Listar alumnos");
                System.out.println("3)Mostrar toda la información de un alumno");
                System.out.println("4)Mostrar profesores de un alumno");
                System.out.println("5)Mostrar nota media de un alumno");
            }
            System.out.println("6)Salir");
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
        if (msg != null && msg.length() > 0) {
            System.out.println(msg);
        }
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
        new Ejercicio13();
    }


}
