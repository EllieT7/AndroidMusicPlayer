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

public class AdaptadorAdminVentas extends RecyclerView.Adapter<AdaptadorAdminVentas.ViewDataHolder> {

    ArrayList<Venta> lista;
    FragmentActivity fragment;

    public AdaptadorAdminVentas(FragmentActivity fragment, ArrayList<Venta> lista) {
        this.lista = lista;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AdaptadorAdminVentas.ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_admin_venta, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorAdminVentas.ViewDataHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvCI.setText("CI: "+lista.get(position).getCi());
        holder.tvNombre.setText("Nombre: "+lista.get(position).getCliente());
        holder.tvUbicacion.setText("Ubicacion: "+ lista.get(position).getUbicacion());
        holder.tvProducto.setText("Producto: "+lista.get(position).getAlbum().getNombre());
        holder.tvPrecio.setText("Precio: "+lista.get(position).getAlbum().getPrecio()+" Bs.");


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        TextView tvCI, tvPrecio, tvProducto, tvUbicacion, tvNombre;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            tvCI = itemView.findViewById(R.id.tvCi);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvPrecio = itemView.findViewById(R.id.tvImporte);
            tvNombre = itemView.findViewById(R.id.tvNombreCliente);

        }
    }
}

