package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Persona;
import model.Provincia;
import model.Tramite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-21T19:16:06")
@StaticMetamodel(DNI.class)
public class DNI_ { 

    public static volatile SingularAttribute<DNI, Integer> ejemplar;
    public static volatile SingularAttribute<DNI, String> numero;
    public static volatile SingularAttribute<DNI, Date> fechaNacimiento;
    public static volatile SingularAttribute<DNI, Provincia> unPaisNacimiento;
    public static volatile SingularAttribute<DNI, Date> fechaVencimiento;
    public static volatile SingularAttribute<DNI, String> direccion;
    public static volatile SingularAttribute<DNI, Date> fechaEmision;
    public static volatile SingularAttribute<DNI, Provincia> unTipoDNI;
    public static volatile SingularAttribute<DNI, String> nombre;
    public static volatile SingularAttribute<DNI, Persona> unaPersona;
    public static volatile SingularAttribute<DNI, Tramite> unTramiteDNI;
    public static volatile SingularAttribute<DNI, String> apellido;
    public static volatile SingularAttribute<DNI, Provincia> unaProvinciaNacimiento;
    public static volatile SingularAttribute<DNI, Long> id;
    public static volatile SingularAttribute<DNI, Boolean> sexo;

}