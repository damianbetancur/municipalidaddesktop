package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.JornadaLaboralOperador;
import model.ListaDePrecioTramite;
import model.Municipalidad;
import model.Operador;
import model.TipoTramite;
import model.Tramite;
import model.Turno;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-17T02:00:22")
@StaticMetamodel(Departamento.class)
public class Departamento_ { 

    public static volatile ListAttribute<Departamento, ListaDePrecioTramite> listasDePreciosDeTramites;
    public static volatile ListAttribute<Departamento, Operador> operadores;
    public static volatile SingularAttribute<Departamento, Integer> atencionMaxima;
    public static volatile ListAttribute<Departamento, Tramite> tramites;
    public static volatile ListAttribute<Departamento, JornadaLaboralOperador> jornadasLaboralesDeOperadores;
    public static volatile ListAttribute<Departamento, Turno> turnos;
    public static volatile SingularAttribute<Departamento, Long> id;
    public static volatile SingularAttribute<Departamento, String> telefono;
    public static volatile SingularAttribute<Departamento, String> nombre;
    public static volatile ListAttribute<Departamento, TipoTramite> tipoTramite;
    public static volatile SingularAttribute<Departamento, Municipalidad> unaMunicipalidadA;

}