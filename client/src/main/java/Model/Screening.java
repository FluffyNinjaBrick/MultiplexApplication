package Model;


import java.util.Date;
import java.util.Set;

public class Screening {

    private long id;

    private Integer ticketCost;

    private Date date;

    private Set<Reservation> reservations;

    private Movie movie;

    private ScreeningRoom screeningRoom;

    public Screening() { super(); }

    public Screening(Integer ticketCost) {
        this.ticketCost = ticketCost;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Integer getTicketCost() { return ticketCost; }
    public void setTicketCost(Integer ticketCost) { this.ticketCost = ticketCost; }
}
