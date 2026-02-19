package com.example.viajes.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.viajes.adapters.PicListAdapter;
import com.example.viajes.database.AppDatabase;
import com.example.viajes.database.ReservationEntity;
import com.example.viajes.databinding.ActivityDetailBinding;
import com.example.viajes.domain.ItemModel;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private ItemModel object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        object = (ItemModel) getIntent().getSerializableExtra("object");

        initList();
        setVariable();
    }

    private void setVariable() {
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$" + object.getPrice());
        binding.bedTxt.setText(object.getBed() + " Ohea");
        binding.descriptionTxt.setText(object.getDescription());
        binding.addressTxt.setText(object.getAddress());
        binding.ratingTxt.setText(object.getScore() + " rating");
        binding.ratingBar.setRating((float) object.getScore());

        binding.backBtn.setOnClickListener(v -> finish());

        if (object.isGuide()) {
            binding.guideTxt.setText("Gidari");
        } else {
            binding.guideTxt.setText("Gidarik ez");
        }

        if (object.isWifi()) {
            binding.wifiTxt.setText("Wifi");
        } else {
            binding.wifiTxt.setText("No-Wifi");
        }

        binding.addressTxt.setOnClickListener(v -> {
            String url = "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(object.getAddress());
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        binding.reserveTxt.setOnClickListener(v -> openDatePicker());
    }

    private void openDatePicker() {
        MaterialDatePicker.Builder<androidx.core.util.Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Hautatu egunak");
        MaterialDatePicker<androidx.core.util.Pair<Long, Long>> picker = builder.build();

        picker.show(getSupportFragmentManager(), "DATE_PICKER");

        picker.addOnPositiveButtonClickListener(selection -> {
            String checkIn = formatDate(selection.first);
            String checkOut = formatDate(selection.second);
            saveReservationLocal(checkIn, checkOut);
        });
    }

    private String formatDate(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date(date));
    }

    private void saveReservationLocal(String checkIn, String checkOut) {
        ReservationEntity reservation = new ReservationEntity(
                object.getTitle(),
                checkIn,
                checkOut,
                object.getPrice(),
                (object.getPic() != null && !object.getPic().isEmpty()) ? object.getPic().get(0) : ""
        );

        // Usando Room en el hilo principal por simplicidad según configuración en AppDatabase
        AppDatabase.getInstance(this).reservationDao().insert(reservation);
        
        Toast.makeText(this, "Erreserba ondo gorde da local-ean!", Toast.LENGTH_SHORT).show();
        finish(); // Volver atrás tras reservar
    }

    private void initList() {
        ArrayList<String> picList = new ArrayList<>(object.getPic());
        Glide.with(this).load(picList.get(0)).into(binding.pic);
        binding.picList.setAdapter(new PicListAdapter(binding.pic, picList));
        binding.picList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}