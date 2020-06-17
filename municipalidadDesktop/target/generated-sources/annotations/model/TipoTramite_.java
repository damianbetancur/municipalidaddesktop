package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Departamento;
import model.Operador;
import model.Requisito;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-17T02:22:41")
@StaticMetamodel(TipoTramite.class)
public class TipoTramite_ { 

    public static volatile SingularAttribute<TipoTramite, String> codigo;
    public static volatile ListAttribute<TipoTramite, Requisito> requisitos;
    public static volatile ListAttribute<TipoTramite, Departamento> departamentos;
    public static volatile ListAttribute<TipoTramite, Operador> empleados;
    public static volatile SingularAttribute<TipoTramite, Long> id;
    public static volatile SingularAttribute<TipoTramite, String> nombre;

}