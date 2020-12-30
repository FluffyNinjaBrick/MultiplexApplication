import cli.BasicModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import controller.GUIAppController;

public class GUIApp extends Application{

    private Stage primaryStage;

    private GUIAppController guiAppController;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Multiplex");
        this.guiAppController = new GUIAppController(primaryStage);
        this.guiAppController.initRootLayout();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
// TODO wylogowanie

// TODO dodaj film - addMovie(Movie movie)
// TODO Zrob rezerwajce - addReservation(Reservation reservation)
// TODO Dodaj Seans - addScreening(Screening screening)
// TODO add new seat - addSeat(Seat seat)
// TODO add screening room - addScreeningRoom(ScreeningRoom screeningRoom)
// TODO usun uzytkownika  - deleteUser(User user)

// TODO pokaz uzytkownika z id - showUserById(User user)###
// TODO pokaz wolne miejsca  - showEmptySeats(Screening screening)###
// TODO pokaz wszystkich uzytkownikow  - showUsers()###


// TODO pokaz rezerwacje
// TODO Podlicz wszystkie rezerwacje
// TODO podlicz jedna rezerwacej uzytkownika