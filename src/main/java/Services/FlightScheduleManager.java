package Services;

import Models.FlightEntry;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlightScheduleManager {
    private List<FlightEntry> flightSchedule;

    public FlightScheduleManager(String flightDataCSV) {
        this.flightSchedule = loadFlightSchedule(flightDataCSV);
    }

    private List<FlightEntry> loadFlightSchedule(String flightDataCsv) {
        List<FlightEntry> flightEntries = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(flightDataCsv))) {
            // Skip csv headers line
            bufferedReader.readLine();

            String csvRow = bufferedReader.readLine();

            while (csvRow != null) {
                String[] values = csvRow.split(",");

                flightEntries.add(
                        new FlightEntry(
                            Integer.parseInt(values[0]),
                            values[1],
                            values[2],
                            Integer.parseInt(values[3])
                        ));

                csvRow = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println("Cannot read CSV File");
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Invalid CSV format");
            throw new RuntimeException(e);
        }

        return flightEntries;
    }

    public void printFlightSchedule() {
        for (FlightEntry flightEntry : this.flightSchedule) {
            System.out.println(
                    "Flight: " + flightEntry.getFlightNumber()
                            + " , departure: " + flightEntry.getSource()
                            + " , arrival: " + flightEntry.getDestination()
                            + " , day: " + flightEntry.getDay()
            );
        }
    }

    public List<FlightEntry> getFlightSchedule() {
        return flightSchedule;
    }
}
