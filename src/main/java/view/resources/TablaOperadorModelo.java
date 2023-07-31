/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.resources;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Operador;

/**
 *
 * @author Ariel
 */
public class TablaOperadorModelo extends AbstractTableModel {

    private static final String[] COLUMNAS = {"N°", "Nombre", "Apellido", "DNI", "Tipo Empleado", "Departamento"};
    private List<Operador> operadores;

    public TablaOperadorModelo() {
        operadores = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return operadores == null ? 0 : operadores.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retorno = null;
        Operador operador = operadores.get(rowIndex);

        switch (columnIndex) {
            case 0:
                retorno = rowIndex;
                break;
            case 1:
                retorno = operador.getNombre();
                break;
            case 2:
                retorno = operador.getApellido();
                break;
            case 3:
                retorno = operador.getDni();
                break;
            case 4:
                retorno = operador.getUnTipoOperador().getDescripcion();
                break;
            case 5:
                retorno = operador.getUnDepartamentoA().getNombre();
                break;
        }

        return retorno;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    public void setOperadores(List<Operador> operadores) {
        this.operadores = operadores;
    }

    public Operador obtenerOperadorEn(int fila) {
        return operadores.get(fila);
    }

    public int buscarFilaOperador(Operador empleadoBuscado) {
        int fila = 0;
        int contador = 0;
        for (Operador empleadoRecorrido : operadores) {
            contador = contador + 1;
            if (empleadoBuscado.getId() == empleadoRecorrido.getId()) {
                fila = contador;
            }
        }
        return fila;
    }

}
