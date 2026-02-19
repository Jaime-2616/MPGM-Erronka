package com.example.viajes.domain;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StayResponse {
    @SerializedName("data")
    private List<Hotel> data;

    public List<Hotel> getData() { return data; }

    public static class Hotel {
        @SerializedName("name")
        private String name;
        @SerializedName("price")
        private String price;
        @SerializedName("rating")
        private double rating;
        @SerializedName("address")
        private String address;
        @SerializedName("image")
        private String image;

        public String getName() { return name; }
        public String getPrice() { return price; }
        public double getRating() { return rating; }
        public String getAddress() { return address; }
        public String getImage() { return image; }
    }
}