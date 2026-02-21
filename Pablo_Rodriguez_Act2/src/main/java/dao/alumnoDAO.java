package dao;

import util.HibernateUtil;
import model.matricula;
import model.nota;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class alumnoDAO {

    // MÉTODO A: Traer todas las matrículas (asignaturas donde está el alumno)
    public static List<matricula> obtenerMatriculas(int idAlumno) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: Buscamos en la tabla matricula filtrando por el ID del alumno
            String hql = "FROM matricula m WHERE m.alumno.id_usuario = :id";
            return session.createQuery(hql, matricula.class)
                    .setParameter("id", idAlumno)
                    .list();
        }
    }

    // MÉTODO B: Traer todas las notas que ya tiene este alumno
    public static List<nota> obtenerNotasDelAlumno(int idAlumno) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: Buscamos en la tabla nota filtrando por el ID del alumno
            String hql = "FROM nota n WHERE n.alumno.id_usuario = :id";
            return session.createQuery(hql, nota.class)
                    .setParameter("id", idAlumno)
                    .list();
        }
    }
}