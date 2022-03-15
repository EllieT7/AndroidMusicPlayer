package com.example.app_crud;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAgregarAlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAgregarAlbumFragment extends Fragment {
    Button btnAgregarCancion, btnAtras;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminAgregarAlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminAgregarAlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAgregarAlbumFragment newInstance(String param1, String param2) {
        AdminAgregarAlbumFragment fragment = new AdminAgregarAlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_admin_agregar_album, container, false);
        btnAgregarCancion = vista.findViewById(R.id.btnAgregarCancion);
        btnAgregarCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View subView = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_cancion, null);
                final EditText etNombre, etDuracion;
                etNombre = subView.findViewById(R.id.etNombreCancion);
                etDuracion = subView.findViewById(R.id.etDuracion);

                AlertDialog.Builder ale =new  AlertDialog.Builder(getContext());
                ale.setTitle("Nueva canción");
                ale.setView(subView);
                ale.setCancelable(false);
                ale.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    /*objProd.setNombreProducto(etNombre.getText().toString());
                    objProd.setCostoUnitario(Float.parseFloat(etCosto.getText().toString()));
                    long res = controlador.cambioProducto(objProd);
                    if(res < 0){
                        Toast.makeText(getApplicationContext(),"Error en el cambio", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"succes, exito en  e cambio "+res, Toast.LENGTH_LONG).show();
                        refrescarListaDeProductos();
                    }*/
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
        btnAtras = vista.findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);

            }
        });
        return vista;

    }
}