package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Pais;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-17T02:29:19")
@StaticMetamodel(Provincia.class)
public class Provincia_ { 

    public static volatile SingularAttribute<Provincia, Long> id;
    public static volatile SingularAttribute<Provincia, String> nombre;
    public static volatile SingularAttribute<Provincia, Pais> unPais;

}