package controller;


import com.google.inject.Inject;
import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import java.util.ArrayList;
import java.util.List;

public class GUIAdminController implements GUIController{
    private GUIAppController guiAppController;
    private Screening screening;
    public User user;
    private Reservation reservation;

    @Inject
    private Communicator communicator;

    @Inject
    private Authentication authentication;

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
    @FXML
    private TableView<SimpleStringProperty[]> allReservationsCostTable;
    @FXML
    private TableColumn<SimpleStringProperty[], String> userAllID;
    @FXML
    private TableColumn<SimpleStringProperty[], String> allCost;
    /* #################### */
    @FXML
    private TableView<SimpleStringProperty[]> singleReservationCostTable;
    @FXML
    private TableColumn<SimpleStringProperty[], String> singleUserID;
    @FXML
    private TableColumn<SimpleStringProperty[], String> singleScreeningID;
    @FXML
    private TableColumn<SimpleStringProperty[], String> singleCost;

    /* #################### */

    /* Najgorsze zło */

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
            Task<ObservableList<User>> task = this.communicator.showUsers();
            task.setOnSucceeded(event -> usersTable.setItems(task.getValue()));
            communicator.execute(task);
        }
        if (this.userByIdTable != null) {
            userByIdTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            idUserName.setCellValueFactory(user -> user.getValue().getUserNameObs());
            idFirstName.setCellValueFactory(user -> user.getValue().getFirstNameObs());
            idLastName.setCellValueFactory(user -> user.getValue().getLastNameObs());
            idEmail.setCellValueFactory(user -> user.getValue().getEmailObs());
            Task<User> task = this.communicator.showUserById(this.communicator.getLastUser());
            task.setOnSucceeded(event -> {
                List<User> list = new ArrayList<User>(1);
                list.add(task.getValue());

                userByIdTable.setItems( FXCollections.observableList(list));
            });
            communicator.execute(task);
        }
        if (this.seatsTable != null) {
            seatsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            placeInRow.setCellValueFactory(seat -> seat.getValue().getSeatNumberObs());
            seatRow.setCellValueFactory(seat -> seat.getValue().getSeatRowObs());
            Task<ObservableList<Seat>> task = this.communicator.showEmptySeats(this.communicator.getLastScreening());
            task.setOnSucceeded(event -> seatsTable.setItems(task.getValue()));
            communicator.execute(task);
        }

        if (this.singleReservationCostTable != null) {
            singleReservationCostTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            singleUserID.setCellValueFactory(res -> res.getValue()[0]);
            singleScreeningID.setCellValueFactory(res -> res.getValue()[1]);
            singleCost.setCellValueFactory(res -> res.getValue()[2]);
            Task<Integer> task = this.communicator.singleReservationCost(this.communicator.getLastReservation());
            task.setOnSucceeded(event -> {
                SimpleStringProperty[] arr = new SimpleStringProperty[3];

                arr[0] = new SimpleStringProperty(String.valueOf(this.communicator.getLastReservation().getUserId()));
                arr[1] = new SimpleStringProperty(String.valueOf(this.communicator.getLastReservation().getScreeningId()));
                arr[2] = new SimpleStringProperty(String.valueOf(task.getValue()));
                List<SimpleStringProperty[]> list = new ArrayList<SimpleStringProperty[]>(1);
                list.add(arr);

                singleReservationCostTable.setItems( FXCollections.observableList(list));
            });
            communicator.execute(task);
        }
        if (this.allReservationsCostTable != null) {
            allReservationsCostTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            // tu zrobić podobnie jak wyżej
            userAllID.setCellValueFactory(res -> res.getValue()[0]);
            allCost.setCellValueFactory(res -> res.getValue()[1]);
            //Task<ObservableList<Screening>> task = this.communicator.showEmptySeats(this.screening);
            //task.setOnSucceeded(event -> screeningsTable.setItems(task.getValue()));
            //communicator.execute(task);
            Task<Integer> task = this.communicator.allReservationCost(this.communicator.getLastUser());
            task.setOnSucceeded(event -> {
                SimpleStringProperty[] arr = new SimpleStringProperty[3];

                arr[0] = new SimpleStringProperty(String.valueOf(this.communicator.getLastUser().getId()));
                arr[1] = new SimpleStringProperty(String.valueOf(task.getValue()));
                List<SimpleStringProperty[]> list = new ArrayList<SimpleStringProperty[]>(1);
                list.add(arr);

                allReservationsCostTable.setItems( FXCollections.observableList(list));
            });
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
            Task<Integer> task = communicator.addMovie(movie);
            task.setOnSucceeded(e -> System.out.println("code: " + task.getValue()));
            task.setOnFailed(e -> System.out.println("adding error: " + task.getValue()));
            communicator.execute(task);
        }
    }
    @FXML
    public void handleAddScreeningAction(ActionEvent actionEvent) throws IOException {

        Screening screening = Screening.newScreening();
        screening.setScreeningRoom(ScreeningRoom.newScreeningRoom());
        screening.setMovie(Movie.newMovie());
        if(guiAppController.showAddScreeningDialog(screening)){
            System.out.println("add screening");
            Task<Integer> task = communicator.addScreening(screening);
            task.setOnSucceeded(e -> System.out.println("code: " + task.getValue()));
            task.setOnFailed(e -> System.out.println("adding error: " + task.getValue()));
            communicator.execute(task);
        }
    }
    @FXML
    public void handleAddReservationAction(ActionEvent actionEvent) throws IOException {
        Reservation reservation = Reservation.newReservation();
        if(guiAppController.showAddReservationDialog(reservation)){
            Task<Integer> task = communicator.addReservation(reservation);
            task.setOnSucceeded(e -> System.out.println("code: " + task.getValue()));
            task.setOnFailed(e -> System.out.println("adding error: " + task.getValue()));
            communicator.execute(task);
        }
    }
    @FXML
    public void handleAddScreeningRoomAction(ActionEvent actionEvent) throws IOException {

        ScreeningRoom screeningRoom = ScreeningRoom.newScreeningRoom();
        if(guiAppController.showAddScreeningRoomDialog(screeningRoom)){
            Task<Integer> task = communicator.addScreeningRoom(screeningRoom);
            task.setOnSucceeded(e -> System.out.println("code: " + task.getValue()));
            task.setOnFailed(e -> System.out.println("adding error: " + task.getValue()));
            communicator.execute(task);
        }
    }
    @FXML
    public void handleAddSeatAction(ActionEvent actionEvent) throws IOException {

        Seat seat = Seat.newSeat();
        if(guiAppController.showAddSeatDialog(seat)){
            Task<Integer> task = communicator.addSeat(seat);
            task.setOnSucceeded(e -> System.out.println("code: " + task.getValue()));
            task.setOnFailed(e -> System.out.println("adding error: " + task.getValue()));
            communicator.execute(task);
        }
    }

    @FXML
    public void handleDeleteUserAction(ActionEvent actionEvent) throws IOException {

        User user = User.newUser();
        if(guiAppController.showDeleteDialog(user)){
            Task<Integer> task = communicator.deleteUser(user);
            task.setOnSucceeded(e -> System.out.println("code: " + task.getValue()));
            task.setOnFailed(e -> System.out.println("adding error: " + task.getValue()));
            communicator.execute(task);
        }
    }
    @FXML
    public void handleGetUserByIdAction(ActionEvent actionEvent) throws IOException {

        User user = User.newUser();
        if(guiAppController.showGetUserByIdDialog(user)){
            this.user = user;
            this.communicator.setLastUser(user);
            this.guiAppController.adminUserByIdLayout(); // TODO
        }
    }

    @FXML
    public void handleGetUserReservationsAction(ActionEvent actionEvent) throws IOException {

        User user = User.newUser();
        System.out.println("robi to ktos?");
        if(guiAppController.showGetUserReservationsDialog(user)){
            this.communicator.getUserReservations(user);

            this.guiAppController.adminUserReservationsLayout();
            // tu trzeba zrobić dodanie do bazy
        }
    }

    @FXML
    public void handleShowEmptySeatsAction(ActionEvent actionEvent) throws IOException {

        Screening screening = Screening.newScreening();
        if(guiAppController.showEmptySeatsDialog(screening)){
            this.screening = screening;
            this.communicator.setLastScreening(screening);
            this.guiAppController.adminSeatsLayout();
        }
    }

    @FXML
    public void handleSumAllReservationsCostAction(ActionEvent actionEvent) throws IOException {

        User user = User.newUser();
        this.communicator.setLastUser(user);
        if(guiAppController.showSumAllReservationsCostDialog(user)){
            this.user = user;
            this.guiAppController.adminAllReservationsCostLayout();
        }
    }

    @FXML
    public void handleSumSingleReservationsCostAction(ActionEvent actionEvent) throws IOException {

        Reservation reservation = Reservation.newReservation();
        this.communicator.setLastReservation(reservation);
        if(guiAppController.showSumSingleReservationsCostDialog(reservation)){
            this.reservation = reservation;
            this.guiAppController.adminSingleReservationCostLayout();
        }
    }
    @FXML
    public void handleLogOutAction(ActionEvent actionEvent) throws IOException {
        this.authentication.clear();
        this.guiAppController.rawMoviesLayout();
    }
    @FXML
    public void handleAddUserAction(ActionEvent actionEvent) throws IOException {
        User user = User.newUser();

        if(guiAppController.showAddUserDialog(user)){
            Task<Integer> task = communicator.addUser(user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword());
            task.setOnSucceeded(e -> System.out.println("code: " + task.getValue()));
            task.setOnFailed(e -> System.out.println("adding error: " + task.getValue()));
            communicator.execute(task);
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
