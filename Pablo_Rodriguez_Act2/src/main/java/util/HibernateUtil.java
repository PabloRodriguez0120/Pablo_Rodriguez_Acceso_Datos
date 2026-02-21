package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    //Con static definimos que pertenece a la clase, no a los objetos
    private static final SessionFactory sessionFactory;

    static { //Con static nos aseguramos que todo esto lo realice solo una vez al iniciar la app
        try {
            // Crea la sesion a partir de hibernate.cfg.xml (donde hemos configurado la bbdd)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Error creando SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
