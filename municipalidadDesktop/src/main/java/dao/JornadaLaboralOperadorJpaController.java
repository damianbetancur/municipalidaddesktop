/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Departamento;
import model.JornadaLaboralOperador;
import model.Operador;
import model.TipoJornadaLaboral;

/**
 *
 * @author Ariel
 */
public class JornadaLaboralOperadorJpaController implements Serializable {

    public JornadaLaboralOperadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JornadaLaboralOperador jornadaLaboralOperador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(jornadaLaboralOperador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JornadaLaboralOperador jornadaLaboralOperador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            jornadaLaboralOperador = em.merge(jornadaLaboralOperador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = jornadaLaboralOperador.getId();
                if (findJornadaLaboralOperador(id) == null) {
                    throw new NonexistentEntityException("The jornadaLaboralOperador with id " + id + " no longer exists.");
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
            JornadaLaboralOperador jornadaLaboralOperador;
            try {
                jornadaLaboralOperador = em.getReference(JornadaLaboralOperador.class, id);
                jornadaLaboralOperador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornadaLaboralOperador with id " + id + " no longer exists.", enfe);
            }
            em.remove(jornadaLaboralOperador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JornadaLaboralOperador> findJornadaLaboralOperadorEntities() {
        return findJornadaLaboralOperadorEntities(true, -1, -1);
    }

    public List<JornadaLaboralOperador> findJornadaLaboralOperadorEntities(int maxResults, int firstResult) {
        return findJornadaLaboralOperadorEntities(false, maxResults, firstResult);
    }

    private List<JornadaLaboralOperador> findJornadaLaboralOperadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JornadaLaboralOperador.class));
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

    public JornadaLaboralOperador findJornadaLaboralOperador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JornadaLaboralOperador.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadaLaboralOperadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JornadaLaboralOperador> rt = cq.from(JornadaLaboralOperador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public JornadaLaboralOperador buscarInicioJornadaLaboralOperador(Departamento d, Operador o, TipoJornadaLaboral tjl, Date unaFecha) {
        EntityManager em = getEntityManager();
        JornadaLaboralOperador jornadaLaboralInicio = null;
        String consulta;
        try {
            consulta ="SELECT jlo FROM JornadaLaboralOperador AS jlo WHERE jlo.unDepartamentoD.id = ?1 and jlo.unOperador.id = ?2 and jlo.unTipoJornadaLaboral.id = ?3 and jlo.unaFecha = ?4";
            Query query = em.createQuery(consulta);
            query.setParameter(1, d.getId());
            query.setParameter(2, o.getId());
            query.setParameter(3, tjl.getId());
            query.setParameter(4, unaFecha, TemporalType.DATE);
            
             List <JornadaLaboralOperador> lista = query.getResultList();
            if (!lista.isEmpty()) {
                jornadaLaboralInicio = lista.get(0);              
            }
        } catch (Exception ex) {
            throw ex;
        } finally{
            em.close();
        }
        return jornadaLaboralInicio;
    }
    
}
