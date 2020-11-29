package cli;

import Model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jakewharton.fliptables.FlipTableConverters;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
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

            System.out.println(FlipTableConverters.fromIterable(users, User.class));
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
//        String firstName = "karol";
//        String lastName = "Hamielec";
//        String email = "SSSS";
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
        } catch (IOException e) {
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
    @Parameters(index = "0", description = "letter")
    private char letter;
    @Parameters(index = "1", description = "description")
    private String description;
    @Parameters(index = "2", description = "floor")
    private int floor;
    @Override
    public void run() {
        System.out.println("Adding room..." + letter+ ", floor: " + floor + " - " + description);
    }
}
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
        cli.addSubcommand(new TestCommand());
        cli.addSubcommand(new ShowUsersCommand());

        BufferedReader br = null;
        Scanner scanner = new Scanner(System.in);
        String cmd;
        while(scanner.hasNextLine()){
            System.out.println("Enter command (q to quite): ");
            cmd = scanner.nextLine();
            if("q".equalsIgnoreCase(cmd)) break;
            cli.execute(cmd.split(" "));
            System.out.println(cmd);
        }
//        InputStream is = null;
//        BufferedReader br = null;

//        try {
//
//            is = System.in;
//            br = new BufferedReader(new InputStreamReader(is));
//
//            String cmd = null;
//            System.out.println("Enter command (q to quite): ");
//            while ((cmd = br.readLine()) != null) {
//
//                if (cmd.equalsIgnoreCase("quit")) {
//                    break;
//                }
//                System.out.println("Line entered : " + cmd);
//                cli.execute(cmd.split(" "));
//                System.out.println("Enter command (q to quite): ");
//            }
//
//        }
//        catch (IOException ioe) {
//            System.out.println("Exception while reading input " + ioe);
//        }
//        finally {
//            // close the streams using close method
//            try {
//                if (br != null) {
//                    br.close();
//                }
//            }
//            catch (IOException ioe) {
//                System.out.println("Error while closing stream: " + ioe);
//            }
//
//        }


    }
}
