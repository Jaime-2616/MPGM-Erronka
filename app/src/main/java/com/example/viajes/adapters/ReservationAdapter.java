package com.example.viajes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.viajes.R;
import com.example.viajes.database.AppDatabase;
import com.example.viajes.domain.ReservationModel;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private ArrayList<ReservationModel> list;

    public ReservationAdapter(ArrayList<ReservationModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_reservation, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReservationModel reservation = list.get(position);
        holder.titleTxt.setText(reservation.getHotelTitle());
        holder.datesTxt.setText(reservation.getCheckInDate() + " -> " + reservation.getCheckOutDate());
        holder.priceTxt.setText("$" + reservation.getTotalPrice());

        Glide.with(holder.itemView.getContext())
                .load(reservation.getHotelImage())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.pic);

        holder.cancelBtn.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase.getInstance(holder.itemView.getContext())
                        .reservationDao()
                        .deleteByTitleAndDate(reservation.getHotelTitle(), reservation.getCheckInDate());
                
                holder.itemView.post(() -> {
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size());
                    Toast.makeText(holder.itemView.getContext(), "Erreserba bertan behera utzi da", Toast.LENGTH_SHORT).show();
                });
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, datesTxt, priceTxt;
        ImageView pic;
        Button cancelBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.hotelTitleTxt);
            datesTxt = itemView.findViewById(R.id.datesTxt);
            priceTxt = itemView.findViewById(R.id.totalPriceTxt);
            pic = itemView.findViewById(R.id.hotelPic);
            cancelBtn = itemView.findViewById(R.id.cancelBtn);
        }
    }
}
