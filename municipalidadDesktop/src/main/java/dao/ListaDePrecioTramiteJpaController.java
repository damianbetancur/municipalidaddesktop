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
import model.ListaDePrecioTramite;

/**
 *
 * @author Ariel
 */
public class ListaDePrecioTramiteJpaController implements Serializable {

    public ListaDePrecioTramiteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaDePrecioTramite listaDePrecioTramite) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(listaDePrecioTramite);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaDePrecioTramite listaDePrecioTramite) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            listaDePrecioTramite = em.merge(listaDePrecioTramite);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = listaDePrecioTramite.getId();
                if (findListaDePrecioTramite(id) == null) {
                    throw new NonexistentEntityException("The listaDePrecioTramite with id " + id + " no longer exists.");
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
            ListaDePrecioTramite listaDePrecioTramite;
            try {
                listaDePrecioTramite = em.getReference(ListaDePrecioTramite.class, id);
                listaDePrecioTramite.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaDePrecioTramite with id " + id + " no longer exists.", enfe);
            }
            em.remove(listaDePrecioTramite);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListaDePrecioTramite> findListaDePrecioTramiteEntities() {
        return findListaDePrecioTramiteEntities(true, -1, -1);
    }

    public List<ListaDePrecioTramite> findListaDePrecioTramiteEntities(int maxResults, int firstResult) {
        return findListaDePrecioTramiteEntities(false, maxResults, firstResult);
    }

    private List<ListaDePrecioTramite> findListaDePrecioTramiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaDePrecioTramite.class));
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

    public ListaDePrecioTramite findListaDePrecioTramite(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaDePrecioTramite.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaDePrecioTramiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaDePrecioTramite> rt = cq.from(ListaDePrecioTramite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
