package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matriculas")
public class matricula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Integer id_matricula;

    //Un alumno puede tener varias matrículas
    @ManyToOne
    @JoinColumn(name = "id_alumno") //
    private usuario alumno;
    //Una asignatura puede tener varias matrículas
    @ManyToOne
    @JoinColumn(name = "id_asignatura") //
    private asignatura asignatura;

}
