package org.com.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket{
    private String origin;
    @SerializedName("origin_name")
    private String originName;
    private String destination;
    @SerializedName("destination_name")
    private String destinationName;
    @SerializedName("departure_date")
    private String departureDate;
    @SerializedName("departure_time")
    private String departureTime;
    @SerializedName("arrival_date")
    private String arrivalDate;
    @SerializedName("arrival_time")
    private String arrivalTime;
    private String carrier;
    private int stops;
    private int price;
}