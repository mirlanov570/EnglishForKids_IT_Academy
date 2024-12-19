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

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorViewHolder> {

    private Context context;
    private int[] images;
    private String[] names;
    private int[] sounds;

    public ColorsAdapter(Context context, int[] images, String[] names, int[] sounds) {
        this.context = context;
        this.images = images;
        this.names = names;
        this.sounds = sounds;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        holder.colorImage.setImageResource(images[position]);
        holder.colorText.setText(names[position]);

        holder.itemView.setOnClickListener(v -> {
            if (holder.colorText.getVisibility() == View.GONE) {
                holder.colorImage.setVisibility(View.GONE);
                holder.colorText.setVisibility(View.VISIBLE);
            } else {
                holder.colorImage.setVisibility(View.VISIBLE);
                holder.colorText.setVisibility(View.GONE);
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

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        ImageView colorImage;
        TextView colorText;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            colorImage = itemView.findViewById(R.id.colorImage);
            colorText = itemView.findViewById(R.id.colorText);
        }
    }
}