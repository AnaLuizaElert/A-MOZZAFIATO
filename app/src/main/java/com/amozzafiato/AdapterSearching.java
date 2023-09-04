package com.amozzafiato;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.amozzafiato.pages.PageCar;
import com.bumptech.glide.Glide;

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
    public void onBindViewHolder(ViewHolderSearching holder, @SuppressLint("RecyclerView") int position) {
        Searching item = itemList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .into(holder.image);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());


        holder.itemView.setOnClickListener(v -> {
            // Obtém o carro clicado com base na posição
            Searching clickedCar = itemList.get(position);

            // Obtém o contexto diretamente da visualização clicada
            Context context = holder.itemView.getContext();

            // Inicia a atividade PageCar e envia dados para ela
            Intent intent = new Intent(context, PageCar.class);
            intent.putExtra("carName", clickedCar.getName());

            context.startActivity(intent);
        });


    }


    // Método para atualizar a lista de itens
    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(ArrayList<Searching> newItems) {
        itemList.clear();
        itemList.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
