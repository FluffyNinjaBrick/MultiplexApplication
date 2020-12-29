package controller;

import command.CommandRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GUIRawController implements GUIController{
    private GUIAppController guiAppController;

    private CommandRegistry commandRegistry;

    @Override
    public void setGuiAppController(GUIAppController guiAppController) {
        this.guiAppController = guiAppController;
    }

    @FXML
    private Button getMoviesOfferButton;

    @FXML
    private Button getScreeningsOfferButton;

    @FXML
    private Button logInButton;

    @FXML
    private Button addUserButton;

    @FXML
    public void handleGetScreeningsOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.rawScreeningsLayout();
    }

    @FXML
    public void handleGetMoviesOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.rawMoviesLayout();
    }
}
