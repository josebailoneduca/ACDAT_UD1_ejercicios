/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
Lista de paquetes:
 */
package ejercicio15.gui.ventanas;

import ejercicio15.dto.Empleado;
import ejercicio15.dto.Trabajo;
import ejercicio15.gui.dialogos.DCrearEditarEmpleado;
import ejercicio15.gui.dialogos.DAsignTrabajosDeEmpleado;
import ejercicio15.gui.dialogos.DCrearEditarTrabajo;
import ejercicio15.gui.dialogos.DAsignEmpleadosDeTrabajo;
import ejercicio15.gui.dialogos.DVerEmpleado;
import ejercicio15.gui.dialogos.DVerTrabajo;
import ejercicio15.gui.tablemodels.EmpleadosTableModel;
import ejercicio15.gui.tablemodels.TrabajosTableModel;
import ejercicio15.logica.Control;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;

/**
 * Ventana principal del programa. Tiene dos paneles, uno para trabajos y otro
 * para empleados. Se puede acceder a ellos desde el menu: Trabajos->Ver todos
 * los trabajos Empleados->Ver todos los empleados
 *
 * Muestra tablas con los existentes y los borrados y lanza dialogos aparte para
 * crear, editar, visualizar y asignar
 *
 * @author Jose Javier Bailon Ortiz
 */
public class VPrincipal15 extends javax.swing.JFrame {

    /**
     * Constructor
     */
    public VPrincipal15() {
        initComponents();
        //enlaza las tablas con los modelos
        inicializarTablas();
    }

    /**
     * Inicializa las tablas de empleados y trabajadores activos y borrados 
     * recogiendo listas desde control para cada una de ellas
     * 
     */
    private void inicializarTablas() {
        //EMPLEADOS ACTIVOS
        EmpleadosTableModel tmEA = new EmpleadosTableModel(Control.getEmpleadosActivos());
        tblEmpleadosActivos.setModel(tmEA);
        //seleccionable
        tblEmpleadosActivos.setRowSelectionAllowed(true);
        tblEmpleadosActivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //sorter
        TableRowSorter<EmpleadosTableModel> rowSorterEA = new TableRowSorter<>(tmEA);
        tblEmpleadosActivos.setRowSorter(rowSorterEA);

        //ordenacion inicial
        List<SortKey> sortKeysEA = new ArrayList<>();
        sortKeysEA.add(new SortKey(0, SortOrder.ASCENDING));
        rowSorterEA.setSortKeys(sortKeysEA);

        //EMPLEADOS BORRADOS
        EmpleadosTableModel tmEB = new EmpleadosTableModel(Control.getEmpleadosBorrados());
        tblEmpleadosBorrados.setModel(tmEB);
        //no seleccionable
        tblEmpleadosBorrados.setEnabled(false);
        //sorter
        TableRowSorter<EmpleadosTableModel> rowSorterEB = new TableRowSorter<>(tmEB);
        tblTrabajosActivos.setRowSorter(rowSorterEB);

        //ordenacion por defecto
        List<SortKey> sortKeysEB = new ArrayList<>();
        sortKeysEB.add(new SortKey(0, SortOrder.ASCENDING));
        rowSorterEB.setSortKeys(sortKeysEB);

        //TRABAJOS ACTIVOS
        TrabajosTableModel tmTA = new TrabajosTableModel(Control.getTrabajosActivos());
        tblTrabajosActivos.setModel(tmTA);
        //seleccionable
        tblTrabajosActivos.setRowSelectionAllowed(true);
        tblTrabajosActivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //sorter
        TableRowSorter<TrabajosTableModel> rowSorterTA = new TableRowSorter<>(tmTA);
        tblTrabajosActivos.setRowSorter(rowSorterTA);

        //ordenacion por defecto
        List<SortKey> sortKeysTA = new ArrayList<>();
        sortKeysTA.add(new SortKey(0, SortOrder.ASCENDING));
        rowSorterTA.setSortKeys(sortKeysTA);

        //TRABAJOS BORRADOS
        TrabajosTableModel tmTB = new TrabajosTableModel(Control.getTrabajosBorrados());
        tblTrabajosBorrados.setModel(tmTB);
        //no seleccionable
        tblTrabajosBorrados.setEnabled(false);
        //sorter
        TableRowSorter<TrabajosTableModel> rowSorterTB = new TableRowSorter<>(tmTB);
        tblTrabajosBorrados.setRowSorter(rowSorterTB);

        //ordenacion por defecto
        List<SortKey> sortKeysTB = new ArrayList<>();
        sortKeysTB.add(new SortKey(0, SortOrder.ASCENDING));
        rowSorterTB.setSortKeys(sortKeysEB);

    }

