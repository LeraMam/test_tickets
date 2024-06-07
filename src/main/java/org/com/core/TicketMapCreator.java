package org.com.core;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketMapCreator {
    public static Map<String, Double> createMinimumMap(ArrayList<Ticket> ticketList) {
        Period period;
        Duration duration;
        Double travelTime;
        Map<String, Double> minimum = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (Ticket ticket : ticketList) {
            String carrierName = ticket.getCarrier();
            if((ticket.getOriginName().equals("Владивосток")) && (ticket.getDestinationName().equals("Тель-Авив"))){
                LocalDate departureDate = LocalDate.parse(ticket.getDepartureDate(), dateFormatter);
                LocalDate arrivalDate = LocalDate.parse(ticket.getArrivalDate(), dateFormatter);
                LocalTime departureTime = LocalTime.parse(ticket.getDepartureTime(), timeFormatter);
                LocalTime arrivalTime = LocalTime.parse(ticket.getArrivalTime(), timeFormatter);

                if(departureDate.equals(arrivalDate)){
                    duration = Duration.between(departureTime, arrivalTime);
                    travelTime = duration.getSeconds() / 3600.0;
                }
                else {
                    period = Period.between(departureDate, arrivalDate);
                    Double years = period.getYears()*8765.82; //8765.82 is average number of hours in 1 year
                    Double month = period.getMonths()*730.485; //730.485 is average number of hours in 1 month
                    Double days = period.getDays() * 24.0;
                    duration = Duration.between(departureTime, arrivalTime);
                    Double time = duration.getSeconds() / 3690.0;
                    travelTime = years + month + days + time;
                }

                if (minimum.containsKey(carrierName)) {
                    Double currentMinTime = minimum.get(carrierName);
                    if (travelTime < currentMinTime) {
                        minimum.put(carrierName, travelTime);
                    }
                } else {
                    minimum.put(carrierName, travelTime);
                }
            }
        }
        return minimum;
    }
}
