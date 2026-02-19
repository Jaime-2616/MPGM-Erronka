package com.example.viajes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.viajes.R;
import com.example.viajes.adapters.PopularAdapter;
import com.example.viajes.api.ApiClient;
import com.example.viajes.api.ApiService;
import com.example.viajes.databinding.ActivityMainBinding;
import com.example.viajes.domain.ItemModel;
import com.example.viajes.domain.StayResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<ItemModel> currentHotelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        searchHotels("Donostia");

        binding.button.setOnClickListener(v -> {
            String city = binding.editTextText.getText().toString().trim();
            if (!city.isEmpty()) {
                searchHotels(city);
            } else {
                Toast.makeText(MainActivity.this, "Idatzi hiri bat, mesedez", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnReservas.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });

        binding.bottomNav.setItemSelected(R.id.etxea, true);
        binding.bottomNav.setOnItemSelectedListener(id -> {
            if (id == R.id.esploratzailea) {
                Intent intent = new Intent(MainActivity.this, ExplorerActivity.class);
                intent.putExtra("hotels", currentHotelList);
                startActivity(intent);
                binding.bottomNav.setItemSelected(R.id.etxea, true);
            } else if (id == R.id.profila) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                binding.bottomNav.setItemSelected(R.id.etxea, true);
            }
        });
    }

    private void searchHotels(String city) {
        binding.progressBarPopular.setVisibility(View.VISIBLE);
        ArrayList<ItemModel> fullList = new ArrayList<>();
        
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<StayResponse> call = apiService.getHotels("KEY_INVALIDA", city, "Spain");
        
        call.enqueue(new Callback<StayResponse>() {
            @Override
            public void onResponse(@NonNull Call<StayResponse> call, @NonNull Response<StayResponse> response) {
                addCustomHotels(fullList, city);
                displayHotels(fullList);
            }

            @Override
            public void onFailure(@NonNull Call<StayResponse> call, @NonNull Throwable t) {
                addCustomHotels(fullList, city);
                displayHotels(fullList);
            }
        });
    }

    private void displayHotels(ArrayList<ItemModel> list) {
        binding.progressBarPopular.setVisibility(View.GONE);
        currentHotelList = list;
        binding.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewPopular.setAdapter(new PopularAdapter(list));
    }

    private void addCustomHotels(ArrayList<ItemModel> list, String city) {
        String cityLower = city.toLowerCase();
        if (cityLower.contains("donostia") || cityLower.contains("sebastian")) {
            list.add(createHotel("Hotel Lasala Plaza", "Donostia", 250, 4.9, "https://images.unsplash.com/photo-1566073771259-6a8506099945", "Hotel elegante en el centro histórico.", 43.3228, -1.9846));
            list.add(createHotel("Hotel de Londres y de Inglaterra", "Donostia", 280, 4.8, "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb", "Clásico frente al mar.", 43.3168, -1.9854));
            list.add(createHotel("Room Mate Collection Gorka", "Donostia", 190, 4.7, "https://images.unsplash.com/photo-1571896349842-33c89424de2d", "Opción moderna y popular.", 43.3208, -1.9806));
            list.add(createHotel("Hotel Catalonia Donosti", "Donostia", 210, 4.6, "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4", "Buen hotel urbano.", 43.3155, -1.9801));
            list.add(createHotel("Hotel Arbaso", "Donostia", 300, 4.9, "https://images.unsplash.com/photo-1517840901100-8179e982ad91", "Hotel de diseño con estilo.", 43.3193, -1.9814));
        } else if (cityLower.contains("barcelona")) {
            list.add(createHotel("W Barcelona", "Barcelona", 400, 4.8, "https://images.unsplash.com/photo-1551882547-ff43c63faf76", "Icono moderno en la costa.", 41.3689, 2.1901));
            list.add(createHotel("Hotel Arts", "Barcelona", 450, 4.9, "https://images.unsplash.com/photo-1566073771259-6a8506099945", "Lujo y vistas al mar.", 41.3861, 2.1965));
            list.add(createHotel("Majestic Hotel", "Barcelona", 350, 4.7, "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4", "Elegancia clásica.", 41.3926, 2.1648));
            list.add(createHotel("Mandarin Oriental", "Barcelona", 500, 4.9, "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb", "Lujo máximo en Paseo de Gracia.", 41.3915, 2.1649));
            list.add(createHotel("Hotel 1898", "Barcelona", 280, 4.6, "https://images.unsplash.com/photo-1571896349842-33c89424de2d", "Estilo colonial en las Ramblas.", 41.3842, 2.1714));
        } else if (cityLower.contains("madrid")) {
            list.add(createHotel("Four Seasons Madrid", "Madrid", 600, 5.0, "https://images.unsplash.com/photo-1566073771259-6a8506099945", "El nuevo icono del lujo.", 40.4175, -3.7001));
            list.add(createHotel("Hotel Ritz", "Madrid", 550, 4.9, "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb", "Elegancia histórica.", 40.4152, -3.6926));
            list.add(createHotel("Riu Plaza España", "Madrid", 220, 4.7, "https://images.unsplash.com/photo-1571896349842-33c89424de2d", "Vistas increíbles de la ciudad.", 40.4241, -3.7121));
        } else if (cityLower.contains("bilbo") || cityLower.contains("bilbao")) {
            list.add(createHotel("Gran Hotel Domine", "Bilbao", 220, 4.8, "https://images.unsplash.com/photo-1566073771259-6a8506099945", "Frente al Guggenheim.", 43.2687, -2.9344));
            list.add(createHotel("Hotel Carlton", "Bilbao", 190, 4.7, "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb", "El hotel más clásico de Bilbao.", 43.2625, -2.9348));
        } else if (cityLower.contains("sevilla")) {
            list.add(createHotel("Hotel Alfonso XIII", "Sevilla", 350, 4.9, "https://images.unsplash.com/photo-1566073771259-6a8506099945", "Palacio histórico sevillano.", 37.3824, -5.9926));
            list.add(createHotel("Hotel Colón", "Sevilla", 210, 4.7, "https://images.unsplash.com/photo-1571896349842-33c89424de2d", "Diseño y tradición.", 37.3912, -5.9972));
        } else {
            list.add(createHotel("Meliá " + city, city, 150, 4.5, "https://images.unsplash.com/photo-1566073771259-6a8506099945", "Confort y calidad.", 40.4167, -3.7033));
            list.add(createHotel("NH " + city, city, 130, 4.3, "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb", "Céntrico y moderno.", 40.4180, -3.7050));
        }
    }

    private ItemModel createHotel(String name, String city, int price, double score, String pic, String desc, double lat, double lng) {
        ItemModel item = new ItemModel();
        item.setTitle(name);
        item.setAddress(city);
        item.setPrice(price);
        item.setScore(score);
        item.setBed(2);
        item.setDistance("1.0 km");
        item.setDuration("Gaua");
        item.setDescription(desc);
        item.setWifi(true);
        item.setLat(lat);
        item.setLng(lng);
        ArrayList<String> pics = new ArrayList<>();
        pics.add(pic);
        item.setPic(pics);
        return item;
    }
}
