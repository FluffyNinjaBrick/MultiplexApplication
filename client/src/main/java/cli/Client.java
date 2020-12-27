package cli;

import Model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jakewharton.fliptables.FlipTable;
import com.jakewharton.fliptables.FlipTableConverters;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

@Command(
        name = "show-users"
)
class ShowUsersCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080/api/users";

    @Override
    public void run() {
        System.out.println("Getting users...");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<User> users = mapper.readValue(response.body(), new TypeReference<List<User>>() {});
            String[][] usersToDisplay = users.stream().map(user -> {
                String[] data = new String[4];
                data[0] = Long.toString(user.getId());
                data[1] = user.getFirstName();
                data[2] = user.getLastName();
                data[3] = user.getEmail();
                return data;
            }).toArray(size -> new String[size][4]);
            System.out.println(FlipTable.of(new String[]{"id", "first name", "last name", "email"}, usersToDisplay));
        } catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
@Command(
        name = "add-user"
)
class AddUserCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080/api/users";

    @Parameters(index = "0", description = "first name")
    private String firstName;
    @Parameters(index = "1", description = "last name")
    private String lastName;
    @Parameters(index = "2", description = "email address")
    private String email;
    @Override
    public void run() {

        System.out.println("Adding user..." + firstName + " " + lastName + " " + email);
        String request_body = String.format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\"}", firstName, lastName, email);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(request_body))
                .header("Content-Type", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
@Command(
        name = "add-screening-room"
)
class AddScreeningRoomCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080/api/rooms";

    @Parameters(index = "0", description = "number")
    private String number;

    @Parameters(index = "1", description = "floor")
    private int floor;
    @Override
    public void run() {

        System.out.println("Adding room..." + number+ ", floor: " + floor);
        String request_body = String.format("{\"number\": \"%s\", \"floor\": \"%d\"}", number, floor);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(request_body))
                .header("Content-Type", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@Command(
        name = "add-movie"
)
class AddMovieCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080/api/movies";

    @Parameters(index = "0", description = "title")
    private String title;

    @Parameters(index = "1", description = "author")
    private String author;

    @Parameters(index = "2", description = "genre")
    private String genre;

    @Parameters(index = "3", description = "description")
    private String description;

    @Override
    public void run() {

        System.out.println("Adding movie..." + title+ ", author: " + author+", genre: "+genre+", description: "+description);

        String request_body = String.format("{\"title\": \"%s\", \"author\": \"%s\", \"genre\": \"%s\", \"description\": \"%s\"}", title, author, genre, description);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(request_body))
                .header("Content-Type", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@Command(
        name = "add-reservation"
)
class AddReservationsCommand implements Runnable{
    private static final String apiURL = "http://localhost:8080/api/reservations";

    @Parameters(index = "0", description = "userID")
    private int userID;

    @Parameters(index = "1", description = "screeningID")
    private int screeningID;

    @Parameters(index = "2", description = "seatID")
    private int seatID;

    @Override
    public void run() {

        System.out.println("Adding reservation..." + userID+ ", screeningID: " + screeningID+", seatID: "+seatID);
        String request_body = String.format("{\"userId\": \"%d\", \"seatId\": \"%d\", \"screeningId\": \"%d\"}", userID, seatID, screeningID);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(request_body))
                .header("Content-Type", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@Command(
        name = "add-seat"
)

class AddSeatCommand implements Runnable{
    private static final String apiURL = "http://localhost:8080/api/seats";

    @Parameters(index = "0", description = "roomId")
    private int roomId;

    @Parameters(index = "1", description = "number")
    private int number;

    @Parameters(index = "2", description = "row")
    private int row;

    @Override
    public void run() {

        System.out.println("Adding seat..." + number+ ", row: " + row+", room: "+roomId);
        String request_body = String.format("{\"number\": \"%d\", \"row\": \"%d\", \"roomID\": \"%d\"}", number, row, roomId);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(request_body))
                .header("Content-Type", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@Command(
        name = "add-screening"
)
class AddScreeningCommand implements Runnable{
    private static final String apiURL = "http://localhost:8080/api/screenings";

    @Parameters(index = "0", description = "ticketCost")
    private int ticketCost;

    @Parameters(index = "1", description = "date")
    private String date;

    @Parameters(index = "2", description = "movieID")
    private int movieID;

    @Parameters(index = "3", description = "roomID")
    private int roomID;

    @Override
    public void run() {

        System.out.println("Adding screenig to room..." + roomID+ ", movieID: " + movieID+", date: "+date+", ticketCost"+ticketCost);
        String request_body = String.format("{\"ticketCost\": \"%d\", \"date\": \"%s\", \"movieID\": \"%d\", \"roomID\": \"%d\"}", ticketCost, date, movieID, roomID);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(request_body))
                .header("Content-Type", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


@Command(
        name = "get-user-by-id"
)
class GetUserByIdCommand implements Runnable {
    private final String apiURL = "http://localhost:8080/api/users/";

    @Parameters(index = "0", description = "userId")
    private long userId;

    @Override
    public void run() {
        System.out.println("Getting user with id: "+userId);
        String request_body = String.format("{\"userId\": \"%d\"}", userId);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiURL+this.userId))
                .build();
        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            User user = mapper.readValue(response.body(), new TypeReference<User>(){});
            String[][] data = new String[1][4];
            data[0][0] = Long.toString(user.getId());
            data[0][1] = user.getFirstName();
            data[0][2] = user.getLastName();
            data[0][3] = user.getEmail();
            System.out.println(FlipTable.of(new String[]{"id", "first name", "last name", "email"}, data));


            //System.out.println(response.body());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
@Command(
        name = "delete-user"
)
class DeleteUserCommand implements Runnable {
    private final String apiURL = "http://localhost:8080/api/users/";

    @Parameters(index = "0", description = "userId")
    private long userId;

    @Override
    public void run() {
        System.out.println("Deleting user with id: "+userId);
        String request_body = String.format("{\"userId\": \"%d\"}", userId);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .header("accept", "application/json")
                .uri(URI.create(apiURL+this.userId))
                .build();
        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//NOT WORKING YER
@Command(
        name = "get-user-reservations"
)
class GetUserReservationsCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080/api";
    @Parameters(index = "0", description = "userId")
    private long userId;
    @Override
    public void run() {
        System.out.println("TEST command");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiURL+"/reservations/forUser/"+this.userId))
                .build();
        HttpRequest requestScreening = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiURL+"/screenings"))
                .build();
        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseScreening = client.send(requestScreening, HttpResponse.BodyHandlers.ofString());
            List<Screening> screenings = mapper.readValue(responseScreening.body(), new TypeReference<List<Screening>>(){});
            List<Reservation> reservations = mapper.readValue(response.body(), new TypeReference<List<Reservation>>() {});
            String[][] dataToDisplay = reservations.stream().map(res -> {
                Screening scr = screenings.stream().filter((screening -> {
                    return screening.getId() == res.getScreening();
                })).findFirst().get();
                String[] data = new String[7];
                data[0] = String.valueOf(res.getId());
                data[1] = scr.getDate().toString();
                data[2] = scr.getMovie().getTitle();
                data[3] = scr.getMovie().getAuthor();
                data[4] = scr.getTicketCost().toString();
                data[5] = String.valueOf(scr.getScreeningRoom());
                data[6] = res.getSeat();

                return data;
            }).toArray(size -> new String[size][2]);
            System.out.println(FlipTable.of(new String[]{"ID", "date", "movie title", "movie author", "ticket cost", "screening room", "seat"}, dataToDisplay));
        } catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

@Command(
        name = "single-reservation-cost"
)
class SumSingleReservationCostCommand implements Runnable {
    @Parameters(index = "0", description = "userId")
    private long userId;

    @Parameters(index = "1", description = "screeningId")
    private long screeningId;

    @Override
    public void run() {
        System.out.println("Getting users: "+userId+" cost of one screenings: "+screeningId);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://localhost:8080/api/reservations/forUser/"+this.userId+"/forScreening/"+this.screeningId))
                .build();
        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String cost = response.body();
            String[][] data = new String[1][1];
            data[0][0] = cost;
            System.out.println(FlipTable.of(new String[]{"cost"}, data));
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*#####################################################################*/
@Command(
        name = "all-reservations-cost"
)
class SumAllReservationsCostCommand implements Runnable {
    @Parameters(index = "0", description = "userId")
    private long userId;


    @Override
    public void run() {
        System.out.println("Getting users: "+userId+" total costs");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://localhost:8080/api/reservations/forUser/"+this.userId+"/total"))
                .build();
        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String cost = response.body();
            String[][] data = new String[1][1];
            data[0][0] = cost;
            System.out.println(FlipTable.of(new String[]{"cost"}, data));
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@Command(
        name = "show-empty-seats"
)
class ShowEmptySeatsCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080/api";

    @Parameters(index = "0", description = "screening id")
    private long screening_id;
    @Override
    public void run() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiURL+"/seats/forScreening/"+this.screening_id))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Seat> seats = mapper.readValue(response.body(), new TypeReference<List<Seat>>() {});
            String[][] dataToDisplay = seats.stream().map(seat -> {
                String[] data = new String[2];
                data[0] = String.valueOf(seat.getRowNumber());
                data[1] = String.valueOf(seat.getSeatNumber());
                return data;
            }).toArray(size -> new String[size][2]);
            System.out.println(FlipTable.of(new String[]{"row", "seat"}, dataToDisplay));
        } catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

@Command(
        name = "get-screenings-offer"
)
class GetScreeningsOfferCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080";

    @Override
    public void run() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiURL+"/api/screenings"))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Screening> screenings = mapper.readValue(response.body(), new TypeReference<List<Screening>>() {});
            String[][] moviesToDisplay = screenings.stream().map(screening -> {
                String[] data = new String[5];
                data[0] = String.valueOf(screening.getId());
                data[1] = screening.getDate().toString();
                data[2] = screening.getTicketCost().toString();
                data[3] = screening.getMovie().getTitle();
                data[4] = screening.getMovie().getAuthor();
                return data;
            }).toArray(size -> new String[size][5]);
            System.out.println(FlipTable.of(new String[]{"id", "date", "ticket cost", "title", "author"}, moviesToDisplay));
        } catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

@Command(
        name = "get-movies-offer"
)
class GetMoviesOfferCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080/api/movies";

    @Override
    public void run() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Movie> movies = mapper.readValue(response.body(), new TypeReference<List<Movie>>() {});
            String[][] usersToDisplay = movies.stream().map(movie -> {
                String[] data = new String[4];
                data[0] = String.valueOf(movie.getId());
                data[1] = movie.getTitle();
                data[2] = movie.getAuthor();
                data[3] = movie.getDescription();
                return data;
            }).toArray(size -> new String[size][4]);
            System.out.println(FlipTable.of(new String[]{"id", "title", "author", "description"}, usersToDisplay));
        } catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*##########################################################################################*/
@Command(
        name = "test-api"
)
class TestCommand implements Runnable {
    private static final String apiURL = "http://localhost:8080";

    @Override
    public void run() {
        System.out.println("TEST command");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiURL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }  catch (java.net.ConnectException e){
            System.out.println("ERROR: Couldn't connect with server.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
@Command(name = "multiplex-cli", mixinStandardHelpOptions = true, version = "checksum 4.0",
        description = "Multiplex command-line interface")

class MultiplexCli implements Callable<Integer> {


    @Override
    public Integer call() {

        System.out.println("error");
        return 0;
    }


}

public class Client {
    public static void main(String... args) {

        CommandLine cli = new CommandLine(new MultiplexCli());
        cli.addSubcommand(new AddScreeningRoomCommand());
        cli.addSubcommand(new AddUserCommand());
        cli.addSubcommand(new AddMovieCommand());
        cli.addSubcommand(new AddReservationsCommand());
        cli.addSubcommand(new AddSeatCommand());
        cli.addSubcommand(new AddScreeningCommand());
        cli.addSubcommand(new DeleteUserCommand());
        cli.addSubcommand(new GetMoviesOfferCommand());
        cli.addSubcommand(new GetScreeningsOfferCommand());
        cli.addSubcommand(new GetUserByIdCommand());
        cli.addSubcommand(new GetUserReservationsCommand());
        cli.addSubcommand(new ShowEmptySeatsCommand());
        cli.addSubcommand(new SumAllReservationsCostCommand());
        cli.addSubcommand(new SumSingleReservationCostCommand());
        cli.addSubcommand(new TestCommand());
        cli.addSubcommand(new ShowUsersCommand());

        Scanner scanner = new Scanner(System.in);
        String cmd;
        System.out.println("Enter command (q to quite): ");
        while(scanner.hasNextLine()){
            cmd = scanner.nextLine();
            if("q".equalsIgnoreCase(cmd)) break;
            System.out.println(cmd);

            cli.execute(cmd.split(" "));
            System.out.println("Enter command (q to quite): ");

        }
    }
}
/*
TODO: HttpClient one instance
TODO: proper exception handling
 */

/*
TODO: (funkcje użytkownika) ->
    {
       wyświetlanie listy seansów od najbardziej zapełnionych do najmniej;
       wyświetlanie listy najbardziej aktywnych klientów;
       wyświetlanie proponowanych filmów dla siebie
       ### jakieś jeszcze? ###
    }
 */