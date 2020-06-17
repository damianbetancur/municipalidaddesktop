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
import model.EstadoTurno;
import model.HorarioAtencionTurno;
import model.Operador;
import model.Turno;

/**
 *
 * @author Ariel
 */
public class TurnoJpaController implements Serializable {

    public TurnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turno turno) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento unDepartamentoB = turno.getUnDepartamentoB();
            if (unDepartamentoB != null) {
                unDepartamentoB = em.getReference(unDepartamentoB.getClass(), unDepartamentoB.getId());
                turno.setUnDepartamentoB(unDepartamentoB);
            }
            em.persist(turno);
            if (unDepartamentoB != null) {
                unDepartamentoB.getTurnos().add(turno);
                unDepartamentoB = em.merge(unDepartamentoB);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turno turno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno persistentTurno = em.find(Turno.class, turno.getId());
            Departamento unDepartamentoBOld = persistentTurno.getUnDepartamentoB();
            Departamento unDepartamentoBNew = turno.getUnDepartamentoB();
            if (unDepartamentoBNew != null) {
                unDepartamentoBNew = em.getReference(unDepartamentoBNew.getClass(), unDepartamentoBNew.getId());
                turno.setUnDepartamentoB(unDepartamentoBNew);
            }
            turno = em.merge(turno);
            if (unDepartamentoBOld != null && !unDepartamentoBOld.equals(unDepartamentoBNew)) {
                unDepartamentoBOld.getTurnos().remove(turno);
                unDepartamentoBOld = em.merge(unDepartamentoBOld);
            }
            if (unDepartamentoBNew != null && !unDepartamentoBNew.equals(unDepartamentoBOld)) {
                unDepartamentoBNew.getTurnos().add(turno);
                unDepartamentoBNew = em.merge(unDepartamentoBNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = turno.getId();
                if (findTurno(id) == null) {
                    throw new NonexistentEntityException("The turno with id " + id + " no longer exists.");
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
            Turno turno;
            try {
                turno = em.getReference(Turno.class, id);
                turno.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turno with id " + id + " no longer exists.", enfe);
            }
            Departamento unDepartamentoB = turno.getUnDepartamentoB();
            if (unDepartamentoB != null) {
                unDepartamentoB.getTurnos().remove(turno);
                unDepartamentoB = em.merge(unDepartamentoB);
            }
            em.remove(turno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnoEntities() {
        return findTurnoEntities(true, -1, -1);
    }

    public List<Turno> findTurnoEntities(int maxResults, int firstResult) {
        return findTurnoEntities(false, maxResults, firstResult);
    }

    private List<Turno> findTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turno.class));
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

    public Turno findTurno(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turno.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turno> rt = cq.from(Turno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<HorarioAtencionTurno> horarioTurnosDisponibles(Departamento a, Operador o, Date unaFecha) {
        EntityManager em = getEntityManager();
        List <HorarioAtencionTurno> horariosDisponibles = new ArrayList<>();
        String consulta;
        try {
            consulta ="SELECT hat FROM HorarioAtencionTurno AS hat "
                                                                   + "WHERE hat.id not in ("
                                                                   + "SELECT t.unaHoraTurno.id FROM Turno AS t WHERE t.unDepartamentoB.id = ?1 and t.unOperador.id = ?2 and t.fecha = ?3)";
            Query query = em.createQuery(consulta);
            query.setParameter(1, a.getId());
            query.setParameter(2, o.getId());
            query.setParameter(3, unaFecha, TemporalType.DATE);
            
            horariosDisponibles = query.getResultList();
        } catch (Exception ex) {
            throw ex;
        } finally{
            em.close();
        }
        return horariosDisponibles;
    }
    
    public List<Turno> buscarTurnosDelEmpleado(Departamento a, Operador o, EstadoTurno et, Date unaFecha) {
        EntityManager em = getEntityManager();
        List <Turno> turnos = new ArrayList<>();
        String consulta;
        try {
            consulta ="SELECT t FROM Turno AS t WHERE t.unDepartamentoB.id = ?1 and t.unOperador.id = ?2 and t.unEstadoTurno.id = ?3 and t.fecha = ?4 ORDER BY t.unaHoraTurno.hora ASC, t.unaHoraTurno.minuto ";
            Query query = em.createQuery(consulta);
            query.setParameter(1, a.getId());
            query.setParameter(2, o.getId());
            query.setParameter(3, et.getId());
            query.setParameter(4, unaFecha, TemporalType.DATE);
            
            turnos = query.getResultList();
        } catch (Exception ex) {
            throw ex;
        } finally{
            em.close();
        }
        return turnos;
    }
    
}
