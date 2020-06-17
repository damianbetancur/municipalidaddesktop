package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.DNI;
import model.Municipalidad;
import model.Pasaporte;
import model.Tramite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-17T02:22:41")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, Tramite> unTramiteInscripcion;
    public static volatile SingularAttribute<Persona, String> apellido;
    public static volatile SingularAttribute<Persona, Long> id;
    public static volatile ListAttribute<Persona, DNI> dnis;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, Municipalidad> unaMunicipalidadB;
    public static volatile ListAttribute<Persona, Pasaporte> pasaportes;
    public static volatile SingularAttribute<Persona, String> dni;

}