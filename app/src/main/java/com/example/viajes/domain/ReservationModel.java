package com.example.viajes.domain;

import java.io.Serializable;

public class ReservationModel implements Serializable {
    private String hotelTitle;
    private String checkInDate;
    private String checkOutDate;
    private int totalPrice;
    private String hotelImage;

    public ReservationModel() {
    }

    public ReservationModel(String hotelTitle, String checkInDate, String checkOutDate, int totalPrice, String hotelImage) {
        this.hotelTitle = hotelTitle;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.hotelImage = hotelImage;
    }

    public String getHotelTitle() { return hotelTitle; }
    public void setHotelTitle(String hotelTitle) { this.hotelTitle = hotelTitle; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public String getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public String getHotelImage() { return hotelImage; }
    public void setHotelImage(String hotelImage) { this.hotelImage = hotelImage; }
}