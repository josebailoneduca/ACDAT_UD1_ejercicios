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
 *
 * @author Jose Javier BO
 */
public class OperacionesLib {



    public static void mostrarError(String msg) {
        JOptionPane.showMessageDialog(null, "Error", msg, JDesktopPane.ERROR);
    }

    public static Object pedirDato(String msg, String titulo) {
        return JOptionPane.showInputDialog(null, msg, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    static void inicializarFichero() {
        ControlEmpleados.fichero = new File(ControlEmpleados.ruta);
        List<Empleado> lista = leerEmpleados();
        if (lista==null)
            return;
    }

    /**
     * Lee los empleados que hay en el archivo
     * @return 
     */
    static List<Empleado> leerEmpleados() {
        ArrayList<Empleado> lista = new ArrayList<Empleado>();

        //si el fichero no existe devuelve null
        if(!ControlEmpleados.fichero.exists())
            return null;
              
        //3-PREPARACI�N LECTURA: streams y datos        
        //Clases necesarias para env�o de datos binarios:
        FileInputStream fis;
        DataInputStream dis =null;
        int cantidadEmpleados=0;
        try {
            fis = new FileInputStream(ControlEmpleados.fichero);
            dis = new DataInputStream(fis);
            cantidadEmpleados = dis.readInt();
            for (int i=0;i<cantidadEmpleados;i++){
                int id = dis.readInt();
                char[] nombreCh = new char[15];
                for (int j=0;j<15;j++){
                    nombreCh[j]=dis.readChar();
                }
               int departamento=dis.readInt();
               double sueldo=dis.readDouble();
               String nombre= new String(nombreCh);
               lista.add(new Empleado(id, nombre, departamento, sueldo));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EOFException ex) {
        } catch (IOException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        //actualizar el valor de empleados leido
        ControlEmpleados.numeroEmpleados=cantidadEmpleados;
        
        return lista;
    }

 

    /**
     * Resetea el fichero de datos
     */
    public void resetearFichero() {
        File f = new File(ControlEmpleados.ruta);
        if (f.exists()) {
            f.delete();
        }
        ControlEmpleados.numeroEmpleados=0;
        actulizarNumeroEmpleadosEndisco();
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

    public void actulizarNumeroEmpleadosEndisco() {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(ControlEmpleados.fichero, "rw");//Lectura y Escritura
            raf.seek(0);
            raf.writeInt(ControlEmpleados.numeroEmpleados);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
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
     * Inserta una nueva perona en el archivo
     * @param empleado 
     */
    private void insertarEmpleado(Empleado empleado) {
        RandomAccessFile raf = null;
        try {
            //acceso a archivo
            raf = new RandomAccessFile(ControlEmpleados.fichero, "rw");//Lectura y Escritura
            //ir al final
            raf.seek(raf.length());
            
            raf.writeInt(empleado.getId());//id Empleado
            //Usamos clase StringBuffer para controlar tama�o de los String
            StringBuffer sb = null;
            //Limitamos el tama�o del apellido:
            sb = new StringBuffer(empleado.getNombre());
            sb.setLength(15);//15 caracteres m�ximo para el nombre
            String nombre = sb.toString();
            raf.writeChars(nombre);//Nombre Empleado
            raf.writeInt(empleado.getDepartamento());//Departamento Empleado
            raf.writeDouble(empleado.getSueldo());//Sueldo Empleado
            //actualizar el numero de empleados
            ControlEmpleados.numeroEmpleados++;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
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
     * @param empleado 
     */
    private void actualizarEmpleado(Empleado empleado) {
        RandomAccessFile raf = null;
        try {
            //acceso a archivo
            raf = new RandomAccessFile(ControlEmpleados.fichero, "rw");//Lectura y Escritura
            //ir a la posicion adecuada
            //cada persona ocupa 46 bytes 4(id) + 4(deparatamento)+ 30 nombre + 8 sueldo(total 46)
            raf.seek(4+4+((empleado.getId()-1)*46));//4 del numero de personas + 4 por la id para no sobreescribirla mas (46*id)
            //Usamos clase StringBuffer para controlar tama�o de los String
            StringBuffer sb = null;
            //Limitamos el tama�o del apellido:
            sb = new StringBuffer(empleado.getNombre());
            sb.setLength(15);//15 caracteres m�ximo para el nombre
            raf.writeChars(sb.toString());//Nombre Empleado
            raf.writeInt(empleado.getDepartamento());//Departamento Empleado
            raf.writeDouble(empleado.getSueldo());//Sueldo Empleado
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OperacionesLib.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
