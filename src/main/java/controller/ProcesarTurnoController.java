/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DepartamentoJpaController;
import dao.Conexion;
import dao.OperadorJpaController;
import dao.EstadoTurnoJpaController;
import dao.HorarioAtencionTurnoJpaController;
import dao.TipoOperadorJpaController;
import dao.TurnoJpaController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import model.Departamento;
import model.Operador;
import model.HorarioAtencionTurno;
import model.Persona;
import model.TipoOperador;
import model.TipoTramite;
import model.Turno;

/**
 *
 * @author Ariel
 */
public class ProcesarTurnoController {

    private final Turno nuevoTurno;

    private final DepartamentoJpaController departamentoDAO;
    private final OperadorJpaController operadorDAO;
    private final TipoOperadorJpaController tipoOperadorDAO;
    private final TurnoJpaController turnoDAO;
    private final HorarioAtencionTurnoJpaController horarioTurnoDAO;
    private final EstadoTurnoJpaController estadoTurnoDAO;

    public ProcesarTurnoController(Persona unaPersona) {

        nuevoTurno = new Turno();
        nuevoTurno.setUnaPersona(unaPersona);
        nuevoTurno.setUnOperador(LoginController.getInstanceUsuario().getUnOperador());

        departamentoDAO = new DepartamentoJpaController(Conexion.getEmf());

        operadorDAO = new OperadorJpaController(Conexion.getEmf());

        tipoOperadorDAO = new TipoOperadorJpaController(Conexion.getEmf());

        turnoDAO = new TurnoJpaController(Conexion.getEmf());

        horarioTurnoDAO = new HorarioAtencionTurnoJpaController(Conexion.getEmf());

        estadoTurnoDAO = new EstadoTurnoJpaController(Conexion.getEmf());
    }

    public Vector<Departamento> buscarTodosLosDepartamentos() {
        Vector<Departamento> departamentosEncontrados = new Vector<>();
        for (Departamento departamentoRecorrido : departamentoDAO.findDepartamentoEntities()) {
            departamentosEncontrados.add(departamentoRecorrido);
        }
        return departamentosEncontrados;
    }

    public Vector<Departamento> buscarDepartamentosParaTurnos() {
        Vector<Departamento> departamentosEncontrados = new Vector<>();
        for (Departamento departamentoRecorrido : departamentoDAO.findDepartamentoEntities()) {
            if (departamentoRecorrido.getNombre().equals("Tramites de DNI y Pasaporte") || departamentoRecorrido.getNombre().equals("Inscripciones")) {
                departamentosEncontrados.add(departamentoRecorrido);
            }
        }
        return departamentosEncontrados;
    }

    public List<TipoTramite> buscarTodosLosTramitesPorArea(Departamento unDepartamento) {
        List<TipoTramite> tiposTramitesEncontrados = new ArrayList<>();
        for (Departamento departamentoRecorrido : departamentoDAO.findDepartamentoEntities()) {
            if (unDepartamento.getId().equals(departamentoRecorrido.getId())) {
                tiposTramitesEncontrados = (List<TipoTramite>) departamentoRecorrido.getTipoTramite();
            }
        }
        return tiposTramitesEncontrados;
    }

    public Turno getNuevoTurno() {
        return nuevoTurno;
    }

    public List<Operador> buscarOperadorPorDepartamentoTipoTramite(Departamento unDepartamento) {
        List<Operador> operadoresEncontrados = new ArrayList<>();

        for (Operador operadorRecorrido : operadorDAO.findOperadorEntities()) {
            if (operadorRecorrido.getUnDepartamentoA().getId().equals(unDepartamento.getId())) {
                operadoresEncontrados.add(operadorRecorrido);
            }
        }
        return operadoresEncontrados;
    }

    public List<Operador> buscaEmpleadosPorDNI(Departamento unDepartamento,String dni) {
        List<Operador> empleadosEncontrados = new ArrayList<>();
        for (Operador empleadoRecorrido : operadorDAO.findOperadorEntities()) {
            if (empleadoRecorrido.getUnDepartamentoA().getId().equals(unDepartamento.getId())) {
                if (empleadoRecorrido.getDni().contains(dni)) {
                    empleadosEncontrados.add(empleadoRecorrido);
                }
            }
        }

        return empleadosEncontrados;
    }

    public List<TipoOperador> buscarTodosLosTiposDeEmpleados() {
        List<TipoOperador> tiposEmpleadosEncontrados = new ArrayList<>();
        for (TipoOperador tipoEmpleadoRecorrido : tipoOperadorDAO.findTipoOperadorEntities()) {
            tiposEmpleadosEncontrados.add(tipoEmpleadoRecorrido);
        }
        return tiposEmpleadosEncontrados;
    }

    public Vector<HorarioAtencionTurno> buscarHorariosDeTurnoDisponibles(Departamento unArea, Operador unEmpleado, Date unaFecha) {
        List<HorarioAtencionTurno> horariosDisponibles = turnoDAO.horarioTurnosDisponibles(unArea, unEmpleado, unaFecha);
        Vector<HorarioAtencionTurno> horariosDeAtencionDisponibles = new Vector<>();

        for (HorarioAtencionTurno horarioAtencionTurnoRecorrido : horariosDisponibles) {
            horariosDeAtencionDisponibles.add(horarioAtencionTurnoRecorrido);
        }
        return horariosDeAtencionDisponibles;
    }

    public void crearNuevoTurno(Turno nuevoTurno) {
        nuevoTurno.setUnEstadoTurno(estadoTurnoDAO.findEstadoTurno(1l));
        turnoDAO.create(nuevoTurno);
    }
    
    public void asignarEstadoTurno(){
        nuevoTurno.setUnEstadoTurno(estadoTurnoDAO.findEstadoTurno(1l));
    }

}
