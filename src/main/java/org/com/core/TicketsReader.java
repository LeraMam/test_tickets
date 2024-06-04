package org.com.core;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class TicketsReader {
    public static void main(String[] args) {
        int meanPrice = 0;
        int medianPrice = 0;
        int subtraction = 0;
        Map<String, Integer> minimumTimeForCarrier;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileReader reader = new FileReader("json/tickets.json")) {
            JsonObject jsonData = new JsonParser().parse(reader).getAsJsonObject();
            JsonArray ticketsArray = jsonData.getAsJsonArray("tickets");
            ArrayList<Ticket> ticketList = gson.fromJson(ticketsArray, new TypeToken<ArrayList<Ticket>>(){}.getType());
            Collections.sort(ticketList, new ComparatorByTicketPrice());

            int centerNumber = ticketList.size()/2;
            for (Ticket ticket : ticketList) {
                meanPrice = meanPrice + ticket.getPrice();
            }
            if(ticketList.size() % 2 == 0){
                int element1 = ticketList.get(centerNumber - 1).getPrice();
                int element2 = ticketList.get(centerNumber).getPrice();
                medianPrice = (element1+ element2) / 2;
            }
            else medianPrice = ticketList.get(centerNumber).getPrice();
            meanPrice = meanPrice / ticketList.size();
            subtraction = meanPrice - medianPrice;
            minimumTimeForCarrier = TicketMapCreator.createMinimumMap(ticketList);

            System.out.println("Mean price for all tickets: " + meanPrice);
            System.out.println("Median price for all tickets: " + medianPrice);
            System.out.println("Subtraction for mean and median: " + subtraction);
            System.out.println("\nMinimum time between VVO and TLV: ");
            for (Map.Entry<String, Integer> entry : minimumTimeForCarrier.entrySet()) {
                String carrier = entry.getKey();
                int travelTime = entry.getValue();
                System.out.println("Carrier: " + carrier
                        + ", minimumTimeForCarrier travel time(in minutes): " + travelTime);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
