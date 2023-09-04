package com.amozzafiato;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterFavorites extends RecyclerView.Adapter<ViewHolderFavorites> {

    private ArrayList<Favorite> itemList;

    public AdapterFavorites(ArrayList<Favorite> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ViewHolderFavorites onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorites, parent, false);
        return new ViewHolderFavorites(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderFavorites holder, @SuppressLint("RecyclerView") int position) {
        Favorite item = itemList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .into(holder.image);
        holder.name.setText(item.getNameCar());


//        holder.itemView.setOnClickListener(v -> {
//            // Obtém o carro clicado com base na posição
//            Favorite clickedCar = itemList.get(position);
//
//            // Obtém o contexto diretamente da visualização clicada
//            Context context = holder.itemView.getContext();
//
//            // Inicia a atividade PageCar e envia dados para ela
//            Intent intent = new Intent(context, PageCar.class);
//            intent.putExtra("carName", clickedCar.getNameCar());
//
//            context.startActivity(intent);
//        });
    }

    // Método para atualizar a lista de itens
    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(ArrayList<Favorite> newItems) {
        itemList.clear();
        itemList.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
