package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Persona;
import model.Tramite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-17T02:22:41")
@StaticMetamodel(Pasaporte.class)
public class Pasaporte_ { 

    public static volatile SingularAttribute<Pasaporte, String> tipo;
    public static volatile SingularAttribute<Pasaporte, Tramite> unTramitePasaporte;
    public static volatile SingularAttribute<Pasaporte, Integer> ejemplar;
    public static volatile SingularAttribute<Pasaporte, String> numero;
    public static volatile SingularAttribute<Pasaporte, Date> fechaVencimiento;
    public static volatile SingularAttribute<Pasaporte, Persona> unaPersona;
    public static volatile SingularAttribute<Pasaporte, Date> fechaEmision;
    public static volatile SingularAttribute<Pasaporte, Long> id;

}