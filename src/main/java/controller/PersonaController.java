/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.PersonaJpaController;
import dao.TurnoJpaController;
import dao.UsuarioJpaController;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import model.Municipalidad;
import model.Persona;
import model.Turno;
import model.Usuario;

/**
 *
 * @author Ariel
 */
public class PersonaController {

    //llamar al organismo Singleton
    //DAO
    private final PersonaJpaController personaDAO;

    private final UsuarioJpaController usuarioDAO;
    private final TurnoJpaController turnoDAO;

    //Model
    private static Municipalidad municipalidadInstanciaUnica;

    public PersonaController() {
        this.municipalidadInstanciaUnica = LoginController.getInstanceMunicipalidad();

        this.personaDAO = new PersonaJpaController(Conexion.getEmf());
        this.usuarioDAO = new UsuarioJpaController(Conexion.getEmf());
        this.turnoDAO = new TurnoJpaController(Conexion.getEmf());
    }

    public boolean agregarPersona(Persona nuevaPersona) {
        //verifica que el dni sea unico
        boolean dniPermitido = true;
        for (Persona personaRecorrido : personaDAO.findPersonaEntities()) {
            if (nuevaPersona.getDni().equals(personaRecorrido.getDni())) {
                dniPermitido = false;
            }
        }

        if (dniPermitido) {
            nuevaPersona.setUnaMunicipalidadB(municipalidadInstanciaUnica);
            personaDAO.create(nuevaPersona);
        }
        return dniPermitido;
    }

    public boolean modificarPersona(Persona actualPersona, Persona nuevaPersona) throws Exception {
        //verifica que el dni sea unico
        nuevaPersona.setId(actualPersona.getId());
        nuevaPersona.setUnaMunicipalidadB(municipalidadInstanciaUnica);
        boolean dniPermitido = true;
        if (actualPersona.getDni().equals(nuevaPersona.getDni())) {
            personaDAO.edit(nuevaPersona);
        } else {
            for (Persona personaRecorrido : personaDAO.findPersonaEntities()) {
                if (nuevaPersona.getDni().equals(personaRecorrido.getDni())) {
                    dniPermitido = false;
                }
            }
            if (dniPermitido) {
                personaDAO.edit(nuevaPersona);
            }
        }
        return dniPermitido;
    }

    public boolean eliminarPersona(Persona nuevaPersona) throws NonexistentEntityException {
        boolean estadoEliminacionUsuario = true;
        if (verificarEliminarPersona(nuevaPersona)) {
            personaDAO.destroy(nuevaPersona.getId());
        } else {
            estadoEliminacionUsuario = false;
        }
        return estadoEliminacionUsuario;
    }

    public List<Persona> buscarTodasLasPersonas() {
        List<Persona> personasEncontradas = new ArrayList<>();
        personasEncontradas = personaDAO.findPersonaEntities();
        return personasEncontradas;
    }

    public List<Persona> buscarPersonasPorDNI(String dni) {
        List<Persona> personasEncontradas = new ArrayList<>();
        for (Persona personaRecorrido : personaDAO.findPersonaEntities()) {
            if (personaRecorrido.getDni().contains(dni)) {
                personasEncontradas.add(personaRecorrido);
            }
        }
        return personasEncontradas;
    }

    private boolean verificarEliminarPersona(Persona personaAEliminar) {
        boolean estadoEliminacionUsuario = true;

        for (Usuario usuarioRecorrido : usuarioDAO.findUsuarioEntities()) {
            if (personaAEliminar.getId().equals(usuarioRecorrido.getId())) {
                estadoEliminacionUsuario = false;
            }
        }
        for (Turno turnoRecorrido : turnoDAO.findTurnoEntities()) {
            if (personaAEliminar.getId().equals(turnoRecorrido.getUnaPersona().getId())) {
                estadoEliminacionUsuario = false;
            }
        }
        return estadoEliminacionUsuario;
    }
}
