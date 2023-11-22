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
 * CONTROL de la aplicacion. Es el punto de inicio tambien. Conserva una serie
 * de datos sobre empleados y trabajos usados por la vista para preseentarlos al
 * usuario. Se encarga de ordenar la escritura y lectura de datos al disco
 * pidiendolo a la clase OperacionesArchivo.
 *
 *
 * @author Jose Javier Bailon Ortiz
 */
public class Control {

    //ATRIBUTOS
    //referencia a la ventana principal
    public static VPrincipal15 ventanaPrincipal;
    //rutas de los archivos binarios y de texto
    public static String rutaEmpleados = ".\\src\\ejercicio15\\recursos\\Empleados.dat";
    public static String rutaTrabajos = ".\\src\\ejercicio15\\recursos\\Trabajos.dat";
    public static String rutaEmpleadosIniciales = ".\\src\\ejercicio15\\recursos\\EmpleadosIni.txt";
    public static String rutaTrabajosIniciales = ".\\src\\ejercicio15\\recursos\\TrabajosIni.txt";

    //todos los empleados
    private static ArrayList<Empleado> empleados = new ArrayList<>();
    //empleados activos
    private static final ArrayList<Empleado> empleadosActivos = new ArrayList<>();
    //empleados borrados
    private static final ArrayList<Empleado> empleadosBorrados = new ArrayList<>();

    //todos los trabajos
    private static ArrayList<Trabajo> trabajos = new ArrayList<>();
    //trabajos activos
    private static final ArrayList<Trabajo> trabajosActivos = new ArrayList<>();
    //trabajos borrados
    private static final ArrayList<Trabajo> trabajosBorrados = new ArrayList<>();

    //borra todos los datos tanto de memoria como de disco
    public static void borrarTodo() {
        empleados.clear();
        empleadosActivos.clear();
        empleadosBorrados.clear();
        trabajos.clear();
        trabajosActivos.clear();
        trabajosBorrados.clear();
        OperacionesArchivo.resetArchivos();
    }

