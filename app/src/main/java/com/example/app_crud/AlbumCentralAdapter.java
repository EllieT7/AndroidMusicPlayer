package com.example.app_crud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderAdapter;

import java.util.ArrayList;

public class AlbumCentralAdapter extends CardSliderAdapter<AlbumCentralAdapter.AlbumViewHolder> {

    private ArrayList<Album> lista;

    public AlbumCentralAdapter(ArrayList<Album> movies){
        this.lista = movies;
    }

    @Override
    public int getItemCount(){
        return lista.size();
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item2, parent, false);
        return new AlbumViewHolder(view);
    }


    @Override
    public void bindVH(AlbumViewHolder holder, int position) {
        holder.tvTitulo.setText(lista.get(position).getNombre());
        holder.tvArtista.setText(lista.get(position).getArtista().getNombre());
        holder.tvPrecio.setText(lista.get(position).getPrecio()+" $");
        holder.tvDescripcion.setText(lista.get(position).getArtista().getDescripcion());
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvTitulo,tvDescripcion, tvArtista;
        Button  tvPrecio;
        public AlbumViewHolder(View view){
            super(view);
            //ivFoto = itemView.findViewById(R.id.ivFoto);
            tvTitulo = itemView.findViewById(R.id.tvAlbumCentro);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvArtista = itemView.findViewById(R.id.tvArtistaCentro);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionCentro);
        }
    }
}