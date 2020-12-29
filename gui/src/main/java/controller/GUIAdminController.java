package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.*;

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
    @FXML
    public void handleAddScreeningAction(ActionEvent actionEvent) throws IOException {

        Screening screening = Screening.newScreening();
        if(guiAppController.showAddScreeningDialog(screening)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
    @FXML
    public void handleAddReservationAction(ActionEvent actionEvent) throws IOException {

        Reservation reservation = Reservation.newReservation();
        if(guiAppController.showAddReservationDialog(reservation)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
    @FXML
    public void handleAddScreeningRoomAction(ActionEvent actionEvent) throws IOException {

        ScreeningRoom screeningRoom = ScreeningRoom.newScreeningRoom();
        if(guiAppController.showAddScreeningRoomDialog(screeningRoom)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
    @FXML
    public void handleAddSeatAction(ActionEvent actionEvent) throws IOException {

        Seat seat = Seat.newSeat();
        if(guiAppController.showAddSeatDialog(seat)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
}
