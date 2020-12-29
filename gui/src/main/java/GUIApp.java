import javafx.application.Application;
import javafx.stage.Stage;
import controller.GUIAppController;

public class GUIApp extends Application{

    private Stage primaryStage;

    private GUIAppController guiAppController;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Multiplex");
        this.guiAppController = new GUIAppController(primaryStage);
        this.guiAppController.initRootLayout();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
