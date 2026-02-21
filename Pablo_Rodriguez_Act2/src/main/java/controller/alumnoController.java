package controller;

import dao.alumnoDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.matricula;
import model.nota;

import java.io.IOException;
import java.util.List;

public class alumnoController {

    @FXML private TableView<FilaNota> tv_notas;
    @FXML private TableColumn<FilaNota, String> col_asignatura;
    @FXML private TableColumn<FilaNota, String> col_nota;
    @FXML private Button btn_salir;

    // ID de ejemplo (En el futuro esto vendrá del Login)
    private int idAlumnoLogueado = 2;

    @FXML
    public void initialize() {
        // Configuramos las columnas para que sepan qué mostrar
        col_asignatura.setCellValueFactory(new PropertyValueFactory<>("asignatura"));
        col_nota.setCellValueFactory(new PropertyValueFactory<>("nota"));

        cargarTabla();
    }

    private void cargarTabla() {
        // 1. Obtenemos los datos de la base de datos
        List<matricula> misMatriculas = alumnoDAO.obtenerMatriculas(idAlumnoLogueado);
        List<nota> misNotas = alumnoDAO.obtenerNotasDelAlumno(idAlumnoLogueado);

        // 2. Esta es la lista que entenderá la TableView de JavaFX
        ObservableList<FilaNota> listaParaMostrar = FXCollections.observableArrayList();

        // 3. Cruzamos los datos
        for (matricula m : misMatriculas) {
            String nombreAsig = m.getAsignatura().getNombre();
            Integer idAsig = m.getAsignatura().getId_asignatura();
            String valorNotaTexto = "SIN NOTA"; // Por defecto, si no encontramos nada

            // Buscamos si hay una nota para esta asignatura en concreto
            for (nota n : misNotas) {
                // Si el ID de la asignatura de la nota coincide con la de la matrícula...
                if (n.getAsignatura().getId_asignatura().equals(idAsig)) {
                    valorNotaTexto = String.valueOf(n.getNota());
                    break; // Ya la encontramos, dejamos de buscar en las notas
                }
            }

            // Añadimos el resultado a la lista de la tabla
            listaParaMostrar.add(new FilaNota(nombreAsig, valorNotaTexto));
        }

        // 4. Ponemos la lista en la tabla
        tv_notas.setItems(listaParaMostrar);
    }

    public void clickSalir() {
        try{
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

    } catch (
    IOException e) {
        // Si hay un error al cargar, lo mostramos en consola
        System.err.println("Error al volver al login: " + e.getMessage());
        e.printStackTrace();
    }
    }

    // Clase interna para definir qué hay en cada fila de la tabla
    public static class FilaNota {
        private String asignatura;
        private String nota;

        public FilaNota(String asignatura, String nota) {
            this.asignatura = asignatura;
            this.nota = nota;
        }

        public String getAsignatura() { return asignatura; }
        public String getNota() { return nota; }
    }
}