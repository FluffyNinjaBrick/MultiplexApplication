package controller;


import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;

import java.io.IOException;

public class GUIAdminController implements GUIController{
    private GUIAppController guiAppController;
    private Screening screening;
    private User user;

    @Inject
    private Communicator communicator;

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

    /* #################### */
    @FXML
    public TableView<Movie> moviesTable;

    @FXML
    public TableColumn<Movie, String> title;

    @FXML
    public TableColumn<Movie, String> author;

    @FXML
    public TableColumn<Movie, String> description;

    /* #################### */
    @FXML
    private TableView<Screening> screeningsTable;
    @FXML
    private TableColumn<Screening, String> titleScreening;
    @FXML
    private TableColumn<Screening, String> cost;
    @FXML
    private TableColumn<Screening, String> date;
    @FXML
    private TableColumn<Screening, String> room;

    /* #################### */
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> usersName ;
    @FXML
    private TableColumn<User, String> firstsName;
    @FXML
    private TableColumn<User, String> lastsName;
    @FXML
    private TableColumn<User, String> emails;

    /* #################### */
    @FXML
    private TableView<User> userByIdTable;
    @FXML
    private TableColumn<User, String> idUserName ;
    @FXML
    private TableColumn<User, String> idFirstName;
    @FXML
    private TableColumn<User, String> idLastName;
    @FXML
    private TableColumn<User, String> idEmail;

    /* #################### */
    @FXML
    private TableView<Seat> seatsTable;
    @FXML
    private TableColumn<Seat, String> seatRow ;
    @FXML
    private TableColumn<Seat, String> placeInRow;

    /* #################### */

    private void logInfo(String text){

    }
    @FXML
    private void initialize() {
        if (this.moviesTable != null) {
            moviesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            title.setCellValueFactory(movie -> movie.getValue().getTitleObs());
            author.setCellValueFactory(movie -> movie.getValue().getAuthorObs());
            description.setCellValueFactory(movie -> movie.getValue().getDescriptionObs());
            Task<ObservableList<Movie>> task = this.communicator.getMovies();
            task.setOnSucceeded(event -> moviesTable.setItems(task.getValue()));
            communicator.execute(task);
        }
        if (this.screeningsTable != null) {
            screeningsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            titleScreening.setCellValueFactory(screening -> screening.getValue().getMovie().getTitleObs());
            cost.setCellValueFactory(screening -> screening.getValue().getCostObs());
            date.setCellValueFactory(screening -> screening.getValue().getDateObs());
            room.setCellValueFactory(screening -> screening.getValue().getRoomObs());
            Task<ObservableList<Screening>> task = this.communicator.getScreenings();
            task.setOnSucceeded(event -> screeningsTable.setItems(task.getValue()));
            communicator.execute(task);
        }
        if (this.usersTable != null) {
            usersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            usersName.setCellValueFactory(user -> user.getValue().getUserNameObs());
            firstsName.setCellValueFactory(user -> user.getValue().getFirstNameObs());
            lastsName.setCellValueFactory(user -> user.getValue().getLastNameObs());
            emails.setCellValueFactory(user -> user.getValue().getEmailObs());
            Task<ObservableList<Screening>> task = this.communicator.showUsers();
            task.setOnSucceeded(event -> screeningsTable.setItems(task.getValue()));
            communicator.execute(task);
        }
        if (this.userByIdTable != null) {
            userByIdTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            idUserName.setCellValueFactory(user -> user.getValue().getUserNameObs());
            idFirstName.setCellValueFactory(user -> user.getValue().getFirstNameObs());
            idLastName.setCellValueFactory(user -> user.getValue().getLastNameObs());
            idEmail.setCellValueFactory(user -> user.getValue().getEmailObs());
            Task<ObservableList<Screening>> task = this.communicator.showUserById(this.user);
            task.setOnSucceeded(event -> screeningsTable.setItems(task.getValue()));
            communicator.execute(task);
        }
        if (this.seatsTable != null) {
            seatsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            placeInRow.setCellValueFactory(seat -> seat.getValue().getSeatNumberObs());
            seatRow.setCellValueFactory(seat -> seat.getValue().getSeatRowObs());
            Task<ObservableList<Screening>> task = this.communicator.showEmptySeats(this.screening);
            task.setOnSucceeded(event -> screeningsTable.setItems(task.getValue()));
            communicator.execute(task);
        }

    }

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
            this.user = user;
            this.guiAppController.adminUserByIdLayout();
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
            this.screening = screening;
            this.guiAppController.adminSeatsLayout();
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
        this.guiAppController.adminAllUsersLayout();
    }
}
