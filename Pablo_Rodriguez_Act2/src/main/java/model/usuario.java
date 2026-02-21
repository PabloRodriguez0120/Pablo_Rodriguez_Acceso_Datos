package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "usuarios")//definimos la tabla en la que se guardara en base de datos
public class usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //se le define como autoincremental de mySQL
    private Integer id_usuario;
    @Column(name = "usuario", unique = true)//indicamos que son columnas de las tablas (opcional su uso)
    private String usuario;
    @Column
    private String contraseña, cargo;
    //Un profesor puede impartir muchas asignaturas
    // El "mappedBy" apunta al nombre del atributo en la clase Asignatura
    @OneToMany(mappedBy = "profesor")
    private List<asignatura> asignaturasImpartidas;

    public usuario(String usuario, String contraseña, String cargo) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.cargo = cargo;
    }

    public usuario() {

    }

}
