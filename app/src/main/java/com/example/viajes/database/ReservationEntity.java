package com.example.viajes.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservations")
public class ReservationEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String hotelTitle;
    private String checkInDate;
    private String checkOutDate;
    private int totalPrice;
    private String hotelImage;

    public ReservationEntity(String hotelTitle, String checkInDate, String checkOutDate, int totalPrice, String hotelImage) {
        this.hotelTitle = hotelTitle;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.hotelImage = hotelImage;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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
