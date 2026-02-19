package com.example.viajes.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.viajes.databinding.ViewholderPicListBinding;

import java.util.List;

public class PicListAdapter extends RecyclerView.Adapter<PicListAdapter.ViewHolder> {
    private List<String> items;
    private ImageView picMain;
    private Context context;

    public PicListAdapter(ImageView picMain, List<String> items) {
        this.picMain = picMain;
        this.items = items;
    }

    @NonNull
    @Override
    public PicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderPicListBinding binding = ViewholderPicListBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PicListAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(items.get(position))
                .into(holder.binding.picList);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(items.get(position))
                        .into(picMain);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class  ViewHolder extends RecyclerView.ViewHolder {
ViewholderPicListBinding binding;
        public ViewHolder(ViewholderPicListBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
