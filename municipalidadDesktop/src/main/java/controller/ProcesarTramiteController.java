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
import model.Tramite;
import model.Turno;

/**
 *
 * @author Ariel
 */
public class ProcesarTramiteController {

    private final Tramite nuevoTramite;

    private final TurnoJpaController turnoDAO;

    private final DepartamentoJpaController departamentoDAO;
    private final OperadorJpaController empleadoDAO;
    private final TipoOperadorJpaController tipoEmpleadoDAO;
    private final HorarioAtencionTurnoJpaController horarioTurnoDAO;
    private final EstadoTurnoJpaController estadoTurnoDAO;

    public ProcesarTramiteController() {

        this.turnoDAO = new TurnoJpaController(Conexion.getEmf());

        this.nuevoTramite = new Tramite();

        departamentoDAO = new DepartamentoJpaController(Conexion.getEmf());

        empleadoDAO = new OperadorJpaController(Conexion.getEmf());

        tipoEmpleadoDAO = new TipoOperadorJpaController(Conexion.getEmf());

        horarioTurnoDAO = new HorarioAtencionTurnoJpaController(Conexion.getEmf());

        estadoTurnoDAO = new EstadoTurnoJpaController(Conexion.getEmf());
    }

    public List<Turno> buscarTodosLosTurnos() {
        List<Turno> turnosEncontrados = new ArrayList<>();

        LoginController.getInstanceUsuario().getUnOperador();

        for (Turno turnoRecorrido : turnoDAO.findTurnoEntities()) {
            turnosEncontrados.add(turnoRecorrido);
        }
        return turnosEncontrados;
    }

    public Tramite getNuevoTramite() {
        return nuevoTramite;
    }

    public List<Turno> buscarTodosLosTurnosDeUnEmpleadoEnElDiaActual() {
        List<Turno> turnosParaAtenderA = new ArrayList<>();
        List<Turno> turnosParaAtenderOrdenado = turnoDAO.buscarTurnosDelEmpleado(LoginController.getInstanceUsuario().getUnOperador().getUnDepartamentoA(), LoginController.getInstanceUsuario().getUnOperador(), estadoTurnoDAO.findEstadoTurno(1l), new Date());
        //Todos los turnos de un area
        for (Turno tr : turnosParaAtenderOrdenado) {
            turnosParaAtenderA.add(tr);
        }
        
        
        return turnosParaAtenderA;
    }
    
    
    

}
