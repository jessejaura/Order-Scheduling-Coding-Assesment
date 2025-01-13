package Models;

public class FlightEntry {
    private int flightNumber;
    private String source;
    private String destination;
    private int day;

    public FlightEntry(int flightNumber, String source, String destination, int day) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.day = day;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getDay() {
        return day;
    }
}
