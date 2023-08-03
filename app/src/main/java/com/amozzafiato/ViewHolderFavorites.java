package com.amozzafiato;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderFavorites extends RecyclerView.ViewHolder {


        public TextView name;
        public ImageView image;

        public ViewHolderFavorites(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
        }
}