    /**
     * Lanza el refresco de las tablas
     */
    private void actualizarTablas() {
        ((EmpleadosTableModel) tblEmpleadosActivos.getModel()).fireTableDataChanged();
        ((EmpleadosTableModel) tblEmpleadosBorrados.getModel()).fireTableDataChanged();
        ((TrabajosTableModel) tblTrabajosActivos.getModel()).fireTableDataChanged();
        ((TrabajosTableModel) tblTrabajosBorrados.getModel()).fireTableDataChanged();
    }


    /**
     * Devuelve la id del trabajo seleccionado en la tabla de trabajos activos
     * @return la id del trabajo o -1 si no hay seleccionados
     */
    private int getIdTrabajoSeleccionado() {
        int seleccionado = tblTrabajosActivos.getSelectedRow();
        if (seleccionado < 0) {
            return -1;
        }
        //extraer id del trabajo
        int indiceSeleccionado = tblTrabajosActivos.convertRowIndexToModel(seleccionado);
        return (int) tblTrabajosActivos.getModel().getValueAt(indiceSeleccionado, 0);
    }

     /**
     * Devuelve la id del empleado seleccionado en la tabla de empleados activos
     * @return la id del empleado o -1 si no hay seleccionados
     */
    private int getIdEmpleadoSeleccionado() {
        int seleccionado = tblEmpleadosActivos.getSelectedRow();
        if (seleccionado < 0) {
            return -1;
        }
        //extraer la id del empleado 
        int indiceSeleccionado = tblEmpleadosActivos.convertRowIndexToModel(seleccionado);
        return (int) tblEmpleadosActivos.getModel().getValueAt(indiceSeleccionado, 0);
    }

    
    /**
     * Muetra un dialogo para recoger una id (un entero)
     * @param msg Mensaje a mostrar en el dialogo
     * @param titulo Titulo del dialogo
     * @return  la id recogida o -1 si se ha pulsado cancelar
     */
    private int recogerId(String msg, String titulo) {
        int id = -1;
        boolean recogido = false;
        while (!recogido) {
            String idSt = JOptionPane.showInputDialog(this, msg, titulo, JOptionPane.PLAIN_MESSAGE);
            if (idSt == null) {
                return id;
            }
            try {
                id = Integer.parseInt(idSt);
                recogido = true;
            } catch (NumberFormatException ex) {
            }
        }
        return id;
    }

