/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ariel
 */
@Entity
public class JornadaLaboralOperador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date unaFecha;
    
    @Temporal(TemporalType.TIME)
    @Column(name = "hora")
    private Date unaHora;

    @ManyToOne
    @JoinColumn(name = "fk_operador")
    private Operador unOperador;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_jornada_laboral")
    private TipoJornadaLaboral unTipoJornadaLaboral;

    @ManyToOne
    @JoinColumn(name = "fk_departamento", nullable = false, updatable = true)
    private Departamento unDepartamentoD;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JornadaLaboralOperador)) {
            return false;
        }
        JornadaLaboralOperador other = (JornadaLaboralOperador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.JornadaLaboralOperador[ id=" + id + " ]";
    }

    public Date getUnaFecha() {
        return unaFecha;
    }

    public void setUnaFecha(Date unaFecha) {
        this.unaFecha = unaFecha;
    }

    public Operador getUnOperador() {
        return unOperador;
    }

    public void setUnOperador(Operador unOperador) {
        this.unOperador = unOperador;
    }

    public TipoJornadaLaboral getUnTipoJornadaLaboral() {
        return unTipoJornadaLaboral;
    }

    public void setUnTipoJornadaLaboral(TipoJornadaLaboral unTipoJornadaLaboral) {
        this.unTipoJornadaLaboral = unTipoJornadaLaboral;
    }

    public Departamento getUnDepartamentoD() {
        return unDepartamentoD;
    }

    public void setUnDepartamentoD(Departamento unDepartamentoD) {
        this.unDepartamentoD = unDepartamentoD;
    }

    public Date getUnaHora() {
        return unaHora;
    }

    public void setUnaHora(Date unaHora) {
        this.unaHora = unaHora;
    }

    

}
