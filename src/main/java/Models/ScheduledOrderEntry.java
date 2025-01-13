package Models;

public class ScheduledOrderEntry {
    private String name;
    private int flightNumber;
    private String source;
    private String destination;
    private int day;

    public ScheduledOrderEntry(String name, int flightNumber, String source, String destination, int day) {
        this.name = name;
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.day = day;
    }

    public String getName() {
        return name;
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
