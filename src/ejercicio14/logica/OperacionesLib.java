/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio14.logica;

import ejercicio14.dto.Empleado;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

/**
 * Libreria de operaciones del programa con el disco
 * 
 * @author Jose Javier BO
 */
public class OperacionesLib {



    /**
     * Inicializa el fichero binario creandolo si no existe
     */
    public void inicializarFichero() {
        File f = new File(ControlEmpleados.ruta);
            if (!f.exists())
                actulizarNumeroEmpleadosEndisco();
    }

    /**
     * Lee los empleados que hay en el archivo
     *
     * @return Lista con los empleados leidos o null si el fichero no existe
     */
    public List<Empleado> leerEmpleados() {
        
        //inicializacion de la lista devuelta
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        
        File fichero = new File(ControlEmpleados.ruta);
        //si el fichero no existe devuelve null
        if (!fichero.exists()) {
            return null;
        }

        //PREPARACIÓN LECTURA: streams y datos        
        //Clases necesarias para leer de datos binarios:
        FileInputStream fis = null;
        DataInputStream dis = null;
        int cantidadEmpleados = 0;
        try {
            fis = new FileInputStream(fichero);
            dis = new DataInputStream(fis);
            
            //leer la cantidad de empleados
            cantidadEmpleados = dis.readInt();
            
            //bucle leyendo empleados
            for (int i = 0; i < cantidadEmpleados; i++) {
                
                //recoger datos de empleado
                int id = dis.readInt();
                char[] nombreCh = new char[15];
                for (int j = 0; j < 15; j++) {
                    nombreCh[j] = dis.readChar();
                }
                int departamento = dis.readInt();
                double sueldo = dis.readDouble();
                String nombre = new String(nombreCh);
                
                //agregar empleado leido a la lista
                lista.add(new Empleado(id, nombre, departamento, sueldo));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EOFException ex) {
        } catch (IOException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //actualizar el valor de empleados leido
        ControlEmpleados.numeroEmpleados = cantidadEmpleados;
        
        //devolver la lista
        return lista;
    }

    /**
     * Resetea el fichero de datos agregando datos iniciales
     */
    public void resetearFichero() {
        
        File fichero = new File(ControlEmpleados.ruta);
        
        //borrar si existe
        if (fichero.exists()) 
               fichero.delete();
        
        //establecer el numero de empleados a 0
        ControlEmpleados.numeroEmpleados = 0;
        actulizarNumeroEmpleadosEndisco();
        
        //datos iniciales
        String nombres[] = {"Ana", "Luis", "Mario", "Pepe", "Alejandro", "Sara"};
        int departamentos[] = {10, 20, 20, 10, 30, 10};
        double sueldos[] = {1500, 1800.75, 2200, 3000.0, 1500.56, 2400.60};
        int id = 0;
        int nEmpleados = nombres.length;
        //introducir empleados iniciales
        for (id = 0; id < nEmpleados; id++) {
            Empleado e = new Empleado((id + 1), nombres[id], departamentos[id], sueldos[id]);
            insertarEmpleado(e);
        }
    }

    /**
     * Actualiza la cantidad de empleados que hay en disco escribiendo el numero
     * al inicio del archivo
     */
    public void actulizarNumeroEmpleadosEndisco() {
        
        File fichero = new File(ControlEmpleados.ruta);
        RandomAccessFile raf = null;
        
        try {
        
            raf = new RandomAccessFile(fichero, "rw"); 
            raf.seek(0);
            raf.writeInt(ControlEmpleados.numeroEmpleados);
        
        } catch (FileNotFoundException ex) {
            mostrarError("Archivo no encontrado "+fichero.getAbsolutePath());
        } catch (IOException ex) {
            mostrarError( "Error escribiendo "+fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Lee un empleado de disco
     * @param idBusqueda Id del empleado a buscar
     * 
     * @return el empleado leido o null si no existe
     */
    public Empleado leerEmpleado(int idBusqueda) {
        Empleado salida = null;
        RandomAccessFile raf = null;
        File fichero = new File(ControlEmpleados.ruta);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "r");//Lectura y Escritura
            //ir a la posicion adecuada
            //cada persona ocupa 46 bytes 4(id) + 4(deparatamento)+ 30 nombre + 8 sueldo(total 46)
            raf.seek(4 + ((idBusqueda - 1) * 46));//4 del numero de personas + 4 por la id para no sobreescribirla mas (46*id)

            int id = raf.readInt();
            String nombre = "";
            for (int i = 0; i < 15; i++) {
                nombre += raf.readChar();
            }
            //quitar caracteres vacios
            nombre = nombre.replace("\0", "");
            int departamento = raf.readInt();
            double sueldo = raf.readDouble();

            salida = new Empleado(id, nombre, departamento, sueldo);
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                return null;
            }
        }
        //devolver empleado leido 
        return salida;
    }

    /**
     * Inserta un nuevo empleado en el archivo
     *
     * @param empleado Empleado a insertar
     */
    public void insertarEmpleado(Empleado empleado) {
        RandomAccessFile raf = null;
        File fichero = new File(ControlEmpleados.ruta);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
             //ir al final
            raf.seek(raf.length());
            //escribir id empleado
            raf.writeInt(empleado.getId());
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sb = null;
            //Limitamos el tamaño del apellido:
            sb = new StringBuffer(empleado.getNombre());
            sb.setLength(15);//15 caracteres máximo para el nombre
            String nombre = sb.toString();
            raf.writeChars(nombre);//Nombre Empleado
            raf.writeInt(empleado.getDepartamento());//Departamento Empleado
            raf.writeDouble(empleado.getSueldo());//Sueldo Empleado
            //actualizar el numero de empleados
            ControlEmpleados.numeroEmpleados++;
        } catch (FileNotFoundException ex) {
            mostrarError( "Archivo no encontrado "+fichero.getAbsolutePath());
        } catch (IOException ex) {
            mostrarError("Error escribiendo "+fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        actulizarNumeroEmpleadosEndisco();
    }

    /**
     * Actualiza los datos de un empleado en el archivo
     *
     * @param empleado Empleado a actualizar
     */
    public void actualizarEmpleado(Empleado empleado) {
        RandomAccessFile raf = null;
        File fichero = new File(ControlEmpleados.ruta);
        try {
            //acceso a archivo
            raf = new RandomAccessFile(fichero, "rw");//Lectura y Escritura
            //ir a la posicion adecuada
            //cada persona ocupa 46 bytes 4(id) + 4(deparatamento)+ 30 nombre + 8 sueldo(total 46)
            raf.seek(4 + 4 + ((empleado.getId() - 1) * 46));//4 del numero de personas + 4 por la id para no sobreescribirla mas (46*id)
            //Usamos clase StringBuffer para controlar tamaño de los String
            StringBuffer sb = null;
            //Limitamos el tamaño del nombre:
            sb = new StringBuffer(empleado.getNombre());
            sb.setLength(15);//15 caracteres máximo para el nombre
            raf.writeChars(sb.toString());//Nombre Empleado
            raf.writeInt(empleado.getDepartamento());//Departamento Empleado
            raf.writeDouble(empleado.getSueldo());//Sueldo Empleado
        } catch (FileNotFoundException ex) {
            mostrarError("Archivo no encontrado "+fichero.getAbsolutePath());
        } catch (IOException ex) {
            mostrarError( "Error escribiendo "+fichero.getAbsolutePath());
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    /**
     * Muestra un joptionpane pidiendo un dato al usuario
     * @param msg Mensaje a mostrar
     * @param titulo Titulo
     * @return el valor introducido
     */
    public static Object pedirDato(String msg, String titulo) {
        return JOptionPane.showInputDialog(null, msg, titulo, JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de error
     * @param msg 
     */
    public static void mostrarError(String msg) {
        JOptionPane.showMessageDialog(null, "Error", msg, JDesktopPane.ERROR);
    }

}
