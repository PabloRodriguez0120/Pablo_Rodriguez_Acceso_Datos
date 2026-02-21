package controller;

import dao.usuarioDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.usuario;

import java.io.IOException;
import java.net.URL;

public class loginController {

    // Variables vinculadas al fx:id del FXML
    @FXML private TextField txt_name;
    @FXML private PasswordField txt_psswd;
    @FXML private ChoiceBox<String> select_cargo;

    private final usuarioDAO usuarioDAO = new usuarioDAO();

    @FXML
    public void initialize() {
        // Poblamos el ChoiceBox al iniciar la vista
        select_cargo.getItems().addAll("Profesor", "Alumno");
    }

    // EVENTO: SALIR
    @FXML
    private void btnSalirClick() {
        Platform.exit();
    }

    // EVENTO: LIMPIAR
    @FXML
    private void btnLimpiarClick() {
        txt_name.clear();
        txt_psswd.clear();
        select_cargo.setValue(null);
    }

    // EVENTO: ENTRAR
    @FXML
    private void btnEntrarClick() {
        String user = txt_name.getText();
        String pass = txt_psswd.getText();
        String cargo = select_cargo.getValue();

        // Validación de campos vacíos
        if (user.isEmpty() || pass.isEmpty() || cargo == null) {
            mostrarAlerta("Error", "Por favor, rellena todos los campos", Alert.AlertType.WARNING);
            return;
        }

        // Validación contra Base de Datos a través del DAO
        usuario u = usuarioDAO.login(user, pass);

        if (u != null && u.getCargo().equalsIgnoreCase(cargo)) {
            mostrarAlerta("Bienvenido", "Hola de nuevo, " + u.getUsuario(), Alert.AlertType.INFORMATION);
            siguienteVentana(cargo);
        } else {
            mostrarAlerta("Error", "Datos incorrectos o cargo erróneo", Alert.AlertType.ERROR);
        }
    }

    private void siguienteVentana(String cargo) {
        String ventanaPath = "";
        String tituloVentana = "";

        // CORRECCIÓN DE RUTAS: Se asume que los FXML están en la raíz de 'resources'
        if ("Profesor".equalsIgnoreCase(cargo)) {
            ventanaPath = "/ventanaProfesor.fxml";
            tituloVentana = "Panel de Control - Profesor";
        } else {
            ventanaPath = "/ventanaAlumno.fxml";
            tituloVentana = "Visor Notas - Alumno";
        }

        try {
            // Intentamos localizar el recurso
            URL resource = getClass().getResource(ventanaPath);

            if (resource == null) {
                throw new IOException("No se encontró el archivo FXML en la ruta: " + ventanaPath);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            // Obtenemos el Stage actual desde cualquier componente (ej. txt_name)
            Stage stage = (Stage) txt_name.getScene().getWindow();

            // Cambiamos la escena y el título
            stage.setScene(new Scene(root));
            stage.setTitle(tituloVentana);
            stage.centerOnScreen(); // Opcional: centra la nueva ventana
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error Crítico", "No se pudo cargar la vista: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}