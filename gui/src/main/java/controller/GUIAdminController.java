package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Movie;
import model.User;

import java.io.IOException;

public class GUIAdminController implements GUIController{
    private GUIAppController guiAppController;

    @Override
    public void setGuiAppController(GUIAppController guiAppController) {
        this.guiAppController = guiAppController;
    }

    @FXML
    public void handleAddMovieAction(ActionEvent actionEvent) throws IOException {
        Movie movie = Movie.newMovie();

        if(guiAppController.showAddMovieDialog(movie)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
}
