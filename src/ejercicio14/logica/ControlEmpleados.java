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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;

/**
 * CONTROL de la aplicacion. Guarda los valores necesarios para mantener el programa
 * e interactua con ventanas y dialogos recogiendo y suministrando informacion
 * @author Jose Javier BO
 */
public class ControlEmpleados {

    //ATRIBUTOS
    //ruta del archivo binario
    public static String ruta = ".\\src\\ejercicio14\\recursos\\AleatoPersonas.dat";
    
    //referencia a la libreria de operciones. En general operaciones de disco
    private static OperacionesLib operaciones = new OperacionesLib();
    
    //numero de empleados actual
    public static int numeroEmpleados = 0;
    
    //referencia a la ventana principal
    public static VPrincipal ventanaPrincipal;
    
    //lista de empleados actuales
    public static List<Empleado> listaEmpleados;
    
    /**
     * Resetea el fiechero binario
     */
    public static void resetearFichero() {
        operaciones.resetearFichero();
    }

    /**
     * Lee los empleados del fichero
     */
    public static void leerFichero() {
        //recoger lista de empleados
        listaEmpleados = operaciones.leerEmpleados();
       if (listaEmpleados==null)
           listaEmpleados=new ArrayList<Empleado>();
       //mostrar dialogo con la tabla de empleados
        JDialog dialogoPersonas = new DListaEmpleados(ventanaPrincipal, true);
        dialogoPersonas.setLocationRelativeTo(null);
        dialogoPersonas.setVisible(true);
    }

    /**
     * Devuelve un empleado o null si no existe
     * @param id La id del empleado
     * @return  El empleado o null is no existe
     */
    public static Empleado getEmpleado(int id) {
        if (id>ControlEmpleados.numeroEmpleados+1)
            return null;
        else
          return operaciones.leerEmpleado(id);
    }

    /**
     * Guarda un empleado a disco
     * @param empleado Empleado a guardar. Empleado con id negativa supone añadir, si su id es positiva lo modifica
     */
   public static void guardarEmpleado(Empleado empleado){
       if (empleado.getId()<0){
           empleado.setId(numeroEmpleados+1);
           operaciones.insertarEmpleado(empleado);
       }else{
           operaciones.actualizarEmpleado(empleado);
       }
   }

        /**
         * INICIO DEL PROGRAMA
         * @param args 
         */
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
}
