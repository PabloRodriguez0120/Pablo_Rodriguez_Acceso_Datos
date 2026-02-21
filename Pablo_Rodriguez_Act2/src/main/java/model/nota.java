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
@Table(name = "notas")
public class nota implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota") // Ahora es una PK normal y corriente
    private Integer idNota;
    //Un alumno puede tener muchas notas
    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private usuario alumno;
    //Una asignatura puede tener muchas notas para muchos alumnos
    @ManyToOne
    @JoinColumn(name = "id_asignatura")
    private asignatura asignatura;

    private double nota;

}
