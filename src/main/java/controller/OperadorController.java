/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import dao.Conexion;
import dao.DepartamentoJpaController;
import dao.OperadorJpaController;
import dao.TipoOperadorJpaController;
import dao.TurnoJpaController;
import dao.UsuarioJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import model.Departamento;
import model.Operador;
import model.Municipalidad;
import model.TipoOperador;
import model.Turno;
import model.Usuario;

/**
 *
 * @author Ariel
 */
public class OperadorController {
    //llamar al organismo Singleton
    //DAO
    private final OperadorJpaController operadorDAO;
    private final TipoOperadorJpaController tipoOperadorDAO;
    private final DepartamentoJpaController departamentoDAO;

    private final UsuarioJpaController usuarioDAO;
    private final TurnoJpaController turnoDAO;

    //Model
    private static Municipalidad municipalidadInstanciaUnica;

    public OperadorController() {
        this.municipalidadInstanciaUnica = LoginController.getInstanceMunicipalidad();

        this.operadorDAO = new OperadorJpaController(Conexion.getEmf());
        this.tipoOperadorDAO = new TipoOperadorJpaController(Conexion.getEmf());
        this.usuarioDAO = new UsuarioJpaController(Conexion.getEmf());
        this.turnoDAO = new TurnoJpaController(Conexion.getEmf());
        this.departamentoDAO = new DepartamentoJpaController(Conexion.getEmf());
    }

    public boolean agregarOperador(Operador nuevoOperador) {
        //verifica que el dni sea unico
        boolean dniPermitido = true;
        for (Operador operadorRecorrido : operadorDAO.findOperadorEntities()) {
            if (nuevoOperador.getDni().equals(operadorRecorrido.getDni())) {
                dniPermitido = false;
            }
        }

        if (dniPermitido) {
            nuevoOperador.setUnaMunicipalidadC(municipalidadInstanciaUnica);
            operadorDAO.create(nuevoOperador);
        }
        return dniPermitido;
    }

    public boolean modificarOperador(Operador actualOperador, Operador nuevoOperador) throws Exception {
        //verifica que el dni sea unico
        nuevoOperador.setId(actualOperador.getId());
        nuevoOperador.setUnaMunicipalidadC(municipalidadInstanciaUnica);
        nuevoOperador.getUnDepartamentoA().setId(actualOperador.getUnDepartamentoA().getId());
        boolean dniPermitido = true;
        if (actualOperador.getDni().equals(nuevoOperador.getDni())) {
            operadorDAO.edit(nuevoOperador);
        } else {
            for (Operador empleadoRecorrido : operadorDAO.findOperadorEntities()) {
                if (nuevoOperador.getDni().equals(empleadoRecorrido.getDni())) {
                    dniPermitido = false;
                }
            }
            if (dniPermitido) {
                operadorDAO.edit(nuevoOperador);
            }
        }
        return dniPermitido;
    }

    public boolean eliminarOperador(Operador nuevoOperador) throws NonexistentEntityException {
        boolean estadoEliminacionUsuario = true;
        if (verificarEliminarOperador(nuevoOperador)) {
            operadorDAO.destroy(nuevoOperador.getId());
        } else {
            estadoEliminacionUsuario = false;
        }
        return estadoEliminacionUsuario;
    }

    public List<Operador> buscarTodosLosOperadores() {
        List<Operador> operadoresEncontrados = new ArrayList<>();
        operadoresEncontrados = operadorDAO.findOperadorEntities();
        return operadoresEncontrados;
    }

    public List<Operador> buscarOperadoresPorDNI(String dni) {
        List<Operador> operadoresEncontrados = new ArrayList<>();
        for (Operador operadorRecorrido : operadorDAO.findOperadorEntities()) {
            if (operadorRecorrido.getDni().contains(dni)) {
                operadoresEncontrados.add(operadorRecorrido);
            }
        }
        return operadoresEncontrados;
    }

    public Vector<TipoOperador> buscarTodosLosTiposDeoperadores() {
        Vector<TipoOperador> tiposOperadoresEncontrados = new Vector<>();
        for (TipoOperador tipoOperadorRecorrido : tipoOperadorDAO.findTipoOperadorEntities()) {
            tiposOperadoresEncontrados.add(tipoOperadorRecorrido);
        }
        return tiposOperadoresEncontrados;
    }
    
    public Vector<Departamento> buscarTodasLosDepartamentos() {
        Vector<Departamento> departamentosEncontrados = new Vector<>();
        for (Departamento departamentoRecorrido : departamentoDAO.findDepartamentoEntities()) {
            departamentosEncontrados.add(departamentoRecorrido);
        }
        return departamentosEncontrados;
    }

    private boolean verificarEliminarOperador(Operador operadorAEliminar) {
        boolean estadoEliminacionUsuario = true;

        for (Usuario usuarioRecorrido : usuarioDAO.findUsuarioEntities()) {
            if (operadorAEliminar.getId().equals(usuarioRecorrido.getId())) {
                estadoEliminacionUsuario = false;
            }
        }
        for (Turno turnoRecorrido : turnoDAO.findTurnoEntities()) {
            if (operadorAEliminar.getId().equals(turnoRecorrido.getUnaPersona().getId())) {
                estadoEliminacionUsuario = false;
            }
        }
        return estadoEliminacionUsuario;
    }
}
