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
import model.Departamento;
import model.Tramite;

/**
 *
 * @author Ariel
 */
public class TramiteJpaController implements Serializable {

    public TramiteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tramite tramite) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento unDepartamentoC = tramite.getUnDepartamentoC();
            if (unDepartamentoC != null) {
                unDepartamentoC = em.getReference(unDepartamentoC.getClass(), unDepartamentoC.getId());
                tramite.setUnDepartamentoC(unDepartamentoC);
            }
            em.persist(tramite);
            if (unDepartamentoC != null) {
                unDepartamentoC.getTramites().add(tramite);
                unDepartamentoC = em.merge(unDepartamentoC);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tramite tramite) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tramite persistentTramite = em.find(Tramite.class, tramite.getId());
            Departamento unDepartamentoCOld = persistentTramite.getUnDepartamentoC();
            Departamento unDepartamentoCNew = tramite.getUnDepartamentoC();
            if (unDepartamentoCNew != null) {
                unDepartamentoCNew = em.getReference(unDepartamentoCNew.getClass(), unDepartamentoCNew.getId());
                tramite.setUnDepartamentoC(unDepartamentoCNew);
            }
            tramite = em.merge(tramite);
            if (unDepartamentoCOld != null && !unDepartamentoCOld.equals(unDepartamentoCNew)) {
                unDepartamentoCOld.getTramites().remove(tramite);
                unDepartamentoCOld = em.merge(unDepartamentoCOld);
            }
            if (unDepartamentoCNew != null && !unDepartamentoCNew.equals(unDepartamentoCOld)) {
                unDepartamentoCNew.getTramites().add(tramite);
                unDepartamentoCNew = em.merge(unDepartamentoCNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tramite.getId();
                if (findTramite(id) == null) {
                    throw new NonexistentEntityException("The tramite with id " + id + " no longer exists.");
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
            Tramite tramite;
            try {
                tramite = em.getReference(Tramite.class, id);
                tramite.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tramite with id " + id + " no longer exists.", enfe);
            }
            Departamento unDepartamentoC = tramite.getUnDepartamentoC();
            if (unDepartamentoC != null) {
                unDepartamentoC.getTramites().remove(tramite);
                unDepartamentoC = em.merge(unDepartamentoC);
            }
            em.remove(tramite);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tramite> findTramiteEntities() {
        return findTramiteEntities(true, -1, -1);
    }

    public List<Tramite> findTramiteEntities(int maxResults, int firstResult) {
        return findTramiteEntities(false, maxResults, firstResult);
    }

    private List<Tramite> findTramiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tramite.class));
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

    public Tramite findTramite(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tramite.class, id);
        } finally {
            em.close();
        }
    }

    public int getTramiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tramite> rt = cq.from(Tramite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
