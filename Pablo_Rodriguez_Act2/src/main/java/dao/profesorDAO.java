package dao;

import util.HibernateUtil;
import model.asignatura;
import model.nota;
import model.usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class profesorDAO {

    // 1. Obtener asignaturas de un profesor
    public static List<asignatura> obtenerAsignaturasPorProfesor(int idProfesor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: Consultamos sobre la clase 'asignatura', no sobre la tabla
            String hql = "FROM asignatura WHERE id_profesor = :idProf";
            Query<asignatura> query = session.createQuery(hql, asignatura.class);
            query.setParameter("idProf", idProfesor);
            return query.list();
        }
    }

    // 2. Obtener alumnos matriculados en una asignatura
    public static List<usuario> obtenerAlumnosPorAsignatura(int idAsignatura) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Buscamos usuarios que estén en la tabla matricula para esa asignatura
            String hql = "SELECT m.alumno FROM matricula m WHERE m.asignatura.id_asignatura = :idAsig";
            Query<usuario> query = session.createQuery(hql, usuario.class);
            query.setParameter("idAsig", idAsignatura);
            return query.list();
        }
    }

    // 3. Guardar o Actualizar Nota
    public static void guardarOActualizarNota(nota nuevaNota) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Hibernate decide solo si hace INSERT o UPDATE gracias a 'saveOrUpdate'
            session.saveOrUpdate(nuevaNota);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}