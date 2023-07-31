/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "operador")
public class Operador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;

    @ManyToOne
    @JoinColumn(name = "fk_municipalidad")
    private Municipalidad unaMunicipalidadC;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_empleado")
    private TipoOperador unTipoOperador;

    @ManyToOne
    @JoinColumn(name = "fk_departamento", nullable = false, updatable = true)
    private Departamento unDepartamentoA;

    @JoinTable(
            name = "rel_Tipo_Tramite_Empleado",
            joinColumns = @JoinColumn(name = "FK_Empleado"),
            inverseJoinColumns = @JoinColumn(name = "FK_Tipo_Tramite")
    )
    @ManyToMany
    private List<TipoTramite> tipoTramite;

    public Operador() {
        this.tipoTramite= new ArrayList<>();
    }

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
        if (!(object instanceof Operador)) {
            return false;
        }
        Operador other = (Operador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Empleado[ id=" + id + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    

    public TipoOperador getUnTipoOperador() {
        return unTipoOperador;
    }

    public void setUnTipoOperador(TipoOperador unTipoOperador) {
        this.unTipoOperador = unTipoOperador;
    }

    public Departamento getUnDepartamentoA() {
        return unDepartamentoA;
    }

    public void setUnDepartamentoA(Departamento unDepartamentoA) {
        this.unDepartamentoA = unDepartamentoA;
    }

    public List<TipoTramite> getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(List<TipoTramite> tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Municipalidad getUnaMunicipalidadC() {
        return unaMunicipalidadC;
    }

    public void setUnaMunicipalidadC(Municipalidad unaMunicipalidadC) {
        this.unaMunicipalidadC = unaMunicipalidadC;
    }

    
    
}
