package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Departamento;
import model.Municipalidad;
import model.TipoOperador;
import model.TipoTramite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-25T19:33:00")
@StaticMetamodel(Operador.class)
public class Operador_ { 

    public static volatile SingularAttribute<Operador, TipoOperador> unTipoOperador;
    public static volatile SingularAttribute<Operador, String> apellido;
    public static volatile SingularAttribute<Operador, Long> id;
    public static volatile SingularAttribute<Operador, Municipalidad> unaMunicipalidadC;
    public static volatile SingularAttribute<Operador, String> nombre;
    public static volatile ListAttribute<Operador, TipoTramite> tipoTramite;
    public static volatile SingularAttribute<Operador, String> dni;
    public static volatile SingularAttribute<Operador, Departamento> unDepartamentoA;

}