package com.amozzafiato;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderSearching extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name;
    public TextView price;

    public ViewHolderSearching(View itemView){
        super(itemView);
        image = itemView.findViewById(R.id.image);
        name = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
    }

}
