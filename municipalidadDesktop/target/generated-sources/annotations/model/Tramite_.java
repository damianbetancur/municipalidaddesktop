package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Departamento;
import model.Requisito;
import model.TipoTramite;
import model.Turno;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-21T19:16:06")
@StaticMetamodel(Tramite.class)
public class Tramite_ { 

    public static volatile SingularAttribute<Tramite, Date> fecha;
    public static volatile SingularAttribute<Tramite, TipoTramite> unTipoTramite;
    public static volatile ListAttribute<Tramite, Requisito> requisitos;
    public static volatile SingularAttribute<Tramite, Date> hora;
    public static volatile SingularAttribute<Tramite, Long> id;
    public static volatile SingularAttribute<Tramite, Departamento> unDepartamentoC;
    public static volatile SingularAttribute<Tramite, Turno> unTurno;

}