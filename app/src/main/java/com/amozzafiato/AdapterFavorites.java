package com.amozzafiato;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterFavorites extends RecyclerView.Adapter<ViewHolderFavorites> {

    private ArrayList<Favorite> itemList;

    public AdapterFavorites(ArrayList<Favorite> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ViewHolderFavorites onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolderFavorites(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderFavorites holder, int position) {
        Favorite item = itemList.get(position);
        holder.name.setText(item.getNameCar());
        holder.image.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
