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
 *
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

    public static ArrayList<Trabajo> leerTrabajosInicialesTXT() {

        //Lista de los leidos
        ArrayList<Trabajo> trabajos = new ArrayList<Trabajo>();

        //PREAPARAR READERS
        File f = new File(Control.rutaTrabajosIniciales);
        FileReader fr = null;
        BufferedReader bis = null;

        try {
            fr = new FileReader(f);
            bis = new BufferedReader(fr);
            //LEER LINEAS CON LA SECUENCIA DE TRABAJOS
            String idSt = "";
            while ((idSt = bis.readLine()) != null) {
                int id = Integer.parseInt(idSt);
                String nombre = bis.readLine();
                Long fecha = (new SimpleDateFormat("dd/MM/yyy").parse(bis.readLine())).getTime();
                int[] empleados = new int[Trabajo.limiteEmpleados];
                for (int i = 0; i < Trabajo.limiteEmpleados; i++) {
                    empleados[i] = Integer.parseInt(bis.readLine());
                }
                //GENERAR EL ALUMNO CON LOS DATOS RECOGIDOS
                trabajos.add(new Trabajo(id, nombre, fecha, empleados));
            }
        } catch (FileNotFoundException ex) {
            //TODO
        } catch (IOException | ParseException ex) {
            //TODO
        } catch (NumberFormatException ex) {
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                //TODO
            }
        }

        //DEVOLVER LISTA DE ALUMNOS
        return trabajos;

    }

    public static ArrayList<Empleado> leerEmpleadosInicialesTXT() {

        //Lista de los leidos
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();

        //PREAPARAR READERS
        File f = new File(Control.rutaEmpleadosIniciales);
        FileReader fr = null;
        BufferedReader bis = null;

        try {
            fr = new FileReader(f);
            bis = new BufferedReader(fr);
            //LEER LINEAS CON LA SECUENCIA DE EMPLEADOS
            String idSt = "";
            while ((idSt = bis.readLine()) != null) {
                int id = Integer.parseInt(idSt);
                String nombre = bis.readLine();
                String apellidos = bis.readLine();
                int sueldo = Integer.parseInt(bis.readLine());
                int[] trabajos = new int[Empleado.limiteTrabajos];
                for (int i = 0; i < Empleado.limiteTrabajos; i++) {
                    trabajos[i] = Integer.parseInt(bis.readLine());
                }
                //GENERAR EL EMPPLEADOS CON LOS DATOS RECOGIDOS
                empleados.add(new Empleado(id, nombre, apellidos, sueldo, trabajos));
            }
        } catch (FileNotFoundException ex) {
            //TODO
        } catch (NumberFormatException ex) {
        } catch (IOException ex) {
            Logger.getLogger(OperacionesArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                //TODO
            }
        }

        //DEVOLVER LISTA DE ALUMNOS
        return empleados;

    }

    /**
     * Actualiza el numero de empleados en disco
     */
    public static void inicializarNumeroEmpleadosEndisco() {
        File fichero = new File(Control.rutaEmpleados);
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            raf.seek(0);
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
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            raf.seek(0);
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
     * Cargar empleados del disco
     */
    static ArrayList<Empleado> cargarEmpleados() {
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        File fichero = new File(Control.rutaEmpleados);

        //3-PREPARACIÓN LECTURA: streams y datos        
        //Clases necesarias para envío de datos binarios:
        FileInputStream fis = null;
        DataInputStream dis = null;
        int cantidadEmpleados = 0;
        try {
            fis = new FileInputStream(fichero);
            dis = new DataInputStream(fis);
            cantidadEmpleados = dis.readInt();
            for (int i = 0; i < cantidadEmpleados; i++) {
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
                int[] trabajos = new int[Empleado.limiteTrabajos];
                for (int j = 0; j < Empleado.limiteTrabajos; j++) {
                    trabajos[j] = dis.readInt();
                }
                lista.add(new Empleado(id, nombre, apellidos, sueldo, trabajos));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ejercicio14.logica.OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EOFException ex) {
        } catch (IOException ex) {
            Logger.getLogger(ejercicio14.logica.OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ejercicio14.logica.OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lista;

    }

    /**
     * Cargar trabajos del disco
     */
    static ArrayList<Trabajo> cargarTrabajos() {
        ArrayList<Trabajo> lista = new ArrayList<Trabajo>();
        File fichero = new File(Control.rutaTrabajos);

        //3-PREPARACIÓN LECTURA: streams y datos        
        //Clases necesarias para envío de datos binarios:
        FileInputStream fis = null;
        DataInputStream dis = null;
        int cantidadTrabajos = 0;
        try {
            fis = new FileInputStream(fichero);
            dis = new DataInputStream(fis);
            cantidadTrabajos = dis.readInt();
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
                lista.add(new Trabajo(id, nombre, fecha, empleados));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ejercicio14.logica.OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EOFException ex) {
        } catch (IOException ex) {
            Logger.getLogger(ejercicio14.logica.OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ejercicio14.logica.OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lista;

    }

    static void agregarTrabajo(Trabajo t) {

        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            long lo = raf.length();
            //actualizar numero de trabajos
            raf.seek(0);
            raf.writeInt(Control.getTrabajos().size());
            //ir al final
            raf.seek(raf.length());

            raf.writeInt(t.getId());//id trabajo
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sb = null;
            //Limitamos el tamaño del nombre:
            sb = new StringBuffer(t.getNombre());
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
     * Lanza una ventana de mensaje de error
     *
     * @param msg El mensaje a mostrar
     */
    private static void msgError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static void agregarEmpleado(Empleado e) {

        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            long lo = raf.length();
            //actualizar numero de trabajos
            raf.seek(0);
            raf.writeInt(Control.getEmpleados().size());
            //ir al final
            raf.seek(raf.length());

            raf.writeInt(e.getId());//id empleado
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sbn = null;
            //Limitamos el tamaño del nombre:
            sbn = new StringBuffer(e.getNombre());
            sbn.setLength(Empleado.limiteNombre);//limite de longitud nombre
            String nombre = sbn.toString();
            raf.writeChars(nombre);//nombre
            //Limitamos el tamaño de apellidos:
            StringBuffer sba = null;
            sba = new StringBuffer(e.getApellidos());
            sba.setLength(Empleado.limiteApellidos);//limite de longitud nombre
            String apellidos = sba.toString();
            raf.writeChars(apellidos);//nombre
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

    static void borrarTrabajo(int idTrabajo) {
        long inicioTrabajo = 4 + ((idTrabajo - 1) * Trabajo.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar numero de trabajos
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

    static void actualizarTrabajosDeEmpleado(Empleado e) {
        int id = Math.abs(e.getId());
        long inicioTrabajos = 4 + ((id - 1) * Empleado.longitudBytes) + Empleado.longitudBytesHastaTrabajos;
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar numero de trabajos
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

    static void borrarEmpleado(int idEmpleado) {
        long inicioEmpleado = 4 + ((idEmpleado - 1) * Empleado.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar numero de trabajos
            raf.seek(inicioEmpleado);
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

    static void actualizarEmpleadosDeTrabajo(Trabajo t) {

        int id = Math.abs(t.getId());
        long inicioEmpleados = 4 + ((id - 1) * Empleado.longitudBytes) + Empleado.longitudBytesHastaTrabajos;
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar numero de trabajos
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

    static public Trabajo getTrabajo(int idTrabajo) {

        Trabajo trabajo = null;

        long inicioTrabajo = 4 + ((idTrabajo - 1) * Trabajo.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //comprobar que el trabajo existe 
            if (inicioTrabajo >= raf.length()) {
                return null;
            }
            //actualizar numero de trabajos
            raf.seek(inicioTrabajo);

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
        return trabajo;
    }

    static void editarTrabajo(Trabajo trabajo) {
        long inicioTrabajo = 4 + 4 + ((trabajo.getId() - 1) * Trabajo.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar numero de trabajos
            raf.seek(inicioTrabajo);
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sb = null;
            //Limitamos el tamaño del nombre:
            sb = new StringBuffer(trabajo.getNombre());
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

    public static Empleado getEmpleado(int idEmpleado) {
        Empleado empleado = null;

        long inicioEmpleado = 4 + ((idEmpleado - 1) * Empleado.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaEmpleados);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //comprobar que el trabajo existe 
            if (inicioEmpleado >= raf.length()) {
                return null;
            }
            //mover hasta inicio de empleado
            raf.seek(inicioEmpleado);

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
        return empleado;
    }

    static void editarEmpleado(Empleado empleado) {
        long inicioEmpleado = 4 + 4 + ((empleado.getId() - 1) * Empleado.longitudBytes);
        RandomAccessFile raf = null;
        File fichero = new File(Control.rutaTrabajos);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //actualizar numero de trabajos
            raf.seek(inicioEmpleado);
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sb = null;
            //Limitamos el tamaño del nombre:
            sb = new StringBuffer(empleado.getNombre());
            sb.setLength(Trabajo.limiteNombre);//15 caracteres máximo para el nombre
            String nombre = sb.toString();
            raf.writeChars(nombre);//nombre
            
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sba = null;
            //Limitamos el tamaño del nombre:
            sba = new StringBuffer(empleado.getApellidos());
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
        }    }
}
