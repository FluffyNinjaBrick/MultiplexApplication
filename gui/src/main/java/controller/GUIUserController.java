package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Reservation;

import java.io.IOException;

public class GUIUserController implements GUIController{
    private GUIAppController guiAppController;

    @Override
    public void setGuiAppController(GUIAppController guiAppController) {
        this.guiAppController = guiAppController;
    }



    @FXML
    public void handleAddReservationAction(ActionEvent actionEvent) throws IOException {

        Reservation reservation = Reservation.newReservation();
        if(guiAppController.showAddReservationDialog(reservation)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
}
