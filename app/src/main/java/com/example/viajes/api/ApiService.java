package com.example.viajes.api;

import com.example.viajes.domain.StayResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("free-tools/hotel-search-and-review")
    Call<StayResponse> getHotels(
            @Query("location") String location,
            @Query("checkin") String checkIn,
            @Query("checkout") String checkOut
    );
}