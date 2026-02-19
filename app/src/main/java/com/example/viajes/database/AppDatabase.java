package com.example.viajes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ReservationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ReservationDao reservationDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "viajes_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Simplificado para este ejemplo, preferible usar hilos de fondo en producción
                    .build();
        }
        return instance;
    }
}
