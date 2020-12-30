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
import model.Authentication;
import model.Movie;

import javafx.event.EventHandler;
import picocli.CommandLine;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        String apiSpecStr = "movies";


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
    public void addUser(String firstName, String lastName, String username, String password, String email,
                        EventHandler<WorkerStateEvent> successHandler, EventHandler<WorkerStateEvent> failHandler){
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
        task.setOnSucceeded(successHandler);
        task.setOnFailed(failHandler);
        exec.execute(task);


    }
    public void login(String username, String password,
                        EventHandler<WorkerStateEvent> successHandler, EventHandler<WorkerStateEvent> failHandler){
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
        task.setOnSucceeded(successHandler);
        task.setOnFailed(failHandler);
        exec.execute(task);

    }
}

