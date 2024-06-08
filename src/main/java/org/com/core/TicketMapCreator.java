package org.com.core;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketMapCreator {
    public static Map<String, Duration> createMinimumMap(ArrayList<Ticket> ticketList) throws IllegalArgumentException {
        Duration travelTime;
        Map<String, Duration> minTimeBetweenCarrier = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (Ticket ticket : ticketList) {
            String carrierName = ticket.getCarrier();
            if((ticket.getOriginName().equals("Владивосток")) && (ticket.getDestinationName().equals("Тель-Авив"))){
                LocalDate departureDate = LocalDate.parse(ticket.getDepartureDate(), dateFormatter);
                LocalDate arrivalDate = LocalDate.parse(ticket.getArrivalDate(), dateFormatter);
                LocalTime departureTime = LocalTime.parse(ticket.getDepartureTime(), timeFormatter);
                LocalTime arrivalTime = LocalTime.parse(ticket.getArrivalTime(), timeFormatter);
                LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);
                LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);
                if(departureDateTime.compareTo(arrivalDateTime) > 0) throw new IllegalArgumentException("Incorrect dates in tickets");
                travelTime = Duration.between(arrivalDateTime, departureDateTime);

                if (minTimeBetweenCarrier.containsKey(carrierName)) {
                    Duration currentMinTime = minTimeBetweenCarrier.get(carrierName);
                    if (travelTime.compareTo(currentMinTime) > 0) {
                        minTimeBetweenCarrier.put(carrierName, travelTime);
                    }
                } else {
                    minTimeBetweenCarrier.put(carrierName, travelTime);
                }
            }
        }
        return minTimeBetweenCarrier;
    }
}
