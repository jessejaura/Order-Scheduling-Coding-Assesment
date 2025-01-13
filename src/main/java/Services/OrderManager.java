package Services;

import Models.FlightEntry;
import Models.OrderEntry;
import Models.ScheduledOrderEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;


public class OrderManager {
    private static String DESTINATION = "destination";
    public static int MAX_ORDERS_PER_PLANE = 20;

    private Map<String, List<OrderEntry>> ordersPerAirport;

    public OrderManager(String orderJSONFile) {
        this.ordersPerAirport = this.orderJSONToMap(orderJSONFile);
    }

    public void printFlightItinerary(List<FlightEntry> flightEntries) {
        List<ScheduledOrderEntry> scheduledOrderEntries = new ArrayList<>();
        for (FlightEntry flightEntry : flightEntries) {
            if (!ordersPerAirport.containsKey(flightEntry.getDestination())) continue;

            List<OrderEntry> orderEntries = ordersPerAirport.get(flightEntry.getDestination());

            for (int i = 0; i < orderEntries.size() && i < MAX_ORDERS_PER_PLANE; i++) {
                OrderEntry currOrderEntry = orderEntries.get(i);
                scheduledOrderEntries.add(new ScheduledOrderEntry(
                        currOrderEntry.getName(),
                        flightEntry.getFlightNumber(),
                        flightEntry.getSource(),
                        flightEntry.getDestination(),
                        flightEntry.getDay()
                ));
            }

            if (orderEntries.size() <= MAX_ORDERS_PER_PLANE) {
                ordersPerAirport.remove(flightEntry.getDestination());
            } else {
                ordersPerAirport.put(flightEntry.getDestination(), orderEntries.subList(MAX_ORDERS_PER_PLANE, orderEntries.size()));
            }
        }

        for (ScheduledOrderEntry scheduledOrderEntry : scheduledOrderEntries) {
            System.out.println("order:" + scheduledOrderEntry.getName()
                    + ", flightNumber: " + scheduledOrderEntry.getFlightNumber()
                    + ", departure: " + scheduledOrderEntry.getSource()
                    + ", arrival: " + scheduledOrderEntry.getDestination()
                    + ", day: " + scheduledOrderEntry.getDay()
            );
        }

        for (String airport : ordersPerAirport.keySet()) {
            for (OrderEntry orderEntry : ordersPerAirport.get(airport)) {
                System.out.println("order: " + orderEntry.getName() + ", flightNumber: not scheduled");
            }
        }
    }

    private Map<String, List<OrderEntry>> orderJSONToMap(String orderJSONFile) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(orderJSONFile))) {
            String currLine;

            while ((currLine = br.readLine()) != null) {
                stringBuilder.append(currLine).append("\n");
            }

            String orderJSON = stringBuilder.toString();

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode parentNode = objectMapper.readTree(orderJSON);

            Map<String, List<OrderEntry>> ordersPerAirport = new HashMap<>();
            Iterator<String> jsonIterator= parentNode.fieldNames();
            int i = 0;

            while (jsonIterator.hasNext()) {
                String orderName = jsonIterator.next();
                String destination = parentNode.get(orderName).get(DESTINATION).asText();
                if (!ordersPerAirport.containsKey(destination)) {
                    ordersPerAirport.put(destination, new ArrayList<>());
                }
                ordersPerAirport.get(destination).add(new OrderEntry(orderName, i));
                i++;
            }

            return ordersPerAirport;
        } catch (JsonProcessingException e) {
            System.err.println("Cannot parse JSON format");
            throw new RuntimeException(e);
        }  catch (IOException e) {
            System.err.println("Cannot read JSON file");
            throw new RuntimeException(e);
        }
    }
}
