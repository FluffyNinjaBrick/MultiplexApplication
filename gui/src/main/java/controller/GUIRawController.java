package controller;

import com.google.inject.Inject;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Movie;
import model.Screening;
import model.User;

import java.awt.*;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.time.LocalDate;

public class GUIRawController implements GUIController{
    private GUIAppController guiAppController;

    @Inject
    private Communicator communicator;

    /* #################### */
    @FXML
    private TableView<Movie> moviesTable;
    @FXML
    private TableColumn<Movie, String> titleMovie;
    @FXML
    private TableColumn<Movie, String> author;
    @FXML
    private TableColumn<Movie, String> description;
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
    public Button getMoviesOfferButton;
    @FXML
    public Button getScreeningsOfferButton;
    @FXML
    public Button logInButton;
    @FXML
    public Button addUserButton;

    @Override
    public void setGuiAppController(GUIAppController guiAppController) {

        this.guiAppController = guiAppController;

    }

    @FXML
    private void initialize() {
        if (this.moviesTable != null) {
            moviesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            titleMovie.setCellValueFactory(movie -> movie.getValue().getTitleObs());
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
    }

    @FXML
    public void handleGetScreeningsOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.rawScreeningsLayout();
    }

    @FXML
    public void handleGetMoviesOfferAction(javafx.event.ActionEvent actionEvent) throws IOException {
        this.guiAppController.rawMoviesLayout();


    }

    @FXML
    public void handleLogInAction(ActionEvent actionEvent) throws IOException {
        User user = User.newUser();
        if(guiAppController.showLogInDialog(user)){
            communicator.login(user.getUserName(), user.getPassword(),
                    e -> {
                            System.out.println("Successfully logged in");
                        try {
                            guiAppController.adminStartLayout();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    },
                    e -> System.out.println("no connection or wrong credentials"));
            // tu trzeba zrobić dodanie do bazy
        }
    }

    @FXML
    public void handleAddUserAction(ActionEvent actionEvent) throws IOException {
        User user = User.newUser();
        if(guiAppController.showAddUserDialog(user)){

            communicator.addUser(user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getUserName(),
                    user.getPassword(),
                    e -> System.out.println("This success callback"),
                    e -> System.out.println("This is failure callback") );
            // tu trzeba zrobić dodanie do bazy
        }
    }
}
