package model;

//@JsonIgnoreProperties({"seat"})

public class Reservation {


    private long id;

    private User user;
    private long userId;

    private Seat seat;
    private long seatId;

    private Screening screening;
    private long screeningId;

    public Reservation() {
        super();
        seat = new Seat();
    }
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
    public static final Reservation newReservation(){ return new Reservation(); }
    public String getSeat() { return "Row: " + seat.getRowNumber() + ", Number: " + seat.getSeatNumber(); }
    public Seat getSeatObj() { return seat;}
    public String getSeatRow(){
        return String.valueOf(seat.getRowNumber());
    }
    public String getSeatNumber(){
        return String.valueOf(seat.getSeatNumber());
    }
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

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setSeatId(long seatId) {
        this.seatId = seatId;
    }

    public void setScreeningId(long screeningId) {
        this.screeningId = screeningId;
    }

    public long getUserId() {
        return userId;
    }

    public long getSeatId() {
        return seatId;
    }

    public long getScreeningId() {
        return screeningId;
    }
}

