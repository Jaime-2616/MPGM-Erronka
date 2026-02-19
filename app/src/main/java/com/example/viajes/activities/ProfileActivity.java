package com.example.viajes.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.viajes.adapters.ReservationAdapter;
import com.example.viajes.database.AppDatabase;
import com.example.viajes.database.ReservationEntity;
import com.example.viajes.databinding.ActivityProfileBinding;
import com.example.viajes.domain.ReservationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityProfileBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            // Configuración del botón de volver (Atzera)
            if (binding.backBtn != null) {
                binding.backBtn.setOnClickListener(v -> finish());
            }
            
            initReservations();
        } catch (Exception e) {
            Log.e("ProfileActivity", "Error onCreate: " + e.getMessage());
            Toast.makeText(this, "Errorea profila kargatzean", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initReservations() {
        binding.progressBarProfile.setVisibility(View.VISIBLE);
        
        executorService.execute(() -> {
            try {
                List<ReservationEntity> entities = AppDatabase.getInstance(ProfileActivity.this)
                        .reservationDao().getAllReservations();
                
                ArrayList<ReservationModel> models = new ArrayList<>();
                for (ReservationEntity entity : entities) {
                    models.add(new ReservationModel(
                            entity.getHotelTitle(),
                            entity.getCheckInDate(),
                            entity.getCheckOutDate(),
                            entity.getTotalPrice(),
                            entity.getHotelImage()
                    ));
                }

                runOnUiThread(() -> {
                    binding.progressBarProfile.setVisibility(View.GONE);
                    if (!models.isEmpty()) {
                        binding.emptyTxt.setVisibility(View.GONE);
                        binding.reservationView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                        binding.reservationView.setAdapter(new ReservationAdapter(models));
                    } else {
                        binding.emptyTxt.setVisibility(View.VISIBLE);
                        binding.emptyTxt.setText("Ez daukazu erreserbarik oraindik");
                    }
                });
            } catch (Exception e) {
                Log.e("ProfileActivity", "Error loading reservations: " + e.getMessage());
                runOnUiThread(() -> {
                    binding.progressBarProfile.setVisibility(View.GONE);
                    binding.emptyTxt.setVisibility(View.VISIBLE);
                    binding.emptyTxt.setText("Errorea erreserbak kargatzean");
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
