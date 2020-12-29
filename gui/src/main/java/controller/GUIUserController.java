package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Reservation;
import model.Screening;
import model.User;

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

    @FXML
    public void handleGetUserByIdAction(ActionEvent actionEvent) throws IOException {

        User user = User.newUser();
        if(guiAppController.showGetUserByIdDialog(user)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
    @FXML
    public void handleGetUserReservationsAction(ActionEvent actionEvent) throws IOException {

        User user = User.newUser();
        if(guiAppController.showGetUserReservationsDialog(user)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
    @FXML
    public void handleShowEmptySeatsAction(ActionEvent actionEvent) throws IOException {

        Screening screening = Screening.newScreening();
        if(guiAppController.showEmptySeatsDialog(screening)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
    @FXML
    public void handleSumAllReservationsCostAction(ActionEvent actionEvent) throws IOException {

        User user = User.newUser();
        if(guiAppController.showSumAllReservationsCostDialog(user)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
    @FXML
    public void handleSumSingleReservationsCostAction(ActionEvent actionEvent) throws IOException {

        Reservation reservation = Reservation.newReservation();
        if(guiAppController.showSumSingleReservationsCostDialog(reservation)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
}
