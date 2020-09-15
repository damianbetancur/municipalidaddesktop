package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Departamento;
import model.Operador;
import model.Persona;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-21T19:16:06")
@StaticMetamodel(Municipalidad.class)
public class Municipalidad_ { 

    public static volatile ListAttribute<Municipalidad, Operador> operadores;
    public static volatile ListAttribute<Municipalidad, Departamento> departamentos;
    public static volatile SingularAttribute<Municipalidad, String> direccion;
    public static volatile SingularAttribute<Municipalidad, Long> id;
    public static volatile SingularAttribute<Municipalidad, String> telefono;
    public static volatile SingularAttribute<Municipalidad, String> nombre;
    public static volatile ListAttribute<Municipalidad, Persona> personas;

}