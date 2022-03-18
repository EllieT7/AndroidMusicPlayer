package com.example.app_crud;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorAdminGeneros extends RecyclerView.Adapter<AdaptadorAdminGeneros.ViewDataHolder> {
    ArrayList<Genero> lista;
    FragmentActivity fragment;
    Controlador controlador;

    public AdaptadorAdminGeneros(FragmentActivity fragment,ArrayList<Genero> lista) {
        this.lista = lista;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_admin_genero, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.ivFoto.setImageResource(lista.get(position).getSrc());
        holder.tvGenero.setText(lista.get(position).getDescripcion());
        holder.btnEditarGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View subView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_add_genero, null);
                EditText etNombreGenero = subView.findViewById(R.id.etDescGenero);
                etNombreGenero.setText(lista.get(position).getDescripcion());
                controlador = new Controlador(view.getContext());
                AlertDialog.Builder ale =new  AlertDialog.Builder(view.getContext());
                ale.setTitle("Editar Genero");
                ale.setView(subView);
                ale.setCancelable(false);
                ale.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Genero genero = new Genero(lista.get(position).getIdGenero(),etNombreGenero.getText().toString());
                        long res = controlador.editarGenero(genero);

                        if(res < 0){
                            Toast.makeText(view.getContext(),"Error la edición del genero 😞", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(view.getContext(),"Género editado correctamente 🥳 "+res, Toast.LENGTH_LONG).show();
                            //refrescarListaDeArtistas();
                            fragment.getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, new AdminGeneroFragment()).commit();

                        }
                    }
                });

                ale.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                ale.show();
            }
        });
        holder.btnEliminarGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlador = new Controlador(view.getContext());
                int res = controlador.deleteGenero(lista.get(position).getIdGenero());
                if(res<0){
                    Toast.makeText(view.getContext(), "Error en la baja", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(view.getContext(), "succes, exito enla baja "+res, Toast.LENGTH_LONG).show();
                    fragment.getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, new AdminGeneroFragment()).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        TextView tvGenero;
        Button btnEditarGenero, btnEliminarGenero;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            tvGenero = itemView.findViewById(R.id.tvGenero);
            btnEditarGenero = itemView.findViewById(R.id.btnEditarGenero);
            btnEliminarGenero = itemView.findViewById(R.id.btnEliminarGenero);

        }
    }

    public ArrayList<Genero> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Genero> lista) {
        this.lista = lista;
    }
}