    /**
     * Inicializa los datos cargando los existentes en disco
     */
    public static void inicializarDatos() {

        //inicializar archivo
        OperacionesArchivo.inicializarArchivos();

        //cargar trabajos existentes
        trabajos = OperacionesArchivo.cargarTrabajos();
        for (Trabajo trabajo : trabajos) {
            if (trabajo.getId() > 0) {
                trabajosActivos.add(trabajo);
            } else {
                trabajosBorrados.add(trabajo);
            }
        }

        //cargar empleados existentes
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
     * Aniade un nuevo trabajo tanto a memoria como a disco
     *
     * @param t Trabajo a aniadir
     */
    public static void agregarTrabajo(Trabajo t) {
        //ajuste de la id
        t.setId(trabajos.size() + 1);
        //agregar a memoria
        trabajos.add(t);
        trabajosActivos.add(t);
        //guardar en disco
        OperacionesArchivo.agregarTrabajo(t);
    }

    /**
     * Aniade un nuevo empleado tanto a memoria como a disco
     *
     * @param e Empleado a aniadir
     */
    public static void agregarEmpleado(Empleado e) {
        //ajuste de la id
        e.setId(empleados.size() + 1);
        //agregar a memoria
        empleados.add(e);
        empleadosActivos.add(e);
        //agregar a disco
        OperacionesArchivo.agregarEmpleado(e);
    }

    /**
     * Devuelve los trabajos activos
     *
     * @return La lista de trabajos activos
     */
    public static List<Trabajo> getTrabajosActivos() {
        return trabajosActivos;
    }

    /**
     * Devuelve los trabajos borrados
     * @return Lista de trabajos borrados
     */
    public static List<Trabajo> getTrabajosBorrados() {
        return trabajosBorrados;
    }

    /**
     * Devuelve los empleados activos
     *
     * @return La lista de empleados activos
     */
    public static List<Empleado> getEmpleadosActivos() {
        return empleadosActivos;
    }

    /**
     * Devuelve los empleados borrados
     *
     * @return lista de empleados borrados
     */
    public static List<Empleado> getEmpleadosBorrados() {
        return empleadosBorrados;
    }

    /**
     * Devuleve todos los mpleados
     * @return  Los empleados
     */
    public static List<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * Devuelve todos los trabajos
     * @return  Los trabajos
     */
    public static List<Trabajo> getTrabajos() {
        return trabajos;
    }

    
    /**
     * Borra todo, carga los datos de los archivos de texto y los guarda en binario
     */
    public static void cargarDatosTxt() {
        //borrar todo
        borrarTodo();
        //cargar empleados y guardarlos en binario
        ArrayList<Empleado> empleadosLeidos = OperacionesArchivo.leerEmpleadosInicialesTXT();
        for (Empleado empleado : empleadosLeidos) {
            empleados.add(empleado);
            empleadosActivos.add(empleado);
            OperacionesArchivo.agregarEmpleado(empleado);
        }

        //cargar trabajos txt y guardarlos en binario
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
        //recoger empleado
        Empleado e = empleados.get(idEmpleado - 1);
        int[] trabajosE = e.getTrabajos();
        //borrar asignacion de trabajo en memoria
        for (int i = 0; i < trabajosE.length; i++) {
            if (trabajosE[i] == idTrabajo) {
                trabajosE[i] = 0;
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
        //recoger trabajo
        Trabajo t = trabajos.get(idTrabajo - 1);
        int[] empleadosT = t.getEmpleados();
        //borrar asignacion dse empleado en  memoria
        for (int i = 0; i < empleadosT.length; i++) {
            if (empleadosT[i] == idEmpleado) {
                empleadosT[i] = 0;
            }
        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarEmpleadosDeTrabajo(t);
    }

    /**
     * Devuleve un trabajo leido desde el archivo
     * @param id Id del trabajo
     * @return  El trabajo o null si no esta
     */
    public static Trabajo getTrabajo(int id) {
        Trabajo trabajo = OperacionesArchivo.getTrabajo(id);
        if (trabajo == null || trabajo.getId() < 1) {
            return null;
        } else {
            return trabajo;
        }
    }

    /**
     * Actualiza la informacion basica de un trabajo en memoria y disco
     * @param t El trabajo a actualizar
     */
    public static void editarTrabajo(Trabajo t) {
        //actualizar en memoria
        Trabajo trabajo = trabajos.get(t.getId() - 1);
        trabajo.setNombre(t.getNombre());
        trabajo.setFecha(t.getFecha());
        //actualizar en disco
        OperacionesArchivo.editarTrabajo(trabajo);
    }

    /**
     * Devuleve un empleado leido del disco
     * @param id Id del empeado
     * @return  El empleado o null si no existe
     */
    public static Empleado getEmpleado(int id) {
        Empleado empleado = OperacionesArchivo.getEmpleado(id);
        if (empleado == null || empleado.getId() < 1) {
            return null;
        } else {
            return empleado;
        }
    }

    /**
     * Actualiza un empleado en memoria y disco
     * @param e El empleado a actualizar
     */
    public static void editarEmpleado(Empleado e) {
        //actualizar en memoria
        Empleado empleado = empleados.get(e.getId() - 1);
        empleado.setNombre(e.getNombre());
        empleado.setApellidos(e.getApellidos());
        empleado.setSueldo(e.getSueldo());
        //actualizar en disco
        OperacionesArchivo.editarEmpleado(empleado);
    }

    /**
     * Devuelve una lista con los empleados asignados a un trabajo
     * @param trabajo El trabajo
     * @return  La lista de empleados asignados
     */
    public static ArrayList<Empleado> getEmpleadosEnTrabajo(Trabajo trabajo) {
        ArrayList<Empleado> seleccion = new ArrayList<>();
        for (int idEmp : trabajo.getEmpleados()) {
            if (idEmp != 0) {
                seleccion.add(OperacionesArchivo.getEmpleado(idEmp));
            }
        }
        return seleccion;
    }

    /**
     * Devuelve una lista de empleados no asignados a un trabajo solo si no estan borrados
     * y tienen huecos libres para asignarles un trabajo
     * @param trabajo El trabajo a excluir
     * @return  La lista de trabajadores
     */
    public static ArrayList<Empleado> getEmpleadosFueraDeTrabajo(Trabajo trabajo) {
        ArrayList<Empleado> seleccion = new ArrayList<>();
        //recorrer los empleados
        for (Empleado empleado : empleados) {
            int idEmp = empleado.getId();
            if (idEmp > 0 && empleado.nTrabajosAsignados() < Empleado.limiteTrabajos) {
                boolean existe = false;
                for (int i = 0; i < Trabajo.limiteEmpleados; i++) {
                    if (trabajo.getEmpleados()[i] == idEmp) {
                        existe = true;
                    }
                }
                //seleccionar si no esta borrado, puede asignarsele trabajos
                //y no esta ya asignado al trabajo a excluir
                if (!existe) {
                    seleccion.add(empleado);
                }
            }
        }
        return seleccion;
    }

    /**
     * Asigna un empleado a un trabajo
     * @param idTrabajo Id del trabajo
     * @param idEmpleado  Id del empleado
     */
    public static void asignarEmpleadoEnTrabajo(int idTrabajo, int idEmpleado) {
        //recoger trabajo
        Trabajo t = trabajos.get(idTrabajo - 1);
        //recorrer huecos de empleados del trabajo 
        //y asignarlo en un hueco 0(vacio)
        int[] empleadosT = t.getEmpleados();
        for (int i = 0; i < empleadosT.length; i++) {
            if (empleadosT[i] == 0) {
                empleadosT[i] = idEmpleado;
                break;
            }
        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarEmpleadosDeTrabajo(t);
    }

     /**
     * Asigna un trabajo a un empleado
     * @param idEmpleado  Id del empleado
     * @param idTrabajo Id del trabajo
     */
    public static void asignarTrabajoEnEmpleado(int idEmpleado, int idTrabajo) {
        //recoger empleado
        Empleado e = empleados.get(idEmpleado - 1);
        //recorrer huecos de trabvajos del empleado
        //y asignarlo aen un hueco 0(vacio)
        int[] trabajosE = e.getTrabajos();
        for (int i = 0; i < trabajosE.length; i++) {
            if (trabajosE[i] == 0) {
                trabajosE[i] = idTrabajo;
                break;
            }

        }
        //actualizar los datos en el archivo
        OperacionesArchivo.actualizarTrabajosDeEmpleado(e);
    }

    /**
     * Devuelve un listgado de trabajos que tiene asignado un empleado
     * @param empleado El empleado
     * @return  Lista con los trabajos asignados al empleado
     */
    public static ArrayList<Trabajo> getTrabajosEnEmpleado(Empleado empleado) {
        ArrayList<Trabajo> seleccion = new ArrayList<>();
        for (int idTrab : empleado.getTrabajos()) {
            if (idTrab != 0) {
                seleccion.add(OperacionesArchivo.getTrabajo(idTrab));
            }
        }
        return seleccion;
    }

    /**
     * Devuelve una lista de trabajos no asignados a un empleado solo si no estan borrados
     * y tienen huecos libres para asignarles un empleado
     * @param empleado El empleado a excluir
     * @return el listado de trabajos
     */
    public static ArrayList<Trabajo> getTrabajosFueraDeEmpleado(Empleado empleado) {
        ArrayList<Trabajo> seleccion = new ArrayList<>();
        //recorrer los trabajos
        for (Trabajo trabajo : trabajos) {
            int idTrab = trabajo.getId();
            if (idTrab > 0 && trabajo.nEmpleadosAsignados() < Trabajo.limiteEmpleados) {
                boolean existe = false;
                for (int i = 0; i < Empleado.limiteTrabajos; i++) {
                    if (empleado.getTrabajos()[i] == idTrab) {
                        existe = true;
                    }
                }

                //seleccionar si no esta borrado, puede asignarsele empleados
                //y no esta ya asignado al empleado a excluir
                if (!existe) {
                    seleccion.add(trabajo);
                }
            }
        }
        return seleccion;
    }

    /**
     * INICIO DE LA APLICACION
     *
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VPrincipal15.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            ventanaPrincipal = new VPrincipal15();
            ventanaPrincipal.setLocationRelativeTo(null);
            ventanaPrincipal.setVisible(true);
        });
    }
}//end LogicaEjercicio15
