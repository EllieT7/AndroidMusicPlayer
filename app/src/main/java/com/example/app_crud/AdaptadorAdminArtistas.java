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

public class AdaptadorAdminArtistas extends RecyclerView.Adapter<AdaptadorAdminArtistas.ViewDataHolder> {
    ArrayList<Artista> lista;
    Controlador controlador;
    FragmentActivity fragment;

    public AdaptadorAdminArtistas(FragmentActivity fragment, ArrayList<Artista> lista) {
        this.lista = lista;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_admin_artista, parent, false);
        return new ViewDataHolder(view);

        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvNombre.setText(lista.get(position).getNombre());
        holder.tvDescripcion.setText(lista.get(position).getDescripcion());
        holder.btnEditarArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View subView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_add_artista, null);
                final EditText etNombre, etDescripcion;
                etNombre = subView.findViewById(R.id.etAddNombreArtista);
                etDescripcion = subView.findViewById(R.id.etAddDescripcionArtista);
                etNombre.setText(lista.get(position).getNombre());
                etDescripcion.setText(lista.get(position).getDescripcion());
                controlador = new Controlador(view.getContext());
                AlertDialog.Builder ale =new  AlertDialog.Builder(view.getContext());
                ale.setTitle("Editar Artista");
                ale.setView(subView);
                ale.setCancelable(false);
                ale.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Artista artista = new Artista(lista.get(position).getIdArtista(),etNombre.getText().toString(), etDescripcion.getText().toString());
                        long res = controlador.editarArtista(artista);

                        if(res < 0){
                            Toast.makeText(view.getContext(),"Error en el registro del artista 😞", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(view.getContext(),"Guardado correctamente 🥳 "+res, Toast.LENGTH_LONG).show();
                            //refrescarListaDeArtistas();
                            fragment.getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, new AdminArtistaFragment()).commit();

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
        holder.btnEliminarArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlador = new Controlador(view.getContext());
                int res = controlador.deleteArtista(lista.get(position).getIdArtista());
                if(res<0){
                    Toast.makeText(view.getContext(), "Error en la baja", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(view.getContext(), "succes, exito enla baja "+res, Toast.LENGTH_LONG).show();
                    fragment.getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, new AdminArtistaFragment()).commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewDataHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion;
        Button btnEditarArtista, btnEliminarArtista;

        public ViewDataHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            btnEditarArtista = itemView.findViewById(R.id.btnEditarArtista);
            btnEliminarArtista = itemView.findViewById(R.id.btnEliminarArtista);


        }
    }

    public ArrayList<Artista> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Artista> lista) {
        this.lista = lista;
    }
}
