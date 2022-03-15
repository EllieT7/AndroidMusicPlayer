package com.example.app_crud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorAdminGeneros extends RecyclerView.Adapter<AdaptadorAdminGeneros.ViewDataHolder> {
    ArrayList<Genero> lista;

    public AdaptadorAdminGeneros(ArrayList<Genero> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_admin_genero, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataHolder holder, int position) {
        //holder.ivFoto.setImageResource(lista.get(position).getSrc());
        holder.tvGenero.setText(lista.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        TextView tvGenero;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            tvGenero = itemView.findViewById(R.id.tvGenero);

        }
    }
}
