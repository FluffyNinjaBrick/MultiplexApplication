package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.*;

import java.io.IOException;

public class GUIAdminController implements GUIController{
    private GUIAppController guiAppController;

    @FXML
    public Button getMoviesOfferButton;

    @FXML
    public Button getScreeningsOfferButton;

    @FXML
    public Button logOutButton;

    @FXML
    public Button addUserButton;

    @FXML
    public Button addMovieButton;

    @FXML
    public Button addReservationButton;

    @FXML
    public Button addScreeningButton;

    @FXML
    public Button addSeatButton;

    @FXML
    public Button deleteUserButton;

    @FXML
    public Button addScreeningRoomButton;

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

    @FXML
    public Button showUsersButton;



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

    @FXML
    public void handleDeleteUserAction(ActionEvent actionEvent) throws IOException {

        User user = User.newUser();
        if(guiAppController.showDeleteDialog(user)){
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
        this.guiAppController.adminScreeningsLayout();
    }

    @FXML
    public void handleGetMoviesOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.adminMoviesLayout();
    }

    @FXML
    public void handleShowUsersAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.rawMoviesLayout();
    }
}
