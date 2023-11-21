/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio15.gui.tablemodels;

import ejercicio15.dto.Empleado;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jose Javier BO
 */
public class EmpleadosTableModel extends AbstractTableModel{

    

    //ATRIBUTOS:
    private List<Empleado> listaEmpleados;//lista actual de encuestas
    private String[] columnas = new String[]{"ID", "Nombre", "Apellidos", "Sueldo"};

    //METODOS:
    //Constructor
    public EmpleadosTableModel(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    @Override
    public int getRowCount() {
        return this.listaEmpleados.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = Math.abs(listaEmpleados.get(rowIndex).getId());
                break;
            case 1:
                value = listaEmpleados.get(rowIndex).getNombre();
                break;
            case 2:
                value = listaEmpleados.get(rowIndex).getApellidos();
                break;
            case 3:
                value = listaEmpleados.get(rowIndex).getSueldo();
                break;
            default:
                value = null;
                throw new AssertionError();
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
    
}
