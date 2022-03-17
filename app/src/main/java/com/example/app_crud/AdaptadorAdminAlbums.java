package com.example.app_crud;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorAdminAlbums extends RecyclerView.Adapter<AdaptadorAdminAlbums.ViewDataHolder> {

    ArrayList<Album> lista;
    FragmentActivity fragment;

    public AdaptadorAdminAlbums(FragmentActivity fragment, ArrayList<Album> lista) {
        this.lista = lista;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AdaptadorAdminAlbums.ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_admin_album, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorAdminAlbums.ViewDataHolder holder, @SuppressLint("RecyclerView") int position) {
        byte[] imagenBytesRecuperado = lista.get(position).getSrc();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytesRecuperado,0,imagenBytesRecuperado.length);
        holder.ivFoto.setImageBitmap(bitmap);
        holder.tvTitulo.setText(lista.get(position).getNombre());
        holder.tvArtista.setText(lista.get(position).getArtista().getNombre());
        holder.tvPrecio.setText(lista.get(position).getPrecio()+" $");
        holder.btnEditarAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Album albumSeleccionado = lista.get(position);
                bundle.putSerializable("album",albumSeleccionado);
                AdminEditarAlbumFragment nuevoFragmentEditar = new AdminEditarAlbumFragment();
                nuevoFragmentEditar.setArguments(bundle);
                fragment.getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, nuevoFragmentEditar).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvTitulo, tvPrecio, tvArtista;
        Button btnEliminarAlbum, btnEditarAlbum;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvArtista = itemView.findViewById(R.id.tvArtista);
            btnEditarAlbum = itemView.findViewById(R.id.btnEditarAlbum);
            btnEliminarAlbum = itemView.findViewById(R.id.btnAgregarAlbum);


        }
    }
}

