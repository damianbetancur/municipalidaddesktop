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
import model.TipoDNI;

/**
 *
 * @author Ariel
 */
public class TipoDNIJpaController implements Serializable {

    public TipoDNIJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDNI tipoDNI) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoDNI);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDNI tipoDNI) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoDNI = em.merge(tipoDNI);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoDNI.getId();
                if (findTipoDNI(id) == null) {
                    throw new NonexistentEntityException("The tipoDNI with id " + id + " no longer exists.");
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
            TipoDNI tipoDNI;
            try {
                tipoDNI = em.getReference(TipoDNI.class, id);
                tipoDNI.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDNI with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoDNI);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoDNI> findTipoDNIEntities() {
        return findTipoDNIEntities(true, -1, -1);
    }

    public List<TipoDNI> findTipoDNIEntities(int maxResults, int firstResult) {
        return findTipoDNIEntities(false, maxResults, firstResult);
    }

    private List<TipoDNI> findTipoDNIEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDNI.class));
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

    public TipoDNI findTipoDNI(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDNI.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDNICount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDNI> rt = cq.from(TipoDNI.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
