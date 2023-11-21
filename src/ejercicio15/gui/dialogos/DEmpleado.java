/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
Lista de paquetes:
 */

package ejercicio15.gui.dialogos;

import ejercicio15.dto.Empleado;
 import ejercicio15.logica.Logica;
 import javax.swing.JOptionPane;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class DEmpleado extends javax.swing.JDialog {


    public enum Tipo{
        EDITAR,
        CREAR
    }
    
    private Tipo tipo;
    /** Creates new form DTrabajo */
    public DEmpleado(java.awt.Frame parent, boolean modal,Empleado empleado, DEmpleado.Tipo tipo) {
        super(parent, modal);
        initComponents();
        this.tipo=tipo;
        switch (tipo) {
           case EDITAR:
                inicializaEditar(empleado);
                break;
            case CREAR:
                inicializaCrear();
                break;
            default:
                throw new AssertionError();
        }
    }
    private void inicializaCrear() {
        this.lbId.setVisible(false);
        this.inputId.setVisible(false);
        this.lbTitulo.setText("CREAR EMPLEADO");    }

    private void inicializaEditar(Empleado empleado) {
        inputId.setText(""+empleado.getId());
        inputNombre.setText(empleado.getNombre());
        //inputApellidos.setText(empleado.getNombre());
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbId = new javax.swing.JLabel();
        inputId = new javax.swing.JTextField();
        lbNombre = new javax.swing.JLabel();
        inputNombre = new javax.swing.JTextField();
        lbApellidos = new javax.swing.JLabel();
        inputApellidos = new javax.swing.JTextField();
        lbSueldo = new javax.swing.JLabel();
        inputSueldo = new javax.swing.JSpinner();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lbTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        lbId.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbId.setText("ID");

        inputId.setEditable(false);

        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbNombre.setText("NOMBRE");

        lbApellidos.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbApellidos.setText("APELLIDOS");

        lbSueldo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbSueldo.setText("SUELDO");

        inputSueldo.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                    .addComponent(lbSueldo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(inputId)
                    .addComponent(inputApellidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(inputSueldo))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbId)
                    .addComponent(inputId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNombre)
                    .addComponent(inputNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbApellidos)
                    .addComponent(inputApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSueldo)
                    .addComponent(inputSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lbTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitulo.setText("Empleado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(52, 52, 52)
                .addComponent(btnCancelar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(lbTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int id=-1;
        String nombre="";
        String apellidos="";
        int sueldo  = 0;
        try{id = Integer.parseInt(this.inputId.getText());} catch(NumberFormatException e){} 
        nombre=inputNombre.getText();
        if (nombre.length()<1){
            JOptionPane.showMessageDialog(this, "No puede dejar el nombre vac�o", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(nombre.length()>Empleado.limiteNombre){
            int opcion = JOptionPane.showConfirmDialog(this, "El nombre se recortar� a "+Empleado.limiteNombre+" caracteres. �Est� de acuerdo?", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opcion==JOptionPane.NO_OPTION)
            return;
        }
               apellidos=inputApellidos.getText();
        if (apellidos.length()<1){
            JOptionPane.showMessageDialog(this, "No puede dejar  el apellido vac�o", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(apellidos.length()>Empleado.limiteApellidos){
            int opcion = JOptionPane.showConfirmDialog(this, "El apellido se recortar� a "+Empleado.limiteApellidos+" caracteres. �Est� de acuerdo?", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opcion==JOptionPane.NO_OPTION)
            return;
        }
        
        try{sueldo = (int)this.inputSueldo.getValue();}catch(Exception e){}
        if (sueldo<1)
            JOptionPane.showMessageDialog(this, "El sueldo no es v�lido.", "Error", JOptionPane.ERROR_MESSAGE);
        
        Empleado e = new Empleado(id, nombre, apellidos, sueldo, new int[Empleado.limiteTrabajos]);
       
        Logica.agregarEmpleado(e);
        JOptionPane.showMessageDialog(this, this.tipo==Tipo.CREAR?"Empleado a�adido":"Empleado editado");
        this.dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
          this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JTextField inputApellidos;
    private javax.swing.JTextField inputId;
    private javax.swing.JTextField inputNombre;
    private javax.swing.JSpinner inputSueldo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbApellidos;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbSueldo;
    private javax.swing.JLabel lbTitulo;
    // End of variables declaration//GEN-END:variables

}//fin DTrabajo