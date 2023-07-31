/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Ariel
 */
@Entity
@Table(name = "dni")
public class DNI implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "fk_tramite_dni")
    private Tramite unTramiteDNI;
    
    private int ejemplar;
    
    private String nombre;
    
    private String apellido;
    
    private String numero;
    
    private boolean sexo;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_emision")
    private Date fechaEmision;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;
    
    private String direccion;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;
    
    
    @ManyToOne
    @JoinColumn(name = "fk_pais_nacimiento")
    private Provincia unPaisNacimiento;
    
    @ManyToOne
    @JoinColumn(name = "fk_provincia_nacimiento")
    private Provincia unaProvinciaNacimiento;
    
    
    @ManyToOne
    @JoinColumn(name = "fk_persona")
    private Persona unaPersona;
    
    @ManyToOne
    @JoinColumn(name = "fk_tipo_dni")
    private Provincia unTipoDNI;

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
        if (!(object instanceof DNI)) {
            return false;
        }
        DNI other = (DNI) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.DNI[ id=" + id + " ]";
    }
    
}
