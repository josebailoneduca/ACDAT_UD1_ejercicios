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
public class Control {

    public static VPrincipal15 ventanaPrincipal;
    public static String rutaEmpleados = ".\\src\\ejercicio15\\recursos\\Empleados.dat";
    public static String rutaTrabajos = ".\\src\\ejercicio15\\recursos\\Trabajos.dat";
    public static String rutaEmpleadosIniciales = ".\\src\\ejercicio15\\recursos\\EmpleadosIni.txt";
    public static String rutaTrabajosIniciales = ".\\src\\ejercicio15\\recursos\\TrabajosIni.txt";
    private static ArrayList<Empleado> empleados = new ArrayList<Empleado>();
    private static ArrayList<Empleado> empleadosActivos = new ArrayList<Empleado>();
    private static ArrayList<Empleado> empleadosBorrados = new ArrayList<Empleado>();
    private static ArrayList<Trabajo> trabajos = new ArrayList<Trabajo>();
    private static ArrayList<Trabajo> trabajosActivos = new ArrayList<Trabajo>();
    private static ArrayList<Trabajo> trabajosBorrados = new ArrayList<Trabajo>();

    public static void borrarTodo() {
        empleados.clear();
        empleadosActivos.clear();
        empleadosBorrados.clear();
        trabajos.clear();
        trabajosActivos.clear();
        trabajosBorrados.clear();
        OperacionesArchivo.resetArchivos();
    }

    public static void inicializarDatos() {
        OperacionesArchivo.inicializarArchivos();
        trabajos = OperacionesArchivo.cargarTrabajos();
        for (Trabajo trabajo : trabajos) {
            if (trabajo.getId() > 0) {
                trabajosActivos.add(trabajo);
            } else {
                trabajosBorrados.add(trabajo);
            }
        }

        empleados = OperacionesArchivo.cargarEmpleados();
        for (Empleado empleado : empleados) {
            if (empleado.getId() > 0) {
                empleadosActivos.add(empleado);
            } else {
                empleadosBorrados.add(empleado);
            }
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
        t.setId(trabajos.size() + 1);
        trabajos.add(t);
        trabajosActivos.add(t);
        OperacionesArchivo.agregarTrabajo(t);
    }

    public static void agregarEmpleado(Empleado e) {
        e.setId(empleados.size() + 1);
        empleados.add(e);
        empleadosActivos.add(e);
        OperacionesArchivo.agregarEmpleado(e);
    }

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
        borrarTodo();
        ArrayList<Empleado> empleadosLeidos = OperacionesArchivo.leerEmpleadosInicialesTXT();
        for (Empleado empleado : empleadosLeidos) {
            empleados.add(empleado);
            empleadosActivos.add(empleado);
            OperacionesArchivo.agregarEmpleado(empleado);
        }

        ArrayList<Trabajo> trabajosLeidos = OperacionesArchivo.leerTrabajosInicialesTXT();
        for (Trabajo trabajo : trabajosLeidos) {
            trabajos.add(trabajo);
            trabajosActivos.add(trabajo);
            OperacionesArchivo.agregarTrabajo(trabajo);
        }
    }

    /**
     * Elimina un trabajo
     *
     * @param idTrabajo Id del trabajo a eliminar
     * @return El resultado de la operacion
     */
    public static boolean eliminarTrabajo(int idTrabajo) {
        //ver si el trabajo existe
        if (idTrabajo > trabajos.size() || idTrabajo < 1) {
            return false;
        }

        //ver si el trabajo ya esta borrado
        Trabajo t = trabajos.get(idTrabajo - 1);
        if (t.getId() < 0) {
            return false;
        }

        //si hemos llegado aquíi lo borramos
        t.setId(-t.getId());
        trabajosActivos.remove(t);
        trabajosBorrados.add(t);
        OperacionesArchivo.borrarTrabajo(idTrabajo);

        //actualizar empleados que tenian el trabajo asignado
        for (int idEmpleado : t.getEmpleados()) {
            if (idEmpleado != 0) {
                desasignarTrabajoEnEmpleado(idEmpleado, idTrabajo);
            }
        }

        return true;
    }

