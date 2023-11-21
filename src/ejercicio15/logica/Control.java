/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio15.logica;

import ejercicio15.dto.Empleado;
import ejercicio15.dto.Trabajo;
import ejercicio15.dto.ResultadoOperacion;
import ejercicio15.gui.ventanas.VPrincipal15;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class Control {
        public static VPrincipal15 ventanaPrincipal;
        public static String rutaEmpleados = ".\\src\\ejercicio15\\recursos\\Empleados.dat";
        public static String rutaTrabajos = ".\\src\\ejercicio15\\recursos\\Trabajos.dat";
        public static String rutaEmpleadosIniciales = ".\\src\\ejercicio15\\recursos\\EmpleadosIni.txt";
        public static String rutaTrabajosIniciales = ".\\src\\ejercicio15\\recursos\\TrabajosIni.txt";
        private static ArrayList<Empleado> empleados=new ArrayList<Empleado>();
        private static ArrayList<Empleado> empleadosActivos=new ArrayList<Empleado>();
        private static ArrayList<Empleado> empleadosBorrados=new ArrayList<Empleado>();
        private static ArrayList<Trabajo> trabajos=new ArrayList<Trabajo>();
        private static ArrayList<Trabajo> trabajosActivos=new ArrayList<Trabajo>();
        private static ArrayList<Trabajo> trabajosBorrados=new ArrayList<Trabajo>();
        
        public static void inicializarDatos(){
            OperacionesArchivo.inicializarArchivos();
            trabajos=OperacionesArchivo.cargarTrabajos();
            for (Trabajo trabajo : trabajos) {
                if (trabajo.getId()>0)
                    trabajosActivos.add(trabajo);
                            else 
                    trabajosBorrados.add(trabajo);
            }

            empleados=OperacionesArchivo.cargarEmpleados();
            for (Empleado empleado : empleados) {
                if (empleado.getId()>0)
                    empleadosActivos.add(empleado);
                    else 
                    empleadosBorrados.add(empleado);
            }
        }
        
           
        /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //inicializar datos y archivos
        inicializarDatos();
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VPrincipal15.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VPrincipal15.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VPrincipal15.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VPrincipal15.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ventanaPrincipal = new VPrincipal15();
                ventanaPrincipal.setLocationRelativeTo(null);
                ventanaPrincipal.setVisible(true);
            }
        });
    }

    public static void agregarTrabajo(Trabajo t) {
        t.setId(trabajos.size()+1);
        trabajos.add(t);
        trabajosActivos.add(t);
        OperacionesArchivo.agregarTrabajo(t);
    }

    public static void agregarEmpleado(Empleado e) {
        e.setId(empleados.size()+1);
        empleados.add(e);
        empleadosActivos.add(e);
        OperacionesArchivo.agregarEmpleado(e);    }

    public static List<Trabajo> getTrabajosActivos() {
        return trabajosActivos;
    }

    public static List<Empleado> getEmpleadosActivos() {
        return empleadosActivos;
    }

    public static List<Empleado> getEmpleadosBorrados() {
        return empleadosBorrados;
    }

    public static List<Trabajo> getTrabajosBorrados() {
        return trabajosBorrados;
    }

    public static List<Empleado> getEmpleados() {
        return empleados;
    }

    public static List<Trabajo> getTrabajos() {
        return trabajos;
    }

    public static void setEmpleados(ArrayList<Empleado> empleados) {
        Control.empleados = empleados;
    }

    public static void setTrabajos(ArrayList<Trabajo> trabajos) {
        Control.trabajos = trabajos;
    }

    public static void cargarDatosTxt() {
        empleados.clear();
        empleadosActivos.clear();
        empleadosBorrados.clear();
        trabajos.clear();
        trabajosActivos.clear();
        trabajosBorrados.clear();
        OperacionesArchivo.resetArchivos();
        ArrayList<Empleado> empleadosLeidos =  OperacionesArchivo.leerEmpleadosInicialesTXT();
        for (Empleado empleado : empleadosLeidos) {
            empleados.add(empleado);
            empleadosActivos.add(empleado);
            OperacionesArchivo.agregarEmpleado(empleado);
        }

        ArrayList<Trabajo> trabajosLeidos =  OperacionesArchivo.leerTrabajosInicialesTXT();
        for (Trabajo trabajo : trabajosLeidos) {
                        trabajos.add(trabajo);
                        trabajosActivos.add(trabajo);
                        OperacionesArchivo.agregarTrabajo(trabajo);
        }
    }
    
    
    /**
     * Elimina un trabajo
     * @param idTrabajo Id del trabajo a eliminar
     * @return  El resultado de la operacion
     */
    public static ResultadoOperacion eliminarTrabajo(int idTrabajo){
        //ver si el trabajo existe
        if (idTrabajo>trabajos.size()||idTrabajo<1)
            return new ResultadoOperacion(false, "El trabajo "+idTrabajo+" no existe");
        
        //ver si el trabajo ya esta borrado
        Trabajo t = trabajos.get(idTrabajo-1);
        if (t.getId()<0)
             return new ResultadoOperacion(false, "El trabajo "+idTrabajo+" ya estaba eliminado");
        
        //si hemos llegado aquíi lo borramos
        t.setId(-t.getId());
        trabajosActivos.remove(t);
        trabajosBorrados.add(t);
        OperacionesArchivo.borrarTrabajo(idTrabajo);
        
        //actualizar empleados que tenian el trabajo asignado
        for (int idEmpleado : t.getEmpleados()){
            if (idEmpleado!=0)
            desactivarTrabajoEnEmpleado(idEmpleado,idTrabajo);
        }
        
        return new ResultadoOperacion(true, "");
    }

    /**
     * Eliminar la asignacion de un trabajo a un empleado
     * @param idEmpleado Id del empleado
     * @param idTrabajo Id del trabajo
     */
    private static void desactivarTrabajoEnEmpleado(int idEmpleado, int idTrabajo) {
        System.out.println(idEmpleado);
        Empleado e = empleados.get(idEmpleado-1);
        int[] trabajos=e.getTrabajos();
        for (int i = 0; i<trabajos.length;i++){
            if (trabajos[i]==idTrabajo)
                trabajos[i]=0;
        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarTrabajosDeEmpleado(e);
    }

    public static ResultadoOperacion eliminarEmpleado(int idEmpleado) {

        //ver si el trabajo existe
        if (idEmpleado>empleados.size()||idEmpleado<1)
            return new ResultadoOperacion(false, "El empleado "+idEmpleado+" no existe");
        
        //ver si el trabajo ya esta borrado
        Empleado e = empleados.get(idEmpleado-1);
        if (e.getId()<0)
             return new ResultadoOperacion(false, "El empleado "+idEmpleado+" ya estaba eliminado");
        
        //si hemos llegado aquíi lo borramos
        e.setId(-e.getId());
        empleadosActivos.remove(e);
        empleadosBorrados.add(e);
        OperacionesArchivo.borrarEmpleado(idEmpleado);
        
        //actualizar empleados que tenian el trabajo asignado
        for (int idTrabajo : e.getTrabajos()){
            if (idTrabajo!=0)
            desactivarEmpleadoEnTrabajo(idTrabajo,idEmpleado);
        }
        
        return new ResultadoOperacion(true, "");    
    }
        /**
     * Eliminar la asignacion de un trabajo a un empleado
     * @param idTtrabajo Id del trabajo
     * @param idEmpleado Id del empleado
     */
    private static void desactivarEmpleadoEnTrabajo(int idTtrabajo, int idEmpleado) {
        Trabajo t = trabajos.get(idTtrabajo-1);
        int[] empleados=t.getEmpleados();
        for (int i = 0; i<empleados.length;i++){
            if (empleados[i]==idEmpleado)
                empleados[i]=0;
        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarEmpleadosDeTrabajo(t);
    }

    public static Trabajo getTrabajo(int id) {
        Trabajo trabajo= OperacionesArchivo.getTrabajo(id);
        if (trabajo==null||trabajo.getId()<1)
            return null;
        else 
            return trabajo;
    }

    public static void editarTrabajo(Trabajo t) {
        Trabajo trabajo = trabajos.get(t.getId()-1);
        trabajo.setNombre(t.getNombre());
        trabajo.setFecha(t.getFecha());
        OperacionesArchivo.editarTrabajo(trabajo);
    }

    public static Empleado getEmpleado(int id) {
        Empleado empleado= OperacionesArchivo.getEmpleado(id);
        if (empleado==null||empleado.getId()<1)
            return null;
        else 
            return empleado;    }

    public static void editarEmpleado(Empleado e) {
        Empleado empleado = empleados.get(e.getId()-1);
        empleado.setNombre(e.getNombre());
        empleado.setApellidos(e.getApellidos());
        empleado.setSueldo(e.getSueldo());
        OperacionesArchivo.editarEmpleado(empleado);    
    }
 
}//end LogicaEjercicio15
