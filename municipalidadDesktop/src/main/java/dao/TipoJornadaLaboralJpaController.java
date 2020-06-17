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
import model.TipoJornadaLaboral;

/**
 *
 * @author Ariel
 */
public class TipoJornadaLaboralJpaController implements Serializable {

    public TipoJornadaLaboralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoJornadaLaboral tipoJornadaLaboral) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoJornadaLaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoJornadaLaboral tipoJornadaLaboral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoJornadaLaboral = em.merge(tipoJornadaLaboral);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoJornadaLaboral.getId();
                if (findTipoJornadaLaboral(id) == null) {
                    throw new NonexistentEntityException("The tipoJornadaLaboral with id " + id + " no longer exists.");
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
            TipoJornadaLaboral tipoJornadaLaboral;
            try {
                tipoJornadaLaboral = em.getReference(TipoJornadaLaboral.class, id);
                tipoJornadaLaboral.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoJornadaLaboral with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoJornadaLaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoJornadaLaboral> findTipoJornadaLaboralEntities() {
        return findTipoJornadaLaboralEntities(true, -1, -1);
    }

    public List<TipoJornadaLaboral> findTipoJornadaLaboralEntities(int maxResults, int firstResult) {
        return findTipoJornadaLaboralEntities(false, maxResults, firstResult);
    }

    private List<TipoJornadaLaboral> findTipoJornadaLaboralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoJornadaLaboral.class));
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

    public TipoJornadaLaboral findTipoJornadaLaboral(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoJornadaLaboral.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoJornadaLaboralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoJornadaLaboral> rt = cq.from(TipoJornadaLaboral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
