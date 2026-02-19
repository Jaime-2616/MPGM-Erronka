package com.example.viajes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReservationDao {
    @Insert
    void insert(ReservationEntity reservation);

    @Query("SELECT * FROM reservations ORDER BY id DESC")
    List<ReservationEntity> getAllReservations();

    @Query("DELETE FROM reservations WHERE hotelTitle = :title AND checkInDate = :checkIn")
    void deleteByTitleAndDate(String title, String checkIn);
}
