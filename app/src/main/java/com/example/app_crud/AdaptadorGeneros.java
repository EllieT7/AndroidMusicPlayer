package com.example.app_crud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorGeneros extends RecyclerView.Adapter<AdaptadorGeneros.ViewDataHolder> {
    ArrayList<Genero> lista;

    public AdaptadorGeneros(ArrayList<Genero> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_genero, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataHolder holder, int position) {
        //holder.ivFoto.setImageResource(lista.get(position).getSrc());
        holder.tvGenero.setText(lista.get(position).getDescripcion());
        holder.tvCantidad.setText(lista.get(position).getCantidad()+"");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        TextView tvGenero,tvCantidad;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            tvGenero = itemView.findViewById(R.id.tvGenero);
            tvCantidad = itemView.findViewById(R.id.tvCantidadGenero);

        }
    }
}
