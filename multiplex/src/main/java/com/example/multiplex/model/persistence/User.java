package com.example.multiplex.model.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    // ----------- db fields -----------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;


    // ----------- one to many -----------
    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;


    public User() {
        super();
    }

    public User(String firstName, String lastName, String email) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<Reservation> getReservations() { return reservations; }
    public void setReservations(Set<Reservation> reservations) { this.reservations = reservations; }
}
