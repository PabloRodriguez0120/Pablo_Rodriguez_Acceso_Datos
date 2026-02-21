package mains;

import model.asignatura;
import model.matricula;
import model.nota;
import model.usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class mainBBDD {

    public static void main(String[] args) {
        // Configuración inicial
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Abrimos una única sesión para todo el proceso de guardado
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            //Iniciamos la transaccion
            transaction = session.beginTransaction();

            // --- CREAR PROFESORES ---
            // Usamos tu constructor personalizado: usuario(nombre, contraseña, cargo)
            usuario prof1 = new usuario("Carlos", "Carlos1234", "profesor");
            usuario prof2 = new usuario("Marta", "Marta1234", "profesor");

            session.save(prof1);
            session.save(prof2);

            // --- CREAR ALUMNOS ---
            usuario alu1 = new usuario("Ana", "Ana1234", "alumno");
            usuario alu2 = new usuario("Luis", "Luis1234", "alumno");
            usuario alu3 = new usuario("Elena", "Elena1234", "alumno");
            usuario alu4 = new usuario("Pablo", "Pablo1234", "alumno");

            session.save(alu1);
            session.save(alu2);
            session.save(alu3);
            session.save(alu4);

            // --- CREAR ASIGNATURAS ---
            // El orden es: id (null), nombre, objeto profesor, lista de matriculas (null)
            asignatura asig1 = new asignatura(null, "Lengua", prof1, null);
            asignatura asig2 = new asignatura(null, "Matematicas", prof1, null);
            asignatura asig3 = new asignatura(null, "Java", prof2, null);
            asignatura asig4 = new asignatura(null, "Ingles", prof2, null);

            session.save(asig1);
            session.save(asig2);
            session.save(asig3);
            session.save(asig4);

            // --- 4. CREAR MATRÍCULAS (1 alumno por asignatura) ---
            session.save(new matricula(null, alu1, asig1)); // Ana en Lengua
            session.save(new matricula(null, alu2, asig2)); // Luis en Matemáticas
            session.save(new matricula(null, alu3, asig3)); // Elena en Java
            session.save(new matricula(null, alu4, asig4)); // Pablo en Inglés

            // Confirmamos los cambios en la base de datos
            transaction.commit();

            System.out.println("----------------------------------------------");
            System.out.println("¡ÉXITO! Datos creados en sistema_estudios.");
            System.out.println("Profesores: Carlos, Marta");
            System.out.println("Alumnos: Ana, Luis, Elena, Pablo");
            System.out.println("----------------------------------------------");

        } catch (Exception e) {
            System.err.println("ERROR al poblar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

         finally {
            session.close();
        }

        // Cerramos la fábrica al finalizar la ejecución
        sessionFactory.close();
    }
}

/*REINICIALIZAR LAS TABLAS EN BBDD
* -- 1. Desactivamos restricciones
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Borramos el contenido de las tablas
DELETE FROM notas;
DELETE FROM matriculas;
DELETE FROM asignaturas;
DELETE FROM usuarios;

-- 3. Reiniciamos los contadores manual mente
ALTER TABLE notas AUTO_INCREMENT = 1;
ALTER TABLE matriculas AUTO_INCREMENT = 1;
ALTER TABLE asignaturas AUTO_INCREMENT = 1;
ALTER TABLE usuarios AUTO_INCREMENT = 1;

-- 4. Reactivamos restricciones
SET FOREIGN_KEY_CHECKS = 1;*/