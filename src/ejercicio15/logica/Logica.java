/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio15.logica;

import ejercicio15.dto.Empleado;
import ejercicio15.dto.Trabajo;
import ejercicio15.gui.ventanas.VPrincipal15;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class Logica {
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
        OperacionesArchivo.agregarTrabajo(t);
    }

    public static void agregarEmpleado(Empleado e) {
        e.setId(empleados.size()+1);
        empleados.add(e);
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
        Logica.empleados = empleados;
    }

    public static void setTrabajos(ArrayList<Trabajo> trabajos) {
        Logica.trabajos = trabajos;
    }

    public static void resetDatosIniciales() {
        OperacionesArchivo.resetArchivos();
        empleados =  OperacionesArchivo.leerEmpleadosInicialesTXT();
        for (Empleado empleado : empleados) {
            OperacionesArchivo.agregarEmpleado(empleado);
        }

        trabajos =  OperacionesArchivo.leerTrabajosInicialesTXT();
        for (Trabajo trabajo : trabajos) {
                        OperacionesArchivo.agregarTrabajo(trabajo);
        }
        
    }
    
    
}//end LogicaEjercicio15
