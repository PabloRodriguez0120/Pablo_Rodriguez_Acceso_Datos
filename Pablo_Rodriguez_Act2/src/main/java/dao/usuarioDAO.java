package dao;

import model.usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class usuarioDAO {
    // Busca un usuario por nombre en la base de datos
    public usuario login(String nombre, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<usuario> query = session.createQuery("FROM usuario WHERE usuario = :u AND contraseña = :p", usuario.class);
            query.setParameter("u", nombre);
            query.setParameter("p", password);
            return query.uniqueResult(); // Devuelve el usuario o null si no existe
        }
    }
}
