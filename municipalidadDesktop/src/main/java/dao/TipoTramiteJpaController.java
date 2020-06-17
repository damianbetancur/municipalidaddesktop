/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
import model.Operador;
import model.TipoTramite;

/**
 *
 * @author Ariel
 */
public class TipoTramiteJpaController implements Serializable {

    public TipoTramiteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTramite tipoTramite) {
        if (tipoTramite.getDepartamentos() == null) {
            tipoTramite.setDepartamentos(new ArrayList<Departamento>());
        }
        if (tipoTramite.getEmpleados() == null) {
            tipoTramite.setEmpleados(new ArrayList<Operador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Departamento> attachedDepartamentos = new ArrayList<Departamento>();
            for (Departamento departamentosDepartamentoToAttach : tipoTramite.getDepartamentos()) {
                departamentosDepartamentoToAttach = em.getReference(departamentosDepartamentoToAttach.getClass(), departamentosDepartamentoToAttach.getId());
                attachedDepartamentos.add(departamentosDepartamentoToAttach);
            }
            tipoTramite.setDepartamentos(attachedDepartamentos);
            List<Operador> attachedEmpleados = new ArrayList<Operador>();
            for (Operador empleadosOperadorToAttach : tipoTramite.getEmpleados()) {
                empleadosOperadorToAttach = em.getReference(empleadosOperadorToAttach.getClass(), empleadosOperadorToAttach.getId());
                attachedEmpleados.add(empleadosOperadorToAttach);
            }
            tipoTramite.setEmpleados(attachedEmpleados);
            em.persist(tipoTramite);
            for (Departamento departamentosDepartamento : tipoTramite.getDepartamentos()) {
                departamentosDepartamento.getTipoTramite().add(tipoTramite);
                departamentosDepartamento = em.merge(departamentosDepartamento);
            }
            for (Operador empleadosOperador : tipoTramite.getEmpleados()) {
                empleadosOperador.getTipoTramite().add(tipoTramite);
                empleadosOperador = em.merge(empleadosOperador);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTramite tipoTramite) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTramite persistentTipoTramite = em.find(TipoTramite.class, tipoTramite.getId());
            List<Departamento> departamentosOld = persistentTipoTramite.getDepartamentos();
            List<Departamento> departamentosNew = tipoTramite.getDepartamentos();
            List<Operador> empleadosOld = persistentTipoTramite.getEmpleados();
            List<Operador> empleadosNew = tipoTramite.getEmpleados();
            List<Departamento> attachedDepartamentosNew = new ArrayList<Departamento>();
            for (Departamento departamentosNewDepartamentoToAttach : departamentosNew) {
                departamentosNewDepartamentoToAttach = em.getReference(departamentosNewDepartamentoToAttach.getClass(), departamentosNewDepartamentoToAttach.getId());
                attachedDepartamentosNew.add(departamentosNewDepartamentoToAttach);
            }
            departamentosNew = attachedDepartamentosNew;
            tipoTramite.setDepartamentos(departamentosNew);
            List<Operador> attachedEmpleadosNew = new ArrayList<Operador>();
            for (Operador empleadosNewOperadorToAttach : empleadosNew) {
                empleadosNewOperadorToAttach = em.getReference(empleadosNewOperadorToAttach.getClass(), empleadosNewOperadorToAttach.getId());
                attachedEmpleadosNew.add(empleadosNewOperadorToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            tipoTramite.setEmpleados(empleadosNew);
            tipoTramite = em.merge(tipoTramite);
            for (Departamento departamentosOldDepartamento : departamentosOld) {
                if (!departamentosNew.contains(departamentosOldDepartamento)) {
                    departamentosOldDepartamento.getTipoTramite().remove(tipoTramite);
                    departamentosOldDepartamento = em.merge(departamentosOldDepartamento);
                }
            }
            for (Departamento departamentosNewDepartamento : departamentosNew) {
                if (!departamentosOld.contains(departamentosNewDepartamento)) {
                    departamentosNewDepartamento.getTipoTramite().add(tipoTramite);
                    departamentosNewDepartamento = em.merge(departamentosNewDepartamento);
                }
            }
            for (Operador empleadosOldOperador : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldOperador)) {
                    empleadosOldOperador.getTipoTramite().remove(tipoTramite);
                    empleadosOldOperador = em.merge(empleadosOldOperador);
                }
            }
            for (Operador empleadosNewOperador : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewOperador)) {
                    empleadosNewOperador.getTipoTramite().add(tipoTramite);
                    empleadosNewOperador = em.merge(empleadosNewOperador);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoTramite.getId();
                if (findTipoTramite(id) == null) {
                    throw new NonexistentEntityException("The tipoTramite with id " + id + " no longer exists.");
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
            TipoTramite tipoTramite;
            try {
                tipoTramite = em.getReference(TipoTramite.class, id);
                tipoTramite.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTramite with id " + id + " no longer exists.", enfe);
            }
            List<Departamento> departamentos = tipoTramite.getDepartamentos();
            for (Departamento departamentosDepartamento : departamentos) {
                departamentosDepartamento.getTipoTramite().remove(tipoTramite);
                departamentosDepartamento = em.merge(departamentosDepartamento);
            }
            List<Operador> empleados = tipoTramite.getEmpleados();
            for (Operador empleadosOperador : empleados) {
                empleadosOperador.getTipoTramite().remove(tipoTramite);
                empleadosOperador = em.merge(empleadosOperador);
            }
            em.remove(tipoTramite);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTramite> findTipoTramiteEntities() {
        return findTipoTramiteEntities(true, -1, -1);
    }

    public List<TipoTramite> findTipoTramiteEntities(int maxResults, int firstResult) {
        return findTipoTramiteEntities(false, maxResults, firstResult);
    }

    private List<TipoTramite> findTipoTramiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTramite.class));
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

    public TipoTramite findTipoTramite(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTramite.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTramiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTramite> rt = cq.from(TipoTramite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
