package com.example.englishforkids_nirs;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.ViewHolder> {

    private Context context;
    private int[] animalImages;
    private String[] animalNames;
    private int[] animalSounds;


    public AnimalsAdapter(Context context, int[] animalImages, String[] animalNames, int[] animalSounds, SharedPreferences sharedPreferences) {
        this.context = context;
        this.animalImages = animalImages;
        this.animalNames = animalNames;
        this.animalSounds = animalSounds;
    
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageView.setImageResource(animalImages[position]);
        holder.textView.setText(animalNames[position]);


        holder.itemView.setOnClickListener(v -> {




            MediaPlayer mediaPlayer = MediaPlayer.create(context, animalSounds[position]);
            mediaPlayer.start();


            if (holder.textView.getVisibility() == View.GONE) {

                holder.textView.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.GONE);
            } else {

                holder.textView.setVisibility(View.GONE);
                holder.imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalNames.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.animalImage);
            textView = itemView.findViewById(R.id.animalText);
        }
    }
}
