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
import model.Departamento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Municipalidad;
import model.Persona;
import model.Operador;

/**
 *
 * @author Ariel
 */
public class MunicipalidadJpaController implements Serializable {

    public MunicipalidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipalidad municipalidad) {
        if (municipalidad.getDepartamentos() == null) {
            municipalidad.setDepartamentos(new ArrayList<Departamento>());
        }
        if (municipalidad.getPersonas() == null) {
            municipalidad.setPersonas(new ArrayList<Persona>());
        }
        if (municipalidad.getOperadores() == null) {
            municipalidad.setOperadores(new ArrayList<Operador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Departamento> attachedDepartamentos = new ArrayList<Departamento>();
            for (Departamento departamentosDepartamentoToAttach : municipalidad.getDepartamentos()) {
                departamentosDepartamentoToAttach = em.getReference(departamentosDepartamentoToAttach.getClass(), departamentosDepartamentoToAttach.getId());
                attachedDepartamentos.add(departamentosDepartamentoToAttach);
            }
            municipalidad.setDepartamentos(attachedDepartamentos);
            List<Persona> attachedPersonas = new ArrayList<Persona>();
            for (Persona personasPersonaToAttach : municipalidad.getPersonas()) {
                personasPersonaToAttach = em.getReference(personasPersonaToAttach.getClass(), personasPersonaToAttach.getId());
                attachedPersonas.add(personasPersonaToAttach);
            }
            municipalidad.setPersonas(attachedPersonas);
            List<Operador> attachedOperadores = new ArrayList<Operador>();
            for (Operador operadoresOperadorToAttach : municipalidad.getOperadores()) {
                operadoresOperadorToAttach = em.getReference(operadoresOperadorToAttach.getClass(), operadoresOperadorToAttach.getId());
                attachedOperadores.add(operadoresOperadorToAttach);
            }
            municipalidad.setOperadores(attachedOperadores);
            em.persist(municipalidad);
            for (Departamento departamentosDepartamento : municipalidad.getDepartamentos()) {
                Municipalidad oldUnaMunicipalidadAOfDepartamentosDepartamento = departamentosDepartamento.getUnaMunicipalidadA();
                departamentosDepartamento.setUnaMunicipalidadA(municipalidad);
                departamentosDepartamento = em.merge(departamentosDepartamento);
                if (oldUnaMunicipalidadAOfDepartamentosDepartamento != null) {
                    oldUnaMunicipalidadAOfDepartamentosDepartamento.getDepartamentos().remove(departamentosDepartamento);
                    oldUnaMunicipalidadAOfDepartamentosDepartamento = em.merge(oldUnaMunicipalidadAOfDepartamentosDepartamento);
                }
            }
            for (Persona personasPersona : municipalidad.getPersonas()) {
                Municipalidad oldUnaMunicipalidadBOfPersonasPersona = personasPersona.getUnaMunicipalidadB();
                personasPersona.setUnaMunicipalidadB(municipalidad);
                personasPersona = em.merge(personasPersona);
                if (oldUnaMunicipalidadBOfPersonasPersona != null) {
                    oldUnaMunicipalidadBOfPersonasPersona.getPersonas().remove(personasPersona);
                    oldUnaMunicipalidadBOfPersonasPersona = em.merge(oldUnaMunicipalidadBOfPersonasPersona);
                }
            }
            for (Operador operadoresOperador : municipalidad.getOperadores()) {
                Municipalidad oldUnaMunicipalidadCOfOperadoresOperador = operadoresOperador.getUnaMunicipalidadC();
                operadoresOperador.setUnaMunicipalidadC(municipalidad);
                operadoresOperador = em.merge(operadoresOperador);
                if (oldUnaMunicipalidadCOfOperadoresOperador != null) {
                    oldUnaMunicipalidadCOfOperadoresOperador.getOperadores().remove(operadoresOperador);
                    oldUnaMunicipalidadCOfOperadoresOperador = em.merge(oldUnaMunicipalidadCOfOperadoresOperador);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipalidad municipalidad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipalidad persistentMunicipalidad = em.find(Municipalidad.class, municipalidad.getId());
            List<Departamento> departamentosOld = persistentMunicipalidad.getDepartamentos();
            List<Departamento> departamentosNew = municipalidad.getDepartamentos();
            List<Persona> personasOld = persistentMunicipalidad.getPersonas();
            List<Persona> personasNew = municipalidad.getPersonas();
            List<Operador> operadoresOld = persistentMunicipalidad.getOperadores();
            List<Operador> operadoresNew = municipalidad.getOperadores();
            List<String> illegalOrphanMessages = null;
            for (Departamento departamentosOldDepartamento : departamentosOld) {
                if (!departamentosNew.contains(departamentosOldDepartamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Departamento " + departamentosOldDepartamento + " since its unaMunicipalidadA field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Departamento> attachedDepartamentosNew = new ArrayList<Departamento>();
            for (Departamento departamentosNewDepartamentoToAttach : departamentosNew) {
                departamentosNewDepartamentoToAttach = em.getReference(departamentosNewDepartamentoToAttach.getClass(), departamentosNewDepartamentoToAttach.getId());
                attachedDepartamentosNew.add(departamentosNewDepartamentoToAttach);
            }
            departamentosNew = attachedDepartamentosNew;
            municipalidad.setDepartamentos(departamentosNew);
            List<Persona> attachedPersonasNew = new ArrayList<Persona>();
            for (Persona personasNewPersonaToAttach : personasNew) {
                personasNewPersonaToAttach = em.getReference(personasNewPersonaToAttach.getClass(), personasNewPersonaToAttach.getId());
                attachedPersonasNew.add(personasNewPersonaToAttach);
            }
            personasNew = attachedPersonasNew;
            municipalidad.setPersonas(personasNew);
            List<Operador> attachedOperadoresNew = new ArrayList<Operador>();
            for (Operador operadoresNewOperadorToAttach : operadoresNew) {
                operadoresNewOperadorToAttach = em.getReference(operadoresNewOperadorToAttach.getClass(), operadoresNewOperadorToAttach.getId());
                attachedOperadoresNew.add(operadoresNewOperadorToAttach);
            }
            operadoresNew = attachedOperadoresNew;
            municipalidad.setOperadores(operadoresNew);
            municipalidad = em.merge(municipalidad);
            for (Departamento departamentosNewDepartamento : departamentosNew) {
                if (!departamentosOld.contains(departamentosNewDepartamento)) {
                    Municipalidad oldUnaMunicipalidadAOfDepartamentosNewDepartamento = departamentosNewDepartamento.getUnaMunicipalidadA();
                    departamentosNewDepartamento.setUnaMunicipalidadA(municipalidad);
                    departamentosNewDepartamento = em.merge(departamentosNewDepartamento);
                    if (oldUnaMunicipalidadAOfDepartamentosNewDepartamento != null && !oldUnaMunicipalidadAOfDepartamentosNewDepartamento.equals(municipalidad)) {
                        oldUnaMunicipalidadAOfDepartamentosNewDepartamento.getDepartamentos().remove(departamentosNewDepartamento);
                        oldUnaMunicipalidadAOfDepartamentosNewDepartamento = em.merge(oldUnaMunicipalidadAOfDepartamentosNewDepartamento);
                    }
                }
            }
            for (Persona personasOldPersona : personasOld) {
                if (!personasNew.contains(personasOldPersona)) {
                    personasOldPersona.setUnaMunicipalidadB(null);
                    personasOldPersona = em.merge(personasOldPersona);
                }
            }
            for (Persona personasNewPersona : personasNew) {
                if (!personasOld.contains(personasNewPersona)) {
                    Municipalidad oldUnaMunicipalidadBOfPersonasNewPersona = personasNewPersona.getUnaMunicipalidadB();
                    personasNewPersona.setUnaMunicipalidadB(municipalidad);
                    personasNewPersona = em.merge(personasNewPersona);
                    if (oldUnaMunicipalidadBOfPersonasNewPersona != null && !oldUnaMunicipalidadBOfPersonasNewPersona.equals(municipalidad)) {
                        oldUnaMunicipalidadBOfPersonasNewPersona.getPersonas().remove(personasNewPersona);
                        oldUnaMunicipalidadBOfPersonasNewPersona = em.merge(oldUnaMunicipalidadBOfPersonasNewPersona);
                    }
                }
            }
            for (Operador operadoresOldOperador : operadoresOld) {
                if (!operadoresNew.contains(operadoresOldOperador)) {
                    operadoresOldOperador.setUnaMunicipalidadC(null);
                    operadoresOldOperador = em.merge(operadoresOldOperador);
                }
            }
            for (Operador operadoresNewOperador : operadoresNew) {
                if (!operadoresOld.contains(operadoresNewOperador)) {
                    Municipalidad oldUnaMunicipalidadCOfOperadoresNewOperador = operadoresNewOperador.getUnaMunicipalidadC();
                    operadoresNewOperador.setUnaMunicipalidadC(municipalidad);
                    operadoresNewOperador = em.merge(operadoresNewOperador);
                    if (oldUnaMunicipalidadCOfOperadoresNewOperador != null && !oldUnaMunicipalidadCOfOperadoresNewOperador.equals(municipalidad)) {
                        oldUnaMunicipalidadCOfOperadoresNewOperador.getOperadores().remove(operadoresNewOperador);
                        oldUnaMunicipalidadCOfOperadoresNewOperador = em.merge(oldUnaMunicipalidadCOfOperadoresNewOperador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = municipalidad.getId();
                if (findMunicipalidad(id) == null) {
                    throw new NonexistentEntityException("The municipalidad with id " + id + " no longer exists.");
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
            Municipalidad municipalidad;
            try {
                municipalidad = em.getReference(Municipalidad.class, id);
                municipalidad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipalidad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Departamento> departamentosOrphanCheck = municipalidad.getDepartamentos();
            for (Departamento departamentosOrphanCheckDepartamento : departamentosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipalidad (" + municipalidad + ") cannot be destroyed since the Departamento " + departamentosOrphanCheckDepartamento + " in its departamentos field has a non-nullable unaMunicipalidadA field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Persona> personas = municipalidad.getPersonas();
            for (Persona personasPersona : personas) {
                personasPersona.setUnaMunicipalidadB(null);
                personasPersona = em.merge(personasPersona);
            }
            List<Operador> operadores = municipalidad.getOperadores();
            for (Operador operadoresOperador : operadores) {
                operadoresOperador.setUnaMunicipalidadC(null);
                operadoresOperador = em.merge(operadoresOperador);
            }
            em.remove(municipalidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipalidad> findMunicipalidadEntities() {
        return findMunicipalidadEntities(true, -1, -1);
    }

    public List<Municipalidad> findMunicipalidadEntities(int maxResults, int firstResult) {
        return findMunicipalidadEntities(false, maxResults, firstResult);
    }

    private List<Municipalidad> findMunicipalidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipalidad.class));
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

    public Municipalidad findMunicipalidad(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipalidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipalidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipalidad> rt = cq.from(Municipalidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
