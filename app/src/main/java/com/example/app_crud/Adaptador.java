package com.example.app_crud;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewDataHolder> {

    ArrayList<Album> lista;

    public Adaptador(ArrayList<Album> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public Adaptador.ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewDataHolder holder, int position) {
        byte[] imagenBytesRecuperado = lista.get(position).getSrc();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytesRecuperado,0,imagenBytesRecuperado.length);
        holder.ivFoto.setImageBitmap(bitmap);
        holder.tvTitulo.setText(lista.get(position).getNombre());
        holder.tvArtista.setText(lista.get(position).getArtista().getNombre());
        holder.tvPrecio.setText(lista.get(position).getPrecio()+" $");

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvTitulo, tvPrecio, tvArtista;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvArtista = itemView.findViewById(R.id.tvArtista);

        }
    }
}

