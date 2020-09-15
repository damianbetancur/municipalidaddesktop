package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Provincia;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-21T19:16:06")
@StaticMetamodel(Pais.class)
public class Pais_ { 

    public static volatile ListAttribute<Pais, Provincia> provincias;
    public static volatile SingularAttribute<Pais, Long> id;
    public static volatile SingularAttribute<Pais, String> nombre;

}