package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-31T06:10:06")
@StaticMetamodel(TipoUsuario.class)
public class TipoUsuario_ { 

    public static volatile SingularAttribute<TipoUsuario, String> descripcion;
    public static volatile SingularAttribute<TipoUsuario, Long> id;
    public static volatile SetAttribute<TipoUsuario, Usuario> usuarios;

}