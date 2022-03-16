package com.example.app_crud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorAdminCanciones extends RecyclerView.Adapter<AdaptadorAdminCanciones.ViewDataHolder> {
    ArrayList<Cancion> lista;

    public AdaptadorAdminCanciones(ArrayList<Cancion> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_admin_cancion, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataHolder holder, int position) {
        //holder.ivFoto.setImageResource(lista.get(position).getSrc());
        holder.tvNombreCancion.setText(lista.get(position).getTitulo());
        holder.tvDuracionCancion.setText(lista.get(position).getDuracion());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCancion, tvDuracionCancion;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCancion = itemView.findViewById(R.id.tvNombreCancion);
            tvDuracionCancion = itemView.findViewById(R.id.tvDuracionCancion);

        }
    }

    public ArrayList<Cancion> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Cancion> lista) {
        this.lista = lista;
    }
}