    /**
     * Muestra un mensaje de error
     * @param msg El mensaje
     */
    private void mensajeError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de aviso
     * @param msg  El mensaje
     */
    private void mensajeAviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un mensaje informativo
     * @param msg  El mensaje
     */
    private void mensajeInfo(String msg) {

        JOptionPane.showMessageDialog(this, msg, "", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelTrabajos = new javax.swing.JPanel();
        lbTrabajos = new javax.swing.JLabel();
        scrollTrabajos = new javax.swing.JScrollPane();
        tblTrabajosActivos = new javax.swing.JTable();
        lbTrabajosEliminados = new javax.swing.JLabel();
        scrollTrabajosEliminados = new javax.swing.JScrollPane();
        tblTrabajosBorrados = new javax.swing.JTable();
        panelTrabajosOperaciones = new javax.swing.JPanel();
        btnAnadirTrabajo = new javax.swing.JButton();
        btnEditarTrabajo = new javax.swing.JButton();
        btnEliminarTrabajo = new javax.swing.JButton();
        btnVerTrabajo = new javax.swing.JButton();
        btnAsignacionEmpleadosATrabajo = new javax.swing.JButton();
        panelEmpleados = new javax.swing.JPanel();
        lbEmpleados = new javax.swing.JLabel();
        scrollEmpleados = new javax.swing.JScrollPane();
        tblEmpleadosActivos = new javax.swing.JTable();
        lbEmpleadosEliminados = new javax.swing.JLabel();
        scrollEmpleadosEliminados = new javax.swing.JScrollPane();
        tblEmpleadosBorrados = new javax.swing.JTable();
        panelEmpleadosOperaciones = new javax.swing.JPanel();
        btnAnadirEmpleado = new javax.swing.JButton();
        btnEditarEmpleado = new javax.swing.JButton();
        btnEliminarEmpleado = new javax.swing.JButton();
        btnVerEmpleado = new javax.swing.JButton();
        btnAsignacionTrabajosAEmpleado = new javax.swing.JButton();
        barraMenu = new javax.swing.JMenuBar();
        mArchivo = new javax.swing.JMenu();
        miCargarDatosTxt = new javax.swing.JMenuItem();
        btnBorrarTodo = new javax.swing.JMenuItem();
        miSalir = new javax.swing.JMenuItem();
        mTrabajo = new javax.swing.JMenu();
        miVerTodosTrabajos = new javax.swing.JMenuItem();
        miVerUnTrabajo = new javax.swing.JMenuItem();
        miAnadirTrabajo = new javax.swing.JMenuItem();
        miEditarTrabajo = new javax.swing.JMenuItem();
        miAsignarEmpleadosATrabajo = new javax.swing.JMenuItem();
        miEliminarTrabajo = new javax.swing.JMenuItem();
        mEmpleados = new javax.swing.JMenu();
        miVerTodosEmpleados = new javax.swing.JMenuItem();
        miVerEmpleado = new javax.swing.JMenuItem();
        miAnadirEmpleado = new javax.swing.JMenuItem();
        miEditarEmpleado = new javax.swing.JMenuItem();
        miAsignarTrabajosAEmpleado = new javax.swing.JMenuItem();
        miEliminarEmpleado = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setLayout(new java.awt.CardLayout());

        lbTrabajos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTrabajos.setText("TRABAJOS");

        tblTrabajosActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollTrabajos.setViewportView(tblTrabajosActivos);

        lbTrabajosEliminados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTrabajosEliminados.setText("TRABAJOS ELIMINADOS");

        tblTrabajosBorrados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollTrabajosEliminados.setViewportView(tblTrabajosBorrados);

        panelTrabajosOperaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelTrabajosOperaciones.setPreferredSize(new java.awt.Dimension(183, 500));

        btnAnadirTrabajo.setText("A�adir trabajo");
        btnAnadirTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirTrabajoActionPerformed(evt);
            }
        });

        btnEditarTrabajo.setText("Editar trabajo");
        btnEditarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarTrabajoActionPerformed(evt);
            }
        });

        btnEliminarTrabajo.setText("Eliminar trabajo");
        btnEliminarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTrabajoActionPerformed(evt);
            }
        });

        btnVerTrabajo.setText("Ver trabajo");
        btnVerTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerTrabajoActionPerformed(evt);
            }
        });

        btnAsignacionEmpleadosATrabajo.setText("Asignaci�n de empleados");
        btnAsignacionEmpleadosATrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignacionEmpleadosATrabajoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTrabajosOperacionesLayout = new javax.swing.GroupLayout(panelTrabajosOperaciones);
        panelTrabajosOperaciones.setLayout(panelTrabajosOperacionesLayout);
        panelTrabajosOperacionesLayout.setHorizontalGroup(
            panelTrabajosOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTrabajosOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTrabajosOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerTrabajo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAsignacionEmpleadosATrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(btnAnadirTrabajo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelTrabajosOperacionesLayout.setVerticalGroup(
            panelTrabajosOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTrabajosOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVerTrabajo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnadirTrabajo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarTrabajo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAsignacionEmpleadosATrabajo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarTrabajo)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelTrabajosLayout = new javax.swing.GroupLayout(panelTrabajos);
        panelTrabajos.setLayout(panelTrabajosLayout);
        panelTrabajosLayout.setHorizontalGroup(
            panelTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTrabajosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTrabajosLayout.createSequentialGroup()
                        .addGroup(panelTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollTrabajosEliminados, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(scrollTrabajos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelTrabajosOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTrabajosLayout.createSequentialGroup()
                        .addGroup(panelTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTrabajos)
                            .addComponent(lbTrabajosEliminados))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTrabajosLayout.setVerticalGroup(
            panelTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTrabajosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTrabajos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTrabajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTrabajos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelTrabajosLayout.createSequentialGroup()
                        .addComponent(panelTrabajosOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTrabajosEliminados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTrabajosEliminados, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.add(panelTrabajos, "panelTrabajos");

        lbEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbEmpleados.setText("EMPLEADOS");

        tblEmpleadosActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollEmpleados.setViewportView(tblEmpleadosActivos);

        lbEmpleadosEliminados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbEmpleadosEliminados.setText("EMPLEADOS ELIMINADOS");

        tblEmpleadosBorrados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollEmpleadosEliminados.setViewportView(tblEmpleadosBorrados);

        panelEmpleadosOperaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelEmpleadosOperaciones.setPreferredSize(new java.awt.Dimension(183, 500));

        btnAnadirEmpleado.setText("A�adir empleado");
        btnAnadirEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirEmpleadoActionPerformed(evt);
            }
        });

        btnEditarEmpleado.setText("Editar empleado");
        btnEditarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarEmpleadoActionPerformed(evt);
            }
        });

        btnEliminarEmpleado.setText("Eliminar empleado");
        btnEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleadoActionPerformed(evt);
            }
        });

        btnVerEmpleado.setText("Ver empleado");
        btnVerEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerEmpleadoActionPerformed(evt);
            }
        });

        btnAsignacionTrabajosAEmpleado.setText("Asignaci�n de trabajos");
        btnAsignacionTrabajosAEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignacionTrabajosAEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEmpleadosOperacionesLayout = new javax.swing.GroupLayout(panelEmpleadosOperaciones);
        panelEmpleadosOperaciones.setLayout(panelEmpleadosOperacionesLayout);
        panelEmpleadosOperacionesLayout.setHorizontalGroup(
            panelEmpleadosOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpleadosOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEmpleadosOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAnadirEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAsignacionTrabajosAEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelEmpleadosOperacionesLayout.setVerticalGroup(
            panelEmpleadosOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpleadosOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVerEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnadirEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAsignacionTrabajosAEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarEmpleado)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEmpleadosLayout = new javax.swing.GroupLayout(panelEmpleados);
        panelEmpleados.setLayout(panelEmpleadosLayout);
        panelEmpleadosLayout.setHorizontalGroup(
            panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(scrollEmpleadosEliminados, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelEmpleadosOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbEmpleados)
                            .addComponent(lbEmpleadosEliminados))
                        .addGap(0, 397, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelEmpleadosLayout.setVerticalGroup(
            panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmpleadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbEmpleados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelEmpleadosOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbEmpleadosEliminados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollEmpleadosEliminados, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.add(panelEmpleados, "panelEmpleados");

        mArchivo.setText("Archivo");

        miCargarDatosTxt.setText("Cargar datos desde TXT");
        miCargarDatosTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCargarDatosTxtActionPerformed(evt);
            }
        });
        mArchivo.add(miCargarDatosTxt);

        btnBorrarTodo.setText("Borrar todo");
        btnBorrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarTodoActionPerformed(evt);
            }
        });
        mArchivo.add(btnBorrarTodo);

        miSalir.setText("Salir");
        miSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSalirActionPerformed(evt);
            }
        });
        mArchivo.add(miSalir);

        barraMenu.add(mArchivo);

        mTrabajo.setText("Trabajos");

        miVerTodosTrabajos.setText("Ver todos los trabajos");
        miVerTodosTrabajos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVerTodosTrabajosActionPerformed(evt);
            }
        });
        mTrabajo.add(miVerTodosTrabajos);

        miVerUnTrabajo.setText("Ver trabajo...");
        miVerUnTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVerUnTrabajoActionPerformed(evt);
            }
        });
        mTrabajo.add(miVerUnTrabajo);

        miAnadirTrabajo.setText("A�adir trabajo");
        miAnadirTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAnadirTrabajoActionPerformed(evt);
            }
        });
        mTrabajo.add(miAnadirTrabajo);

        miEditarTrabajo.setText("Editar trabajo...");
        miEditarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditarTrabajoActionPerformed(evt);
            }
        });
        mTrabajo.add(miEditarTrabajo);

        miAsignarEmpleadosATrabajo.setText("Asignaci�n de empleados a trabajo...");
        miAsignarEmpleadosATrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAsignarEmpleadosATrabajoActionPerformed(evt);
            }
        });
        mTrabajo.add(miAsignarEmpleadosATrabajo);

        miEliminarTrabajo.setText("Eliminar trabajo...");
        miEliminarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEliminarTrabajoActionPerformed(evt);
            }
        });
        mTrabajo.add(miEliminarTrabajo);

        barraMenu.add(mTrabajo);

        mEmpleados.setText("Empleados");

        miVerTodosEmpleados.setText("Ver todos los empleados");
        miVerTodosEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVerTodosEmpleadosActionPerformed(evt);
            }
        });
        mEmpleados.add(miVerTodosEmpleados);

        miVerEmpleado.setText("Ver empleado...");
        miVerEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVerEmpleadoActionPerformed(evt);
            }
        });
        mEmpleados.add(miVerEmpleado);

        miAnadirEmpleado.setText("A�adir empleado");
        miAnadirEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAnadirEmpleadoActionPerformed(evt);
            }
        });
        mEmpleados.add(miAnadirEmpleado);

        miEditarEmpleado.setText("Editar empleado...");
        miEditarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditarEmpleadoActionPerformed(evt);
            }
        });
        mEmpleados.add(miEditarEmpleado);

        miAsignarTrabajosAEmpleado.setText("Asignaci�n de trabajos a empleado...");
        miAsignarTrabajosAEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAsignarTrabajosAEmpleadoActionPerformed(evt);
            }
        });
        mEmpleados.add(miAsignarTrabajosAEmpleado);

        miEliminarEmpleado.setText("Eliminar empleado...");
        miEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEliminarEmpleadoActionPerformed(evt);
            }
        });
        mEmpleados.add(miEliminarEmpleado);

        barraMenu.add(mEmpleados);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSalirActionPerformed
        if (JOptionPane.showConfirmDialog(this, "�Desea salir?", "Salir del programa", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            System.exit(0);
    }//GEN-LAST:event_miSalirActionPerformed

    private void miVerTodosTrabajosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miVerTodosTrabajosActionPerformed
        verPanel("panelTrabajos");
    }//GEN-LAST:event_miVerTodosTrabajosActionPerformed

    private void miCargarDatosTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCargarDatosTxtActionPerformed
        int r = JOptionPane.showConfirmDialog(this, "�Desea cargar los datos del archivo de texto? Se borrar�n los datos actuales", "Cargar datos desde TXT", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            Control.cargarDatosTxt();
            actualizarTablas();
        }
    }//GEN-LAST:event_miCargarDatosTxtActionPerformed

    private void miVerTodosEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miVerTodosEmpleadosActionPerformed
        verPanel("panelEmpleados");
    }//GEN-LAST:event_miVerTodosEmpleadosActionPerformed

    private void miAnadirTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAnadirTrabajoActionPerformed
        DCrearEditarTrabajo dt = new DCrearEditarTrabajo(this, true, null, DCrearEditarTrabajo.Tipo.CREAR);
        dt.setLocationRelativeTo(this);
        dt.setVisible(true);
        actualizarTablas();
    }//GEN-LAST:event_miAnadirTrabajoActionPerformed

    private void btnAnadirTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirTrabajoActionPerformed
        DCrearEditarTrabajo dt = new DCrearEditarTrabajo(this, true, null, DCrearEditarTrabajo.Tipo.CREAR);
        dt.setLocationRelativeTo(this);
        dt.setVisible(true);
        actualizarTablas();
    }//GEN-LAST:event_btnAnadirTrabajoActionPerformed

    private void btnAnadirEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirEmpleadoActionPerformed
        DCrearEditarEmpleado dt = new DCrearEditarEmpleado(this, true, null, DCrearEditarEmpleado.Tipo.CREAR);
        dt.setLocationRelativeTo(this);
        dt.setVisible(true);
        actualizarTablas();
    }//GEN-LAST:event_btnAnadirEmpleadoActionPerformed

    private void miAnadirEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAnadirEmpleadoActionPerformed
        DCrearEditarEmpleado dt = new DCrearEditarEmpleado(this, true, null, DCrearEditarEmpleado.Tipo.CREAR);
        dt.setLocationRelativeTo(this);
        dt.setVisible(true);
        actualizarTablas();
    }//GEN-LAST:event_miAnadirEmpleadoActionPerformed

    private void miEliminarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEliminarTrabajoActionPerformed

        int id = recogerId("Escriba la ID del trabajo a eliminar", "Eliminar trabajo");
        if (id == -1) {
            return;
        }
        boolean borrado = Control.eliminarTrabajo(id);
        if (borrado) {
            mensajeInfo("Trabajo eliminado");
            actualizarTablas();
        } else {
            mensajeError("No hay trabajos activos con la id " + id);
        }

    }//GEN-LAST:event_miEliminarTrabajoActionPerformed

    private void miEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEliminarEmpleadoActionPerformed
        int id = recogerId("Escriba la ID del empleado a eliminar", "Eliminar empleado");
        if (id == -1) {
            return;
        }
        boolean borrado = Control.eliminarEmpleado(id);
        if (borrado) {
            mensajeInfo("Empleado eliminado");
            actualizarTablas();
        } else {
            mensajeError("No hay empleados activos con la id " + id);
        }
    }//GEN-LAST:event_miEliminarEmpleadoActionPerformed

    private void btnEliminarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTrabajoActionPerformed
        int idTrabajo = getIdTrabajoSeleccionado();
        if (idTrabajo == -1)
            mensajeAviso("Seleccione un trabajo de la tabla");
        else {
            int respuesta = JOptionPane.showConfirmDialog(this, "�Realmente desea elminiar el trabajo " + idTrabajo + "?", "Eliminar trabajo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                Control.eliminarTrabajo(idTrabajo);
                actualizarTablas();
            }
        }
    }//GEN-LAST:event_btnEliminarTrabajoActionPerformed

    private void btnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadoActionPerformed
        int idEmpleado = getIdEmpleadoSeleccionado();
        if (idEmpleado == -1)
            mensajeAviso("Seleccione un empleado de la tabla");
        else {
            int respuesta = JOptionPane.showConfirmDialog(this, "�Realmente desea elminiar el empleado " + idEmpleado + "?", "Eliminar empleado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                Control.eliminarEmpleado(idEmpleado);
                actualizarTablas();
            }
        }
    }//GEN-LAST:event_btnEliminarEmpleadoActionPerformed

    private void miEditarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditarTrabajoActionPerformed

        int id = recogerId("Escriba la ID del trabajo a editar", "Editar trabajo");
        if (id == -1) {
            return;
        }
        Trabajo trabajo = Control.getTrabajo(id);
        if (trabajo == null || trabajo.getId() < 0) {
            mensajeError("El trabajo " + id + " no existe");
            return;
        }
        DCrearEditarTrabajo dt = new DCrearEditarTrabajo(this, true, trabajo, DCrearEditarTrabajo.Tipo.EDITAR);
        dt.setLocationRelativeTo(this);
        dt.setVisible(true);
        actualizarTablas();
    }//GEN-LAST:event_miEditarTrabajoActionPerformed

    private void btnEditarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarTrabajoActionPerformed
        int idTrabajo = getIdTrabajoSeleccionado();
        if (idTrabajo == -1)
            mensajeAviso("Seleccione un trabajo de la tabla");
        else {
            Trabajo trabajo = Control.getTrabajo(idTrabajo);
            if (trabajo == null) {
                mensajeError("El trabajo " + idTrabajo + " no existe");
                return;
            }
            DCrearEditarTrabajo dt = new DCrearEditarTrabajo(this, true, trabajo, DCrearEditarTrabajo.Tipo.EDITAR);
            dt.setLocationRelativeTo(this);
            dt.setVisible(true);
            actualizarTablas();
        }
    }//GEN-LAST:event_btnEditarTrabajoActionPerformed

    private void miEditarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditarEmpleadoActionPerformed
        int id = recogerId("Escriba la ID del empleado a editar", "Editar empleado");
        if (id == -1) {
            return;
        }

        Empleado empleado = Control.getEmpleado(id);
        if (empleado == null) {
            mensajeError("El empleado " + id + " no existe");
            return;
        }
        DCrearEditarEmpleado dt = new DCrearEditarEmpleado(this, true, empleado, DCrearEditarEmpleado.Tipo.EDITAR);
        dt.setLocationRelativeTo(this);
        dt.setVisible(true);
        actualizarTablas();
    }//GEN-LAST:event_miEditarEmpleadoActionPerformed

    private void btnEditarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarEmpleadoActionPerformed
        int idEmpleado = getIdEmpleadoSeleccionado();
        if (idEmpleado == -1)
            mensajeAviso("Seleccione un empleado de la tabla");
        else {
            Empleado empleado = Control.getEmpleado(idEmpleado);
            if (empleado == null) {
                mensajeError("El empleado " + idEmpleado + " no existe");
                return;
            }
            DCrearEditarEmpleado dt = new DCrearEditarEmpleado(this, true, empleado, DCrearEditarEmpleado.Tipo.EDITAR);
            dt.setLocationRelativeTo(this);
            dt.setVisible(true);
            actualizarTablas();
        }
    }//GEN-LAST:event_btnEditarEmpleadoActionPerformed

    private void miAsignarEmpleadosATrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAsignarEmpleadosATrabajoActionPerformed
        int id = recogerId("Escriba la ID del trabajo", "Asignar empleados a trabajo");
        if (id == -1) {
            return;
        }
        Trabajo trabajo = Control.getTrabajo(id);
        if (trabajo == null || trabajo.getId() < 0) {
            mensajeError("El trabajo " + id + " no existe");
            return;
        }
        DAsignEmpleadosDeTrabajo dta = new DAsignEmpleadosDeTrabajo(this, true, trabajo);
        dta.setLocationRelativeTo(this);
        dta.setVisible(true);
    }//GEN-LAST:event_miAsignarEmpleadosATrabajoActionPerformed

    private void btnAsignacionEmpleadosATrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignacionEmpleadosATrabajoActionPerformed
        int idTrabajo = getIdTrabajoSeleccionado();
        if (idTrabajo == -1)
            mensajeAviso("Seleccione un trabajo de la tabla");
        else {
            Trabajo trabajo = Control.getTrabajo(idTrabajo);
            if (trabajo == null) {
                mensajeError("El trabajo " + idTrabajo + " no existe");
                return;
            }
            DAsignEmpleadosDeTrabajo dta = new DAsignEmpleadosDeTrabajo(this, true, trabajo);
            dta.setLocationRelativeTo(this);
            dta.setVisible(true);
        }
    }//GEN-LAST:event_btnAsignacionEmpleadosATrabajoActionPerformed

    private void miAsignarTrabajosAEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAsignarTrabajosAEmpleadoActionPerformed
        int id = recogerId("Escriba la ID del empleado", "Asignar trabajos a empleado");
        if (id == -1) {
            return;
        }
        Empleado empleado = Control.getEmpleado(id);
        if (empleado == null || empleado.getId() < 0) {
            mensajeError("El empleado " + id + " no existe");
            return;
        }
        DAsignTrabajosDeEmpleado dea = new DAsignTrabajosDeEmpleado(this, true, empleado);
        dea.setLocationRelativeTo(this);
        dea.setVisible(true);
    }//GEN-LAST:event_miAsignarTrabajosAEmpleadoActionPerformed

    private void btnAsignacionTrabajosAEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignacionTrabajosAEmpleadoActionPerformed
        int idEmpleado = getIdEmpleadoSeleccionado();
        if (idEmpleado == -1)
            mensajeAviso("Seleccione un empleado de la tabla");
        else {
            Empleado empleado = Control.getEmpleado(idEmpleado);
            if (empleado == null) {
                mensajeError("El empleado " + idEmpleado + " no existe");
                return;
            }
            DAsignTrabajosDeEmpleado dea = new DAsignTrabajosDeEmpleado(this, true, empleado);
            dea.setLocationRelativeTo(this);
            dea.setVisible(true);
        }
    }//GEN-LAST:event_btnAsignacionTrabajosAEmpleadoActionPerformed

    private void btnBorrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarTodoActionPerformed
        int r = JOptionPane.showConfirmDialog(this, "�Borrar todos los datos? Se borrar�n todos los trabajos y empleados", "Borar todo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            Control.borrarTodo();
            actualizarTablas();
        }
    }//GEN-LAST:event_btnBorrarTodoActionPerformed

    private void miVerUnTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miVerUnTrabajoActionPerformed
        int id = recogerId("Escriba la ID del trabajo", "Ver detalles de trabajo");
        if (id == -1) {
            return;
        }
        Trabajo trabajo = Control.getTrabajo(id);
        if (trabajo == null) {
            mensajeError("El trabajo " + id + " no existe");
            return;
        }
        ArrayList<Empleado> empleados = Control.getEmpleadosEnTrabajo(trabajo);
        DVerTrabajo dvt = new DVerTrabajo(this, true, trabajo, empleados);
        dvt.setLocationRelativeTo(this);
        dvt.setVisible(true);
    }//GEN-LAST:event_miVerUnTrabajoActionPerformed

    private void btnVerTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerTrabajoActionPerformed
        int idTrabajo = getIdTrabajoSeleccionado();
        if (idTrabajo == -1)
            mensajeAviso("Seleccione un trabajo de la tabla");
        else {
            Trabajo trabajo = Control.getTrabajo(idTrabajo);
            if (trabajo == null) {
                mensajeError("El trabajo " + idTrabajo + " no existe");
                return;
            }
            ArrayList<Empleado> empleados = Control.getEmpleadosEnTrabajo(trabajo);
            DVerTrabajo dvt = new DVerTrabajo(this, true, trabajo, empleados);
            dvt.setLocationRelativeTo(this);
            dvt.setVisible(true);
        }
    }//GEN-LAST:event_btnVerTrabajoActionPerformed

    private void miVerEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miVerEmpleadoActionPerformed
        int id = recogerId("Escriba la ID del empleado", "Ver detalles de empleado");
        if (id == -1) {
            return;
        }
        Empleado empleado = Control.getEmpleado(id);
        if (empleado == null) {
            mensajeError("El empleado " + id + " no existe");
            return;
        }
        ArrayList<Trabajo> trabajos = Control.getTrabajosEnEmpleado(empleado);
        DVerEmpleado dve = new DVerEmpleado(this, true, empleado, trabajos);
        dve.setLocationRelativeTo(this);
        dve.setVisible(true);
    }//GEN-LAST:event_miVerEmpleadoActionPerformed

    private void btnVerEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerEmpleadoActionPerformed
        int idEmpleado = getIdEmpleadoSeleccionado();
        if (idEmpleado == -1)
            mensajeAviso("Seleccione un empleado de la tabla");
        else {
            Empleado empleado = Control.getEmpleado(idEmpleado);
            if (empleado == null) {
                mensajeError("El empleado " + idEmpleado + " no existe");
                return;
            }
            ArrayList<Trabajo> trabajos = Control.getTrabajosEnEmpleado(empleado);
            DVerEmpleado dve = new DVerEmpleado(this, true, empleado, trabajos);
            dve.setLocationRelativeTo(this);
            dve.setVisible(true);
        }    }//GEN-LAST:event_btnVerEmpleadoActionPerformed

    private void verPanel(String nombre) {

        CardLayout layout = (CardLayout) panelPrincipal.getLayout();
        layout.show(panelPrincipal, nombre);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton btnAnadirEmpleado;
    private javax.swing.JButton btnAnadirTrabajo;
    private javax.swing.JButton btnAsignacionEmpleadosATrabajo;
    private javax.swing.JButton btnAsignacionTrabajosAEmpleado;
    private javax.swing.JMenuItem btnBorrarTodo;
    private javax.swing.JButton btnEditarEmpleado;
    private javax.swing.JButton btnEditarTrabajo;
    private javax.swing.JButton btnEliminarEmpleado;
    private javax.swing.JButton btnEliminarTrabajo;
    private javax.swing.JButton btnVerEmpleado;
    private javax.swing.JButton btnVerTrabajo;
    private javax.swing.JLabel lbEmpleados;
    private javax.swing.JLabel lbEmpleadosEliminados;
    private javax.swing.JLabel lbTrabajos;
    private javax.swing.JLabel lbTrabajosEliminados;
    private javax.swing.JMenu mArchivo;
    private javax.swing.JMenu mEmpleados;
    private javax.swing.JMenu mTrabajo;
    private javax.swing.JMenuItem miAnadirEmpleado;
    private javax.swing.JMenuItem miAnadirTrabajo;
    private javax.swing.JMenuItem miAsignarEmpleadosATrabajo;
    private javax.swing.JMenuItem miAsignarTrabajosAEmpleado;
    private javax.swing.JMenuItem miCargarDatosTxt;
    private javax.swing.JMenuItem miEditarEmpleado;
    private javax.swing.JMenuItem miEditarTrabajo;
    private javax.swing.JMenuItem miEliminarEmpleado;
    private javax.swing.JMenuItem miEliminarTrabajo;
    private javax.swing.JMenuItem miSalir;
    private javax.swing.JMenuItem miVerEmpleado;
    private javax.swing.JMenuItem miVerTodosEmpleados;
    private javax.swing.JMenuItem miVerTodosTrabajos;
    private javax.swing.JMenuItem miVerUnTrabajo;
    private javax.swing.JPanel panelEmpleados;
    private javax.swing.JPanel panelEmpleadosOperaciones;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelTrabajos;
    private javax.swing.JPanel panelTrabajosOperaciones;
    private javax.swing.JScrollPane scrollEmpleados;
    private javax.swing.JScrollPane scrollEmpleadosEliminados;
    private javax.swing.JScrollPane scrollTrabajos;
    private javax.swing.JScrollPane scrollTrabajosEliminados;
    private javax.swing.JTable tblEmpleadosActivos;
    private javax.swing.JTable tblEmpleadosBorrados;
    private javax.swing.JTable tblTrabajosActivos;
    private javax.swing.JTable tblTrabajosBorrados;
    // End of variables declaration//GEN-END:variables

}//fin VPrincipal15
