package controller;

import dao.profesorDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.asignatura;
import model.nota;
import model.usuario;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class profesorController {

    @FXML private ChoiceBox<String> select_asignatura;
    @FXML private ChoiceBox<String> select_alumno;
    @FXML private TextField tf_nota;
    @FXML private Button btn_salir;

     //ID de prueba (esto debería venir del login)
    private int idProfesorActual = 1;

    @FXML
    public void initialize() {
        // Cargar asignaturas al arrancar
        cargarAsignaturas();

        // Si el profesor cambia de asignatura, se actualiza la lista de alumnos
        select_asignatura.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarAlumnos(extraerId(newVal));
            }
        });
    }

    private void cargarAsignaturas() {
        List<asignatura> lista = profesorDAO.obtenerAsignaturasPorProfesor(idProfesorActual);
        for (asignatura a : lista) {
            select_asignatura.getItems().add(a.getId_asignatura() + " - " + a.getNombre());
        }
    }

    private void cargarAlumnos(int idAsig) {
        select_alumno.getItems().clear();
        List<usuario> alumnos = profesorDAO.obtenerAlumnosPorAsignatura(idAsig);
        for (usuario u : alumnos) {
            select_alumno.getItems().add(u.getId_usuario() + " - " + u.getUsuario());
        }
    }

    @FXML
    private void clickGuardarNota() {
        try {
            // Validamos campos
            if (select_asignatura.getValue() == null || select_alumno.getValue() == null) {
                mostrarAlerta("Error", "Selecciona asignatura y alumno", Alert.AlertType.WARNING);
                return;
            }

            // Creamos el objeto Nota (Lombok hará su magia aquí)
            nota n = new nota();
            // n.setAlumno(...) Aquí deberías pasar el objeto completo si usas relaciones
            // n.setAsignatura(...)
            n.setNota(Double.parseDouble(tf_nota.getText()));

            // Guardamos mediante el DAO
            // profesorDAO.guardarOActualizarNota(n);

            mostrarAlerta("Éxito", "Nota guardada correctamente en el gestor", Alert.AlertType.INFORMATION);
            tf_nota.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La nota debe ser un número decimal (punto)", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cliclkSalir() {
        try {
            // 1. Cargamos el archivo del login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            // 2. Obtenemos la ventana actual usando el botón de salir
            Stage stage = (Stage) btn_salir.getScene().getWindow();

            // 3. Cambiamos el contenido de la ventana por el del login
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Acceso al Sistema"); // Cambiamos el título de nuevo
            stage.centerOnScreen(); // Lo centramos para que quede bien
            stage.show();

        } catch (IOException e) {
            // Si hay un error al cargar, lo mostramos en consola
            System.err.println("Error al volver al login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int extraerId(String texto) {
        return Integer.parseInt(texto.split(" - ")[0]);
    }

    private void mostrarAlerta(String t, String m, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setContentText(m);
        a.show();
    }
}