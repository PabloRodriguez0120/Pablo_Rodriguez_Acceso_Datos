package mains;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.HibernateUtil;
import java.io.IOException;

public class mainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargamos el archivo FXML
            // Asegúrate de que la ruta coincida con tu carpeta resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            // Creamos la escena con el contenido del FXML
            Scene scene = new Scene(root);

            // Configuramos la ventana principal (Stage)
            primaryStage.setTitle("Ventana de acceso"); // Título según enunciado
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Opcional: para que no deformen tu diseño

            // Mostramos ventana
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la interfaz: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        // Cerramos Hibernate al cerrar la ventana

        HibernateUtil.getSessionFactory().close();
        System.out.println("Aplicación cerrada y Hibernate liberado.");
    }

    public static void main(String[] args) {
        launch(args); // Este metodo llama a START)
    }
}
