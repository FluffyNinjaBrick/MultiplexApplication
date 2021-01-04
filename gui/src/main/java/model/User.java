package model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonIgnoreProperties({"enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities"})
public class User {


    private long id;



    private List<Map<String, String>> roles;

    private SimpleStringProperty firstName;

    private SimpleStringProperty lastName;

    private SimpleStringProperty email;

    private SimpleStringProperty username;

    private SimpleStringProperty password;

    private Set<Reservation> reservations;

    public User() {
        this(0);
    }
    public User(int id) {
        super();
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.username = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.id = id;
    }




    public User(String firstName, String lastName, String email, String userName) {
        this();
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.username = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty("");

    }

    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName.getValue(); }
    public ObservableStringValue getFirstNameObs() { return firstName; }
    public void setFirstName(String firstName) { this.firstName.set(firstName); }

    public String getLastName() { return lastName.getValue(); }
    public ObservableStringValue getLastNameObs() { return lastName;}
    public void setLastName(String lastName) { this.lastName.set(lastName); }

    public String getEmail() { return email.getValue(); }
    public ObservableStringValue getEmailObs() { return email;}
    public void setEmail(String email) { this.email.set(email); }

    public Set<Reservation> getReservations() { return reservations; }
    public void setReservations(Set<Reservation> reservations) { this.reservations = reservations; }

    public String getUsername() {return username.getValue();}
    public ObservableStringValue getUserNameObs() {return username;}
    public void setUsername(String username) { this.username.set(username); }

    public String getPassword() { return password.getValue(); }

    public void setPassword(String password) { this.password.set(password); }

    public List<Map<String, String>> getRoles() {
        return roles;
    }

    public void setRoles(List<Map<String, String>> roles) {
        this.roles = roles;
    }

    public static final User newUser(){
        return new User();
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
