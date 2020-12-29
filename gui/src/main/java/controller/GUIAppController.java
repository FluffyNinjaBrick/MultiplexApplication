package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import presenter.*;

import java.io.IOException;

public class GUIAppController {
    private Stage primaryStage;
    private GUIController controller;
    public GUIAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout(){
        try{
            this.primaryStage.setTitle("Multiplex");
            FXMLLoader loader = new FXMLLoader();
            rawStartLayout(loader);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void showScene(FXMLLoader loader) throws IOException {
        BorderPane rootLayout = loader.load();
        controller = loader.getController();
        controller.setGuiAppController(this);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    /*###############################  DIALOG SECTION    ########################################################*/

    public boolean showDeleteDialog(User user) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIAppController.class.getResource("../operations/DeleteUserDialog.fxml"));

            BorderPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Delete user");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DeleteUserPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(user);
            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddSeatDialog(Seat seat) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIAppController.class.getResource("../operations/AddSeatDialog.fxml"));

            BorderPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new seat");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddSeatPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(seat);
            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddScreeningRoomDialog(ScreeningRoom screeningRoom) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIAppController.class.getResource("../operations/AddScreeningRoomDialog.fxml"));

            BorderPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new screening");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddScreeningRoomPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(screeningRoom);
            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddScreeningDialog(Screening screening) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIAppController.class.getResource("../operations/AddScreeningDialog.fxml"));

            BorderPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new screening");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddScreeningPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(screening);
            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddReservationDialog(Reservation reservation) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIAppController.class.getResource("../operations/AddReservationDialog.fxml"));

            BorderPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddReservationPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(reservation);
            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddUserDialog(User user) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIAppController.class.getResource("../operations/AddUserDialog.fxml"));

            BorderPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new user");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddUserPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(user);
            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddMovieDialog(Movie movie) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIAppController.class.getResource("../operations/AddUserDialog.fxml"));

            BorderPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new user");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddMoviePresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setData(movie);
            dialogStage.showAndWait();
            return presenter.isApproved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*###############################  RAW SECTION    ########################################################*/

    public void rawStartLayout(FXMLLoader loader) throws IOException {
        loader.setLocation(GUIAppController.class.getResource("../raw_views/RawStartView.fxml"));
        showScene(loader);
    }

    public void rawMoviesLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../raw_views/RawMoviesView.fxml"));
        showScene(loader);
    }

    public void rawScreeningsLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../raw_views/RawScreeningsView.fxml"));
        showScene(loader);
    }
    /*###############################  USER SECTION    ########################################################*/

    public void userStartLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../user_views/UserStartView.fxml"));
        showScene(loader);
    }
    public void userByIdLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../user_views/UserByIdView.fxml"));
        showScene(loader);
    }
    public void userMoviesLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../user_views/UserMoviesView.fxml"));
        showScene(loader);
    }
    public void userReservationsLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../user_views/UserReservationsView.fxml"));
        showScene(loader);
    }
    public void userScreeningsLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../user_views/UserScreeningsView.fxml"));
        showScene(loader);
    }
    public void userSeatsLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../user_views/UserSeatsView.fxml"));
        showScene(loader);
    }
    public void userSingleReservationCostLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../user_views/UserSingleReservationCostView.fxml"));
        showScene(loader);
    }
    public void userAllReservationsCostLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../user_views/UserAllReservationsView.fxml"));
        showScene(loader);
    }

    /*###############################  ADMIN SECTION    ########################################################*/
    public void adminStartLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminStartView.fxml"));
        showScene(loader);
    }
    public void adminUserByIdLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminUserByIdView.fxml"));
        showScene(loader);
    }
    public void adminMoviesLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminMoviesView.fxml"));
        showScene(loader);
    }
    public void adminUserReservationsLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminUserReservationsView.fxml"));
        showScene(loader);
    }
    public void adminScreeningsLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminScreeningsView.fxml"));
        showScene(loader);
    }
    public void adminSeatsLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminSeatsView.fxml"));
        showScene(loader);
    }
    public void adminSingleReservationCostLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminSingleReservationView.fxml"));
        showScene(loader);
    }
    public void adminAllReservationsCostLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminAllReservationsView.fxml"));
        showScene(loader);
    }
    public void adminAllUsersLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUIAppController.class.getResource("../admin_views/AdminUsersView.fxml"));
        showScene(loader);
    }
}
