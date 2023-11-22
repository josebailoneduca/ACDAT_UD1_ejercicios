/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio15.logica;

import ejercicio15.dto.Empleado;
import ejercicio15.dto.Trabajo;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase de funciones de acceso y lectura desde disco
 * @author Jose Javier BO
 */
public class OperacionesArchivo {

    /**
     * Inicializa los archivos si no existen
     */
    static void inicializarArchivos() {
        //comprobar existencia
        File ft = new File(Control.rutaTrabajos);
        File fe = new File(Control.rutaEmpleados);
        //si no existen crear vacios
        boolean existen = ft.exists() && fe.exists();
        if (!existen) {
            resetArchivos();
        }
    }

    /**
     * Lee trabajos del TXT de datos iniciales
     * @return ArrayList con los trabajos leidos
     */
    public static ArrayList<Trabajo> leerTrabajosInicialesTXT() {

        //Lista de los leidos
        ArrayList<Trabajo> trabajos = new ArrayList<>();

        //PREAPARAR READERS
        File f = new File(Control.rutaTrabajosIniciales);
        FileReader fr = null;
        BufferedReader bis = null;

        try {
            fr = new FileReader(f);
            bis = new BufferedReader(fr);
            //LEER LINEAS CON LA SECUENCIA DE TRABAJOS
            String idSt;
            while ((idSt = bis.readLine()) != null) {
                int id = Integer.parseInt(idSt);
                String nombre = bis.readLine();
                Long fecha = (new SimpleDateFormat("dd/MM/yyy").parse(bis.readLine())).getTime();
                int[] empleados = new int[Trabajo.limiteEmpleados];
                for (int i = 0; i < Trabajo.limiteEmpleados; i++) {
                    empleados[i] = Integer.parseInt(bis.readLine());
                }
                //GENERAR EL TRABAJO CON LOS DATOS RECOGIDOS
                trabajos.add(new Trabajo(id, nombre, fecha, empleados));
            }
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado "+f.getAbsolutePath());
        } catch (IOException | ParseException | NumberFormatException ex) {
            msgError("Error leyendo "+f.getAbsolutePath());
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                msgError("Error leyendo "+f.getAbsolutePath());
            }
        }

