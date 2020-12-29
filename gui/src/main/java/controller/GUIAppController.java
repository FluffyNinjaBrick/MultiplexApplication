package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIAppController {
    private Stage primaryStage;
    private FXMLLoader loader;
    private GUIController controller;
    public GUIAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loader = new FXMLLoader();
    }

    public void initRootLayout(){
        try{
            this.primaryStage.setTitle("Multiplex");
            rawStartLayout(loader);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void showScene(BorderPane rootLayout){
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void rawStartLayout(FXMLLoader loader) throws IOException {
        this.loader.setLocation(GUIAppController.class.getResource("../raw_views/RawStartView.fxml"));
        BorderPane rootLayout = (BorderPane) this.loader.load();
        controller = loader.getController();
        controller.setGuiAppController(this);
        showScene(rootLayout);
    }

    public void rawMoviesLayout() throws IOException {
        loader.setLocation(GUIAppController.class.getResource("../raw_views/RawMoviesView.fxml"));
        BorderPane rootLayout = (BorderPane) loader.load();
        showScene(rootLayout);
    }

    public void rawScreeningsLayout() throws IOException {
        loader.setLocation(GUIAppController.class.getResource("../raw_views/RawScreeningsView.fxml"));
        BorderPane rootLayout = (BorderPane) loader.load();
        showScene(rootLayout);
    }

    // robimy to layouty dla kazdego pojedyńczego widoku
}
