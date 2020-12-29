package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Reservation;
import model.Screening;
import model.User;

import java.io.IOException;

public class GUIUserController implements GUIController{
    private GUIAppController guiAppController;

    @FXML
    public Button getMoviesOfferButton;

    @FXML
    public Button getScreeningsOfferButton;

    @FXML
    public Button logOutButton;


    @FXML
    public Button addReservationButton;

    @FXML
    public Button getUserByIdButton;

    @FXML
    public Button getUserReservationsButton;

    @FXML
    public Button showEmptySeatsButton;

    @FXML
    public Button sumAllReservationsButton;

    @FXML
    public Button sumSingleReservationsButton;


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

    @FXML
    public void handleLogOutAction(ActionEvent actionEvent) throws IOException {

    }
    @FXML
    public void handleAddUserAction(ActionEvent actionEvent) throws IOException {
        User user = User.newUser();

        if(guiAppController.showAddUserDialog(user)){
            // tu trzeba zrobić dodanie do bazy
        }
    }
    @FXML
    public void handleGetScreeningsOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.userScreeningsLayout();
    }

    @FXML
    public void handleGetMoviesOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.userMoviesLayout();
    }
}
