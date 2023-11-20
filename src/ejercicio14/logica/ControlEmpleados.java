/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio14.logica;

import ejercicio14.dto.Empleado;
import ejercicio14.gui.dialogos.DListaEmpleados;
import ejercicio14.gui.ventanas.VPrincipal;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

/**
 *
 * @author Jose Javier BO
 */
public class ControlEmpleados {


    public static String ruta = ".\\src\\ejercicio14\\recursos\\AleatoPersonas.dat";
    private static OperacionesLib operaciones = new OperacionesLib();
    public static int numeroEmpleados = 0;
    public static VPrincipal ventanaPrincipal;
    public static List<Empleado> listaPersonas;
    
    
    public static void resetearFichero() {
        operaciones.resetearFichero();
    }

 
    public static void main(String[] args) {
     
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
            java.util.logging.Logger.getLogger(VPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(() ->{
                ventanaPrincipal = new VPrincipal();
                ventanaPrincipal.setLocationRelativeTo(null);
                ventanaPrincipal.setVisible(true);
            }
        );

        //inicializar fichero
        operaciones.inicializarFichero();
    }

    public static void leerFichero() {
        listaPersonas = operaciones.leerEmpleados();
       if (listaPersonas==null)
           listaPersonas=new ArrayList<Empleado>();
        JDialog dialogoPersonas = new DListaEmpleados(ventanaPrincipal, true);
        dialogoPersonas.setLocationRelativeTo(null);
        dialogoPersonas.setVisible(true);
    }

    /**
     * Devuelve un empleado  null si no existe
     * @param id La id del empleado
     * @return  El empleado o null is no existe
     */
    public static Empleado getEmpleado(int id) {
        if (id>ControlEmpleados.numeroEmpleados+1)
            return null;
        else
          return operaciones.leerEmpleado(id);
    }

   public static void guardarEmpleado(Empleado e){
       if (e.getId()<0){
           e.setId(numeroEmpleados+1);
           operaciones.insertarEmpleado(e);
       }else{
           operaciones.actualizarEmpleado(e);
       }
       
       
   }
}