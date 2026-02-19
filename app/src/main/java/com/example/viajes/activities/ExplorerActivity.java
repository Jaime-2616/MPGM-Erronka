package com.example.viajes.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.viajes.databinding.ActivityExplorerBinding;
import com.example.viajes.domain.ItemModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class ExplorerActivity extends AppCompatActivity {

    private ActivityExplorerBinding binding;
    private ArrayList<ItemModel> hotelList;
    private MapView map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // OSMDroid configuration
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        binding = ActivityExplorerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hotelList = (ArrayList<ItemModel>) getIntent().getSerializableExtra("hotels");
        if (hotelList == null) hotelList = new ArrayList<>();

        setupMapView();
        binding.backBtn.setOnClickListener(v -> finish());
    }

    private void setupMapView() {
        map = binding.map;
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        if (hotelList.isEmpty()) {
            map.getController().setCenter(new GeoPoint(40.4167, -3.7033)); // Centro de España
            map.getController().setZoom(6.0);
            return;
        }

        for (ItemModel hotel : hotelList) {
            if (hotel.getLat() != 0 && hotel.getLng() != 0) {
                Marker hotelMarker = new Marker(map);
                hotelMarker.setPosition(new GeoPoint(hotel.getLat(), hotel.getLng()));
                hotelMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                hotelMarker.setTitle(hotel.getTitle());
                hotelMarker.setRelatedObject(hotel);

                hotelMarker.setOnMarkerClickListener((marker, mapView) -> {
                    showHotelDetails((ItemModel) marker.getRelatedObject());
                    return true; // Indica que hemos gestionado el evento
                });

                map.getOverlays().add(hotelMarker);
            }
        }

        // Centrar el mapa en el primer hotel de la lista
        ItemModel firstHotel = hotelList.get(0);
        if (firstHotel.getLat() != 0 && firstHotel.getLng() != 0) {
            map.getController().setCenter(new GeoPoint(firstHotel.getLat(), firstHotel.getLng()));
            map.getController().setZoom(14.0);
        }
    }

    private void showHotelDetails(ItemModel hotel) {
        binding.hotelCard.setVisibility(View.VISIBLE);
        binding.hotelTitle.setText(hotel.getTitle());
        binding.hotelAddress.setText(hotel.getAddress());
        binding.hotelPrice.setText("$" + hotel.getPrice());

        if (hotel.getPic() != null && !hotel.getPic().isEmpty()) {
            Glide.with(this).load(hotel.getPic().get(0)).into(binding.hotelPic);
        } else {
            binding.hotelPic.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        binding.hotelCard.setOnClickListener(v -> {
            Intent intent = new Intent(ExplorerActivity.this, DetailActivity.class);
            intent.putExtra("object", hotel);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (map != null) map.onPause();
    }
}
