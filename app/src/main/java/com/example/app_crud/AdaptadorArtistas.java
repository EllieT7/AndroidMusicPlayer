package com.example.app_crud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorArtistas extends RecyclerView.Adapter<AdaptadorArtistas.ViewDataHolder> {
    ArrayList<Artista> lista;

    public AdaptadorArtistas(ArrayList<Artista> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_artista, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataHolder holder, int position) {
        //holder.ivFoto.setImageResource(lista.get(position).getSrc());
        holder.tvNombre.setText(lista.get(position).getNombre());
        holder.tvDescripcion.setText(lista.get(position).getDescripcion());
        holder.tvCantidad.setText(lista.get(position).getCantidad()+"");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion, tvCantidad;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);

        }
    }
}
