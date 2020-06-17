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
import model.Municipalidad;
import model.TipoTramite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Operador;

/**
 *
 * @author Ariel
 */
public class OperadorJpaController implements Serializable {

    public OperadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operador operador) {
        if (operador.getTipoTramite() == null) {
            operador.setTipoTramite(new ArrayList<TipoTramite>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento unDepartamentoA = operador.getUnDepartamentoA();
            if (unDepartamentoA != null) {
                unDepartamentoA = em.getReference(unDepartamentoA.getClass(), unDepartamentoA.getId());
                operador.setUnDepartamentoA(unDepartamentoA);
            }
            Municipalidad unaMunicipalidadC = operador.getUnaMunicipalidadC();
            if (unaMunicipalidadC != null) {
                unaMunicipalidadC = em.getReference(unaMunicipalidadC.getClass(), unaMunicipalidadC.getId());
                operador.setUnaMunicipalidadC(unaMunicipalidadC);
            }
            List<TipoTramite> attachedTipoTramite = new ArrayList<TipoTramite>();
            for (TipoTramite tipoTramiteTipoTramiteToAttach : operador.getTipoTramite()) {
                tipoTramiteTipoTramiteToAttach = em.getReference(tipoTramiteTipoTramiteToAttach.getClass(), tipoTramiteTipoTramiteToAttach.getId());
                attachedTipoTramite.add(tipoTramiteTipoTramiteToAttach);
            }
            operador.setTipoTramite(attachedTipoTramite);
            em.persist(operador);
            if (unDepartamentoA != null) {
                unDepartamentoA.getOperadores().add(operador);
                unDepartamentoA = em.merge(unDepartamentoA);
            }
            if (unaMunicipalidadC != null) {
                unaMunicipalidadC.getOperadores().add(operador);
                unaMunicipalidadC = em.merge(unaMunicipalidadC);
            }
            for (TipoTramite tipoTramiteTipoTramite : operador.getTipoTramite()) {
                tipoTramiteTipoTramite.getEmpleados().add(operador);
                tipoTramiteTipoTramite = em.merge(tipoTramiteTipoTramite);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operador operador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operador persistentOperador = em.find(Operador.class, operador.getId());
            Departamento unDepartamentoAOld = persistentOperador.getUnDepartamentoA();
            Departamento unDepartamentoANew = operador.getUnDepartamentoA();
            Municipalidad unaMunicipalidadCOld = persistentOperador.getUnaMunicipalidadC();
            Municipalidad unaMunicipalidadCNew = operador.getUnaMunicipalidadC();
            List<TipoTramite> tipoTramiteOld = persistentOperador.getTipoTramite();
            List<TipoTramite> tipoTramiteNew = operador.getTipoTramite();
            if (unDepartamentoANew != null) {
                unDepartamentoANew = em.getReference(unDepartamentoANew.getClass(), unDepartamentoANew.getId());
                operador.setUnDepartamentoA(unDepartamentoANew);
            }
            if (unaMunicipalidadCNew != null) {
                unaMunicipalidadCNew = em.getReference(unaMunicipalidadCNew.getClass(), unaMunicipalidadCNew.getId());
                operador.setUnaMunicipalidadC(unaMunicipalidadCNew);
            }
            List<TipoTramite> attachedTipoTramiteNew = new ArrayList<TipoTramite>();
            for (TipoTramite tipoTramiteNewTipoTramiteToAttach : tipoTramiteNew) {
                tipoTramiteNewTipoTramiteToAttach = em.getReference(tipoTramiteNewTipoTramiteToAttach.getClass(), tipoTramiteNewTipoTramiteToAttach.getId());
                attachedTipoTramiteNew.add(tipoTramiteNewTipoTramiteToAttach);
            }
            tipoTramiteNew = attachedTipoTramiteNew;
            operador.setTipoTramite(tipoTramiteNew);
            operador = em.merge(operador);
            if (unDepartamentoAOld != null && !unDepartamentoAOld.equals(unDepartamentoANew)) {
                unDepartamentoAOld.getOperadores().remove(operador);
                unDepartamentoAOld = em.merge(unDepartamentoAOld);
            }
            if (unDepartamentoANew != null && !unDepartamentoANew.equals(unDepartamentoAOld)) {
                unDepartamentoANew.getOperadores().add(operador);
                unDepartamentoANew = em.merge(unDepartamentoANew);
            }
            if (unaMunicipalidadCOld != null && !unaMunicipalidadCOld.equals(unaMunicipalidadCNew)) {
                unaMunicipalidadCOld.getOperadores().remove(operador);
                unaMunicipalidadCOld = em.merge(unaMunicipalidadCOld);
            }
            if (unaMunicipalidadCNew != null && !unaMunicipalidadCNew.equals(unaMunicipalidadCOld)) {
                unaMunicipalidadCNew.getOperadores().add(operador);
                unaMunicipalidadCNew = em.merge(unaMunicipalidadCNew);
            }
            for (TipoTramite tipoTramiteOldTipoTramite : tipoTramiteOld) {
                if (!tipoTramiteNew.contains(tipoTramiteOldTipoTramite)) {
                    tipoTramiteOldTipoTramite.getEmpleados().remove(operador);
                    tipoTramiteOldTipoTramite = em.merge(tipoTramiteOldTipoTramite);
                }
            }
            for (TipoTramite tipoTramiteNewTipoTramite : tipoTramiteNew) {
                if (!tipoTramiteOld.contains(tipoTramiteNewTipoTramite)) {
                    tipoTramiteNewTipoTramite.getEmpleados().add(operador);
                    tipoTramiteNewTipoTramite = em.merge(tipoTramiteNewTipoTramite);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = operador.getId();
                if (findOperador(id) == null) {
                    throw new NonexistentEntityException("The operador with id " + id + " no longer exists.");
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
            Operador operador;
            try {
                operador = em.getReference(Operador.class, id);
                operador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operador with id " + id + " no longer exists.", enfe);
            }
            Departamento unDepartamentoA = operador.getUnDepartamentoA();
            if (unDepartamentoA != null) {
                unDepartamentoA.getOperadores().remove(operador);
                unDepartamentoA = em.merge(unDepartamentoA);
            }
            Municipalidad unaMunicipalidadC = operador.getUnaMunicipalidadC();
            if (unaMunicipalidadC != null) {
                unaMunicipalidadC.getOperadores().remove(operador);
                unaMunicipalidadC = em.merge(unaMunicipalidadC);
            }
            List<TipoTramite> tipoTramite = operador.getTipoTramite();
            for (TipoTramite tipoTramiteTipoTramite : tipoTramite) {
                tipoTramiteTipoTramite.getEmpleados().remove(operador);
                tipoTramiteTipoTramite = em.merge(tipoTramiteTipoTramite);
            }
            em.remove(operador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operador> findOperadorEntities() {
        return findOperadorEntities(true, -1, -1);
    }

    public List<Operador> findOperadorEntities(int maxResults, int firstResult) {
        return findOperadorEntities(false, maxResults, firstResult);
    }

    private List<Operador> findOperadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operador.class));
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

    public Operador findOperador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operador.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operador> rt = cq.from(Operador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
