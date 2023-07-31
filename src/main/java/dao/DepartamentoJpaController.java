/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Municipalidad;
import model.Operador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Departamento;
import model.Turno;
import model.Tramite;
import model.TipoTramite;

/**
 *
 * @author Ariel
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getOperadores() == null) {
            departamento.setOperadores(new ArrayList<Operador>());
        }
        if (departamento.getTurnos() == null) {
            departamento.setTurnos(new ArrayList<Turno>());
        }
        if (departamento.getTramites() == null) {
            departamento.setTramites(new ArrayList<Tramite>());
        }
        if (departamento.getTipoTramite() == null) {
            departamento.setTipoTramite(new ArrayList<TipoTramite>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipalidad unaMunicipalidadA = departamento.getUnaMunicipalidadA();
            if (unaMunicipalidadA != null) {
                unaMunicipalidadA = em.getReference(unaMunicipalidadA.getClass(), unaMunicipalidadA.getId());
                departamento.setUnaMunicipalidadA(unaMunicipalidadA);
            }
            List<Operador> attachedEmpleados = new ArrayList<Operador>();
            for (Operador empleadosOperadorToAttach : departamento.getOperadores()) {
                empleadosOperadorToAttach = em.getReference(empleadosOperadorToAttach.getClass(), empleadosOperadorToAttach.getId());
                attachedEmpleados.add(empleadosOperadorToAttach);
            }
            departamento.setOperadores(attachedEmpleados);
            List<Turno> attachedTurnos = new ArrayList<Turno>();
            for (Turno turnosTurnoToAttach : departamento.getTurnos()) {
                turnosTurnoToAttach = em.getReference(turnosTurnoToAttach.getClass(), turnosTurnoToAttach.getId());
                attachedTurnos.add(turnosTurnoToAttach);
            }
            departamento.setTurnos(attachedTurnos);
            List<Tramite> attachedTramites = new ArrayList<Tramite>();
            for (Tramite tramitesTramiteToAttach : departamento.getTramites()) {
                tramitesTramiteToAttach = em.getReference(tramitesTramiteToAttach.getClass(), tramitesTramiteToAttach.getId());
                attachedTramites.add(tramitesTramiteToAttach);
            }
            departamento.setTramites(attachedTramites);
            List<TipoTramite> attachedTipoTramite = new ArrayList<TipoTramite>();
            for (TipoTramite tipoTramiteTipoTramiteToAttach : departamento.getTipoTramite()) {
                tipoTramiteTipoTramiteToAttach = em.getReference(tipoTramiteTipoTramiteToAttach.getClass(), tipoTramiteTipoTramiteToAttach.getId());
                attachedTipoTramite.add(tipoTramiteTipoTramiteToAttach);
            }
            departamento.setTipoTramite(attachedTipoTramite);
            em.persist(departamento);
            if (unaMunicipalidadA != null) {
                unaMunicipalidadA.getDepartamentos().add(departamento);
                unaMunicipalidadA = em.merge(unaMunicipalidadA);
            }
            for (Operador empleadosOperador : departamento.getOperadores()) {
                Departamento oldUnDepartamentoAOfEmpleadosOperador = empleadosOperador.getUnDepartamentoA();
                empleadosOperador.setUnDepartamentoA(departamento);
                empleadosOperador = em.merge(empleadosOperador);
                if (oldUnDepartamentoAOfEmpleadosOperador != null) {
                    oldUnDepartamentoAOfEmpleadosOperador.getOperadores().remove(empleadosOperador);
                    oldUnDepartamentoAOfEmpleadosOperador = em.merge(oldUnDepartamentoAOfEmpleadosOperador);
                }
            }
            for (Turno turnosTurno : departamento.getTurnos()) {
                Departamento oldUnDepartamentoBOfTurnosTurno = turnosTurno.getUnDepartamentoB();
                turnosTurno.setUnDepartamentoB(departamento);
                turnosTurno = em.merge(turnosTurno);
                if (oldUnDepartamentoBOfTurnosTurno != null) {
                    oldUnDepartamentoBOfTurnosTurno.getTurnos().remove(turnosTurno);
                    oldUnDepartamentoBOfTurnosTurno = em.merge(oldUnDepartamentoBOfTurnosTurno);
                }
            }
            for (Tramite tramitesTramite : departamento.getTramites()) {
                Departamento oldUnDepartamentoCOfTramitesTramite = tramitesTramite.getUnDepartamentoC();
                tramitesTramite.setUnDepartamentoC(departamento);
                tramitesTramite = em.merge(tramitesTramite);
                if (oldUnDepartamentoCOfTramitesTramite != null) {
                    oldUnDepartamentoCOfTramitesTramite.getTramites().remove(tramitesTramite);
                    oldUnDepartamentoCOfTramitesTramite = em.merge(oldUnDepartamentoCOfTramitesTramite);
                }
            }
            for (TipoTramite tipoTramiteTipoTramite : departamento.getTipoTramite()) {
                tipoTramiteTipoTramite.getDepartamentos().add(departamento);
                tipoTramiteTipoTramite = em.merge(tipoTramiteTipoTramite);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getId());
            Municipalidad unaMunicipalidadAOld = persistentDepartamento.getUnaMunicipalidadA();
            Municipalidad unaMunicipalidadANew = departamento.getUnaMunicipalidadA();
            List<Operador> empleadosOld = persistentDepartamento.getOperadores();
            List<Operador> empleadosNew = departamento.getOperadores();
            List<Turno> turnosOld = persistentDepartamento.getTurnos();
            List<Turno> turnosNew = departamento.getTurnos();
            List<Tramite> tramitesOld = persistentDepartamento.getTramites();
            List<Tramite> tramitesNew = departamento.getTramites();
            List<TipoTramite> tipoTramiteOld = persistentDepartamento.getTipoTramite();
            List<TipoTramite> tipoTramiteNew = departamento.getTipoTramite();
            List<String> illegalOrphanMessages = null;
            for (Operador empleadosOldOperador : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldOperador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Operador " + empleadosOldOperador + " since its unDepartamentoA field is not nullable.");
                }
            }
            for (Turno turnosOldTurno : turnosOld) {
                if (!turnosNew.contains(turnosOldTurno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Turno " + turnosOldTurno + " since its unDepartamentoB field is not nullable.");
                }
            }
            for (Tramite tramitesOldTramite : tramitesOld) {
                if (!tramitesNew.contains(tramitesOldTramite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tramite " + tramitesOldTramite + " since its unDepartamentoC field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (unaMunicipalidadANew != null) {
                unaMunicipalidadANew = em.getReference(unaMunicipalidadANew.getClass(), unaMunicipalidadANew.getId());
                departamento.setUnaMunicipalidadA(unaMunicipalidadANew);
            }
            List<Operador> attachedEmpleadosNew = new ArrayList<Operador>();
            for (Operador empleadosNewOperadorToAttach : empleadosNew) {
                empleadosNewOperadorToAttach = em.getReference(empleadosNewOperadorToAttach.getClass(), empleadosNewOperadorToAttach.getId());
                attachedEmpleadosNew.add(empleadosNewOperadorToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            departamento.setOperadores(empleadosNew);
            List<Turno> attachedTurnosNew = new ArrayList<Turno>();
            for (Turno turnosNewTurnoToAttach : turnosNew) {
                turnosNewTurnoToAttach = em.getReference(turnosNewTurnoToAttach.getClass(), turnosNewTurnoToAttach.getId());
                attachedTurnosNew.add(turnosNewTurnoToAttach);
            }
            turnosNew = attachedTurnosNew;
            departamento.setTurnos(turnosNew);
            List<Tramite> attachedTramitesNew = new ArrayList<Tramite>();
            for (Tramite tramitesNewTramiteToAttach : tramitesNew) {
                tramitesNewTramiteToAttach = em.getReference(tramitesNewTramiteToAttach.getClass(), tramitesNewTramiteToAttach.getId());
                attachedTramitesNew.add(tramitesNewTramiteToAttach);
            }
            tramitesNew = attachedTramitesNew;
            departamento.setTramites(tramitesNew);
            List<TipoTramite> attachedTipoTramiteNew = new ArrayList<TipoTramite>();
            for (TipoTramite tipoTramiteNewTipoTramiteToAttach : tipoTramiteNew) {
                tipoTramiteNewTipoTramiteToAttach = em.getReference(tipoTramiteNewTipoTramiteToAttach.getClass(), tipoTramiteNewTipoTramiteToAttach.getId());
                attachedTipoTramiteNew.add(tipoTramiteNewTipoTramiteToAttach);
            }
            tipoTramiteNew = attachedTipoTramiteNew;
            departamento.setTipoTramite(tipoTramiteNew);
            departamento = em.merge(departamento);
            if (unaMunicipalidadAOld != null && !unaMunicipalidadAOld.equals(unaMunicipalidadANew)) {
                unaMunicipalidadAOld.getDepartamentos().remove(departamento);
                unaMunicipalidadAOld = em.merge(unaMunicipalidadAOld);
            }
            if (unaMunicipalidadANew != null && !unaMunicipalidadANew.equals(unaMunicipalidadAOld)) {
                unaMunicipalidadANew.getDepartamentos().add(departamento);
                unaMunicipalidadANew = em.merge(unaMunicipalidadANew);
            }
            for (Operador empleadosNewOperador : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewOperador)) {
                    Departamento oldUnDepartamentoAOfEmpleadosNewOperador = empleadosNewOperador.getUnDepartamentoA();
                    empleadosNewOperador.setUnDepartamentoA(departamento);
                    empleadosNewOperador = em.merge(empleadosNewOperador);
                    if (oldUnDepartamentoAOfEmpleadosNewOperador != null && !oldUnDepartamentoAOfEmpleadosNewOperador.equals(departamento)) {
                        oldUnDepartamentoAOfEmpleadosNewOperador.getOperadores().remove(empleadosNewOperador);
                        oldUnDepartamentoAOfEmpleadosNewOperador = em.merge(oldUnDepartamentoAOfEmpleadosNewOperador);
                    }
                }
            }
            for (Turno turnosNewTurno : turnosNew) {
                if (!turnosOld.contains(turnosNewTurno)) {
                    Departamento oldUnDepartamentoBOfTurnosNewTurno = turnosNewTurno.getUnDepartamentoB();
                    turnosNewTurno.setUnDepartamentoB(departamento);
                    turnosNewTurno = em.merge(turnosNewTurno);
                    if (oldUnDepartamentoBOfTurnosNewTurno != null && !oldUnDepartamentoBOfTurnosNewTurno.equals(departamento)) {
                        oldUnDepartamentoBOfTurnosNewTurno.getTurnos().remove(turnosNewTurno);
                        oldUnDepartamentoBOfTurnosNewTurno = em.merge(oldUnDepartamentoBOfTurnosNewTurno);
                    }
                }
            }
            for (Tramite tramitesNewTramite : tramitesNew) {
                if (!tramitesOld.contains(tramitesNewTramite)) {
                    Departamento oldUnDepartamentoCOfTramitesNewTramite = tramitesNewTramite.getUnDepartamentoC();
                    tramitesNewTramite.setUnDepartamentoC(departamento);
                    tramitesNewTramite = em.merge(tramitesNewTramite);
                    if (oldUnDepartamentoCOfTramitesNewTramite != null && !oldUnDepartamentoCOfTramitesNewTramite.equals(departamento)) {
                        oldUnDepartamentoCOfTramitesNewTramite.getTramites().remove(tramitesNewTramite);
                        oldUnDepartamentoCOfTramitesNewTramite = em.merge(oldUnDepartamentoCOfTramitesNewTramite);
                    }
                }
            }
            for (TipoTramite tipoTramiteOldTipoTramite : tipoTramiteOld) {
                if (!tipoTramiteNew.contains(tipoTramiteOldTipoTramite)) {
                    tipoTramiteOldTipoTramite.getDepartamentos().remove(departamento);
                    tipoTramiteOldTipoTramite = em.merge(tipoTramiteOldTipoTramite);
                }
            }
            for (TipoTramite tipoTramiteNewTipoTramite : tipoTramiteNew) {
                if (!tipoTramiteOld.contains(tipoTramiteNewTipoTramite)) {
                    tipoTramiteNewTipoTramite.getDepartamentos().add(departamento);
                    tipoTramiteNewTipoTramite = em.merge(tipoTramiteNewTipoTramite);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = departamento.getId();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Operador> empleadosOrphanCheck = departamento.getOperadores();
            for (Operador empleadosOrphanCheckOperador : empleadosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Operador " + empleadosOrphanCheckOperador + " in its empleados field has a non-nullable unDepartamentoA field.");
            }
            List<Turno> turnosOrphanCheck = departamento.getTurnos();
            for (Turno turnosOrphanCheckTurno : turnosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Turno " + turnosOrphanCheckTurno + " in its turnos field has a non-nullable unDepartamentoB field.");
            }
            List<Tramite> tramitesOrphanCheck = departamento.getTramites();
            for (Tramite tramitesOrphanCheckTramite : tramitesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Tramite " + tramitesOrphanCheckTramite + " in its tramites field has a non-nullable unDepartamentoC field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Municipalidad unaMunicipalidadA = departamento.getUnaMunicipalidadA();
            if (unaMunicipalidadA != null) {
                unaMunicipalidadA.getDepartamentos().remove(departamento);
                unaMunicipalidadA = em.merge(unaMunicipalidadA);
            }
            List<TipoTramite> tipoTramite = departamento.getTipoTramite();
            for (TipoTramite tipoTramiteTipoTramite : tipoTramite) {
                tipoTramiteTipoTramite.getDepartamentos().remove(departamento);
                tipoTramiteTipoTramite = em.merge(tipoTramiteTipoTramite);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Departamento findDepartamento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
