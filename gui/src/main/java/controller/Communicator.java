package controller;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jakewharton.fliptables.FlipTable;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class Communicator {


    private String apiBaseUrl = "http://localhost:8080/api/";

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

    public void getMovies(EventHandler<WorkerStateEvent> successHandler, EventHandler<WorkerStateEvent> failHandler){
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
                        .header("Authorization", "Bearer " + authInfo.getToken())
                        .uri(URI.create(apiURLfinal))
                        .build();

                    ObjectMapper mapper = new ObjectMapper();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//                    movies = mapper.readValue(response.body(), new TypeReference<ObservableList<Movie>>() {});
                } catch (ConnectException e){
                    System.out.println("ERROR: Couldn't connect with server.");
                } catch (
                        IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
                return movies;
            }
        };
        getMoviesTask.setOnSucceeded(successHandler);
        getMoviesTask.setOnFailed(failHandler);
        exec.execute(getMoviesTask);


    }
    public void addUser(String firstName, String lastName, String username, String password, String email,
                        EventHandler<WorkerStateEvent> successHandler, EventHandler<WorkerStateEvent> failHandler){
        String apiSpecStr = "users/";

        String apiURL = apiBaseUrl + apiSpecStr;
        Task<ObservableList<Movie>> getMoviesTask = new Task<ObservableList<Movie>>(){
            @Override
            public ObservableList<Movie> call() throws Exception{
                ObservableList<Movie> movies = null;
                System.out.println("Adding user..." + firstName + " " + lastName + " " + email);
                String request_body = String.format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"username\": \"%s\", \"password\": \"%s\"}", firstName, lastName, email, username, password);
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
                return movies;
            }
        };
        getMoviesTask.setOnSucceeded(successHandler);
        getMoviesTask.setOnFailed(failHandler);
        exec.execute(getMoviesTask);


    }
}

