package fr.sae201.gestiongroupesetudiant;
//http://ufile.io/rqsjeqxn

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ConnexionView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Page de connexion");
        stage.setScene(scene);
        stage.show();
    }

    public static String getResource(String path) {
        try {
            return HelloApplication.class.getResource(path).toString();
        } catch(NullPointerException e) {
            return HelloApplication.class.getResource("images/profile-photo.jpeg").toString();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}