        //DEVOLVER LISTA DE TRABAJOS
        return trabajos;

    }

    /**
     * Lee empleados del TXT de datos iniciales
     * @return ArrayList con los empleados leidos
     */
    public static ArrayList<Empleado> leerEmpleadosInicialesTXT() {

        //Lista de los leidos
        ArrayList<Empleado> empleados = new ArrayList<>();

        //PREAPARAR READERS
        File f = new File(Control.rutaEmpleadosIniciales);
        FileReader fr = null;
        BufferedReader bis = null;

        try {
            fr = new FileReader(f);
            bis = new BufferedReader(fr);
            //LEER LINEAS CON LA SECUENCIA DE EMPLEADOS
            String idSt;
            while ((idSt = bis.readLine()) != null) {
                int id = Integer.parseInt(idSt);
                String nombre = bis.readLine();
                String apellidos = bis.readLine();
                int sueldo = Integer.parseInt(bis.readLine());
                int[] trabajos = new int[Empleado.limiteTrabajos];
                for (int i = 0; i < Empleado.limiteTrabajos; i++) {
                    trabajos[i] = Integer.parseInt(bis.readLine());
                }
                //GENERAR EL EMPLEADOS CON LOS DATOS RECOGIDOS
                empleados.add(new Empleado(id, nombre, apellidos, sueldo, trabajos));
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            msgError("Error leyendo "+f.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error leyendo "+f.getAbsolutePath());
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                msgError("Error leyendo "+f.getAbsolutePath());            }
        }

        //DEVOLVER LISTA DE EMPLEADOS
        return empleados;

    }

    /**
     * Actualiza el numero de empleados en disco
     */
    public static void inicializarNumeroEmpleadosEndisco() {
        File fichero = new File(Control.rutaEmpleados);
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fichero, "rw"); 
            //IR AL INICIO
            raf.seek(0);
            //ESCRIBIR 0 INDICANDO QUE NO HAY EMPLEADOS
            raf.writeInt(0);
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error escribiendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error escribiendo a " + fichero.getAbsolutePath());
            }
        }
    }

    /**
     * Actualiza el numero de trabajos en disco
     */
    public static void inicializarNumeroTrabajosEndisco() {
        File fichero = new File(Control.rutaTrabajos);
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fichero, "rw");
            //IR AL INICIO
            raf.seek(0);
            //ESCRIBIR 0 INDICANDO QUE NO HAY TRABAJOS
            raf.writeInt(0);
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error escribiendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error escribiendo a " + fichero.getAbsolutePath());
            }
        }
    }

    /**
     * Cargar empleados del disco de manera secuencial
     */
    static ArrayList<Empleado> cargarEmpleados() {
        ArrayList<Empleado> lista = new ArrayList<>();
        File fichero = new File(Control.rutaEmpleados);

        //3-PREPARACIÓN LECTURA: streams y datos        
        //Clases necesarias para envío de datos binarios:
        FileInputStream fis = null;
        DataInputStream dis = null;
        int cantidadEmpleados;
        try {
            fis = new FileInputStream(fichero);
            dis = new DataInputStream(fis);
            //LEER CANTIDAD DE EMPLEADOS
            cantidadEmpleados = dis.readInt();
            //RECORRER EL ARCHIVO CARGANDOLOS
            for (int i = 0; i < cantidadEmpleados; i++) {
                //lectura de atributos
                int id = dis.readInt();
                char[] nombreCh = new char[Empleado.limiteNombre];
                for (int j = 0; j < Empleado.limiteNombre; j++) {
                    nombreCh[j] = dis.readChar();
                }
                String nombre = new String(nombreCh);

                char[] apellidosCh = new char[Empleado.limiteApellidos];
                for (int j = 0; j < Empleado.limiteApellidos; j++) {
                    apellidosCh[j] = dis.readChar();
                }
                String apellidos = new String(apellidosCh);
                int sueldo = dis.readInt();
                //lectura de trabajos asignados
                int[] trabajos = new int[Empleado.limiteTrabajos];
                for (int j = 0; j < Empleado.limiteTrabajos; j++) {
                    trabajos[j] = dis.readInt();
                }
                lista.add(new Empleado(id, nombre, apellidos, sueldo, trabajos));
            }
        } catch (FileNotFoundException ex) {
            msgError("Error leyendo de " + fichero.getAbsolutePath());
        } catch (EOFException ex) {
        } catch (IOException ex) {
            msgError("Error leyendo de " + fichero.getAbsolutePath());
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
            msgError("Error leyendo de " + fichero.getAbsolutePath());
            }
        }
        
        //devolver la lista
        return lista;

    }

    /**
     * Cargar trabajos del disco de manera secuencial
     */
    static ArrayList<Trabajo> cargarTrabajos() {
        ArrayList<Trabajo> lista = new ArrayList<>();
        File fichero = new File(Control.rutaTrabajos);

        //3-PREPARACIÓN LECTURA: streams y datos        
        //Clases necesarias para envío de datos binarios:
        FileInputStream fis = null;
        DataInputStream dis = null;
        int cantidadTrabajos;
        try {
            fis = new FileInputStream(fichero);
            dis = new DataInputStream(fis);
            //leer cantidad de trabajos
            cantidadTrabajos = dis.readInt();
            
            //leer trabajos
            for (int i = 0; i < cantidadTrabajos; i++) {
                int id = dis.readInt();
                char[] nombreCh = new char[Trabajo.limiteNombre];
                for (int j = 0; j < Trabajo.limiteNombre; j++) {
                    nombreCh[j] = dis.readChar();
                }
                String nombre = new String(nombreCh);
                long fecha = dis.readLong();
                int[] empleados = new int[Trabajo.limiteEmpleados];
                for (int j = 0; j < Trabajo.limiteEmpleados; j++) {
                    empleados[j] = dis.readInt();
                }
                //agregar trabajo a la lista
                lista.add(new Trabajo(id, nombre, fecha, empleados));
            }
        } catch (FileNotFoundException ex) {
            msgError("Error leyendo de " + fichero.getAbsolutePath());
        } catch (EOFException ex) {
        } catch (IOException ex) {
            msgError("Error leyendo de " + fichero.getAbsolutePath());
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
            msgError("Error leyendo de " + fichero.getAbsolutePath());
            }
        }
        
        //devolver la lista
        return lista;

    }
    
    /**
     * Agrega un trabajo al archivo binario de trabajos
     * @param t El trabajo a agregar
     */
    static void agregarTrabajo(Trabajo t) {

        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar numero de trabajos
            raf.seek(0);
            raf.writeInt(Control.getTrabajos().size());
            //ir al final
            raf.seek(raf.length());

            //ESCRIBIR TRABAJO
            raf.writeInt(t.getId());//id trabajo
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sb = new StringBuffer(t.getNombre());
            sb.setLength(Trabajo.limiteNombre);//15 caracteres máximo para el nombre
            String nombre = sb.toString();
            raf.writeChars(nombre);//nombre
            raf.writeLong(t.getFecha());//fecha
            for (int empleado : t.getEmpleados()) {
                raf.writeInt(empleado);
            }
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
    }

  

    /**
     * Agregar un empleado al archivo binario
     * @param e El empleado a agregar
     */
    static void agregarEmpleado(Empleado e) {

        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar numero de empleados
            raf.seek(0);
            raf.writeInt(Control.getEmpleados().size());
            //ir al final
            raf.seek(raf.length());

            raf.writeInt(e.getId());//id empleado
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sbn = new StringBuffer(e.getNombre());
            sbn.setLength(Empleado.limiteNombre);//limite de longitud nombre
            String nombre = sbn.toString();
            raf.writeChars(nombre);//nombre
            //Limitamos el tamaño de apellidos:
            StringBuffer sba = new StringBuffer(e.getApellidos());
            sba.setLength(Empleado.limiteApellidos);//limite de longitud apellidos
            String apellidos = sba.toString();
            raf.writeChars(apellidos);//apellidos
            raf.writeInt(e.getSueldo());
            for (int trabajo : e.getTrabajos()) {
                raf.writeInt(trabajo);
            }
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
    }

    /**
     * Elimina y recrea vacios los archivos de datos binarios
     */
    static void resetArchivos() {
        File ft = new File(Control.rutaTrabajos);
        File fe = new File(Control.rutaEmpleados);
        ft.delete();
        fe.delete();
        try {
            //crear archivos vacios
            ft.createNewFile();
            fe.createNewFile();
            //actualizar almacenados a 0
            inicializarNumeroEmpleadosEndisco();
            inicializarNumeroTrabajosEndisco();
        } catch (IOException ex) {
            Logger.getLogger(OperacionesArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Elimina un trabajo accediendo de manera aleatoria y poniendo su ID a negativa
     * @param idTrabajo id del trabajo
     */
    static void borrarTrabajo(int idTrabajo) {
        long inicioTrabajo = 4 + ((idTrabajo - 1) * Trabajo.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar id del trabajo a negativo
            raf.seek(inicioTrabajo);
            raf.writeInt(-idTrabajo);
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
    }

    /**
     * Actualiza en disco los trabajos que tiene asignados un empleado
     * accediendo de manera aleatoria al archivo y escribiendo solo los datos de
     * asignacion
     * @param e Empleado a actualizar
     */
    static void actualizarTrabajosDeEmpleado(Empleado e) {
        int id = Math.abs(e.getId());
        //calcular punto de escritura: int + inicio de entrada del empleado + desfase hasta zona de trabajos
        long inicioTrabajos = 4 + ((id - 1) * Empleado.longitudBytes) + Empleado.longitudBytesHastaTrabajos;
        
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //moverse hasta el inicio de los trabajos y escribirlos
            raf.seek(inicioTrabajos);
            for (int trabajo : e.getTrabajos()) {
                raf.writeInt(trabajo);
            }
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
    }

    /**
     * Eliminar empleado accediendo de manera aleatoria al archivo y poniendo en negativo
     * su id
     * @param idEmpleado  Id del empleado a borrar
     */
    static void borrarEmpleado(int idEmpleado) {
        //calcular punto de escritura
        long inicioEmpleado = 4 + ((idEmpleado - 1) * Empleado.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            raf.seek(inicioEmpleado);
            //actualizar id a negativo
            raf.writeInt(-idEmpleado);
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
    }

    /**
     * Actualiza en disco los empleados que tiene asignados un trabajo
     * accediendo de manera aleatoria al archivo y escribiendo solo los datos de
     * asignacion
     * @param t El trabajo a actualizar
     */
    static void actualizarEmpleadosDeTrabajo(Trabajo t) {

        int id = Math.abs(t.getId());
        //calcular punto de escritura: int + inicio de entrada del trabajo + desfase hasta zona de empleados
        long inicioEmpleados = 4 + ((id - 1) * Trabajo.longitudBytes) + Trabajo.longitudBytesHastaEmpleados;
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //moverse al inicio de los empleados y escribirlos
            raf.seek(inicioEmpleados);
            for (int empleado : t.getEmpleados()) {
                raf.writeInt(empleado);
            }
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
    }

    /**
     * Lee un trabajo accediendo al archivo binario de manera aleatoria
     * @param idTrabajo Id del trabajo a leer
     * @return El trabajo leido o null si no existe
     */
    static public Trabajo getTrabajo(int idTrabajo) {

        Trabajo trabajo = null;
        //calculo de la posicion del trabajo en el archivo
        long inicioTrabajo = 4 + ((idTrabajo - 1) * Trabajo.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "r");//Lectura y Escritura
            //comprobar que el trabajo existe 
            if (inicioTrabajo >= raf.length()) {
                return null;
            }
            //moverse hasta la posicion inicial del trabajo
            raf.seek(inicioTrabajo);
            
            //leer campos
            int id = raf.readInt();
            char[] nombreCh = new char[Trabajo.limiteNombre];
            for (int j = 0; j < Trabajo.limiteNombre; j++) {
                nombreCh[j] = raf.readChar();
            }
            String nombre = new String(nombreCh);
            long fecha = raf.readLong();
            int[] empleados = new int[Trabajo.limiteEmpleados];
            for (int j = 0; j < Trabajo.limiteEmpleados; j++) {
                empleados[j] = raf.readInt();
            }
            //crear trabajo
            trabajo = new Trabajo(id, nombre, fecha, empleados);
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
        //devolver trabajo
        return trabajo;
    }

    /**
     * Actualiza en disco los campos principales de un trabajo
     * @param trabajo El trabajo a actualizar
     */
    static void editarTrabajo(Trabajo trabajo) {
        //calcular posicion
        long inicioTrabajo = 4 + 4 + ((trabajo.getId() - 1) * Trabajo.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //moverse hasta el inicio del trabajo
            raf.seek(inicioTrabajo);
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sb = new StringBuffer(trabajo.getNombre());
            sb.setLength(Trabajo.limiteNombre);//15 caracteres máximo para el nombre
            String nombre = sb.toString();
            raf.writeChars(nombre);//nombre
            raf.writeLong(trabajo.getFecha());//fecha
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
    }

    /**
     * Leer un empleado de disco accediendo de manera aleatoria
     * @param idEmpleado Id del empleado
     * @return El empleado leido o null si no existe
     */
    public static Empleado getEmpleado(int idEmpleado) {
        Empleado empleado = null;
        //calcular posicion de inicio del empleado en el disco
        long inicioEmpleado = 4 + ((idEmpleado - 1) * Empleado.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "r");//Lectura y Escritura
            //comprobar que el trabajo existe 
            if (inicioEmpleado >= raf.length()) {
                return null;
            }
            //mover hasta inicio de empleado
            raf.seek(inicioEmpleado);

            //leer datos del empleado
            int id = raf.readInt();
            char[] nombreCh = new char[Empleado.limiteNombre];
            for (int j = 0; j < Empleado.limiteNombre; j++) {
                nombreCh[j] = raf.readChar();
            }
            String nombre = new String(nombreCh);

            char[] apellidosCh = new char[Empleado.limiteApellidos];
            for (int j = 0; j < Empleado.limiteApellidos; j++) {
                apellidosCh[j] = raf.readChar();
            }
            String apellidos = new String(apellidosCh);
            int sueldo = raf.readInt();
            int[] trabajos = new int[Empleado.limiteTrabajos];
            for (int j = 0; j < Empleado.limiteTrabajos; j++) {
                trabajos[j] = raf.readInt();
            }
            
            //crear empleado
            empleado = new Empleado(id, nombre, apellidos, sueldo, trabajos);
        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }
        //devolver empleado
        return empleado;
    }

    /**
     * Actualiza los campos principales de un elmpeado en disco.
     * @param empleado El empleado a actualizar
     */
    static void editarEmpleado(Empleado empleado) {
        //calcular posicion del empleado en el disco
        long inicioEmpleado = 4 + 4 + ((empleado.getId() - 1) * Empleado.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //moverse hasta el inicio del empleado en el disco
            //escribir los campos
            raf.seek(inicioEmpleado);
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sb = new StringBuffer(empleado.getNombre());
            sb.setLength(Trabajo.limiteNombre);//15 caracteres máximo para el nombre
            String nombre = sb.toString();
            raf.writeChars(nombre);//nombre
            
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sba = new StringBuffer(empleado.getApellidos());
            sba.setLength(Empleado.limiteApellidos);//15 caracteres máximo para el nombre
            String apellidos = sba.toString();
            raf.writeChars(apellidos);//nombre
            raf.writeInt(empleado.getSueldo());

        } catch (FileNotFoundException ex) {
            msgError("Archivo no encontrado " + fichero.getAbsolutePath());
        } catch (IOException ex) {
            msgError("Error accediendo a " + fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                msgError("Error accediendo a " + fichero.getAbsolutePath());
            }
        }    
    }
    
      /**
     * Lanza una ventana de mensaje de error
     *
     * @param msg El mensaje a mostrar
     */
    private static void msgError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
