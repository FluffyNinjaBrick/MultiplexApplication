package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIAppController {
    private Stage primaryStage;
    private GUIController controller;
    public GUIAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout(){
        try{
            this.primaryStage.setTitle("Multiplex");
            FXMLLoader loader = new FXMLLoader();
            rawStartLayout(loader);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void showScene(FXMLLoader loader) throws IOException {
        BorderPane rootLayout = loader.load();
        controller = loader.getController();
        controller.setGuiAppController(this);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*###############################  RAW SECTION    ########################################################*/

    public void rawStartLayout(FXMLLoader loader) throws IOException {
        loader.setLocation(GUIAppController.class.getResource("../raw_views/RawStartView.fxml"));
        showScene(loader);
    }

    public void rawMoviesLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../raw_views/RawMoviesView.fxml"));
        showScene(loader);
    }

    public void rawScreeningsLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../raw_views/RawScreeningsView.fxml"));
        showScene(loader);
    }

    /*###############################  USER SECTION    ########################################################*/



    /*###############################  ADMIN SECTION    ########################################################*/
}