    /**
     * Eliminar la asignacion de un trabajo a un empleado
     *
     * @param idEmpleado Id del empleado
     * @param idTrabajo Id del trabajo
     */
    public static void desasignarTrabajoEnEmpleado(int idEmpleado, int idTrabajo) {
        Empleado e = empleados.get(idEmpleado - 1);
        int[] trabajos = e.getTrabajos();
        for (int i = 0; i < trabajos.length; i++) {
            if (trabajos[i] == idTrabajo) {
                trabajos[i] = 0;
            }
        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarTrabajosDeEmpleado(e);
    }

    public static Boolean eliminarEmpleado(int idEmpleado) {

        //ver si el trabajo existe
        if (idEmpleado > empleados.size() || idEmpleado < 1) {
            return false;
        }

        //ver si el trabajo ya esta borrado
        Empleado e = empleados.get(idEmpleado - 1);
        if (e.getId() < 0) {
            return false;
        }

        //si hemos llegado aquíi lo borramos
        e.setId(-e.getId());
        empleadosActivos.remove(e);
        empleadosBorrados.add(e);
        OperacionesArchivo.borrarEmpleado(idEmpleado);

        //actualizar empleados que tenian el trabajo asignado
        for (int idTrabajo : e.getTrabajos()) {
            if (idTrabajo != 0) {
                desasignarEmpleadoEnTrabajo(idTrabajo, idEmpleado);
            }
        }

        return true;
    }

    /**
     * Eliminar la asignacion de un trabajo a un empleado
     *
     * @param idTrabajo Id del trabajo
     * @param idEmpleado Id del empleado
     */
    public static void desasignarEmpleadoEnTrabajo(int idTrabajo, int idEmpleado) {
        Trabajo t = trabajos.get(idTrabajo - 1);
        int[] empleados = t.getEmpleados();
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] == idEmpleado) {
                empleados[i] = 0;
            }
        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarEmpleadosDeTrabajo(t);
    }

    public static Trabajo getTrabajo(int id) {
        Trabajo trabajo = OperacionesArchivo.getTrabajo(id);
        if (trabajo == null || trabajo.getId() < 1) {
            return null;
        } else {
            return trabajo;
        }
    }

    public static void editarTrabajo(Trabajo t) {
        Trabajo trabajo = trabajos.get(t.getId() - 1);
        trabajo.setNombre(t.getNombre());
        trabajo.setFecha(t.getFecha());
        OperacionesArchivo.editarTrabajo(trabajo);
    }

    public static Empleado getEmpleado(int id) {
        Empleado empleado = OperacionesArchivo.getEmpleado(id);
        if (empleado == null || empleado.getId() < 1) {
            return null;
        } else {
            return empleado;
        }
    }

    public static void editarEmpleado(Empleado e) {
        Empleado empleado = empleados.get(e.getId() - 1);
        empleado.setNombre(e.getNombre());
        empleado.setApellidos(e.getApellidos());
        empleado.setSueldo(e.getSueldo());
        OperacionesArchivo.editarEmpleado(empleado);
    }

    public static ArrayList<Empleado> getEmpleadosEnTrabajo(Trabajo trabajo) {
        ArrayList<Empleado> seleccion = new ArrayList<Empleado>();
        for (int idEmp : trabajo.getEmpleados()) {
            if (idEmp != 0) {
                seleccion.add(OperacionesArchivo.getEmpleado(idEmp));
            }
        }
        return seleccion;
    }

    public static ArrayList<Empleado> getEmpleadosFueraDeTrabajo(Trabajo trabajo) {
        ArrayList<Empleado> seleccion = new ArrayList<Empleado>();
        for (Empleado empleado : empleados) {
            int idEmp = empleado.getId();
            if (idEmp > 0 && empleado.nTrabajosAsignados() < Empleado.limiteTrabajos) {
                boolean existe = false;
                for (int i = 0; i < Trabajo.limiteEmpleados; i++) {
                    if (trabajo.getEmpleados()[i] == idEmp) {
                        existe = true;
                    }
                }

                if (!existe) {
                    seleccion.add(empleado);
                }
            }
        }
        return seleccion;
    }

    public static void asignarEmpleadoEnTrabajo(int idTrabajo, int idEmpleado) {
        Trabajo t = trabajos.get(idTrabajo - 1);
        int[] empleados = t.getEmpleados();
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] == 0) {
                empleados[i] = idEmpleado;
                break;
            }
        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarEmpleadosDeTrabajo(t);
    }

    public static void asignarTrabajoEnEmpleado(int idEmpleado, int idTrabajo) {
        Empleado e = empleados.get(idEmpleado - 1);
        int[] trabajos = e.getTrabajos();
        for (int i = 0; i < trabajos.length; i++) {
            if (trabajos[i] == 0) {
                trabajos[i] = idTrabajo;
                break;
            }

        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarTrabajosDeEmpleado(e);
    }

    public static ArrayList<Trabajo> getTrabajosEnEmpleado(Empleado empleado) {
        ArrayList<Trabajo> seleccion = new ArrayList<Trabajo>();
        for (int idTrab : empleado.getTrabajos()) {
            if (idTrab != 0) {
                seleccion.add(OperacionesArchivo.getTrabajo(idTrab));
            }
        }
        return seleccion;
    }

    public static ArrayList<Trabajo> getTrabajosFueraDeEmpleado(Empleado empleado) {
        ArrayList<Trabajo> seleccion = new ArrayList<Trabajo>();
        for (Trabajo trabajo : trabajos) {
            int idTrab = trabajo.getId();
            if (idTrab > 0 && trabajo.nEmpleadosAsignados() < Trabajo.limiteEmpleados) {
                boolean existe = false;
                for (int i = 0; i < Empleado.limiteTrabajos; i++) {
                    if (empleado.getTrabajos()[i] == idTrab) {
                        existe = true;
                    }
                }

                if (!existe) {
                    seleccion.add(trabajo);
                }
            }
        }
        return seleccion;
    }

}//end LogicaEjercicio15
