package Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonMappingException;

//@JsonIgnoreProperties({"seat"})

public class Reservation {


    private long id;

    private User user;


    private Seat seat;


    private Screening screening;

    public Reservation() { super(); }
//    public Reservation(int id) {
//        super();
//        this.id = id;
//    }

    public Reservation(User user, Seat seat, Screening screening) {
        super();
        this.user = user;
        this.seat = seat;
        this.screening = screening;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUser() { return user.getId(); }
    public void setUser(User user) { this.user = user; }

    public String getSeat() { return "Row: " + seat.getRowNumber() + ", Number: " + seat.getSeatNumber(); }
//    public void setSeat(Seat seat) { this.seat = seat; }
    public void setSeat(String seatInput) {
        this.seat = new Seat();

        try {
            String[] props = seatInput.split(",");
            this.seat.setRowNumber(Integer.valueOf(props[0].split(":")[1].substring(1)));
            this.seat.setSeatNumber(Integer.valueOf(props[1].split(":")[1].substring(1)));
        }catch(NumberFormatException e){
            System.out.println("couldn't set proper seat number");
        }

    }

    public long getScreening() { return screening.getId(); }
    public void setScreening(Screening screening) { this.screening = screening; }
}

