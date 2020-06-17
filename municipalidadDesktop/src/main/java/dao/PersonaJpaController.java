/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Municipalidad;
import model.Persona;

/**
 *
 * @author Ariel
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipalidad unaMunicipalidadB = persona.getUnaMunicipalidadB();
            if (unaMunicipalidadB != null) {
                unaMunicipalidadB = em.getReference(unaMunicipalidadB.getClass(), unaMunicipalidadB.getId());
                persona.setUnaMunicipalidadB(unaMunicipalidadB);
            }
            em.persist(persona);
            if (unaMunicipalidadB != null) {
                unaMunicipalidadB.getPersonas().add(persona);
                unaMunicipalidadB = em.merge(unaMunicipalidadB);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getId());
            Municipalidad unaMunicipalidadBOld = persistentPersona.getUnaMunicipalidadB();
            Municipalidad unaMunicipalidadBNew = persona.getUnaMunicipalidadB();
            if (unaMunicipalidadBNew != null) {
                unaMunicipalidadBNew = em.getReference(unaMunicipalidadBNew.getClass(), unaMunicipalidadBNew.getId());
                persona.setUnaMunicipalidadB(unaMunicipalidadBNew);
            }
            persona = em.merge(persona);
            if (unaMunicipalidadBOld != null && !unaMunicipalidadBOld.equals(unaMunicipalidadBNew)) {
                unaMunicipalidadBOld.getPersonas().remove(persona);
                unaMunicipalidadBOld = em.merge(unaMunicipalidadBOld);
            }
            if (unaMunicipalidadBNew != null && !unaMunicipalidadBNew.equals(unaMunicipalidadBOld)) {
                unaMunicipalidadBNew.getPersonas().add(persona);
                unaMunicipalidadBNew = em.merge(unaMunicipalidadBNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Municipalidad unaMunicipalidadB = persona.getUnaMunicipalidadB();
            if (unaMunicipalidadB != null) {
                unaMunicipalidadB.getPersonas().remove(persona);
                unaMunicipalidadB = em.merge(unaMunicipalidadB);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
