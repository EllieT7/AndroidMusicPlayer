package com.example.app_crud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorAdminArtistas extends RecyclerView.Adapter<AdaptadorAdminArtistas.ViewDataHolder> {
    ArrayList<Artista> lista;

    public AdaptadorAdminArtistas(ArrayList<Artista> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_admin_artista, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataHolder holder, int position) {
        //holder.ivFoto.setImageResource(lista.get(position).getSrc());
        holder.tvNombre.setText(lista.get(position).getNombre());
        holder.tvDescripcion.setText(lista.get(position).getDescripcion());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);


        }
    }

    public ArrayList<Artista> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Artista> lista) {
        this.lista = lista;
    }
}
