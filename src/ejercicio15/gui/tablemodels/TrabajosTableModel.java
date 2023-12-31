/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ejercicio15.gui.tablemodels;

import ejercicio15.dto.Trabajo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Table model para tablas que muestren trabajos
 * @author Jose Javier BO
 */
public class TrabajosTableModel extends AbstractTableModel{

    

    //ATRIBUTOS:
    private final List<Trabajo> listaTrabajos;//lista actual de trabajos
    private final String[] columnas = new String[]{"ID", "Nombre", "Fecha"};
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
    //METODOS:
    //Constructor
    public TrabajosTableModel(List<Trabajo> listaTrabajos) {
        this.listaTrabajos = listaTrabajos;
    }

    @Override
    public int getRowCount() {
        return this.listaTrabajos.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value;
        switch (columnIndex) {
            case 0 -> value = Math.abs(listaTrabajos.get(rowIndex).getId());
            case 1 -> value = listaTrabajos.get(rowIndex).getNombre();
            case 2 -> value = sdf.format(new Date(listaTrabajos.get(rowIndex).getFecha()));
            default -> {
                value = null;
                throw new AssertionError();
            }
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
    
     @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class<?> value;
        switch (columnIndex) {
            case 0 -> value = Integer.class;
            case 1 -> value = String.class;
            case 2 -> value = String.class;
            default -> {
                value = null;
                throw new AssertionError();
            }
        }
        return value;    
    }
}
