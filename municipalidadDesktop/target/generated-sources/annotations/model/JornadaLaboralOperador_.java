package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Departamento;
import model.Operador;
import model.TipoJornadaLaboral;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-17T02:22:41")
@StaticMetamodel(JornadaLaboralOperador.class)
public class JornadaLaboralOperador_ { 

    public static volatile SingularAttribute<JornadaLaboralOperador, Operador> unOperador;
    public static volatile SingularAttribute<JornadaLaboralOperador, Date> unaFecha;
    public static volatile SingularAttribute<JornadaLaboralOperador, Date> unaHora;
    public static volatile SingularAttribute<JornadaLaboralOperador, TipoJornadaLaboral> unTipoJornadaLaboral;
    public static volatile SingularAttribute<JornadaLaboralOperador, Long> id;
    public static volatile SingularAttribute<JornadaLaboralOperador, Departamento> unDepartamentoD;

}