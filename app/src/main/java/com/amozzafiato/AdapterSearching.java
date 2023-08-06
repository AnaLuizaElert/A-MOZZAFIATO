package com.amozzafiato;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSearching extends RecyclerView.Adapter<ViewHolderSearching> {

    private ArrayList<Searching> itemList;

    public AdapterSearching(ArrayList<Searching> itemList) {
        this.itemList = itemList;
    }
    @Override
    public ViewHolderSearching onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_searching, parent, false);
        return new ViewHolderSearching(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderSearching holder, int position) {
        Searching item = itemList.get(position);
        holder.image.setImageResource(item.getImage());
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
