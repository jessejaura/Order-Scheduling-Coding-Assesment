import Models.FlightEntry;
import Services.FlightScheduleManager;
import Services.OrderManager;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        try {
            System.out.println("Please input CSV File name");
            String CSVFile = System.console().readLine();

            FlightScheduleManager flightScheduleManager = new FlightScheduleManager(CSVFile);

            flightScheduleManager.printFlightSchedule();

            System.out.println("Please input JSON File name");
            String JSONFile = System.console().readLine();

            OrderManager orderManager = new OrderManager(JSONFile);
            orderManager.printFlightItinerary(flightScheduleManager.getFlightSchedule());

            System.exit(0);
        } catch (RuntimeException e) {
            System.exit(-1);
        }
    }
}