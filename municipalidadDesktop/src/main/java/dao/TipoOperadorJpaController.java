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
import model.TipoOperador;

/**
 *
 * @author Ariel
 */
public class TipoOperadorJpaController implements Serializable {

    public TipoOperadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoOperador tipoOperador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoOperador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoOperador tipoOperador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoOperador = em.merge(tipoOperador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoOperador.getId();
                if (findTipoOperador(id) == null) {
                    throw new NonexistentEntityException("The tipoOperador with id " + id + " no longer exists.");
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
            TipoOperador tipoOperador;
            try {
                tipoOperador = em.getReference(TipoOperador.class, id);
                tipoOperador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoOperador with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoOperador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoOperador> findTipoOperadorEntities() {
        return findTipoOperadorEntities(true, -1, -1);
    }

    public List<TipoOperador> findTipoOperadorEntities(int maxResults, int firstResult) {
        return findTipoOperadorEntities(false, maxResults, firstResult);
    }

    private List<TipoOperador> findTipoOperadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoOperador.class));
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

    public TipoOperador findTipoOperador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoOperador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoOperadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoOperador> rt = cq.from(TipoOperador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
