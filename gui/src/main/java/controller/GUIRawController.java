package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.User;

import java.awt.*;
import java.io.IOException;

public class GUIRawController implements GUIController{
    private GUIAppController guiAppController;

    @Override
    public void setGuiAppController(GUIAppController guiAppController) {
        this.guiAppController = guiAppController;
    }

    @FXML
    public Button getMoviesOfferButton;

    @FXML
    public Button getScreeningsOfferButton;

    @FXML
    public Button logInButton;

    @FXML
    public Button addUserButton;

    @FXML
    public void handleGetScreeningsOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.rawScreeningsLayout();
    }

    @FXML
    public void handleGetMoviesOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.rawMoviesLayout();
    }

    public boolean handleLogInAction(ActionEvent actionEvent) {
        return false;
    }

    @FXML
    public void handleAddUserAction(ActionEvent actionEvent) throws IOException {
        User user = User.newUser();

        if(guiAppController.showAddUserDialog(user)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
}
