package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Departamento;
import model.EstadoTurno;
import model.HorarioAtencionTurno;
import model.Operador;
import model.Persona;
import model.TipoTramite;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-31T08:19:29")
@StaticMetamodel(Turno.class)
public class Turno_ { 

    public static volatile SingularAttribute<Turno, Date> fecha;
    public static volatile SingularAttribute<Turno, Operador> unOperador;
    public static volatile SingularAttribute<Turno, TipoTramite> unTipoTramite;
    public static volatile SingularAttribute<Turno, Persona> unaPersona;
    public static volatile SingularAttribute<Turno, HorarioAtencionTurno> unaHoraTurno;
    public static volatile SingularAttribute<Turno, Long> id;
    public static volatile SingularAttribute<Turno, Departamento> unDepartamentoB;
    public static volatile SingularAttribute<Turno, EstadoTurno> unEstadoTurno;

}