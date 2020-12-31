package controller;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jakewharton.fliptables.FlipTable;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import model.*;

import javafx.event.EventHandler;
import picocli.CommandLine;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class Communicator {

//    @Inject
//    @Named("apiBaseUrl")
    private String apiBaseUrl = "http://localhost:8080/api/"; // TODO injection doesnt work

    @Inject
    Authentication authInfo;

    private Executor exec ;
    private HttpClient client;

    public Communicator() {
         client = HttpClient.newHttpClient();

        // create executor that uses daemon threads:
        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t ;
        });
    }
    public void execute(Runnable task){
        exec.execute(task);
    }

    public Task<ObservableList<Movie>> getMovies(){
        String apiSpecStr = "movies/";


        String apiURLfinal = apiBaseUrl + apiSpecStr;
        Task<ObservableList<Movie>> getMoviesTask = new Task<ObservableList<Movie>>(){
            @Override
            public ObservableList<Movie> call() throws Exception{
                ObservableList<Movie> movies = null;
                try {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .header("accept", "application/json")
//                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURLfinal))
                        .build();

                    ObjectMapper mapper = new ObjectMapper();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.body() == null || response.body().equals("")){
                        return new SimpleListProperty<Movie>();
                    }
                    List<Movie>  movieRaw= mapper.readValue(response.body(), new TypeReference<List<Movie>>() {});
                    movies = FXCollections.observableList(movieRaw);

                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }

                return movies;
            }
        };
        return getMoviesTask;
    }

    public Task<Integer> addUser(String firstName, String lastName, String username, String password, String email){
        String apiSpecStr = "users/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                System.out.println("Adding user..." + firstName + " " + lastName + " " + email);
                String request_body = String.format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"username\": \"%s\", \"password\": \"%s\"}", firstName, lastName, email, username, password);
                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .uri(URI.create(apiURL))
                        .build();

                try {
                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    return response.statusCode();
                }catch (Exception e){
                    throw e;
                }
            }
        };
        return task;

    }
    public Task<Integer> deleteUser(User user){
        String apiSpecStr = "users/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                System.out.println("delete user..." + user.getId());
                String request_body = String.format("{\"userId\": \"%d\"}", user.getId());
                HttpRequest request = HttpRequest.newBuilder()
                        .DELETE()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURL + user.getId()))
                        .build();

                try {
                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    return response.statusCode();
                }catch (Exception e){
                    throw e;
                }
            }
        };
        return task;

    }
    public Task<Integer> login(String username, String password){
        String apiSpecStr = "authenticate/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                System.out.println("Logging in..." + username);
                String request_body = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);
                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .uri(URI.create(apiURL))
                        .build();

                try {
                    ObjectMapper mapper = new ObjectMapper();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    Map<String, String> body = mapper.readValue(response.body(), new TypeReference<Map<String, String>>() {});
                    System.out.println(response.body());
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    System.out.println(body.get("jwt"));
                    authInfo.setToken(body.get("jwt"));
                    return  response.statusCode();
                }catch (Exception e){
                    throw e;
                }
            }
        };
        return task;

    }
    public Task<ObservableList<Screening>> getScreenings(){
        String apiSpecStr = "screenings/";
        String apiURLfinal = apiBaseUrl + apiSpecStr;
        Task<ObservableList<Screening>> task = new Task<ObservableList<Screening>>(){
            @Override
            public ObservableList<Screening> call() throws Exception{
                ObservableList<Screening> data = null;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .header("accept", "application/json")
//                        .header("Authorization", "Bearer " + authInfo.getToken())
                            .uri(URI.create(apiURLfinal))
                            .build();

                    ObjectMapper mapper = new ObjectMapper();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.body() == null || response.body().equals("")){
                        return new SimpleListProperty<Screening>();
                    }
                    List<Screening>  dataRaw= mapper.readValue(response.body(), new TypeReference<List<Screening>>() {});
                    data = FXCollections.observableList(dataRaw);

                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }

                return data;
            }
        };
        return task;
    }
    public Task<Integer> addMovie(Movie movie){
        String apiSpecStr = "admin/movies/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                System.out.println("Adding movie..." + movie.getTitle() );
                String request_body = String.format("{\"title\": \"%s\", " +
                        "\"author\": \"%s\", " +
                        "\"description\": \"%s\"}",
                        movie.getTitle(), movie.getAuthor(), movie.getDescription());
                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURL))
                        .build();

                try {
                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    return response.statusCode();
                }catch (Exception e){
                    throw e;
                }
            }
        };
        return task;

    }
    public Task<Integer> addReservation(Reservation reservation){
        String apiSpecStr = "admin/reservations/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                System.out.println("Adding reservation.." );
                String request_body = String.format("{" +
                                "\"screeningId\": \"%s\", " +
                                "\"userId\": \"%s\", " +
                                "\"seatId\": \"%s\"" +
                                "}",
                        reservation.getScreeningId(),
                        reservation.getUserId(),
                        reservation.getSeatId());
                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURL))
                        .build();

                try {
                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    return response.statusCode();
                }catch (Exception e){
                    throw e;
                }
            }
        };
        return task;

    }
    public Task<Integer> addSeat(Seat seat){
        String apiSpecStr = "admin/seats/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                System.out.println("Adding seat.." );
                String request_body = String.format("{" +
                                "\"number\": \"%s\", " +
                                "\"row\": \"%s\", " +
                                "\"roomID\": \"%s\"" +
                                "}",
                        seat.getSeatNumber(),
                        seat.getRowNumber(),
                        seat.getScreeningRoomId());
                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURL))
                        .build();

                try {
                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    return response.statusCode();
                }catch (Exception e){
                    throw e;
                }
            }
        };
        return task;

    }
    public Task<Integer> addScreening(Screening screening){
        String apiSpecStr = "admin/screenings/";
        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                // Conversion
                try {

                    System.out.println(screening.getDate().toString());
                System.out.println("Adding screening.." );
                String request_body = String.format("{" +
                                "\"ticketCost\": \"%d\", " +
                                "\"date\": \"%s\", " +
                                "\"movieId\": \"%d\"" +
                                "\"roomId\": \"%d\"" +
                                "}",
                        screening.getTicketCost(),
                        screening.getDate(),
                        screening.getMovieId(),
                        screening.getScreeningRoomId());
                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURL))
                        .build();


                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    return response.statusCode();
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }
            }
        };
        return task;

    }
    public Task<Integer> addScreeningRoom(ScreeningRoom screeningRoom){
        String apiSpecStr = "admin/screeningRooms/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                System.out.println("Adding screeningRoom.." );
                String request_body = String.format("{" +
                                "\"number\": \"%s\", " +
                                "\"floor\": \"%s\", " +
                                "}",
                        screeningRoom.getNumber(),
                        screeningRoom.getFloor());

                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURL))
                        .build();

                try {
                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    return response.statusCode();
                }catch (Exception e){
                    throw e;
                }
            }
        };
        return task;

    }
    public Task<User> showUserById(User user){
        String apiSpecStr = "user/users/";
        String apiURLfinal = apiBaseUrl + apiSpecStr;
        Task<User> task = new Task<User>(){
            @Override
            public User call() throws Exception{
                User data = null;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .header("accept", "application/json")
//                        .header("Authorization", "Bearer " + authInfo.getToken())
                            .uri(URI.create(apiURLfinal + user.getId()))
                            .build();

                    ObjectMapper mapper = new ObjectMapper();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.body() == null || response.body().equals("")){
                        return new User();
                    }
                    User  dataRaw= mapper.readValue(response.body(), new TypeReference<User>() {});
                    data = dataRaw;

                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }

                return data;
            }
        };
        return task;
    }
    public Task<ObservableList<Seat>> showEmptySeats(Screening screening){
        String apiSpecStr = "screenings/"; // TODO
        String apiURLfinal = apiBaseUrl + apiSpecStr;
        Task<ObservableList<Seat>> task = new Task<ObservableList<Seat>>(){
            @Override
            public ObservableList<Seat> call() throws Exception{
                ObservableList<Seat> data = null;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .header("accept", "application/json")
                            .header("Authorization", "Bearer " + authInfo.getToken())
                            .uri(URI.create(apiURLfinal))
                            .build();

                    ObjectMapper mapper = new ObjectMapper();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.body() == null || response.body().equals("")){
                        return new SimpleListProperty<Seat>();
                    }
                    List<Seat>  dataRaw= mapper.readValue(response.body(), new TypeReference<List<Seat>>() {});
                    data = FXCollections.observableList(dataRaw);

                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }

                return data;
            }
        };
        return task;
    }
    public Task<ObservableList<User>> showUsers(){
        String apiSpecStr = "admin/users/"; // TODO
        String apiURLfinal = apiBaseUrl + apiSpecStr;
        Task<ObservableList<User>> task = new Task<ObservableList<User>>(){
            @Override
            public ObservableList<User> call() throws Exception{
                ObservableList<User> data = null;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .header("accept", "application/json")
                            .header("Authorization", "Bearer " + authInfo.getToken())
                            .uri(URI.create(apiURLfinal))
                            .build();

                    ObjectMapper mapper = new ObjectMapper();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.body() == null || response.body().equals("")){
                        return new SimpleListProperty<User>();
                    }
                    List<User>  dataRaw= mapper.readValue(response.body(), new TypeReference<List<User>>() {});
                    data = FXCollections.observableList(dataRaw);

                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }

                return data;
            }
        };
        return task;
    }
}

