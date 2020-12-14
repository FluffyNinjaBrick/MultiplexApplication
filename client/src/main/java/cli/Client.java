package cli;

import Model.User;
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
        name = "add-reservations"
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
        name = "add-seats"
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

    /*    private Integer ticketCost;
    private Date date;
    private long movieID;
    private long roomID;*/

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

/*#####################################################################*/
@Command(
        name = "get-user-by-id"
)
class GetUserByIdCommand implements Runnable {
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

@Command(
        name = "delete-user"
)
class DeleteUserCommand implements Runnable {
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

@Command(
        name = "get-user-reservations"
)
class GetUserReservationsCommand implements Runnable {
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

@Command(
        name = "single-reservation-cost"
)
class SumSingleReservationCostCommand implements Runnable {
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

@Command(
        name = "all-reservations-cost"
)
class SumAllReservationsCostCommand implements Runnable {
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

@Command(
        name = "show-empty-seats"
)
class ShowEmptySeatsCommand implements Runnable {
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

@Command(
        name = "get-screenings-offer"
)
class GetScreeningsOfferCommand implements Runnable {
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

@Command(
        name = "get-movies-offer"
)
class GetMoviesOfferCommand implements Runnable {
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
       ##wyświetlanie swoich rezerwacji;
       !!!podliczanie swoich rezerwacji;
       ##wyświetlanie rezerwacji na danych film;
       !!wyświetlanie pustych miejsc na film;
       ##wyświetlanie aktualnej listy seansów/filmów;

       wyświetlanie listy seansów od najbardziej zapełnionych do najmniej;
       wyświetlanie listy najbardziej aktywnych klientów;
       wyświetlanie proponowanych filmów dla siebie
       ### jakieś jeszcze? ###
    }
 */