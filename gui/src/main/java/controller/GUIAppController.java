package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIAppController {
    private Stage primaryStage;

    // command registry
    public GUIAppController(Stage primaryStage) { this.primaryStage = primaryStage; }
    // tu się coś psuje
    public void initRootLayout(){
        try{
            this.primaryStage.setTitle("Multiplex");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminStartView.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();


            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
