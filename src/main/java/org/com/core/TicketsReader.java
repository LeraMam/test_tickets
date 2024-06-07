package org.com.core;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

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
        Map<String, Double> minimumTimeForCarrier;

        Gson gson = new GsonBuilder().setLenient().create();

        try (FileReader reader = new FileReader("json/tickets.json")) {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);

            JsonElement jsonElement = JsonParser.parseReader(jsonReader);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonData = jsonElement.getAsJsonObject();
                JsonArray ticketsArray = jsonData.getAsJsonArray("tickets");

                ArrayList<Ticket> ticketList = gson.fromJson(ticketsArray, new TypeToken<ArrayList<Ticket>>() {
                }.getType());
                Collections.sort(ticketList, new ComparatorByTicketPrice());

                int centerNumber = ticketList.size()/2;
                int count = 0;
                for (Ticket ticket : ticketList) {
                    if((ticket.getOriginName().equals("Владивосток")) && (ticket.getDestinationName().equals("Тель-Авив"))){
                        count++;
                        meanPrice = meanPrice + ticket.getPrice();
                    }
                }
                if(ticketList.size() % 2 == 0){
                    int element1 = ticketList.get(centerNumber - 1).getPrice();
                    int element2 = ticketList.get(centerNumber).getPrice();
                    medianPrice = (element1+ element2) / 2;
                }
                else medianPrice = ticketList.get(centerNumber).getPrice();
                meanPrice = meanPrice / count;
                subtraction = meanPrice - medianPrice;
                minimumTimeForCarrier = TicketMapCreator.createMinimumMap(ticketList);

                System.out.println("Mean price for all tickets: " + meanPrice);
                System.out.println("Median price for all tickets: " + medianPrice);
                System.out.println("Subtraction for mean and median: " + subtraction);
                System.out.println("\nMinimum time between VVO and TLV: ");
                for (Map.Entry<String, Double> entry : minimumTimeForCarrier.entrySet()) {
                    String carrier = entry.getKey();
                    Double travelTime = entry.getValue();
                    System.out.println("Carrier: " + carrier
                            + ", minimum travel time(in hours): " + String.format("%.3f",travelTime));
                }
            } else {
                System.out.println("The input is not a valid JSON object.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
