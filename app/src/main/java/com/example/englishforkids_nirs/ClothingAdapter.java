package com.example.englishforkids_nirs;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ClothingViewHolder> {

    private Context context;
    private int[] images;
    private String[] names;
    private int[] sounds;

    public ClothingAdapter(Context context, int[] images, String[] names, int[] sounds) {
        this.context = context;
        this.images = images;
        this.names = names;
        this.sounds = sounds;
    }

    @NonNull
    @Override
    public ClothingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clothing, parent, false);
        return new ClothingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingViewHolder holder, int position) {
        holder.clothingImage.setImageResource(images[position]);
        holder.clothingText.setText(names[position]);

        holder.itemView.setOnClickListener(v -> {
            if (holder.clothingText.getVisibility() == View.GONE) {
                holder.clothingImage.setVisibility(View.GONE);
                holder.clothingText.setVisibility(View.VISIBLE);
            } else {
                holder.clothingImage.setVisibility(View.VISIBLE);
                holder.clothingText.setVisibility(View.GONE);
            }

            MediaPlayer mediaPlayer = MediaPlayer.create(context, sounds[position]);
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ClothingViewHolder extends RecyclerView.ViewHolder {
        ImageView clothingImage;
        TextView clothingText;

        public ClothingViewHolder(@NonNull View itemView) {
            super(itemView);
            clothingImage = itemView.findViewById(R.id.clothingImage);
            clothingText = itemView.findViewById(R.id.clothingText);
        }
    }
}
