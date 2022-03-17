package com.example.app_crud;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminGeneroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminGeneroFragment extends Fragment {
    private RecyclerView rvlista;
    private ArrayList<Genero> listaGeneros;
    private AdaptadorAdminGeneros adaptador;
    Button btnAtras, btnAgregarGenero;
    private Controlador controlador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminGeneroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminGeneroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminGeneroFragment newInstance(String param1, String param2) {
        AdminGeneroFragment fragment = new AdminGeneroFragment();
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
        View vista = inflater.inflate(R.layout.fragment_admin_genero, container, false);
        listaGeneros = new ArrayList<>();
        controlador = new Controlador(getContext());
        listaGeneros = controlador.readAllGeneros();
        adaptador = new AdaptadorAdminGeneros(getActivity(),listaGeneros);
        rvlista = vista.findViewById(R.id.rvGenerosAdmin);
        btnAtras = vista.findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);

            }
        });
        rvlista.setAdapter(adaptador);
        rvlista.setLayoutManager(new GridLayoutManager(this.getContext(),1));


        btnAgregarGenero = vista.findViewById(R.id.btnAgregarGenero);
        btnAgregarGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View subView = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_genero, null);
                final EditText etNombreGenero;
                etNombreGenero = subView.findViewById(R.id.etDescGenero);

                AlertDialog.Builder ale =new  AlertDialog.Builder(getContext());
                ale.setTitle("Nuevo género");
                ale.setView(subView);
                ale.setCancelable(false);
                ale.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    Genero genero = new Genero(etNombreGenero.getText().toString());
                    long res = controlador.createGenero(genero);
                    if(res < 0){
                        Toast.makeText(getActivity().getApplicationContext(),"Error en el registro del género 😞", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(),"Guaradado correctamente 🥳 "+res, Toast.LENGTH_LONG).show();
                        refrescarListaDeGeneros();
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
        return vista;
    }

    public void refrescarListaDeGeneros() {

        System.out.println("100");
        if (adaptador == null) return;
        System.out.println("200");
        listaGeneros = controlador.readAllGeneros();
        adaptador.setLista(listaGeneros);
        adaptador.notifyDataSetChanged();
        System.out.println("300");
    }
}