package com.example.multiplex.model.persistence;

import javax.persistence.*;

@Entity
@Table(name = "Screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ticket_cost")
    private Integer ticketCost;

    //TODO - add foreign keys

    public Screening() { super(); }


}
