package controller;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jakewharton.fliptables.FlipTable;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.stream.Collectors;

public class Communicator {

    @Inject
    @Named("apiBaseUrl")
    private String apiBaseUrl;// = "http://localhost:8080/api/";

    @Inject
    Authentication authInfo;

    private User lastUser;
    private Screening lastScreening;



    private Reservation lastReservation;
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
    public Reservation getLastReservation() {
        return lastReservation;
    }

    public void setLastReservation(Reservation lastReservation) {
        this.lastReservation = lastReservation;
    }
    public Screening getLastScreening() {
        return lastScreening;
    }

    public void setLastScreening(Screening lastScreening) {
        this.lastScreening = lastScreening;
    }
    public User getLastUser() {
        return lastUser;
    }

    public void setLastUser(User lastUser) {
        this.lastUser = lastUser;
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
        String apiSpecStr = "admin/users/";

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
                    e.printStackTrace();
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
                    int commaPos = response.body().indexOf(",");
                    String bodyJWT = response.body().substring(0,commaPos) + "}";
                    System.out.println(bodyJWT);

                    String bodyUserDetails = "{" + response.body().substring(commaPos+1);
                    Map<String, String> bodyJwtMap = mapper.readValue(bodyJWT, new TypeReference<Map<String, String>>() {});
                    Map<String, User> bodyUserMap = mapper.readValue(bodyUserDetails, new TypeReference<Map<String, User>>() {});
                    if(response.statusCode() != 200){
                        throw new ConnectException("response code: " + response.statusCode());
                    }
                    List<String> roles = bodyUserMap.get("userDetails").getRoles().stream()
                            .map(r -> r.get("authority")).collect(Collectors.toList());
                    authInfo.setRoles(roles);
                    System.out.println(authInfo.getRoles().toString());
                    authInfo.setToken(bodyJwtMap.get("jwt"));
                    authInfo.setUserId(bodyUserMap.get("userDetails").getId());
                    return  response.statusCode();
                }catch (Exception e){
//                    e.printStackTrace();
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
                try {
                System.out.println("Adding reservation.." );
                String request_body = String.format("{" +
                                "\"screeningId\": \"%d\", " +
                                "\"userId\": \"%d\", " +
                                "\"seatNumber\": \"%d\", " +
                                "\"rowNumber\": \"%d\"" +
                                "}",
                        reservation.getScreeningId(),
                        reservation.getUserId(),
                        reservation.getSeatObj().getSeatNumber(),
                        reservation.getSeatObj().getRowNumber());
                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURL))
                        .build();


                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(request_body);
                    System.out.println(response.body());
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
                                "\"roomNumber\": \"%s\"" +
                                "}",
                        seat.getSeatNumber(),
                        seat.getRowNumber(),
                        seat.getScreeningRoom().getNumber());
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
                        System.out.println(response.body());
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
                                "\"movieTitle\": \"%s\", " +
                                "\"roomNumber\": \"%s\"" +
                                "}",
                        screening.getTicketCost(),
                        screening.getDate(),
                        screening.getMovie().getTitle(),
                        screening.getScreeningRoomObject().getNumber());

                    HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(request_body))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURL))
                        .build();


                    ObjectMapper mapper = new ObjectMapper();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.body());
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
        String apiSpecStr = "admin/rooms/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                System.out.println("Adding screeningRoom.." );
                String request_body = String.format("{" +
                                "\"number\": \"%s\", " +
                                "\"floor\": \"%s\"" +
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
                        System.out.println(response.body());
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
                        .header("Authorization", "Bearer " + authInfo.getToken())
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
        String apiSpecStr = "user/seats/"; // TODO
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
                            .uri(URI.create(apiURLfinal + screening.getId()))
                            .build();

                    ObjectMapper mapper = new ObjectMapper();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.body());

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
    public Task<Integer> singleReservationCost(Reservation reservation){
        String apiSpecStr = "user/reservations/cost/forUser/"; // TODO
        String apiURLfinal = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                Integer data = null;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                            .header("Authorization", "Bearer " + authInfo.getToken())
                            .uri(URI.create(apiURLfinal + reservation.getUserId() + "/forScreening/" + reservation.getScreeningId()))
                            .build();
                    System.out.println(apiURLfinal + reservation.getUserId() + "/forScreening/" + reservation.getScreeningId());
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println("body" + response.body());
                    if(response.body() == null || response.body().equals("")){
                        return 0;
                    }

                    data = Integer.parseInt(response.body());

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
    public Task<Integer> allReservationCost(User user){
        String apiSpecStr = "user/reservations/cost/forUser/"; // TODO
        String apiURLfinal = apiBaseUrl + apiSpecStr;
        Task<Integer> task = new Task<Integer>(){
            @Override
            public Integer call() throws Exception{
                Integer data = null;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                            .header("Authorization", "Bearer " + authInfo.getToken())
                            .uri(URI.create(apiURLfinal + user.getId()))
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println("body" + response.body());
                    if(response.body() == null || response.body().equals("")){
                        return 0;
                    }

                    data = Integer.parseInt(response.body());
                    System.out.println(data);
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
    public Task<ObservableList<Map<String, SimpleStringProperty>>> getUserReservations(User user){
        String apiSpecStr = "user/reservations/forUser/"; // TODO
        String apiURLfinal = apiBaseUrl + apiSpecStr;

        Communicator communicator = this;

        Task<ObservableList<Map<String, SimpleStringProperty>>> task = new Task<ObservableList<Map<String, SimpleStringProperty>>>(){
            @Override
            public ObservableList<Map<String, SimpleStringProperty>> call() throws Exception{
                ObservableList<Map<String, SimpleStringProperty>> data = null;
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .GET()
                            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                            .header("Authorization", "Bearer " + authInfo.getToken())
                            .uri(URI.create(apiURLfinal + user.getId()))
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if(response.body() == null || response.body().equals("")){
                        return new SimpleListProperty<Map<String, SimpleStringProperty>>();

                    }
                    ObjectMapper mapper = new ObjectMapper();
                    List<Map<String, String>> dataRaw= mapper.readValue(response.body(), new TypeReference<List<Map<String, String>>>() {});
                    //get screenings
                    Task<ObservableList<Screening>> taskScreening = communicator.getScreenings();
                    communicator.execute(taskScreening);
                    ObservableList<Screening> screenings = taskScreening.get();
                    List<Map<String, SimpleStringProperty>> dataRaw2 = dataRaw.stream().map(rec -> {
                        Map<String, SimpleStringProperty> map =  new HashMap<String, SimpleStringProperty>();
                        for( Map.Entry<String, String> str : rec.entrySet()){
                            map.put(str.getKey(), new SimpleStringProperty(str.getValue()));
                        }
                        return map;
                    }).collect(Collectors.toList());
                    dataRaw2 = dataRaw2.stream().map(record -> {
                        Screening screening = screenings.stream()
                                .filter( scr -> scr.getId() == Long.parseLong(record.get("screening").get()))
                                .findAny().orElse(null);
                        if(screening == null){
                            System.out.println("error downloading screenings");
                        }
                        record.put("title", new SimpleStringProperty(screening.getMovie().getTitle()));
                        record.put("date", new SimpleStringProperty(screening.getDate()));
                        record.put("cost", new SimpleStringProperty(screening.getTicketCost().toString()));
                        record.put("screeningRoom", new SimpleStringProperty(String.valueOf(screening.getScreeningRoomId())));
                        String seatRow = record.get("seat").getValue().split(",")[0].split(":")[1].substring(1);
                        String seatNumber = record.get("seat").getValue().split(",")[1].split(":")[1].substring(1);
                        record.put("seatRow", new SimpleStringProperty(seatRow));
                        record.put("seatNumber", new SimpleStringProperty(seatNumber));
                        return record;

                    }).collect(Collectors.toList());


                    System.out.println(dataRaw2.toString());
                    data = FXCollections.observableList(dataRaw2);
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

