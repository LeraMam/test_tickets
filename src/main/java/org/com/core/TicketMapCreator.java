package org.com.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketMapCreator {
    public static Map<String, Integer> createMinimumMap(ArrayList<Ticket> ticketList) {
        Map<String, Integer> minimum = new HashMap<>();
        for (Ticket ticket : ticketList) {
            String carrierName = ticket.getCarrier();
            String[] departureTime = ticket.getDepartureTime().split(":");
            String[] arrivalTime = ticket.getArrivalTime().split(":");

            int departureHour = Integer.parseInt(departureTime[0]);
            int departureMinute = Integer.parseInt(departureTime[1]);
            int arrivalHour = Integer.parseInt(arrivalTime[0]);
            int arrivalMinute = Integer.parseInt(arrivalTime[1]);

            int travelHours = arrivalHour - departureHour;
            int travelMinutes = arrivalMinute - departureMinute;
            int travelTime = travelHours * 60 + travelMinutes;

            if (minimum.containsKey(carrierName)) {
                int currentMinTime = minimum.get(carrierName);
                if (travelTime < currentMinTime) {
                    minimum.put(carrierName, travelTime);
                }
            } else {
                minimum.put(carrierName, travelTime);
            }
        }
        return minimum;
    }
}
