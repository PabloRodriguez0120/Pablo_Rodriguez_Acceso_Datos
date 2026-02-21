package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asignaturas")
public class asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_asignatura;
    @Column(name = "nombre")
    private String nombre;

    // Muchos asignaturas pueden pertenecer a UN usuario (profesor)
    @ManyToOne
    @JoinColumn(name = "id_profesor", nullable = false)
    private usuario profesor;

    // Una asignatura tiene muchas matrículas
    @OneToMany(mappedBy = "asignatura", cascade = CascadeType.ALL)
    private List <matricula> matriculas = new ArrayList<>(); //guardamos en una lista

}
